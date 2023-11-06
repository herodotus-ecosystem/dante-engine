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

package cn.herodotus.engine.message.mqtt.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * <p>Description: Mqtt 消息接收处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/11/2 13:42
 */
public class MqttMessageReceivingHandler implements MessageHandler {

    private static final Logger log = LoggerFactory.getLogger(MqttMessageReceivingHandler.class);

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        MessageHeaders headers = message.getHeaders();
        //获取消息Topic
        String receivedTopic = (String) headers.get(MqttHeaders.RECEIVED_TOPIC);
        log.info("获取到v5的消息的topic :{} ", receivedTopic);
        String responseTopic = (String) headers.get(MqttHeaders.RESPONSE_TOPIC);
        log.info("获取到v5的消息的响应主题 :{} ", responseTopic);

        String correlationData = null;
        if (Objects.nonNull(headers.get(MqttHeaders.CORRELATION_DATA))) {
            correlationData = new String((byte[]) headers.get(MqttHeaders.CORRELATION_DATA), StandardCharsets.UTF_8);
            log.info("获取到v5的消息关联数据 :{} ", correlationData);
        }
        //获取消息体
        String payload = new String((byte[]) message.getPayload(), StandardCharsets.UTF_8);
        log.info("获取到v5的消息的payload :{} ", payload);
    }
}
