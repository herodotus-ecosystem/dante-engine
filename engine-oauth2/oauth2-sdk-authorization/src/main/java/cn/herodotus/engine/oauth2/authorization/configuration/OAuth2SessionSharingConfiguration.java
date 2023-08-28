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

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
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
public class OAuth2SessionSharingConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OAuth2SessionSharingConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- SDK [OAuth2 Session Sharing] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public <S extends Session> SessionRegistry sessionRegistry(FindByIndexNameSessionRepository<S> sessionRepository) {
        SpringSessionBackedSessionRegistry<S> springSessionBackedSessionRegistry =  new SpringSessionBackedSessionRegistry<>(sessionRepository);
        log.trace("[Herodotus] |- Bean [Spring Session Backed Session Registry] Auto Configure.");
        return springSessionBackedSessionRegistry;
    }

    @Bean
    @ConditionalOnMissingBean
    public SessionAuthenticationStrategy sessionAuthenticationStrategy (SessionRegistry sessionRegistry) {
        RegisterSessionAuthenticationStrategy registerSessionAuthenticationStrategy =  new RegisterSessionAuthenticationStrategy(sessionRegistry);
        log.trace("[Herodotus] |- Bean [Register Session Authentication Strategy] Auto Configure.");
        return registerSessionAuthenticationStrategy;
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
