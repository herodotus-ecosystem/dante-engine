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

package cn.herodotus.engine.oauth2.management.configuration;

import cn.herodotus.engine.oauth2.authentication.configuration.OAuth2AuthenticationConfiguration;
import cn.herodotus.engine.oauth2.authentication.stamp.SignInFailureLimitedStampManager;
import cn.herodotus.engine.oauth2.data.jpa.configuration.OAuth2DataJpaConfiguration;
import cn.herodotus.engine.oauth2.management.compliance.listener.AuthenticationSuccessListener;
import cn.herodotus.engine.oauth2.management.response.OAuth2DeviceVerificationResponseHandler;
import cn.herodotus.engine.oauth2.management.response.OidcClientRegistrationResponseHandler;
import cn.herodotus.engine.oauth2.management.service.OAuth2ComplianceService;
import cn.herodotus.engine.oauth2.management.service.OAuth2DeviceService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * <p>Description: OAuth2 Manager 模块配置 </p>
 * <p>
 * {@link org.springframework.security.oauth2.jwt.JwtTimestampValidator}
 *
 * @author : gengwei.zheng
 * @date : 2022/2/26 12:35
 */
@Configuration(proxyBeanMethods = false)
@Import({OAuth2DataJpaConfiguration.class, OAuth2AuthenticationConfiguration.class, OAuth2ComplianceConfiguration.class})
@EntityScan(basePackages = {
        "cn.herodotus.engine.oauth2.management.entity"
})
@EnableJpaRepositories(basePackages = {
        "cn.herodotus.engine.oauth2.management.repository",
})
@ComponentScan(basePackages = {
        "cn.herodotus.engine.oauth2.management.service",
        "cn.herodotus.engine.oauth2.management.controller",
})
public class OAuth2ManagementConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OAuth2ManagementConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [OAuth2 Management] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationSuccessListener authenticationSuccessListener(SignInFailureLimitedStampManager stampManager, OAuth2ComplianceService complianceService, OAuth2DeviceService deviceService) {
        AuthenticationSuccessListener listener = new AuthenticationSuccessListener(stampManager, complianceService);
        log.trace("[Herodotus] |- Bean [OAuth2 Authentication Success Listener] Auto Configure.");
        return listener;
    }

    @Bean
    @ConditionalOnMissingBean
    public OAuth2DeviceVerificationResponseHandler oauth2DeviceVerificationResponseHandler(OAuth2DeviceService oauth2DeviceService) {
        OAuth2DeviceVerificationResponseHandler handler = new OAuth2DeviceVerificationResponseHandler(oauth2DeviceService);
        log.trace("[Herodotus] |- Bean [OAuth2 Device Verification Response Handler] Auto Configure.");
        return handler;
    }

    @Bean
    @ConditionalOnMissingBean
    public OidcClientRegistrationResponseHandler oidcClientRegistrationResponseHandler(OAuth2DeviceService oauth2DeviceService) {
        OidcClientRegistrationResponseHandler handler = new OidcClientRegistrationResponseHandler(oauth2DeviceService);
        log.trace("[Herodotus] |- Bean [Oidc Client Registration Response Handler] Auto Configure.");
        return handler;
    }

}
