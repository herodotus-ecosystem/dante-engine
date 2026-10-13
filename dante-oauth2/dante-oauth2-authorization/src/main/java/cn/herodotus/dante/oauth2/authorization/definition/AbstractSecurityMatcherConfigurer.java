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

package cn.herodotus.dante.oauth2.authorization.definition;

import cn.herodotus.dante.core.builder.SecurityMatcher;
import cn.herodotus.dante.oauth2.commons.domain.HerodotusRequest;
import cn.herodotus.dante.oauth2.commons.domain.HerodotusSecurityAttribute;
import org.apache.commons.collections4.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 静态安全资源配置器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/1/26 21:15
 */
public abstract class AbstractSecurityMatcherConfigurer {

    private final List<String> staticResources;
    private final List<String> permitAllResources;
    private final List<String> hasAuthenticatedResources;
    private final Map<HerodotusRequest, List<HerodotusSecurityAttribute>> permitAllAttributes;
    private final boolean strictMode;

    protected AbstractSecurityMatcherConfigurer(boolean strictMode, SecurityMatcher securityMatcher) {
        this.strictMode = strictMode;
        this.staticResources = securityMatcher.getStaticResources();
        this.permitAllResources = securityMatcher.getPermitAllResources();
        this.hasAuthenticatedResources = securityMatcher.getHasAuthenticatedResources();
        this.permitAllAttributes = createPermitAllAttributes(permitAllResources);
    }

    /**
     * 获取 SecurityFilterChain 中配置的 RequestMatchers 信息。
     * <p>
     * RequestMatchers 可以理解为“静态配置”，将其与平台后端的“动态配置”有机结合。同时，防止因动态配置导致的静态配置失效的问题。
     * <p>
     * 目前只处理了 permitAll 类型。其它内容根据后续使用情况再行添加。
     *
     * @return RequestMatchers 中配置的权限数据
     */
    private Map<HerodotusRequest, List<HerodotusSecurityAttribute>> createPermitAllAttributes(List<String> permitAllResources) {
        if (CollectionUtils.isNotEmpty(permitAllResources)) {
            Map<HerodotusRequest, List<HerodotusSecurityAttribute>> result = new LinkedHashMap<>();
            permitAllResources.forEach(item -> result.put(new HerodotusRequest(item), List.of(HerodotusSecurityAttribute.createDefaultPermitAll())));
            return result;
        }
        return new LinkedHashMap<>();
    }

    public boolean isStrictMode() {
        return strictMode;
    }

    protected List<String> getPermitAllResources() {
        if (CollectionUtils.isNotEmpty(this.permitAllResources)) {
            return CollectionUtils.collate(this.staticResources, this.permitAllResources);
        }

        return staticResources;
    }

    protected List<String> getHasAuthenticatedResources() {
        return hasAuthenticatedResources;
    }

    public Map<HerodotusRequest, List<HerodotusSecurityAttribute>> getPermitAllAttributes() {
        return permitAllAttributes;
    }
}
