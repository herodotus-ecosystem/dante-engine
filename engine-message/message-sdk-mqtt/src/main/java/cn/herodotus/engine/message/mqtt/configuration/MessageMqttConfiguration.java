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

package cn.herodotus.engine.message.mqtt.configuration;

import cn.herodotus.engine.assistant.core.utils.type.ListUtils;
import cn.herodotus.engine.assistant.core.utils.type.NumberUtils;
import cn.herodotus.engine.message.core.constants.HerodotusChannels;
import cn.herodotus.engine.message.mqtt.annotation.ConditionalOnMqttEnabled;
import cn.herodotus.engine.message.mqtt.properties.MqttProperties;
import jakarta.annotation.PostConstruct;
import org.dromara.hutool.core.util.ByteUtil;
import org.eclipse.paho.mqttv5.client.IMqttAsyncClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.persist.MqttDefaultFilePersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.mqtt.core.ClientManager;
import org.springframework.integration.mqtt.core.Mqttv5ClientManager;
import org.springframework.integration.mqtt.inbound.Mqttv5PahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.Mqttv5PahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;

/**
 * <p>Description: Mqtt 模块配置 </p>
 * <p>
 * Mqtt 协议框架中没有“客户端”和“服务端”概念，只有 Broker 和 Client。所有接入 Broker 的组件都是 Client。如果使用本组件，那么包含本组件的应用即为 Client。
 * <p>
 * Mqtt 中的 Inbound 和 Outbound 均为 Client 中的概念，对应 Client 的数据 "输入"和 "输出"
 * · Inbound：入站，对应的是接受某个被订阅主题的数据，即 Subscribe
 * · Outbound：出站，对应的是向某个主题发送数据，即 Publish
 *
 * @author : gengwei.zheng
 * @date : 2023/9/10 17:24
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnMqttEnabled
@EnableConfigurationProperties(MqttProperties.class)
@IntegrationComponentScan(basePackages = {
        "cn.herodotus.engine.message.mqtt.gateway",
})
@ComponentScan(basePackages = {
        "cn.herodotus.engine.message.mqtt.messaging",
})
public class MessageMqttConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MessageMqttConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Message Mqtt] Auto Configure.");
    }

    @Bean(name = HerodotusChannels.MQTT_DEFAULT_INBOUND_CHANNEL)
    public MessageChannel mqttDefaultInboundChannel() {
        return MessageChannels.publishSubscribe().getObject();
    }

    @Bean(name = HerodotusChannels.MQTT_DEFAULT_OUTBOUND_CHANNEL)
    public MessageChannel mqttDefaultOutboundChannel() {
        return MessageChannels.direct().getObject();
    }

    @Bean
    public ClientManager<IMqttAsyncClient, MqttConnectionOptions> clientManager(MqttProperties mqttProperties) {
        MqttConnectionOptions options = new MqttConnectionOptions();
        options.setUserName(mqttProperties.getUsername());
        options.setPassword(ByteUtil.toBytes(mqttProperties.getPassword(), StandardCharsets.UTF_8));
        options.setCleanStart(mqttProperties.getCleanStart());
        options.setKeepAliveInterval(NumberUtils.longToInt(mqttProperties.getKeepAliveInterval().getSeconds()));
        options.setServerURIs(ListUtils.toStringArray(mqttProperties.getServerUrls()));
        options.setAutomaticReconnect(mqttProperties.getAutomaticReconnect());
        options.setAutomaticReconnectDelay(
                NumberUtils.longToInt(mqttProperties.getAutomaticReconnectMinDelay().getSeconds()),
                NumberUtils.longToInt(mqttProperties.getAutomaticReconnectMaxDelay().getSeconds()));
        Mqttv5ClientManager clientManager = new Mqttv5ClientManager(options, mqttProperties.getClientId());
        clientManager.setPersistence(new MqttDefaultFilePersistence());

        log.trace("[Herodotus] |- Bean [Mqtt Connection Options] Auto Configure.");
        return clientManager;
    }

    @Bean
    public MessageProducer mqttDefaultInbound(ClientManager<IMqttAsyncClient, MqttConnectionOptions> clientManager, @Qualifier(HerodotusChannels.MQTT_DEFAULT_INBOUND_CHANNEL) MessageChannel mqttDefaultInboundChannel, MqttProperties mqttProperties) {
        Assert.notNull(mqttProperties.getDefaultSubscribes(), "'Property Subscribes' cannot be null");
        Mqttv5PahoMessageDrivenChannelAdapter adapter = new Mqttv5PahoMessageDrivenChannelAdapter(clientManager, ListUtils.toStringArray(mqttProperties.getDefaultSubscribes()));
        adapter.setPayloadType(String.class);
        adapter.setManualAcks(false);
        adapter.setOutputChannel(mqttDefaultInboundChannel);
        log.trace("[Herodotus] |- Bean [Mqtt v5 Paho Message Driven Channel Adapter] Auto Configure.");
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = HerodotusChannels.MQTT_DEFAULT_OUTBOUND_CHANNEL)
    public MessageHandler mqttDefaultOutbound(ClientManager<IMqttAsyncClient, MqttConnectionOptions> clientManager, MqttProperties mqttProperties) {
        Mqttv5PahoMessageHandler handler = new Mqttv5PahoMessageHandler(clientManager);
        handler.setDefaultTopic(mqttProperties.getDefaultTopic());
        handler.setDefaultQos(mqttProperties.getDefaultQos());
        handler.setAsync(true);
        handler.setAsyncEvents(true);
        log.trace("[Herodotus] |- Bean [Mqtt v5 Paho Message Handler] Auto Configure.");
        return handler;
    }
}


