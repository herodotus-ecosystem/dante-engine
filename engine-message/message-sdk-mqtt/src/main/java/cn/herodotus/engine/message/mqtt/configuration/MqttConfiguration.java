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
import cn.herodotus.engine.message.mqtt.properties.MqttProperties;
import jakarta.annotation.PostConstruct;
import org.dromara.hutool.core.util.ByteUtil;
import org.eclipse.paho.mqttv5.client.IMqttAsyncClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.persist.MqttDefaultFilePersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.core.ClientManager;
import org.springframework.integration.mqtt.core.Mqttv5ClientManager;
import org.springframework.integration.mqtt.inbound.Mqttv5PahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.Mqttv5PahoMessageHandler;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * <p>Description: Mqtt 模块配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/9/10 17:24
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MqttProperties.class)
public class MqttConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MqttConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Message Mqtt] Auto Configure.");
    }

    @Bean
    public ClientManager<IMqttAsyncClient, MqttConnectionOptions> clientManager(MqttProperties mqttProperties) {
        MqttConnectionOptions mqttConnectionOptions = new MqttConnectionOptions();
        mqttConnectionOptions.setUserName(mqttProperties.getUsername());
        mqttConnectionOptions.setPassword(ByteUtil.toBytes(mqttProperties.getPassword(), StandardCharsets.UTF_8));
        mqttConnectionOptions.setCleanStart(mqttProperties.getCleanStart());
        mqttConnectionOptions.setKeepAliveInterval(toInt(mqttProperties.getKeepAliveInterval()));
        mqttConnectionOptions.setServerURIs(ListUtils.toStringArray(mqttProperties.getServerUrls()));
        mqttConnectionOptions.setAutomaticReconnect(mqttProperties.getAutomaticReconnect());
        mqttConnectionOptions.setAutomaticReconnectDelay(toInt(mqttProperties.getAutomaticReconnectMinDelay()), toInt(mqttProperties.getAutomaticReconnectMaxDelay()));
        log.info("[Herodotus] |- Bean [Mqtt Connection Options] Auto Configure.");
        Mqttv5ClientManager clientManager = new Mqttv5ClientManager(mqttConnectionOptions, mqttProperties.getClientId());
        clientManager.setPersistence(new MqttDefaultFilePersistence());
        return clientManager;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqtt5OutboundChannel")
    public IntegrationFlow mqtt5InFlowTopic2(ClientManager<IMqttAsyncClient, MqttConnectionOptions> clientManager) {
        Mqttv5PahoMessageDrivenChannelAdapter messageProducer = new Mqttv5PahoMessageDrivenChannelAdapter(clientManager, "topic2");
        return IntegrationFlow.from(messageProducer)
                .channel(c -> c.queue("fromMqttChannel"))
                .get();
    }

    @Bean
    public IntegrationFlow mqttOutFlow(ClientManager<IMqttAsyncClient, MqttConnectionOptions> clientManager) {

        return f -> f.handle(new Mqttv5PahoMessageHandler(clientManager));
    }

    private int toInt(Duration duration) {
        long value = duration.getSeconds();
        return Long.valueOf(value).intValue();
    }
}


