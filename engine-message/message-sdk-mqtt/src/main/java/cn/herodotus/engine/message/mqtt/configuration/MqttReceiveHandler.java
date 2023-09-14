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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/9/11 16:08
 */
@Configuration(proxyBeanMethods = false)
public class MqttReceiveHandler {

    private static final Logger log = LoggerFactory.getLogger(MqttReceiveHandler.class);

    @Bean
    public MessageHandler handlerMqtt5Message() {
        return message -> {
            MessageHeaders headers = message.getHeaders();
            //获取消息Topic
            String receivedTopic = (String) headers.get(MqttHeaders.RECEIVED_TOPIC);
            log.info("获取到v5的消息的topic :{} ", receivedTopic);
            String responseTopic = (String) headers.get(MqttHeaders.RESPONSE_TOPIC);
            log.info("获取到v5的消息的响应主题 :{} ", responseTopic);

            String correlationData = null;
            if (Objects.nonNull(headers.get(MqttHeaders.CORRELATION_DATA))) {
                correlationData = new String((byte[]) Objects.requireNonNull(headers.get(MqttHeaders.CORRELATION_DATA)), StandardCharsets.UTF_8);
                log.info("获取到v5的消息关联数据 :{} ", correlationData);
            }
            //获取消息体
            String payload = new String((byte[]) message.getPayload(), StandardCharsets.UTF_8);
            log.info("获取到v5的消息的payload :{} ", payload);
        };
    }
}
