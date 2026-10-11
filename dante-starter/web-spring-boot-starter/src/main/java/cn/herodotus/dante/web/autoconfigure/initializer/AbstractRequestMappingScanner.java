/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 是 Dante Cloud 系统核心组件库，采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dante.web.autoconfigure.initializer;

import cn.herodotus.dante.core.constant.SymbolConstants;
import cn.herodotus.dante.core.domain.Scan;
import cn.herodotus.dante.messaging.definition.AbstractMappingAttributeScanner;
import cn.herodotus.dante.messaging.domain.AttributeCollector;
import cn.herodotus.dante.messaging.domain.MappingAttribute;
import cn.herodotus.dante.messaging.strategy.AttributeCollectionEventManager;
import cn.herodotus.dante.spring.enums.MappingCategory;
import cn.herodotus.dante.spring.event.ApplicationStrategyEventManager;
import cn.hutool.v7.crypto.SecureUtil;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;

/**
 * <p>Description: RequestMapping 扫描器抽象定义 </p>
 * <p>
 * 提取 RequestMapping 扫描共性内容，方便维护
 *
 * @author : gengwei.zheng
 * @date : 2024/1/31 23:38
 */
public abstract class AbstractRequestMappingScanner extends AbstractMappingAttributeScanner {

    private static final Logger log = LoggerFactory.getLogger(AbstractRequestMappingScanner.class);

    protected AbstractRequestMappingScanner(Scan scan, AttributeCollectionEventManager attributeCollectionEventManager) {
        super(scan, attributeCollectionEventManager);
    }

    /**
     * 检测RequestMapping是否需要被排除
     *
     * @param handlerMethod HandlerMethod
     * @return boolean
     */
    protected boolean isExcludedRequestMapping(HandlerMethod handlerMethod) {
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
    protected boolean isSpringAnnotationMatched(HandlerMethod handlerMethod) {
        return handlerMethod.getMethod().getDeclaringClass().getAnnotation(RestController.class) != null;
    }

    /**
     * 有ApiIgnore注解的方法不扫描, 没有ApiOperation注解不扫描
     *
     * @param handlerMethod HandlerMethod
     * @return boolean
     */
    protected boolean isSwaggerAnnotationMatched(HandlerMethod handlerMethod) {
        if (handlerMethod.getMethodAnnotation(Hidden.class) != null) {
            return false;
        }

        Operation operation = handlerMethod.getMethodAnnotation(Operation.class);
        return ObjectUtils.isNotEmpty(operation) && !operation.hidden();
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
    protected String createCode(String url, String requestMethods) {
        String[] search = new String[]{SymbolConstants.OPEN_CURLY_BRACE, SymbolConstants.CLOSE_CURLY_BRACE, SymbolConstants.FORWARD_SLASH};
        String[] replacement = new String[]{SymbolConstants.BLANK, SymbolConstants.BLANK, SymbolConstants.COLON};
        String code = StringUtils.replaceEach(url, search, replacement);

        String result = StringUtils.isNotBlank(requestMethods) ? StringUtils.lowerCase(requestMethods) + code : Strings.CS.removeStart(code, SymbolConstants.COLON);
        log.trace("[Herodotus] |- Create code [{}] for Request [{}] : [{}]", result, requestMethods, url);
        return result;
    }

    /**
     * 判断当前环境是否符合扫描的条件设定
     *
     * @return 是否执行扫描
     */
    protected boolean notExecuteScanning() {
        ApplicationStrategyEventManager<AttributeCollector> manager = getStrategyEventManager();
        if (ObjectUtils.isNotEmpty(manager) && manager instanceof AttributeCollectionEventManager collectionEventManager) {
            return collectionEventManager.isPerformScan();
        }

        return true;
    }

    /**
     * 生成 RestMapping Id。根据服务ID、请求方法，URL 以及版本生成 ID
     *
     * @param serviceId      服务ID
     * @param requestMethods 请求方法
     * @param urls           请求 URL
     * @param version        请求版本
     * @return id
     */
    private String createId(String serviceId, String requestMethods, String urls, String version) {
        String flag = serviceId + SymbolConstants.DASH + requestMethods + SymbolConstants.DASH + urls;
        if (StringUtils.isNotEmpty(version)) {
            flag = flag + SymbolConstants.DASH + version;
        }

        return SecureUtil.md5(flag);
    }

    /**
     * 将接口相关信息，转换为系统统一定义的 {@link MappingAttribute} 对象
     *
     * @param requestMethods 请求方法
     * @param urls           请求 URL
     * @param version        请求版本
     * @param method         接口对应的方法对象 {@link HandlerMethod}
     * @return 封装好的对象 {@link MappingAttribute}
     */
    protected MappingAttribute buildMappingAttribute(String requestMethods, String urls, String version, HandlerMethod method) {

        String serviceId = getServiceId();
        // 1. 获取类名
        // method.getMethod().getDeclaringClass().getName() 取到的是注解实际所在类的名字，比如注解在父类叫BaseController，那么拿到的就是BaseController
        // method.getBeanType().getName() 取到的是注解实际Bean的名字，比如注解在在父类叫BaseController，而实际类是SysUserController，那么拿到的就是SysUserController
        String className = method.getBeanType().getName();

        // 2. 检测该类是否在GroupIds列表中
        if (isIllegalGroupId(className)) {
            return null;
        }

        // 3. 获取RequestMapping注解对应的方法名
        String methodName = method.getMethod().getName();

        // 4. 生成 ID
        String id = createId(serviceId, requestMethods, urls, version);

        MappingAttribute mappingAttribute = new MappingAttribute();
        mappingAttribute.setId(id);
        mappingAttribute.setCode(createCode(urls, requestMethods));
        mappingAttribute.setServiceId(serviceId);
        Operation apiOperation = method.getMethodAnnotation(Operation.class);
        if (ObjectUtils.isNotEmpty(apiOperation)) {
            mappingAttribute.setDescription(apiOperation.summary());
        }
        mappingAttribute.setRequestMethod(requestMethods);
        mappingAttribute.setUrl(urls);
        mappingAttribute.setClassName(className);
        mappingAttribute.setMethodName(methodName);
        mappingAttribute.setVersion(version);
        mappingAttribute.setCategory(MappingCategory.REST.getValue());
        return mappingAttribute;
    }
}
