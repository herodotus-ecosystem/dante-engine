/*
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君) (herodotus@aliyun.com & www.herodotus.cn)
 *
 * Dante Engine licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.gnu.org/licenses/lgpl.html>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.herodotus.engine.message.websocket.autoconfigre;

import cn.herodotus.engine.assistant.core.definition.BearerTokenResolver;
import cn.herodotus.engine.message.websocket.annotation.ConditionalOnMultipleWebSocketInstance;
import cn.herodotus.engine.message.websocket.annotation.ConditionalOnSingleWebSocketInstance;
import cn.herodotus.engine.message.websocket.configuration.WebSocketMessageBrokerConfiguration;
import cn.herodotus.engine.message.websocket.definition.WebSocketMessageSender;
import cn.herodotus.engine.message.websocket.domain.WebSocketMessage;
import cn.herodotus.engine.message.websocket.interceptor.WebSocketAuthenticationHandshakeInterceptor;
import cn.herodotus.engine.message.websocket.processor.MultipleInstanceMessageSender;
import cn.herodotus.engine.message.websocket.processor.MultipleInstanceMessageSyncConsumer;
import cn.herodotus.engine.message.websocket.processor.SingleInstanceMessageSender;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;

import java.util.function.Consumer;

/**
 * <p>Description: WebSocket 处理器相关配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/29 15:52
 */
@AutoConfiguration
public class MessageWebSocketAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MessageWebSocketAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Message WebSocket] Auto Configure.");
    }

    @Bean
    public WebSocketAuthenticationHandshakeInterceptor webSocketPrincipalHandshakeHandler(BearerTokenResolver bearerTokenResolver) {
        WebSocketAuthenticationHandshakeInterceptor webSocketAuthenticationHandshakeInterceptor = new WebSocketAuthenticationHandshakeInterceptor(bearerTokenResolver);
        log.trace("[Herodotus] |- Bean [WebSocket Authentication Handshake Interceptor] Auto Configure.");
        return webSocketAuthenticationHandshakeInterceptor;
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnSingleWebSocketInstance
    static class SingleInstanceConfiguration {
        @Bean
        @ConditionalOnMissingBean
        public WebSocketMessageSender webSocketMessageSender(SimpMessagingTemplate simpMessagingTemplate, SimpUserRegistry simpUserRegistry) {
            SingleInstanceMessageSender singleInstanceMessageSender = new SingleInstanceMessageSender(simpMessagingTemplate, simpUserRegistry);
            log.debug("[Herodotus] |- Strategy [Single Instance Web Socket Message Sender] Auto Configure.");
            return singleInstanceMessageSender;
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnMultipleWebSocketInstance
    static class MultipleInstanceConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public WebSocketMessageSender webSocketMessageSender(SimpMessagingTemplate simpMessagingTemplate, SimpUserRegistry simpUserRegistry, StreamBridge streamBridge) {
            MultipleInstanceMessageSender multipleInstanceMessageSender = new MultipleInstanceMessageSender(simpMessagingTemplate, simpUserRegistry, streamBridge);
            log.debug("[Herodotus] |- Strategy [Single Instance Web Socket Message Sender] Auto Configure.");
            return multipleInstanceMessageSender;
        }

        @Bean
        public <T> Consumer<WebSocketMessage<T>> webSocketConsumer(WebSocketMessageSender webSocketMessageSender) {
            MultipleInstanceMessageSyncConsumer<T> multipleInstanceMessageSyncConsumer = new MultipleInstanceMessageSyncConsumer<>(webSocketMessageSender);
            log.trace("[Herodotus] |- Bean [Multiple Instance Message Receiver] Auto Configure.");
            return multipleInstanceMessageSyncConsumer;
        }
    }

    @Configuration(proxyBeanMethods = false)
    @Import({
            WebSocketMessageBrokerConfiguration.class,
    })
    @ComponentScan(basePackages = {
            "cn.herodotus.engine.message.websocket.controller",
            "cn.herodotus.engine.message.websocket.listener",
    })
    static class WebSocketConfiguration {

    }
}
