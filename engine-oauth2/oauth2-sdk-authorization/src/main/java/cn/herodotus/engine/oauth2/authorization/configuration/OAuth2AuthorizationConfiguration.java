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

package cn.herodotus.engine.oauth2.authorization.configuration;

import cn.herodotus.engine.assistant.core.support.BearerTokenResolver;
import cn.herodotus.engine.oauth2.authorization.auditing.SecurityAuditorAware;
import cn.herodotus.engine.oauth2.authorization.customizer.OAuth2AuthorizeHttpRequestsConfigurerCustomer;
import cn.herodotus.engine.oauth2.authorization.customizer.OAuth2ResourceServerConfigurerCustomer;
import cn.herodotus.engine.oauth2.authorization.processor.SecurityAuthorizationManager;
import cn.herodotus.engine.oauth2.authorization.processor.SecurityMatcherConfigurer;
import cn.herodotus.engine.oauth2.authorization.processor.SecurityMetadataSourceAnalyzer;
import cn.herodotus.engine.oauth2.authorization.processor.SecurityMetadataSourceStorage;
import cn.herodotus.engine.oauth2.authorization.properties.OAuth2AuthorizationProperties;
import cn.herodotus.engine.oauth2.core.exception.SecurityGlobalExceptionHandler;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

/**
 * <p>Description: SecurityAttribute 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/23 15:42
 */
@AutoConfiguration
@EnableConfigurationProperties({OAuth2AuthorizationProperties.class})
@EnableMethodSecurity(proxyTargetClass = true)
@Import({
        SecurityGlobalExceptionHandler.class,
        OAuth2SessionConfiguration.class,
})
public class OAuth2AuthorizationConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OAuth2AuthorizationConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [OAuth2 Authorization] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityMetadataSourceStorage securityMetadataSourceStorage() {
        SecurityMetadataSourceStorage securityMetadataSourceStorage = new SecurityMetadataSourceStorage();
        log.trace("[Herodotus] |- Bean [Security Metadata Source Storage] Auto Configure.");
        return securityMetadataSourceStorage;
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityMatcherConfigurer securityMatcherConfigurer(OAuth2AuthorizationProperties authorizationProperties, ResourceUrlProvider resourceUrlProvider) {
        SecurityMatcherConfigurer securityMatcherConfigurer = new SecurityMatcherConfigurer(authorizationProperties, resourceUrlProvider);
        log.trace("[Herodotus] |- Bean [Security Metadata Configurer] Auto Configure.");
        return securityMatcherConfigurer;
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityAuthorizationManager securityAuthorizationManager(SecurityMetadataSourceStorage securityMetadataSourceStorage, SecurityMatcherConfigurer securityMatcherConfigurer) {
        SecurityAuthorizationManager securityAuthorizationManager = new SecurityAuthorizationManager(securityMetadataSourceStorage, securityMatcherConfigurer);
        log.trace("[Herodotus] |- Bean [Authorization Manager] Auto Configure.");
        return securityAuthorizationManager;
    }

    @Bean
    @ConditionalOnMissingBean
    public OAuth2AuthorizeHttpRequestsConfigurerCustomer authorizeHttpRequestsConfigurerCustomer(SecurityMatcherConfigurer securityMatcherConfigurer, SecurityAuthorizationManager securityAuthorizationManager) {
        OAuth2AuthorizeHttpRequestsConfigurerCustomer OAuth2AuthorizeHttpRequestsConfigurerCustomer = new OAuth2AuthorizeHttpRequestsConfigurerCustomer(securityMatcherConfigurer, securityAuthorizationManager);
        log.trace("[Herodotus] |- Bean [Authorize Http Requests Configurer Customer] Auto Configure.");
        return OAuth2AuthorizeHttpRequestsConfigurerCustomer;
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityMetadataSourceAnalyzer securityMetadataSourceAnalyzer(SecurityMetadataSourceStorage securityMetadataSourceStorage, SecurityMatcherConfigurer securityMatcherConfigurer) {
        SecurityMetadataSourceAnalyzer securityMetadataSourceAnalyzer = new SecurityMetadataSourceAnalyzer(securityMetadataSourceStorage, securityMatcherConfigurer);
        log.trace("[Herodotus] |- Bean [Security Metadata Source Analyzer] Auto Configure.");
        return securityMetadataSourceAnalyzer;
    }

    @Bean
    @ConditionalOnMissingBean
    public OAuth2ResourceServerConfigurerCustomer oauth2ResourceServerConfigurerCustomer(OAuth2AuthorizationProperties authorizationProperties, JwtDecoder jwtDecoder, OAuth2ResourceServerProperties resourceServerProperties) {
        OAuth2ResourceServerConfigurerCustomer oauth2ResourceServerConfigurerCustomer = new OAuth2ResourceServerConfigurerCustomer(authorizationProperties, jwtDecoder, resourceServerProperties);
        log.trace("[Herodotus] |- Bean [OAuth2 Resource Server Configurer Customer] Auto Configure.");
        return oauth2ResourceServerConfigurerCustomer;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(OAuth2ResourceServerConfigurerCustomer.class)
    public BearerTokenResolver bearerTokenResolver(OAuth2ResourceServerConfigurerCustomer oauth2ResourceServerConfigurerCustomer) {
        BearerTokenResolver bearerTokenResolver = oauth2ResourceServerConfigurerCustomer.createBearerTokenResolver();
        log.trace("[Herodotus] |- Bean [Bearer Token Resolver] Auto Configure.");
        return bearerTokenResolver;
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        SecurityAuditorAware securityAuditorAware = new SecurityAuditorAware();
        log.debug("[Herodotus] |- Bean [Security Auditor Aware] Auto Configure.");
        return securityAuditorAware;
    }
}
