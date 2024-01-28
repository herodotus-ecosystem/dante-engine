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

import cn.herodotus.stirrup.oauth2.authorization.definition.HerodotusConfigAttribute;
import cn.herodotus.stirrup.oauth2.authorization.definition.HerodotusRequest;
import cn.herodotus.stirrup.oauth2.authorization.processor.SecurityMetadataSourceStorage;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthenticatedReactiveAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/1/26 21:35
 */
public class ReactiveSecurityAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private static final Logger log = LoggerFactory.getLogger(ReactiveSecurityAuthorizationManager.class);

    private final SecurityMetadataSourceStorage securityMetadataSourceStorage;
    private final ReactiveSecurityMatcherConfigurer reactiveSecurityMatcherConfigurer;

    public ReactiveSecurityAuthorizationManager(SecurityMetadataSourceStorage securityMetadataSourceStorage, ReactiveSecurityMatcherConfigurer reactiveSecurityMatcherConfigurer) {
        this.securityMetadataSourceStorage = securityMetadataSourceStorage;
        this.reactiveSecurityMatcherConfigurer = reactiveSecurityMatcherConfigurer;
    }

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext object) {

        ServerWebExchange serverWebExchange = object.getExchange();

        String uri = serverWebExchange.getRequest().getURI().getPath();
        HttpMethod httpMethod = serverWebExchange.getRequest().getMethod();

        Mono<Boolean> isStaticMono =  reactiveSecurityMatcherConfigurer.isStaticRequest(uri, serverWebExchange);

        Mono<Boolean> isPermitAllMono = reactiveSecurityMatcherConfigurer.isPermitAllRequest(uri);

        Mono<Boolean> isHasAuthenticatedMono = reactiveSecurityMatcherConfigurer.isHasAuthenticatedRequest(uri);

        Mono<Boolean> isStrictModeMono = Mono.just(reactiveSecurityMatcherConfigurer.isStrictMode());

        return Mono.zip(isStaticMono, isPermitAllMono, isHasAuthenticatedMono, isStrictModeMono).flatMap(objects -> {

            Boolean isStaticRequest = objects.getT1();
            if (isStaticRequest) {
                log.trace("[Herodotus] |- Is static resource : [{}], Passed!", uri);
                return toAuthorizationDecision(true);
            }

            Boolean isPermitAll = objects.getT2();
            if(isPermitAll) {
                log.trace("[Herodotus] |- Is white list resource : [{}], Passed!", uri);
                return toAuthorizationDecision(true);
            }

            Boolean isHasAuthenticated = objects.getT3();
            if (isHasAuthenticated) {
                log.trace("[Herodotus] |- Is has authenticated resource : [{}]", uri);
                return AuthenticatedReactiveAuthorizationManager.authenticated().check(authentication, object);
            }

            List<HerodotusConfigAttribute> configAttributes = findConfigAttribute(uri, httpMethod, serverWebExchange);
            if (CollectionUtils.isEmpty(configAttributes)) {
                log.warn("[Herodotus] |- NO PRIVILEGES : [{}].", uri);

                Boolean isStrictMode = objects.getT4();
                if (!isStrictMode) {
                    log.debug("[Herodotus] |- Request is authenticated: [{}].", uri);
                    return AuthenticatedReactiveAuthorizationManager.authenticated().check(authentication, object);
                }

                return toAuthorizationDecision(false);
            }

            for (HerodotusConfigAttribute configAttribute : configAttributes) {

            }

            return toAuthorizationDecision(false);
        });
    }

    private Mono<AuthorizationDecision> toAuthorizationDecision(boolean granted) {
        return Mono.just(new AuthorizationDecision(false));
    }

    private List<HerodotusConfigAttribute> findConfigAttribute(String url, HttpMethod method, ServerWebExchange serverWebExchange) {

        log.debug("[Herodotus] |- Current Request is : [{}] - [{}]", url, method);

        List<HerodotusConfigAttribute> configAttributes = this.securityMetadataSourceStorage.getConfigAttribute(url, method.name());
        if (CollectionUtils.isNotEmpty(configAttributes)) {
            log.debug("[Herodotus] |- Get configAttributes from local storage for : [{}] - [{}]", url, method.name());
            return configAttributes;
        } else {
            LinkedHashMap<HerodotusRequest, List<HerodotusConfigAttribute>> compatible = this.securityMetadataSourceStorage.getCompatible();
            if (MapUtils.isNotEmpty(compatible)) {
                // 支持含有**通配符的路径搜索
                for (Map.Entry<HerodotusRequest, List<HerodotusConfigAttribute>> entry : compatible.entrySet()) {
                    PathPatternParserServerWebExchangeMatcher matcher = toMatcher(entry.getKey());
                    Mono<ServerWebExchangeMatcher.MatchResult> result = matcher.matches(serverWebExchange);
                    Optional<ServerWebExchangeMatcher.MatchResult> optional = result.blockOptional();
                    if (optional.isPresent() && optional.get().isMatch()) {
                        log.debug("[Herodotus] |- Request match the wildcard [{}] - [{}]", entry.getKey(), entry.getValue());
                        return entry.getValue();
                    }
                }
            }
        }

        return null;
    }

    private PathPatternParserServerWebExchangeMatcher toMatcher(HerodotusRequest herodotusRequest) {
        HttpMethod httpMethod = null;
        if (StringUtils.isNotBlank(herodotusRequest.getHttpMethod())) {
            httpMethod = HttpMethod.valueOf(herodotusRequest.getHttpMethod());
        }

        return new PathPatternParserServerWebExchangeMatcher(herodotusRequest.getPattern(), httpMethod);
    }
}
