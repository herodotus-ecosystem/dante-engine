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

package cn.herodotus.dante.webmvc.autoconfigure.initializer;

import cn.herodotus.dante.core.constant.SymbolConstants;
import cn.herodotus.dante.core.domain.Scan;
import cn.herodotus.dante.messaging.domain.MappingAttribute;
import cn.herodotus.dante.messaging.strategy.AttributeCollectionEventManager;
import cn.herodotus.dante.web.autoconfigure.initializer.AbstractRequestMappingScanner;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.condition.VersionRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>Description: RequestMapping扫描器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/6/2 19:52
 */
public class RequestMappingScanner extends AbstractRequestMappingScanner {

    private static final Logger log = LoggerFactory.getLogger(RequestMappingScanner.class);
    private final WebMvcProperties webMvcProperties;

    public RequestMappingScanner(WebMvcProperties webMvcProperties, Scan scan, AttributeCollectionEventManager attributeCollectionEventManager) {
        super(scan, attributeCollectionEventManager);
        this.webMvcProperties = webMvcProperties;
    }

    @Override
    protected void onApplicationEvent(ApplicationContext applicationContext) {

        // 2、只针对有EnableResourceServer注解的微服务进行扫描。如果变为单体架构目前不会用到EnableResourceServer所以增加的了一个Architecture判断
        if (notExecuteScanning()) {
            // 只扫描资源服务器
            log.warn("[Herodotus] |- Can not found scan annotation in Service [{}], Skip!", getServiceId());
            return;
        }

        // 3、获取所有接口映射
        Map<String, RequestMappingHandlerMapping> mappings = applicationContext.getBeansOfType(RequestMappingHandlerMapping.class);

        // 4、 获取url与类和方法的对应信息
        List<MappingAttribute> resources = new ArrayList<>();
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
                    MappingAttribute mappingAttribute = createMappingAttribute(requestMappingInfo, handlerMethod);
                    if (ObjectUtils.isEmpty(mappingAttribute)) {
                        continue;
                    }

                    resources.add(mappingAttribute);
                }
            }
        }

        complete(resources);
    }

    private MappingAttribute createMappingAttribute(RequestMappingInfo info, HandlerMethod method) {
        // 4.2.1、获取注解对应的请求类型
        RequestMethodsRequestCondition requestMethodsRequestCondition = info.getMethodsCondition();
        String requestMethods = StringUtils.join(requestMethodsRequestCondition.getMethods(), SymbolConstants.COMMA);

        // 4.2.2、获取主机对应的请求路径
        PathPatternsRequestCondition pathPatternsCondition = info.getPathPatternsCondition();
        Set<String> patternValues = pathPatternsCondition.getPatternValues();
        if (CollectionUtils.isEmpty(patternValues)) {
            return null;
        }

        // 4.2.3、获取 API 版本信息
        String version = parseVersion(info);

        String urls = String.join(SymbolConstants.COMMA, patternValues);

        return buildMappingAttribute(requestMethods, urls, version, method);
    }

    private String parseVersion(RequestMappingInfo info) {
        VersionRequestCondition versionRequestCondition = info.getVersionCondition();
        String version = versionRequestCondition.getVersion();
        if (ObjectUtils.isNotEmpty(version)) {
            return version;
        }

        return webMvcProperties.getApiversion().getDefaultVersion();
    }
}
