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

package cn.herodotus.stirrup.oauth2.authorization.reactive;

import cn.herodotus.stirrup.core.foundation.utils.type.ListUtils;
import cn.herodotus.stirrup.oauth2.authorization.definition.AbstractSecurityMatcherConfigurer;
import cn.herodotus.stirrup.oauth2.authorization.properties.OAuth2AuthorizationProperties;
import cn.herodotus.stirrup.web.core.utils.WebFluxUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.reactive.resource.ResourceUrlProvider;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/1/26 21:26
 */
public class ReactiveSecurityMatcherConfigurer extends AbstractSecurityMatcherConfigurer {

    private final ResourceUrlProvider resourceUrlProvider;

    private final String[] staticRequestMatchers;
    private final String[] permitAllRequestMatchers;
    private final String[] hasAuthenticatedRequestMatchers;

    protected ReactiveSecurityMatcherConfigurer(OAuth2AuthorizationProperties authorizationProperties, ResourceUrlProvider resourceUrlProvider) {
        super(authorizationProperties);
        this.resourceUrlProvider = resourceUrlProvider;
        this.staticRequestMatchers = ListUtils.toStringArray(getStaticResources());
        this.permitAllRequestMatchers = ListUtils.toStringArray(getPermitAllResources());
        this.hasAuthenticatedRequestMatchers = ListUtils.toStringArray(getHasAuthenticatedResources());
    }

    public String[] getStaticRequestMatchers() {
        return staticRequestMatchers;
    }

    public String[] getPermitAllRequestMatchers() {
        return permitAllRequestMatchers;
    }

    public String[] getHasAuthenticatedRequestMatchers() {
        return hasAuthenticatedRequestMatchers;
    }

    /**
     * 判断是否为静态资源
     *
     * @param uri 请求 URL
     * @return 是否为静态资源
     */
    public Mono<Boolean> isStaticRequest(String uri, ServerWebExchange exchange) {
        Mono<String> staticUri = resourceUrlProvider.getForUriString(uri, exchange);
        return staticUri.flatMap(resource -> Mono.just(StringUtils.isNotBlank(resource)));
    }

    public Mono<Boolean> isPermitAllRequest(String uri) {
        boolean isMatch = WebFluxUtils.isPathMatch(getPermitAllRequestMatchers(), uri);
        return Mono.just(isMatch);
    }

    public Mono<Boolean> isHasAuthenticatedRequest(String uri) {
        boolean isMatch = WebFluxUtils.isPathMatch(getHasAuthenticatedRequestMatchers(), uri);
        return Mono.just(isMatch);
    }
}
