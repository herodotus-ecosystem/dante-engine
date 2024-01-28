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

package cn.herodotus.stirrup.oauth2.authorization.servlet;

import cn.herodotus.stirrup.oauth2.authorization.definition.AbstractSecurityMatcherConfigurer;
import cn.herodotus.stirrup.oauth2.authorization.properties.OAuth2AuthorizationProperties;
import cn.herodotus.stirrup.web.core.utils.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

/**
 * <p>Description: 安全过滤配置处理器 </p>
 * <p>
 * 对静态资源、开放接口等静态配置进行处理。整合默认配置和配置文件中的配置
 *
 * @author : gengwei.zheng
 * @date : 2022/3/8 22:57
 */
@Component
public class ServletSecurityMatcherConfigurer extends AbstractSecurityMatcherConfigurer {

    private final ResourceUrlProvider resourceUrlProvider;

    private final RequestMatcher[] staticRequestMatchers;
    private final RequestMatcher[] permitAllRequestMatchers;
    private final RequestMatcher[] hasAuthenticatedRequestMatchers;

    public ServletSecurityMatcherConfigurer(OAuth2AuthorizationProperties authorizationProperties, ResourceUrlProvider resourceUrlProvider) {
        super(authorizationProperties);
        this.resourceUrlProvider = resourceUrlProvider;
        this.staticRequestMatchers = WebUtils.toRequestMatchers(getStaticResources());
        this.permitAllRequestMatchers = WebUtils.toRequestMatchers(getPermitAllResources());
        this.hasAuthenticatedRequestMatchers = WebUtils.toRequestMatchers(getHasAuthenticatedResources());
    }

    public RequestMatcher[] getStaticRequestMatchers() {
        return staticRequestMatchers;
    }

    public RequestMatcher[] getPermitAllRequestMatchers() {
        return permitAllRequestMatchers;
    }

    public RequestMatcher[] getHasAuthenticatedRequestMatchers() {
        return hasAuthenticatedRequestMatchers;
    }

    /**
     * 判断是否为静态资源
     *
     * @param uri 请求 URL
     * @return 是否为静态资源
     */
    public boolean isStaticRequest(String uri) {
        String staticUri = resourceUrlProvider.getForLookupPath(uri);
        return StringUtils.isNotBlank(staticUri);
    }

    public boolean isPermitAllRequest(HttpServletRequest request) {
        return WebUtils.isRequestMatched(getPermitAllRequestMatchers(), request);
    }

    public boolean isHasAuthenticatedRequest(HttpServletRequest request) {
        return WebUtils.isRequestMatched(getHasAuthenticatedRequestMatchers(), request);
    }
}
