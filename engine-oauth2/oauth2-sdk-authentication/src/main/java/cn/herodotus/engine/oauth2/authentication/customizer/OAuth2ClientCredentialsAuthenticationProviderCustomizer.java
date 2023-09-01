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

package cn.herodotus.engine.oauth2.authentication.customizer;

import cn.herodotus.engine.oauth2.authentication.provider.OAuth2ClientCredentialsAuthenticationProvider;
import cn.herodotus.engine.oauth2.authentication.utils.OAuth2ConfigurerUtils;
import cn.herodotus.engine.oauth2.core.definition.service.ClientDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.util.List;
import java.util.function.Consumer;

/**
 * <p>Description: OAuth2ClientCredentialsAuthenticationProvider 扩展 </p>
 * <p>
 * 用于替换 SAS 默认配置的 OAuth2ClientCredentialsAuthenticationProvider，以实现功能的扩展
 *
 * @author : gengwei.zheng
 * @date : 2023/9/1 14:29
 */
public class OAuth2ClientCredentialsAuthenticationProviderCustomizer implements Consumer<List<AuthenticationProvider>> {

    private static final Logger log = LoggerFactory.getLogger(OAuth2ClientCredentialsAuthenticationProviderCustomizer.class);

    private final HttpSecurity httpSecurity;
    private final ClientDetailsService clientDetailsService;

    public OAuth2ClientCredentialsAuthenticationProviderCustomizer(HttpSecurity httpSecurity, ClientDetailsService clientDetailsService) {
        this.httpSecurity = httpSecurity;
        this.clientDetailsService = clientDetailsService;
    }

    @Override
    public void accept(List<AuthenticationProvider> authenticationProviders) {
        authenticationProviders.removeIf(authenticationProvider ->
                authenticationProvider instanceof org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientCredentialsAuthenticationProvider);

        OAuth2AuthorizationService authorizationService = OAuth2ConfigurerUtils.getAuthorizationService(httpSecurity);
        OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator = OAuth2ConfigurerUtils.getTokenGenerator(httpSecurity);
        OAuth2ClientCredentialsAuthenticationProvider provider = new OAuth2ClientCredentialsAuthenticationProvider(authorizationService, tokenGenerator, clientDetailsService);
        log.debug("[Herodotus] |- Custom OAuth2ClientCredentialsAuthenticationProvider is in effect!");
        authenticationProviders.add(provider);
    }
}
