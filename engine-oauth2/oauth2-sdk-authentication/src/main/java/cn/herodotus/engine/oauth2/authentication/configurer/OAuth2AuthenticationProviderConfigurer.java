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

package cn.herodotus.engine.oauth2.authentication.configurer;

import cn.herodotus.engine.oauth2.authentication.properties.OAuth2AuthenticationProperties;
import cn.herodotus.engine.oauth2.authentication.provider.OAuth2ResourceOwnerPasswordAuthenticationProvider;
import cn.herodotus.engine.oauth2.authentication.provider.OAuth2SocialCredentialsAuthenticationProvider;
import cn.herodotus.engine.oauth2.authentication.utils.OAuth2ConfigurerUtils;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

/**
 * <p>Description: 自定义 AuthenticationProvider 配置器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/9/1 15:46
 */
public class OAuth2AuthenticationProviderConfigurer extends AbstractHttpConfigurer<OAuth2AuthenticationProviderConfigurer, HttpSecurity> {

    private final SessionRegistry sessionRegistry;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final OAuth2AuthenticationProperties authenticationProperties;

    public OAuth2AuthenticationProviderConfigurer(SessionRegistry sessionRegistry, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, OAuth2AuthenticationProperties authenticationProperties) {
        this.sessionRegistry = sessionRegistry;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.authenticationProperties = authenticationProperties;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        OAuth2AuthorizationService authorizationService = OAuth2ConfigurerUtils.getAuthorizationService(httpSecurity);
        OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator = OAuth2ConfigurerUtils.getTokenGenerator(httpSecurity);

        OAuth2ResourceOwnerPasswordAuthenticationProvider resourceOwnerPasswordAuthenticationProvider =
                new OAuth2ResourceOwnerPasswordAuthenticationProvider(authorizationService, tokenGenerator, userDetailsService, authenticationProperties);
        resourceOwnerPasswordAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        resourceOwnerPasswordAuthenticationProvider.setSessionRegistry(sessionRegistry);
        httpSecurity.authenticationProvider(resourceOwnerPasswordAuthenticationProvider);

        OAuth2SocialCredentialsAuthenticationProvider socialCredentialsAuthenticationProvider =
                new OAuth2SocialCredentialsAuthenticationProvider(authorizationService, tokenGenerator, userDetailsService, authenticationProperties);
        socialCredentialsAuthenticationProvider.setSessionRegistry(sessionRegistry);
        httpSecurity.authenticationProvider(socialCredentialsAuthenticationProvider);
    }
}
