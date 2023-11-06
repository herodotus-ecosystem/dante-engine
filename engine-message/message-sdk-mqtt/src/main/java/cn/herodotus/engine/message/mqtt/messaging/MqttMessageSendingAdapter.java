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

package cn.herodotus.engine.message.mqtt.messaging;

import cn.herodotus.engine.message.core.definition.MessageSendingAdapter;
import cn.herodotus.engine.message.core.definition.domain.MqttMessage;
import cn.herodotus.engine.message.core.definition.event.MqttMessageSendingEvent;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * <p>Description: Mqtt 消息发送适配器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/11/2 13:43
 */
@Component
public class MqttMessageSendingAdapter implements MessageSendingAdapter<MqttMessage, MqttMessageSendingEvent> {

    private final MqttMessagingTemplate mqttMessagingTemplate;

    public MqttMessageSendingAdapter(MqttMessagingTemplate mqttMessagingTemplate) {
        this.mqttMessagingTemplate = mqttMessagingTemplate;
    }

    @Override
    public void onApplicationEvent(MqttMessageSendingEvent event) {
        MqttMessage mqttMessage = event.getData();

        if (StringUtils.isNotBlank(mqttMessage.getTopic()) && ObjectUtils.isNotEmpty(mqttMessage.getQos())) {
            if (StringUtils.isNotBlank(mqttMessage.getResponseTopic()) && StringUtils.isNotBlank(mqttMessage.getCorrelationData())) {
                mqttMessagingTemplate.publish(mqttMessage.getTopic(),
                        mqttMessage.getResponseTopic(),
                        mqttMessage.getCorrelationData(),
                        mqttMessage.getQos(),
                        mqttMessage.getPayload());
            } else {
                mqttMessagingTemplate.publish(mqttMessage.getTopic(), mqttMessage.getQos(), mqttMessage.getPayload());
            }
        } else {
            if (StringUtils.isNotBlank(mqttMessage.getTopic())) {
                mqttMessagingTemplate.publish(mqttMessage.getTopic(), mqttMessage.getPayload());
            }

            if (ObjectUtils.isNotEmpty(mqttMessage.getQos())) {
                mqttMessagingTemplate.publish(mqttMessage.getQos(), mqttMessage.getPayload());
            }
        }
    }
}
