/*
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君) (herodotus@aliyun.com & www.herodotus.cn)
 *
 * Dante Engine licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.gnu.org/licenses/lgpl-3.0.html>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.herodotus.engine.rest.service.processor;

import cn.herodotus.engine.assistant.definition.constants.SymbolConstants;
import cn.herodotus.engine.message.core.logic.strategy.RequestMappingScanEventManager;
import cn.herodotus.engine.message.core.logic.domain.RequestMapping;
import cn.herodotus.engine.rest.condition.constants.RestPropertyFinder;
import cn.herodotus.engine.rest.condition.properties.ScanProperties;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.crypto.SecureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Description: RequestMapping扫描器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/6/2 19:52
 */
public class RequestMappingScanner implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(RequestMappingScanner.class);

    private final ScanProperties restProperties;
    private final RequestMappingScanEventManager requestMappingScanStrategyEvent;

    public RequestMappingScanner(ScanProperties restProperties, RequestMappingScanEventManager requestMappingScanStrategyEvent) {
        this.restProperties = restProperties;
        this.requestMappingScanStrategyEvent = requestMappingScanStrategyEvent;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        ApplicationContext applicationContext = event.getApplicationContext();

        log.debug("[Herodotus] |- [1] Application is READY, start to scan request mapping!");
        onApplicationEvent(applicationContext);
    }

    public void onApplicationEvent(ApplicationContext applicationContext) {
        // 1、获取服务ID：该服务ID对于微服务是必需的。
        String serviceId = RestPropertyFinder.getApplicationName(applicationContext);

        // 2、只针对有EnableResourceServer注解的微服务进行扫描。如果变为单体架构目前不会用到EnableResourceServer所以增加的了一个Architecture判断
        if (!requestMappingScanStrategyEvent.isPerformScan()) {
            // 只扫描资源服务器
            log.warn("[Herodotus] |- Can not found scan annotation in Service [{}], Skip!", serviceId);
            return;
        }

        // 3、获取所有接口映射
        Map<String, RequestMappingHandlerMapping> mappings = applicationContext.getBeansOfType(RequestMappingHandlerMapping.class);

        // 4、 获取url与类和方法的对应信息
        List<RequestMapping> resources = new ArrayList<>();
        for (RequestMappingHandlerMapping mapping : mappings.values()) {
            Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();
            if (MapUtils.isNotEmpty(handlerMethods)) {
                for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
                    RequestMappingInfo requestMappingInfo = entry.getKey();
                    HandlerMethod handlerMethod = entry.getValue();

                    // 4.1、如果是被排除的requestMapping，那么就进行不扫描
                    if (isExcludedRequestMapping(handlerMethod)) {
                        continue;
                    }

                    // 4.2、拼装扫描信息
                    RequestMapping requestMapping = createRequestMapping(serviceId, requestMappingInfo, handlerMethod);
                    if (ObjectUtils.isEmpty(requestMapping)) {
                        continue;
                    }

                    resources.add(requestMapping);
                }
            }
        }

        if (CollectionUtils.isNotEmpty(resources)) {
            log.debug("[Herodotus] |- [2] Request mapping scan found [{}] resources in service [{}], go to next stage!", serviceId, resources.size());
            requestMappingScanStrategyEvent.postProcess(resources);
        } else {
            log.debug("[Herodotus] |- [2] Request mapping scan can not find any resources in service [{}]!", serviceId);
        }

        log.info("[Herodotus] |- Request Mapping Scan for Service: [{}] FINISHED!", serviceId);
    }

    /**
     * 检测RequestMapping是否需要被排除
     *
     * @param handlerMethod HandlerMethod
     * @return boolean
     */
    private boolean isExcludedRequestMapping(HandlerMethod handlerMethod) {
        if (!isSpringAnnotationMatched(handlerMethod)) {
            return true;
        }

        return !isSwaggerAnnotationMatched(handlerMethod);
    }

    /**
     * 如果开启isJustScanRestController，那么就只扫描RestController
     *
     * @param handlerMethod HandlerMethod
     * @return boolean
     */
    private boolean isSpringAnnotationMatched(HandlerMethod handlerMethod) {
        if (restProperties.isJustScanRestController()) {
            return handlerMethod.getMethod().getDeclaringClass().getAnnotation(RestController.class) != null;
        }

        return true;
    }

    /**
     * 有ApiIgnore注解的方法不扫描, 没有ApiOperation注解不扫描
     *
     * @param handlerMethod HandlerMethod
     * @return boolean
     */
    private boolean isSwaggerAnnotationMatched(HandlerMethod handlerMethod) {
        if (handlerMethod.getMethodAnnotation(Hidden.class) != null) {
            return false;
        }

        Operation operation = handlerMethod.getMethodAnnotation(Operation.class);
        return ObjectUtils.isNotEmpty(operation) && !operation.hidden();
    }

    /**
     * 如果当前class的groupId在GroupId列表中，那么就进行扫描，否则就排除
     *
     * @param className 当前扫描的controller类名
     * @return Boolean
     */
    private boolean isLegalGroup(String className) {
        if (StringUtils.isNotEmpty(className)) {
            List<String> groupIds = restProperties.getScanGroupIds();
            List<String> result = groupIds.stream().filter(groupId -> StringUtils.contains(className, groupId)).collect(Collectors.toList());
            return !CollectionUtils.sizeIsEmpty(result);
        } else {
            return false;
        }
    }

    /**
     * 根据 url 和 method 生成与当前 url 对应的 code。
     * <p>
     * 例如：
     * 1. POST /element 生成为 post:element
     * 2. /open/identity/session 生成为 open:identity:session
     *
     * @param url            请求 url
     * @param requestMethods 请求 method。
     * @return url 对应的 code
     */
    private String createCode(String url, String requestMethods) {
        String[] search = new String[]{SymbolConstants.OPEN_CURLY_BRACE, SymbolConstants.CLOSE_CURLY_BRACE, SymbolConstants.FORWARD_SLASH};
        String[] replacement = new String[]{SymbolConstants.BLANK, SymbolConstants.BLANK, SymbolConstants.COLON};
        String code = StringUtils.replaceEach(url, search, replacement);

        String result = StringUtils.isNotBlank(requestMethods) ? StringUtils.lowerCase(requestMethods) + code : StringUtils.removeStart(code, SymbolConstants.COLON);
        log.trace("[Herodotus] |- Create code [{}] for Request [{}] : [{}]", result, requestMethods, url);
        return result;
    }

    private RequestMapping createRequestMapping(String serviceId, RequestMappingInfo info, HandlerMethod method) {
        // 4.2.1、获取类名
        // method.getMethod().getDeclaringClass().getName() 取到的是注解实际所在类的名字，比如注解在父类叫BaseController，那么拿到的就是BaseController
        // method.getBeanType().getName() 取到的是注解实际Bean的名字，比如注解在在父类叫BaseController，而实际类是SysUserController，那么拿到的就是SysUserController
        String className = method.getBeanType().getName();

        // 4.2.2、检测该类是否在GroupIds列表中
        if (!isLegalGroup(className)) {
            return null;
        }

        // 5.2.3、获取不包含包路径的类名
        String classSimpleName = method.getBeanType().getSimpleName();

        // 4.2.4、获取RequestMapping注解对应的方法名
        String methodName = method.getMethod().getName();

        // 5.2.5、获取注解对应的请求类型
        RequestMethodsRequestCondition requestMethodsRequestCondition = info.getMethodsCondition();
        String requestMethods = StringUtils.join(requestMethodsRequestCondition.getMethods(), SymbolConstants.COMMA);

        // 5.2.6、获取主机对应的请求路径
        PathPatternsRequestCondition pathPatternsCondition = info.getPathPatternsCondition();
        Set<String> patternValues = pathPatternsCondition.getPatternValues();
        if (CollectionUtils.isEmpty(patternValues)) {
            return null;
        }

        String urls = StringUtils.join(patternValues, SymbolConstants.COMMA);

        // 5.2.8、根据serviceId, requestMethods, urls生成的MD5值，作为自定义主键
        String flag = serviceId + SymbolConstants.DASH + requestMethods + SymbolConstants.DASH + urls;
        String id = SecureUtil.md5(flag);

        // 5.2.9、组装对象
        RequestMapping requestMapping = new RequestMapping();
        requestMapping.setMappingId(id);
        requestMapping.setMappingCode(createCode(urls, requestMethods));
        requestMapping.setServiceId(serviceId);
        Operation apiOperation = method.getMethodAnnotation(Operation.class);
        if (ObjectUtils.isNotEmpty(apiOperation)) {
            requestMapping.setDescription(apiOperation.summary());
        }
        requestMapping.setRequestMethod(requestMethods);
        requestMapping.setUrl(urls);
        requestMapping.setClassName(className);
        requestMapping.setMethodName(methodName);
        return requestMapping;
    }
}
