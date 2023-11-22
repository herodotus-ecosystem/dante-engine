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


