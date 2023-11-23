/*
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君) (herodotus@aliyun.com & www.herodotus.cn)
 *
 * Dante Engine licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.gnu.org/licenses/lgpl-3.0.html>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.herodotus.engine.message.mqtt.gateway;

import cn.herodotus.engine.message.core.constants.HerodotusChannels;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * <p>Description: Mqtt 消息发送网关定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/9/11 16:10
 */
@MessagingGateway(defaultRequestChannel = HerodotusChannels.MQTT_DEFAULT_OUTBOUND_CHANNEL)
public interface MessageSendingGateway {

    /**
     * 使用 默认 Topic 和 默认 Qos 发送数据
     *
     * @param payload string 类型内容
     */
    void publish(String payload);

    /**
     * 使用 默认 Topic 和 自定义 Qos 发送数据
     *
     * @param qos     Qos 等级
     * @param payload string 类型内容
     */
    void publish(@Header(MqttHeaders.QOS) Integer qos, String payload);

    /**
     * 使用 自定义 Topic 和 Qos 发送数据
     *
     * @param topic   主题
     * @param payload string 类型内容
     */
    void publish(@Header(MqttHeaders.TOPIC) String topic, String payload);

    /**
     * 使用 自定义 Topic 和 自定义 Qos 发送数据
     *
     * @param topic   主题
     * @param qos     Qos 等级
     * @param payload string 类型内容
     */
    void publish(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) Integer qos, String payload);

    /**
     * 使用 自定义 Topic 和 自定义 Qos 发送数据
     *
     * @param topic   主题
     * @param qos     Qos 等级
     * @param payload byte[] 类型内容
     */
    void publish(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) Integer qos, byte[] payload);

    /**
     * 发送请求响应的消息（MQTT v5新特性）
     * <p>
     * 所以为 PUBLISH 报文新增了一个 对比数据（Correlation Data） 属性。
     * <p>
     * 由于发布订阅模式本身的一些局限性，使用大于 0 的 QoS 也只能保证消息到达了对端而不是订阅端，如果发布消息时订阅端还未完成订阅，那么消息就会丢失，但发布方却无法得知。因此，对于一些投递要求比较严格的消息，可以通过请求响应来确认消息是否到达订阅端。
     *
     * @param topic           请求主题
     * @param responseTopic   响应主题
     * @param correlationData 对比数据
     * @param qos             Qos 等级
     * @param payload         内容
     */
    void publish(@Header(MqttHeaders.TOPIC) String topic,
                 @Header(MqttHeaders.RESPONSE_TOPIC) String responseTopic,
                 @Header(MqttHeaders.CORRELATION_DATA) String correlationData,
                 @Header(MqttHeaders.QOS) Integer qos,
                 String payload);
}
