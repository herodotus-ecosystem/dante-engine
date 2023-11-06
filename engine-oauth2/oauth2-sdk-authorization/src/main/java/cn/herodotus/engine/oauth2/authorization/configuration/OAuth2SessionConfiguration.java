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

import cn.herodotus.engine.oauth2.authorization.customizer.OAuth2SessionManagementConfigurerCustomer;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisIndexedHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

/**
 * <p>Description: OAuth2 Session 共享配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/27 16:05
 */
@Configuration(proxyBeanMethods = false)
@EnableRedisIndexedHttpSession
public class OAuth2SessionConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OAuth2SessionConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [OAuth2 Session Sharing] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public <S extends Session> SessionRegistry sessionRegistry(FindByIndexNameSessionRepository<S> sessionRepository) {
        SpringSessionBackedSessionRegistry<S> springSessionBackedSessionRegistry = new SpringSessionBackedSessionRegistry<>(sessionRepository);
        log.trace("[Herodotus] |- Bean [Spring Session Backed Session Registry] Auto Configure.");
        return springSessionBackedSessionRegistry;
    }

    @Bean
    @ConditionalOnMissingBean
    public OAuth2SessionManagementConfigurerCustomer sessionManagementConfigurerCustomer(SessionRegistry sessionRegistry) {
        OAuth2SessionManagementConfigurerCustomer OAuth2SessionManagementConfigurerCustomer = new OAuth2SessionManagementConfigurerCustomer(sessionRegistry);
        log.trace("[Herodotus] |- Bean [Session Management Configurer Customer] Auto Configure.");
        return OAuth2SessionManagementConfigurerCustomer;
    }

    /**
     * If a SessionRegistry @Bean is registered and is an instance of SessionRegistryImpl,
     * a HttpSessionEventPublisher @Bean SHOULD also be registered as it’s responsible for notifying SessionRegistryImpl of session lifecycle events, f
     * or example, SessionDestroyedEvent, to provide the ability to remove the SessionInformation instance.
     *
     * @return {@link HttpSessionEventPublisher}
     */
    @Bean
    @ConditionalOnMissingBean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
