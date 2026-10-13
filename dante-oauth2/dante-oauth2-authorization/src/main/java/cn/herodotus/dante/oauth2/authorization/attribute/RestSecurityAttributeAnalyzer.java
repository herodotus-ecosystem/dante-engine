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

package cn.herodotus.dante.oauth2.authorization.attribute;

import cn.herodotus.dante.core.constant.SymbolConstants;
import cn.herodotus.dante.messaging.domain.SecurityAttribute;
import cn.herodotus.dante.oauth2.commons.domain.HerodotusRequest;
import cn.herodotus.dante.oauth2.commons.domain.HerodotusSecurityAttribute;
import cn.herodotus.dante.spring.enums.UrlCategory;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: Rest 接口权限元数据分析处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/1 17:43
 */
public class RestSecurityAttributeAnalyzer extends AbstractSecurityAttributeAnalyzer {

    private static final Logger log = LoggerFactory.getLogger(RestSecurityAttributeAnalyzer.class);

    private final RestSecurityAttributeStorage restSecurityAttributeStorage;
    private final Map<HerodotusRequest, List<HerodotusSecurityAttribute>> permitAllAttributes;

    public RestSecurityAttributeAnalyzer(RestSecurityAttributeStorage restSecurityAttributeStorage, Map<HerodotusRequest, List<HerodotusSecurityAttribute>> permitAllAttributes) {
        this.restSecurityAttributeStorage = restSecurityAttributeStorage;
        this.permitAllAttributes = permitAllAttributes;
    }

    /**
     * 将解析后的数据添加对应的分组中
     *
     * @param container   分组结果数据容器
     * @param urlCategory 分组类别
     * @param resources   权限数据
     */
    private void appendToGroup(Map<UrlCategory, Map<HerodotusRequest, List<HerodotusSecurityAttribute>>> container, UrlCategory urlCategory, Map<HerodotusRequest, List<HerodotusSecurityAttribute>> resources) {
        Map<HerodotusRequest, List<HerodotusSecurityAttribute>> value = new LinkedHashMap<>();

        if (container.containsKey(urlCategory)) {
            value = container.get(urlCategory);
        }

        value.putAll(resources);

        container.put(urlCategory, value);
    }

    /**
     * 将各个服务配置的静态权限数据分组
     *
     * @param securityMatchers 静态权限数据
     * @return 分组后的权限数据
     */
    private Map<UrlCategory, Map<HerodotusRequest, List<HerodotusSecurityAttribute>>> groupingSecurityMatchers(Map<HerodotusRequest, List<HerodotusSecurityAttribute>> securityMatchers) {

        Map<UrlCategory, Map<HerodotusRequest, List<HerodotusSecurityAttribute>>> group = new LinkedHashMap<>();

        securityMatchers.forEach((key, value) -> {
            Map<HerodotusRequest, List<HerodotusSecurityAttribute>> resources = new LinkedHashMap<>();
            resources.put(key, value);
            appendToGroup(group, UrlCategory.getCategory(key.getPattern()), resources);
        });

        log.debug("[Herodotus] |- Grouping security matcher by category.");
        return group;
    }

    /**
     * 创建请求和权限的映射数据
     *
     * @param url        请求url
     * @param methods    请求method
     * @param attributes Security权限{@link HerodotusSecurityAttribute}
     * @return 保存请求和权限的映射的Map
     */
    private Map<HerodotusRequest, List<HerodotusSecurityAttribute>> convert(String url, String methods, String version, List<HerodotusSecurityAttribute> attributes) {
        Map<HerodotusRequest, List<HerodotusSecurityAttribute>> result = new LinkedHashMap<>();
        if (StringUtils.isBlank(methods)) {
            result.put(new HerodotusRequest(url, null, version), attributes);
        } else {
            // 如果methods是以逗号分隔的字符串，那么进行拆分处理
            if (Strings.CS.contains(methods, SymbolConstants.COMMA)) {
                String[] multiMethod = StringUtils.split(methods, SymbolConstants.COMMA);
                for (String method : multiMethod) {
                    result.put(new HerodotusRequest(url, method, version), attributes);
                }
            } else {
                result.put(new HerodotusRequest(url, methods, version), attributes);
            }
        }

        return result;
    }

    /**
     * 将 UPMS 分发的 SecurityAttributes 数据进行权限转换并分组
     *
     * @param securityAttributes 权限数据
     * @return 分组后的权限数据
     */
    private Map<UrlCategory, Map<HerodotusRequest, List<HerodotusSecurityAttribute>>> groupingAttributeTransmitters(List<SecurityAttribute> securityAttributes) {

        Map<UrlCategory, Map<HerodotusRequest, List<HerodotusSecurityAttribute>>> group = new LinkedHashMap<>();

        securityAttributes.forEach(transmitter -> {
            Map<HerodotusRequest, List<HerodotusSecurityAttribute>> resources = convert(transmitter.getUrl(), transmitter.getRequestMethod(), transmitter.getVersion(), analysis(transmitter));
            appendToGroup(group, UrlCategory.getCategory(transmitter.getUrl()), resources);
        });

        log.debug("[Herodotus] |- Grouping security metadata by category.");
        return group;
    }

