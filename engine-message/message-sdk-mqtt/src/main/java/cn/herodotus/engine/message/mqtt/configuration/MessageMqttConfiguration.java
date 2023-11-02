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

package cn.herodotus.engine.message.mqtt.configuration;

import cn.herodotus.engine.assistant.core.utils.type.ListUtils;
import cn.herodotus.engine.assistant.core.utils.type.NumberUtils;
import cn.herodotus.engine.message.mqtt.properties.MqttProperties;
import cn.herodotus.engine.message.mqtt.properties.Topic;
import jakarta.annotation.PostConstruct;
import org.dromara.hutool.core.util.ByteUtil;
import org.eclipse.paho.mqttv5.client.IMqttAsyncClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.persist.MqttDefaultFilePersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.mqtt.core.ClientManager;
import org.springframework.integration.mqtt.core.Mqttv5ClientManager;
import org.springframework.integration.mqtt.inbound.Mqttv5PahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.Mqttv5PahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaderMapper;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;

/**
 * <p>Description: Mqtt 模块配置 </p>
 *
 * Mqtt 协议框架中没有“客户端”和“服务端”概念，只有 Broker 和 Client。所有接入 Broker 的组件都是 Client。如果使用本组件，那么包含本组件的应用即为 Client。
 *
 * Mqtt 中的 Inbound 和 Outbound 均为 Client 中的概念，对应 Client 的数据 "输入"和 "输出"
 * · Inbound：入站，对应的是接受某个被订阅主题的数据，即 Subscribe
 * · Outbound：出站，对应的是向某个主题发送数据，即 Publish
 *
 * @author : gengwei.zheng
 * @date : 2023/9/10 17:24
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MqttProperties.class)
@IntegrationComponentScan(basePackages = {
        "cn.herodotus.engine.message.mqtt.gateway",
})
@ComponentScan(basePackages = {
        "cn.herodotus.engine.message.mqtt.messaging",
})
@Import({
        MqttReceiveHandler.class
})
public class MessageMqttConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MessageMqttConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Message Mqtt] Auto Configure.");
    }

    /**
     *
     * @return
     */
    @Bean
    public MessageChannel mqtt5InboundChannel() {
        return MessageChannels.publishSubscribe().getObject();
    }

    @Bean
    public MessageChannel mqtt5OutboundChannel() {
        return MessageChannels.direct().getObject();
    }

    @Bean
    public ClientManager<IMqttAsyncClient, MqttConnectionOptions> clientManager(MqttProperties mqttProperties) {
        MqttConnectionOptions mqttConnectionOptions = new MqttConnectionOptions();
        mqttConnectionOptions.setUserName(mqttProperties.getUsername());
        mqttConnectionOptions.setPassword(ByteUtil.toBytes(mqttProperties.getPassword(), StandardCharsets.UTF_8));
        mqttConnectionOptions.setCleanStart(mqttProperties.getCleanStart());
        mqttConnectionOptions.setKeepAliveInterval(NumberUtils.longToInt(mqttProperties.getKeepAliveInterval().getSeconds()));
        mqttConnectionOptions.setServerURIs(ListUtils.toStringArray(mqttProperties.getServerUrls()));
        mqttConnectionOptions.setAutomaticReconnect(mqttProperties.getAutomaticReconnect());
        mqttConnectionOptions.setAutomaticReconnectDelay(
                NumberUtils.longToInt(mqttProperties.getAutomaticReconnectMinDelay().getSeconds()),
                NumberUtils.longToInt(mqttProperties.getAutomaticReconnectMaxDelay().getSeconds()));
        log.info("[Herodotus] |- Bean [Mqtt Connection Options] Auto Configure.");
        Mqttv5ClientManager clientManager = new Mqttv5ClientManager(mqttConnectionOptions, mqttProperties.getClientId());
        clientManager.setPersistence(new MqttDefaultFilePersistence());
        return clientManager;
    }

    @Bean
    public MessageProducer mqttInbound(ClientManager<IMqttAsyncClient, MqttConnectionOptions> clientManager, MessageChannel mqtt5InboundChannel, MqttProperties mqttProperties) {
        Assert.notNull(mqttProperties.getSubscribes(), "'Property Subscribes' cannot be null");
        Mqttv5PahoMessageDrivenChannelAdapter messageProducer =
                new Mqttv5PahoMessageDrivenChannelAdapter(clientManager, ListUtils.toStringArray(mqttProperties.getSubscribes()));
        messageProducer.setPayloadType(String.class);
        messageProducer.setManualAcks(false);
        messageProducer.setOutputChannel(mqtt5InboundChannel);
        return messageProducer;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqtt5OutboundChannel")
    public MessageHandler mqttOutbound(ClientManager<IMqttAsyncClient, MqttConnectionOptions> clientManager, MqttProperties mqttProperties) {
        Mqttv5PahoMessageHandler messageHandler = new Mqttv5PahoMessageHandler(clientManager);
        MqttHeaderMapper mqttHeaderMapper = new MqttHeaderMapper();
        mqttHeaderMapper.setOutboundHeaderNames(MqttHeaders.RESPONSE_TOPIC, MqttHeaders.CORRELATION_DATA, MessageHeaders.CONTENT_TYPE);
        messageHandler.setHeaderMapper(mqttHeaderMapper);
        messageHandler.setDefaultTopic(mqttProperties.getDefaultTopic());
        messageHandler.setDefaultQos(mqttProperties.getDefaultQos());
        messageHandler.setAsync(true);
        messageHandler.setAsyncEvents(true);
        return messageHandler;
    }
}


