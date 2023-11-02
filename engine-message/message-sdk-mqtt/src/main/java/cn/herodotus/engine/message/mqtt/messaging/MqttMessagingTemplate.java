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

package cn.herodotus.engine.message.mqtt.messaging;

import cn.herodotus.engine.message.mqtt.gateway.MessageSendingGateway;
import org.springframework.stereotype.Component;

/**
 * <p>Description: Mqtt 消息发送模版 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/10/31 12:07
 */
@Component
public class MqttMessagingTemplate {

    private final MessageSendingGateway messageSendingGateway;

    public MqttMessagingTemplate(MessageSendingGateway messageSendingGateway) {
        this.messageSendingGateway = messageSendingGateway;
    }

    public void publish(String payload) {
        messageSendingGateway.publish(payload);
    }

    public void publish(Integer qos, String payload) {
        messageSendingGateway.publish(qos, payload);
    }

    public void publish(String topic, String payload) {
        messageSendingGateway.publish(topic, payload);
    }

    public void publish(String topic, Integer qos, String payload) {
        messageSendingGateway.publish(topic, qos, payload);
    }

    public void publish(String topic, String responseTopic, String correlationData, Integer qos, String payload) {
        messageSendingGateway.publish(topic, responseTopic, correlationData, qos, payload);
    }
}
