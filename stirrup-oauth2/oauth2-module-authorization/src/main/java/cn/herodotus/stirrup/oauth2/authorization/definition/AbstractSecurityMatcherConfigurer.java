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

package cn.herodotus.stirrup.oauth2.authorization.definition;

import cn.herodotus.engine.oauth2.core.enums.PermissionExpression;
import cn.herodotus.stirrup.core.foundation.utils.type.ListUtils;
import cn.herodotus.stirrup.oauth2.authorization.properties.OAuth2AuthorizationProperties;
import cn.herodotus.stirrup.oauth2.core.constants.SecurityResources;
import org.apache.commons.collections4.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * <p>Description: 静态安全资源配置器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/1/26 21:15
 */
public abstract class AbstractSecurityMatcherConfigurer {

    private final OAuth2AuthorizationProperties authorizationProperties;
    private final List<String> staticResources;
    private final List<String> permitAllResources;
    private final List<String> hasAuthenticatedResources;
    private final LinkedHashMap<HerodotusRequest, List<HerodotusConfigAttribute>> permitAllAttributes;

    protected AbstractSecurityMatcherConfigurer(OAuth2AuthorizationProperties authorizationProperties) {
        this.authorizationProperties = authorizationProperties;
        this.staticResources = ListUtils.merge(authorizationProperties.getMatcher().getStaticResources(), SecurityResources.DEFAULT_IGNORED_STATIC_RESOURCES);
        this.permitAllResources = ListUtils.merge(authorizationProperties.getMatcher().getPermitAll(), SecurityResources.DEFAULT_PERMIT_ALL_RESOURCES);
        this.hasAuthenticatedResources = ListUtils.merge(authorizationProperties.getMatcher().getHasAuthenticated(), SecurityResources.DEFAULT_HAS_AUTHENTICATED_RESOURCES);
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
    private LinkedHashMap<HerodotusRequest, List<HerodotusConfigAttribute>> createPermitAllAttributes(List<String> permitAllResources) {
        if (CollectionUtils.isNotEmpty(permitAllResources)) {
            LinkedHashMap<HerodotusRequest, List<HerodotusConfigAttribute>> result = new LinkedHashMap<>();
            permitAllResources.forEach(item -> {
                result.put(new HerodotusRequest(item), List.of(new HerodotusConfigAttribute(PermissionExpression.PERMIT_ALL.getValue())));
            });
            return result;
        }
        return new LinkedHashMap<>();
    }

    public boolean isStrictMode() {
        return authorizationProperties.getStrict();
    }

    protected List<String> getStaticResources() {
        return staticResources;
    }

    protected List<String> getPermitAllResources() {
        return permitAllResources;
    }

    protected List<String> getHasAuthenticatedResources() {
        return hasAuthenticatedResources;
    }

    public LinkedHashMap<HerodotusRequest, List<HerodotusConfigAttribute>> getPermitAllAttributes() {
        return permitAllAttributes;
    }
}
