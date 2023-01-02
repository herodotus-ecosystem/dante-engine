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

import cn.herodotus.engine.message.core.constants.MessageConstants;
import cn.herodotus.engine.message.websocket.interceptor.WebSocketChannelInterceptor;
import cn.herodotus.engine.message.websocket.interceptor.WebSocketPrincipalHandshakeHandler;
import cn.herodotus.engine.message.websocket.interceptor.WebSocketSessionHandshakeInterceptor;
import cn.herodotus.engine.message.websocket.properties.WebSocketProperties;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.session.Session;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

/**
 * <p>Description: WebSocketMessageBrokerConfigurer </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/4 19:19
 */
@AutoConfiguration(after = {WebSocketSessionHandshakeInterceptor.class})
@EnableScheduling
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfiguration extends AbstractSessionWebSocketMessageBrokerConfigurer<Session> {

    private static final Logger log = LoggerFactory.getLogger(WebSocketMessageBrokerConfiguration.class);

    private final WebSocketProperties webSocketProperties;
    private final WebSocketChannelInterceptor webSocketChannelInterceptor;
    private final WebSocketSessionHandshakeInterceptor webSocketSessionHandshakeInterceptor;

    public WebSocketMessageBrokerConfiguration(WebSocketProperties webSocketProperties, WebSocketChannelInterceptor webSocketChannelInterceptor, WebSocketSessionHandshakeInterceptor webSocketSessionHandshakeInterceptor) {
        this.webSocketProperties = webSocketProperties;
        this.webSocketChannelInterceptor = webSocketChannelInterceptor;
        this.webSocketSessionHandshakeInterceptor = webSocketSessionHandshakeInterceptor;
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [WebSocket Message Broker] Auto Configure.");
    }

    /**
     * 添加 Stomp Endpoint，创建配置客户端尝试连接地址，并对外暴露该接口，这样就可以通过websocket连接上服务
     *
     * @param registry {@link StompEndpointRegistry}
     */
    @Override
    protected void configureStompEndpoints(StompEndpointRegistry registry) {
        WebSocketPrincipalHandshakeHandler principalHandshakeHandler = new WebSocketPrincipalHandshakeHandler();

        /*
         * 1. 将 /serviceName/stomp路径注册为Stomp的端点，
         *    用户连接了这个端点后就可以进行websocket通讯，支持socketJs
         * 2. setAllowedOriginPatterns("*")表示可以跨域
         * 3. withSockJS()表示支持socktJS访问
         * 4. 添加自定义拦截器，这个拦截器是上一个demo自己定义的获取httpsession的拦截器
         */
        registry.addEndpoint(webSocketProperties.getEndpoint())
                .setAllowedOrigins("*")
                .addInterceptors(webSocketSessionHandshakeInterceptor)
                .setHandshakeHandler(principalHandshakeHandler)
                .withSockJS();

        registry.addEndpoint(webSocketProperties.getEndpoint())
                .setAllowedOrigins("*")
                .addInterceptors(webSocketSessionHandshakeInterceptor)
                .setHandshakeHandler(principalHandshakeHandler);
    }

    /**
     * 配置消息代理
     *
     * @param registry {@link MessageBrokerRegistry}
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /*
         *  enableStompBrokerRelay 配置外部的STOMP服务，需要安装额外的支持 比如rabbitmq或activemq
         * 1. 配置代理域，可以配置多个，此段代码配置代理目的地的前缀为 /topicTest 或者 /userTest
         *    我们就可以在配置的域上向客户端推送消息
         * 3. 可以通过 setRelayHost 配置代理监听的host,默认为localhost
         * 4. 可以通过 setRelayPort 配置代理监听的端口，默认为61613
         * 5. 可以通过 setClientLogin 和 setClientPasscode 配置账号和密码
         * 6. setxxx这种设置方法是可选的，根据业务需要自行配置，也可以使用默认配置
         */
//        registry.enableStompBrokerRelay("/topicTest", "/userTest")
//                .setRelayHost("rabbit.someotherserver")
//                .setRelayPort(62623);
//                .setClientLogin("userName")
//                .setClientPasscode("password");

        // 自定义调度器，用于控制心跳线程
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        // 线程池线程数，心跳连接开线程
        taskScheduler.setPoolSize(1);
        // 线程名前缀
        taskScheduler.setThreadNamePrefix("herodotus-websocket-heartbeat-thread-");
        // 初始化
        taskScheduler.initialize();

        /*
         * spring 内置broker对象 广播式应配置一个/topic消息代理,点对点应配置一个/user消息代理，
         * 1. 配置代理域，可以配置多个，此段代码配置代理目的地的前缀为 /topicTest 或者 /userTest 我们就可以在配置的域上向客户端推送消息
         * 2. 进行心跳设置，第一值表示server最小能保证发的心跳间隔毫秒数, 第二个值代码server希望client发的心跳间隔毫秒数
         * 3. 可以配置心跳线程调度器 setHeartbeatValue这个不能单独设置，不然不起作用，要配合setTaskScheduler才可以生效
         *    调度器我们可以自己写一个，也可以自己使用默认的调度器 new DefaultManagedTaskScheduler()
         */
        registry.enableSimpleBroker(MessageConstants.WEBSOCKET_CHANNEL_PROXY_BROADCAST, MessageConstants.WEBSOCKET_CHANNEL_PROXY_PERSONAL)
                .setHeartbeatValue(new long[]{10000, 10000})
                .setTaskScheduler(taskScheduler);

        /*
         * 全局使用的消息前缀（客户端订阅路径上会体现出来）
         * "/app" 为配置应用服务器的地址前缀，表示所有以/app 开头的客户端消息或请求
         *  都会路由到带有@MessageMapping 注解的方法中
         */
        String[] applicationDestinationPrefixes = webSocketProperties.getApplicationPrefixes();
        if (ArrayUtils.isNotEmpty(applicationDestinationPrefixes)) {
            registry.setApplicationDestinationPrefixes(applicationDestinationPrefixes);
        }

        /*
         * 点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
         * 1. 配置一对一消息前缀， 客户端接收一对一消息需要配置的前缀 如“'/user/'+userid + '/message'”，
         *    是客户端订阅一对一消息的地址 stompClient.subscribe js方法调用的地址
         * 2. 使用@SendToUser发送私信的规则不是这个参数设定，在框架内部是用UserDestinationMessageHandler处理，
         *    而不是 AnnotationMethodMessageHandler 或  SimpleBrokerMessageHandler
         *    or StompBrokerRelayMessageHandler，是在@SendToUser的URL前加“user+sessionId"组成
         */
        if (StringUtils.isNotBlank(webSocketProperties.getUserDestinationPrefix())) {
            registry.setUserDestinationPrefix(webSocketProperties.getUserDestinationPrefix());
        }

        /*
         * 自定义路径分割符
         * 注释掉的这段代码添加的分割符为. 分割是类级别的@messageMapping和方法级别的@messageMapping的路径
         * 例如类注解路径为 “topic”,方法注解路径为“hello”，那么客户端JS stompClient.send 方法调用的路径为“/app/topic.hello”
         * 注释掉此段代码后，类注解路径“/topic”,方法注解路径“/hello”,JS调用的路径为“/app/topic/hello”
         */
        //registry.setPathMatcher(new AntPathMatcher("."));
    }

    /**
     * 配置发送与接收的消息参数，可以指定消息字节大小，缓存大小，发送超时时间
     *
     * @param registration {@link WebSocketTransportRegistration}
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        /*
         * 1. setMessageSizeLimit 设置消息缓存的字节数大小 字节
         * 2. setSendBufferSizeLimit 设置websocket会话时，缓存的大小 字节
         * 3. setSendTimeLimit 设置消息发送会话超时时间，毫秒
         */
        registration.setMessageSizeLimit(10240)
                .setSendBufferSizeLimit(10240)
                .setSendTimeLimit(10000);
        super.configureWebSocketTransport(registration);
    }

    /**
     * 采用自定义拦截器，获取connect时候传递的参数
     * 设置输入消息通道的线程数，默认线程为1，可以自己自定义线程数，最大线程数，线程存活时间
     *
     * @param registration {@link ChannelRegistration}
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        /*
         * 配置消息线程池
         * 1. corePoolSize 配置核心线程池，当线程数小于此配置时，不管线程中有无空闲的线程，都会产生新线程处理任务
         * 2. maxPoolSize 配置线程池最大数，当线程池数等于此配置时，不会产生新线程
         * 3. keepAliveSeconds 线程池维护线程所允许的空闲时间，单位秒
         */
        registration.taskExecutor()
                .corePoolSize(10)
                .maxPoolSize(20)
                .keepAliveSeconds(60);

        /*
         * 添加stomp自定义拦截器，可以根据业务做一些处理
         * 消息拦截器，实现ChannelInterceptor接口
         */
        registration.interceptors(webSocketChannelInterceptor);
        super.configureClientInboundChannel(registration);
    }

    /**
     * 设置输出消息通道的线程数，默认线程为1，可以自己自定义线程数，最大线程数，线程存活时间
     *
     * @param registration {@link ChannelRegistration}
     */
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(10)
                .maxPoolSize(20)
                .keepAliveSeconds(60);
    }
}