    /**
     * 各个服务静态化配置的权限过滤，通常为通配符型或者全路径型，很少有站位符型。即：大多数情况为 {@code Category.WILDCARD} 和 {@code Category.PLACEHOLDER}，很少有 {@code Category.FULL_PATH}
     * <p>
     * 此处的逻辑是：
     * 1. 先处理各个服务静态化配置的权限，当前假设不会有{@code Category.FULL_PATH}类型的权限。后期如果该种权限较多再补充即可。
     * 同时，静态服务都是开发人员手工配置，假定手工配置时就会对是否冲突进行处理，当然也可能出现冲突，那么这个开发人员得多不负责。
     * 2. 经过考虑，服务本地接口扫描完，就对所有的 RequestMapping 做一遍解析，现在感觉意义不大。
     * 因为，RequestMapping 汇总至 UPMS 后，还会做一次统一的分发。所以当前的设计思路是不对 RequestMapping 进行处理。后续根据需要再补充即可。
     */
    public void postLocalResourceMatcherProcess() {

        log.debug("[Herodotus] |- [R3] Process local configured security attribute.");

        if (MapUtils.isNotEmpty(permitAllAttributes)) {
            Map<UrlCategory, Map<HerodotusRequest, List<HerodotusSecurityAttribute>>> grouping = groupingSecurityMatchers(permitAllAttributes);

            Map<HerodotusRequest, List<HerodotusSecurityAttribute>> wildcards = grouping.get(UrlCategory.WILDCARD);
            restSecurityAttributeStorage.addToStorage(wildcards, false);

            Map<HerodotusRequest, List<HerodotusSecurityAttribute>> fullPaths = grouping.get(UrlCategory.FULL_PATH);
            restSecurityAttributeStorage.addToStorage(fullPaths, true);
        }
    }

    /**
     * 处理分发的 SecurityAttribute，将其转换、解析为表达式权限，并存入本地缓存，用于权限校验
     * <p>
     * 处理过程中，会根据规则对权限类型分组，然后进行去重的操作。
     *
     * @param securityAttributes 权限数据
     */
    @Override
    public void postAttributeDistributionProcess(List<SecurityAttribute> securityAttributes) {

        // 从缓存中获取全部带有特殊字符的匹配规则
        Map<HerodotusRequest, List<HerodotusSecurityAttribute>> compatibles = restSecurityAttributeStorage.getCompatible();
        // 创建一个临时的 Matcher 容器
        Map<HerodotusRequest, List<HerodotusSecurityAttribute>> matchers = new LinkedHashMap<>(compatibles);

        // 1. 对分发的 SecurityAttribute 进行分组
        Map<UrlCategory, Map<HerodotusRequest, List<HerodotusSecurityAttribute>>> grouping = groupingAttributeTransmitters(securityAttributes);

        // 2. 拿到带有通配符的分组数据后，先存入本地然后将其作为 matchers 作为后续权限冲突分析的依据
        // 注意：静态权限采用聚合方式之后，matchers 可能为空
        Map<HerodotusRequest, List<HerodotusSecurityAttribute>> wildcards = grouping.get(UrlCategory.WILDCARD);
        if (MapUtils.isNotEmpty(wildcards)) {
            matchers.putAll(wildcards);
            restSecurityAttributeStorage.addToStorage(wildcards, false);
        }

        // 3. 拿到带有占位符的分组数据，并检测是否存在冲突的匹配规则，然后将结果存入本地存储
        // 注意：这里 matchers 可能为空。
        Map<HerodotusRequest, List<HerodotusSecurityAttribute>> placeholders = grouping.get(UrlCategory.PLACEHOLDER);
        log.debug("[Herodotus] |- Store placeholder type security attributes.");
        restSecurityAttributeStorage.addToStorage(matchers, placeholders, false);

        // 4. 占位符数据也需要被拿来做权限冲突，前面第三步仅是将其存入本地存储，当前 matchers 中并未包含这部分数据，所以重新设置
        if (MapUtils.isNotEmpty(placeholders)) {
            matchers.putAll(placeholders);
        }
        // 5. 拿到全路径的分组数据，并检测是否存在冲突的匹配规则，然后将结果存入本地存储
        Map<HerodotusRequest, List<HerodotusSecurityAttribute>> fullPaths = grouping.get(UrlCategory.FULL_PATH);
        log.debug("[Herodotus] |- Store full path type security attributes.");
        restSecurityAttributeStorage.addToStorage(matchers, fullPaths, true);

        log.debug("[Herodotus] |- [R7] Security attributes process has FINISHED!");
    }
}
