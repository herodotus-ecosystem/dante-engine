/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.oauth2.authorization.configuration;

import cn.herodotus.engine.assistant.core.definition.BearerTokenResolver;
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
    public SecurityMatcherConfigurer securityMatcherConfigurer(OAuth2AuthorizationProperties authorizationProperties) {
        SecurityMatcherConfigurer securityMatcherConfigurer = new SecurityMatcherConfigurer(authorizationProperties);
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
