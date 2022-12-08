/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine Licensed under the Apache License, Version 2.0 (the "License");
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
 * 2.请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.message.websocket.configuration;

import cn.herodotus.engine.message.websocket.interceptor.WebSocketChannelInterceptor;
import cn.herodotus.engine.message.websocket.processor.WebSocketMessageSender;
import cn.herodotus.engine.message.websocket.properties.WebSocketProperties;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;

/**
 * <p>Description: Web Socket 核心配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/24 18:54
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({WebSocketProperties.class})

public class WebSocketConfiguration {

    private static final Logger log = LoggerFactory.getLogger(WebSocketConfiguration.class);

//    @Autowired
//    private SimpMessagingTemplate simpMessagingTemplate;
//    @Autowired
//    private SimpUserRegistry simpUserRegistry;
//    @Autowired
//    private RedissonClient redissonClient;

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Message WebSocket] Auto Configure.");
    }

    @Bean
    public WebSocketChannelInterceptor webSocketChannelInterceptor(WebSocketProperties webSocketProperties) {
        WebSocketChannelInterceptor webSocketChannelInterceptor = new WebSocketChannelInterceptor();
        webSocketChannelInterceptor.setWebSocketProperties(webSocketProperties);
        log.trace("[Herodotus] |- Bean [Web Socket Inbound Channel Interceptor] Auto Configure.");
        return webSocketChannelInterceptor;
    }

//
    @Bean
    public WebSocketMessageSender webSocketMessageSender(SimpMessagingTemplate simpMessagingTemplate, SimpUserRegistry simpUserRegistry, WebSocketProperties webSocketProperties) {
        WebSocketMessageSender webSocketMessageSender = new WebSocketMessageSender();
        webSocketMessageSender.setSimpMessagingTemplate(simpMessagingTemplate);
        webSocketMessageSender.setSimpUserRegistry(simpUserRegistry);
        webSocketMessageSender.setWebSocketProperties(webSocketProperties);
        log.trace("[Herodotus] |- Bean [Web Socket Message Sender] Auto Configure.");
        return webSocketMessageSender;
    }
//
//    @Bean
//    @ConditionalOnBean(WebSocketMessageSender.class)
//    public WebSocketClusterProcessor webSocketClusterProcessor(WebSocketMessageSender webSocketMessageSender) {
//        WebSocketClusterProcessor webSocketClusterProcessor = new WebSocketClusterProcessor();
//        webSocketClusterProcessor.setWebSocketProperties(webSocketProperties);
//        webSocketClusterProcessor.setWebSocketMessageSender(webSocketMessageSender);
//        webSocketClusterProcessor.setRedissonClient(redissonClient);
//        log.trace("[Herodotus] |- Bean [Web Socket Cluster Processor] Auto Configure.");
//        return webSocketClusterProcessor;
//    }

    @Configuration(proxyBeanMethods = false)
    @Import({
            WebSocketMessageBrokerConfiguration.class
    })
        @ComponentScan(basePackages = {
            "cn.herodotus.engine.message.websocket.controller"
    })
    static class HerodotusWebSocketMessageBrokerConfiguration {

    }
}
