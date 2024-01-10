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

package cn.herodotus.engine.oauth2.data.jpa.configuration;

import cn.herodotus.stirrup.oauth2.data.jpa.service.HerodotusAuthorizationConsentService;
import cn.herodotus.stirrup.oauth2.data.jpa.service.HerodotusAuthorizationService;
import cn.herodotus.stirrup.oauth2.data.jpa.service.HerodotusRegisteredClientService;
import cn.herodotus.engine.oauth2.data.jpa.storage.JpaOAuth2AuthorizationConsentService;
import cn.herodotus.engine.oauth2.data.jpa.storage.JpaOAuth2AuthorizationService;
import cn.herodotus.engine.oauth2.data.jpa.storage.JpaRegisteredClientRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

/**
 * <p>Description: OAuth2 Manager 模块配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/3/1 18:25
 */
@Configuration(proxyBeanMethods = false)
@Import({
        cn.herodotus.stirrup.oauth2.data.jpa.configuration.OAuth2DataJpaConfiguration.class
})
public class OAuth2DataJpaConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OAuth2DataJpaConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [OAuth2 Data JPA] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public RegisteredClientRepository registeredClientRepository(HerodotusRegisteredClientService herodotusRegisteredClientService, PasswordEncoder passwordEncoder) {
        JpaRegisteredClientRepository jpaRegisteredClientRepository = new JpaRegisteredClientRepository(herodotusRegisteredClientService, passwordEncoder);
        log.debug("[Herodotus] |- Bean [Jpa Registered Client Repository] Auto Configure.");
        return jpaRegisteredClientRepository;
    }

    @Bean
    @ConditionalOnMissingBean
    public OAuth2AuthorizationService authorizationService(HerodotusAuthorizationService herodotusAuthorizationService, RegisteredClientRepository registeredClientRepository) {
        JpaOAuth2AuthorizationService jpaOAuth2AuthorizationService = new JpaOAuth2AuthorizationService(herodotusAuthorizationService, registeredClientRepository);
        log.debug("[Herodotus] |- Bean [Jpa OAuth2 Authorization Service] Auto Configure.");
        return jpaOAuth2AuthorizationService;
    }

    @Bean
    @ConditionalOnMissingBean
    public OAuth2AuthorizationConsentService authorizationConsentService(HerodotusAuthorizationConsentService herodotusAuthorizationConsentService, RegisteredClientRepository registeredClientRepository) {
        JpaOAuth2AuthorizationConsentService jpaOAuth2AuthorizationConsentService = new JpaOAuth2AuthorizationConsentService(herodotusAuthorizationConsentService, registeredClientRepository);
        log.debug("[Herodotus] |- Bean [Jpa OAuth2 Authorization Consent Service] Auto Configure.");
        return jpaOAuth2AuthorizationConsentService;
    }
}
