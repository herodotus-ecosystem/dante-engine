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

package cn.herodotus.engine.message.websocket.configuration;

import cn.herodotus.engine.assistant.core.definition.BearerTokenResolver;
import cn.herodotus.engine.message.websocket.interceptor.WebSocketChannelInterceptor;
import cn.herodotus.engine.message.websocket.interceptor.WebSocketSessionHandshakeInterceptor;
import cn.herodotus.engine.message.websocket.processor.WebSocketBearerTokenResolver;
import cn.herodotus.engine.message.websocket.processor.WebSocketMessageSender;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;

/**
 * <p>Description: WebSocket 处理器相关配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/29 15:52
 */
@Configuration(proxyBeanMethods = false)
public class WebSocketProcessorConfiguration {

    private static final Logger log = LoggerFactory.getLogger(WebSocketProcessorConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [WebSocket Processor] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public BearerTokenResolver principalResolver() {
        WebSocketBearerTokenResolver webSocketPrincipalResolver = new WebSocketBearerTokenResolver();
        log.trace("[Herodotus] |- Bean [WebSocket Principal Resolver] Auto Configure.");
        return webSocketPrincipalResolver;
    }

    @Bean
    @ConditionalOnBean(BearerTokenResolver.class)
    public WebSocketSessionHandshakeInterceptor webSocketSessionHandshakeInterceptor(BearerTokenResolver bearerTokenResolver) {
        WebSocketSessionHandshakeInterceptor webSocketSessionHandshakeInterceptor = new WebSocketSessionHandshakeInterceptor(bearerTokenResolver);
        log.trace("[Herodotus] |- Bean [Web Socket Session Handshake Interceptor] Auto Configure.");
        return webSocketSessionHandshakeInterceptor;
    }

    @Bean
    public WebSocketMessageSender webSocketMessageSender(SimpMessagingTemplate simpMessagingTemplate, SimpUserRegistry simpUserRegistry) {
        WebSocketMessageSender webSocketMessageSender = new WebSocketMessageSender();
        webSocketMessageSender.setSimpMessagingTemplate(simpMessagingTemplate);
        webSocketMessageSender.setSimpUserRegistry(simpUserRegistry);
        log.trace("[Herodotus] |- Bean [Web Socket Message Sender] Auto Configure.");
        return webSocketMessageSender;
    }

    @Bean
    @ConditionalOnMissingBean
    public WebSocketChannelInterceptor webSocketChannelInterceptor() {
        WebSocketChannelInterceptor webSocketChannelInterceptor = new WebSocketChannelInterceptor();
        log.trace("[Herodotus] |- Bean [Web Socket Channel Interceptor] Auto Configure.");
        return webSocketChannelInterceptor;
    }
}
