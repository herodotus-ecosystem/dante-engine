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

package cn.herodotus.dante.oauth2.authorization.config;

import cn.herodotus.dante.core.builder.SecurityMatcher;
import cn.herodotus.dante.core.function.SecurityMatcherBuilderCustomizer;
import cn.herodotus.dante.oauth2.authorization.customizer.DefaultOAuth2ProtectedResourceMetadataStorage;
import cn.herodotus.dante.oauth2.authorization.customizer.OAuth2AuthorizationSecurityMatcherBuilderCustomizer;
import cn.herodotus.dante.oauth2.authorization.properties.OAuth2AuthorizationProperties;
import cn.herodotus.dante.oauth2.authorization.servlet.OAuth2SessionManagementConfigurerCustomer;
import cn.herodotus.dante.oauth2.authorization.servlet.ServletOAuth2AuthorizationConfigurerManager;
import cn.herodotus.dante.oauth2.authorization.servlet.ServletOAuth2ResourceMatcherConfigurer;
import cn.herodotus.dante.oauth2.authorization.servlet.ServletSecurityAuthorizationManager;
import cn.herodotus.dante.oauth2.commons.definition.AuthorizationSecurityAttributeManager;
import cn.herodotus.dante.security.definition.OAuth2ProtectedResourceMetadataStorage;
import cn.herodotus.dante.web.definition.template.ServletTemplateHandler;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.web.accept.ApiVersionStrategy;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

/**
 * <p>Description: SecurityAttribute 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/23 15:42
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableConfigurationProperties({OAuth2AuthorizationProperties.class})
@EnableMethodSecurity(proxyTargetClass = true, securedEnabled = true, jsr250Enabled = true)
@Import({
        OAuth2ServletSessionConfiguration.class,
})
public class OAuth2ServletAuthorizationConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OAuth2ServletAuthorizationConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [OAuth2 Servlet Authorization] Configure.");
    }

    @Bean
    public SecurityMatcherBuilderCustomizer oauth2AuthorizationSecurityMatcherBuilderCustomizer(OAuth2AuthorizationProperties authorizationProperties) {
        OAuth2AuthorizationSecurityMatcherBuilderCustomizer customizer = new OAuth2AuthorizationSecurityMatcherBuilderCustomizer(authorizationProperties);
        log.debug("[Herodotus] |- Strategy [OAuth2 Authorization Security Matcher Builder Customizer] Configure.");
        return customizer;
    }

    @Bean
    public ServletOAuth2ResourceMatcherConfigurer servletSecurityMatcherConfigurer(OAuth2AuthorizationProperties authorizationProperties, ResourceUrlProvider resourceUrlProvider, SecurityMatcher securityMatcher) {
        ServletOAuth2ResourceMatcherConfigurer configurer = new ServletOAuth2ResourceMatcherConfigurer(authorizationProperties, resourceUrlProvider, securityMatcher);
        log.trace("[Herodotus] |- Bean [Servlet Security Matcher Configurer] Configure.");
        return configurer;
    }

    @Bean
    public AuthorizationSecurityAttributeManager resourceServiceSecurityAttributeManager(ServletOAuth2ResourceMatcherConfigurer servletOAuth2ResourceMatcherConfigurer) {
        SecurityAttributeManager analyzer = new SecurityAttributeManager(servletOAuth2ResourceMatcherConfigurer.getPermitAllAttributes());
        log.trace("[Herodotus] |- Bean [Security Attribute Analyzer] Configure.");
        return analyzer;
    }

    @Bean
    public ServletSecurityAuthorizationManager servletSecurityAuthorizationManager(AuthorizationSecurityAttributeManager authorizationSecurityAttributeManager, ServletOAuth2ResourceMatcherConfigurer servletOAuth2ResourceMatcherConfigurer, ObjectProvider<ApiVersionStrategy> apiVersionStrategies) {
        ServletSecurityAuthorizationManager manager = new ServletSecurityAuthorizationManager(authorizationSecurityAttributeManager, servletOAuth2ResourceMatcherConfigurer, apiVersionStrategies);
        log.trace("[Herodotus] |- Bean [Servlet Security Authorization Manager] Configure.");
        return manager;
    }


    @Bean
    @ConditionalOnMissingBean
    public OAuth2ProtectedResourceMetadataStorage oauth2ProtectedResourceMetadataRepository() {
        DefaultOAuth2ProtectedResourceMetadataStorage repository = new DefaultOAuth2ProtectedResourceMetadataStorage();
        log.trace("[Herodotus] |- Bean [OAuth2 Protected Resource Metadata Repository] Configure.");
        return repository;
    }

    @Bean
    public ServletOAuth2AuthorizationConfigurerManager servletOAuth2AuthorizationFacadeConfigurer(
            ServletTemplateHandler servletTemplateHandler,
            JwtDecoder jwtDecoder,
            OpaqueTokenIntrospector opaqueTokenIntrospector,
            OAuth2SessionManagementConfigurerCustomer sessionManagementConfigurerCustomer,
            ServletOAuth2ResourceMatcherConfigurer servletOAuth2ResourceMatcherConfigurer,
            ServletSecurityAuthorizationManager servletSecurityAuthorizationManager,
            OAuth2ProtectedResourceMetadataStorage oauth2ProtectedResourceMetadataStorage) {
        ServletOAuth2AuthorizationConfigurerManager configurer = new ServletOAuth2AuthorizationConfigurerManager(
                servletTemplateHandler,
                jwtDecoder,
                opaqueTokenIntrospector,
                sessionManagementConfigurerCustomer,
                servletOAuth2ResourceMatcherConfigurer,
                servletSecurityAuthorizationManager,
                oauth2ProtectedResourceMetadataStorage);
        log.trace("[Herodotus] |- Bean [Servlet OAuth2 Resource Server Configurer] Configure.");
        return configurer;
    }
}
