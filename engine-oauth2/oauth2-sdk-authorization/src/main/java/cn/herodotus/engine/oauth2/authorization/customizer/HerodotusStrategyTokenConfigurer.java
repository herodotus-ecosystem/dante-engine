/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.oauth2.authorization.customizer;

import cn.herodotus.engine.assistant.core.enums.Target;
import cn.herodotus.engine.oauth2.authorization.converter.HerodotusJwtAuthenticationConverter;
import cn.herodotus.engine.oauth2.authorization.introspector.HerodotusOpaqueTokenIntrospector;
import cn.herodotus.engine.oauth2.core.properties.SecurityProperties;
import cn.herodotus.engine.oauth2.core.response.HerodotusAccessDeniedHandler;
import cn.herodotus.engine.oauth2.core.response.HerodotusAuthenticationEntryPoint;
import cn.herodotus.engine.web.core.properties.EndpointProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.util.Assert;

/**
 * <p>Description: Token 配置 通用代码 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/10/14 17:29
 */
public class HerodotusStrategyTokenConfigurer {

    public static Builder from(OAuth2ResourceServerConfigurer<HttpSecurity> configurer) {
        return new Builder(configurer);
    }

    public static class Builder {

        private JwtDecoder jwtDecoder;
        private SecurityProperties securityProperties;
        private OAuth2ResourceServerProperties resourceServerProperties;
        private EndpointProperties endpointProperties;

        private Target target = Target.REMOTE;

        private final OAuth2ResourceServerConfigurer<HttpSecurity> configurer;

        public Builder(OAuth2ResourceServerConfigurer<HttpSecurity> configurer) {
            this.configurer = configurer;
        }

        public Builder jwtDecoder(JwtDecoder jwtDecoder) {
            this.jwtDecoder = jwtDecoder;
            return this;
        }

        public Builder securityProperties(SecurityProperties securityProperties) {
            this.securityProperties = securityProperties;
            return this;
        }

        public Builder resourceServerProperties(OAuth2ResourceServerProperties resourceServerProperties) {
            this.resourceServerProperties = resourceServerProperties;
            return this;
        }

        public Builder endpointProperties(EndpointProperties endpointProperties) {
            this.endpointProperties = endpointProperties;
            return this;
        }

        public OAuth2ResourceServerConfigurer<HttpSecurity> build() {
            Assert.notNull(this.jwtDecoder, "jwtDecoder must be set");
            Assert.notNull(this.securityProperties, "securityProperties must be set");
            Assert.notNull(this.resourceServerProperties, "resourceServerProperties must be set");
            Assert.notNull(this.endpointProperties, "endpointProperties must be set");

            if (this.securityProperties.getValidate() == Target.REMOTE) {
                this.configurer
                        .opaqueToken(opaque -> opaque.introspector(new HerodotusOpaqueTokenIntrospector(this.endpointProperties, this.resourceServerProperties)))
                        .accessDeniedHandler(new HerodotusAccessDeniedHandler())
                        .authenticationEntryPoint(new HerodotusAuthenticationEntryPoint());
            } else {
                this.configurer
                        .jwt(jwt -> jwt.decoder(this.jwtDecoder).jwtAuthenticationConverter(new HerodotusJwtAuthenticationConverter()))
                        .bearerTokenResolver(new DefaultBearerTokenResolver())
                        .accessDeniedHandler(new HerodotusAccessDeniedHandler())
                        .authenticationEntryPoint(new HerodotusAuthenticationEntryPoint());
            }
            return this.configurer;
        }
    }
}
