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

package cn.herodotus.engine.oauth2.authentication.configuration;

import cn.herodotus.engine.oauth2.authentication.customizer.HerodotusJwtTokenCustomizer;
import cn.herodotus.engine.oauth2.authentication.customizer.HerodotusOpaqueTokenCustomizer;
import cn.herodotus.engine.oauth2.authentication.form.OAuth2FormLoginConfigurerCustomer;
import cn.herodotus.engine.oauth2.authentication.properties.OAuth2AuthenticationProperties;
import cn.herodotus.engine.oauth2.authentication.stamp.LockedUserDetailsStampManager;
import cn.herodotus.engine.oauth2.authentication.stamp.SignInFailureLimitedStampManager;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

/**
 * <p>Description: OAuth2 认证基础模块配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/13 15:40
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({OAuth2AuthenticationProperties.class})
public class OAuth2AuthenticationConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OAuth2AuthenticationConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- SDK [OAuth2 Authentication] Auto Configure.");
    }

    @Bean
    public LockedUserDetailsStampManager lockedUserDetailsStampManager(OAuth2AuthenticationProperties authenticationProperties) {
        LockedUserDetailsStampManager manager = new LockedUserDetailsStampManager(authenticationProperties);
        log.trace("[Herodotus] |- Bean [Locked UserDetails Stamp Manager] Auto Configure.");
        return manager;
    }

    @Bean
    public SignInFailureLimitedStampManager signInFailureLimitedStampManager(OAuth2AuthenticationProperties authenticationProperties) {
        SignInFailureLimitedStampManager manager = new SignInFailureLimitedStampManager(authenticationProperties);
        log.trace("[Herodotus] |- Bean [SignIn Failure Limited Stamp Manager] Auto Configure.");
        return manager;
    }

    @Bean
    @ConditionalOnMissingBean
    public OAuth2FormLoginConfigurerCustomer oauth2FormLoginConfigurerCustomer(OAuth2AuthenticationProperties authenticationProperties) {
        OAuth2FormLoginConfigurerCustomer configurer = new OAuth2FormLoginConfigurerCustomer(authenticationProperties);
        log.trace("[Herodotus] |- Bean [OAuth2 FormLogin Configurer Customer] Auto Configure.");
        return configurer;
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
        HerodotusJwtTokenCustomizer herodotusJwtTokenCustomizer = new HerodotusJwtTokenCustomizer();
        log.trace("[Herodotus] |- Bean [OAuth2 Jwt Token Customizer] Auto Configure.");
        return herodotusJwtTokenCustomizer;
    }

    @Bean
    public OAuth2TokenCustomizer<OAuth2TokenClaimsContext> opaqueTokenCustomizer() {
        HerodotusOpaqueTokenCustomizer herodotusOpaqueTokenCustomizer = new HerodotusOpaqueTokenCustomizer();
        log.trace("[Herodotus] |- Bean [OAuth2 Opaque Token Customizer] Auto Configure.");
        return herodotusOpaqueTokenCustomizer;
    }
}
