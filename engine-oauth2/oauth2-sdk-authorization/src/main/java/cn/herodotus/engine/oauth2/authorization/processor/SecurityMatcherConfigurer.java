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

package cn.herodotus.engine.oauth2.authorization.processor;

import cn.herodotus.engine.assistant.core.utils.type.ListUtils;
import cn.herodotus.engine.oauth2.authorization.properties.OAuth2AuthorizationProperties;
import cn.herodotus.engine.rest.core.constants.WebResources;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: 安全过滤配置处理器 </p>
 * <p>
 * 对静态资源、开放接口等静态配置进行处理。整合默认配置和配置文件中的配置
 *
 * @author : gengwei.zheng
 * @date : 2022/3/8 22:57
 */
@Component
public class SecurityMatcherConfigurer {

    private final OAuth2AuthorizationProperties authorizationProperties;
    private List<String> staticResources;
    private List<String> permitAllResources;
    private List<String> hasAuthenticatedResources;

    public SecurityMatcherConfigurer(OAuth2AuthorizationProperties authorizationProperties) {
        this.authorizationProperties = authorizationProperties;
        this.staticResources = new ArrayList<>();
        this.permitAllResources = new ArrayList<>();
        this.hasAuthenticatedResources = new ArrayList<>();
    }


    public List<String> getStaticResourceList() {
        if (CollectionUtils.isEmpty(this.staticResources)) {
            this.staticResources = ListUtils.merge(authorizationProperties.getMatcher().getStaticResources(), WebResources.DEFAULT_IGNORED_STATIC_RESOURCES);
        }
        return this.staticResources;
    }

    public List<String> getPermitAllList() {
        if (CollectionUtils.isEmpty(this.permitAllResources)) {
            this.permitAllResources = ListUtils.merge(authorizationProperties.getMatcher().getPermitAll(), WebResources.DEFAULT_PERMIT_ALL_RESOURCES);
        }
        return this.permitAllResources;
    }

    public List<String> getHasAuthenticatedList() {
        if (CollectionUtils.isEmpty(this.hasAuthenticatedResources)) {
            this.hasAuthenticatedResources = ListUtils.merge(authorizationProperties.getMatcher().getHasAuthenticated(), WebResources.DEFAULT_HAS_AUTHENTICATED_RESOURCES);
        }
        return this.hasAuthenticatedResources;
    }

    private RequestMatcher[] toRequestMatchers(List<String> paths) {
        if (CollectionUtils.isNotEmpty(paths)) {
            List<AntPathRequestMatcher> matchers = paths.stream().map(AntPathRequestMatcher::new).toList();
            RequestMatcher[] result = new RequestMatcher[matchers.size()];
            return matchers.toArray(result);
        } else {
            return new RequestMatcher[]{};
        }
    }

    public RequestMatcher[] getStaticResourceArray() {
        return toRequestMatchers(getStaticResourceList());
    }

    public RequestMatcher[] getPermitAllArray() {
        return toRequestMatchers(getPermitAllList());
    }

    public OAuth2AuthorizationProperties getAuthorizationProperties() {
        return authorizationProperties;
    }
}
