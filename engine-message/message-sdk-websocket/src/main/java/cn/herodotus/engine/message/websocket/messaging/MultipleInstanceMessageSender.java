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

package cn.herodotus.engine.message.websocket.messaging;

import cn.herodotus.stirrup.core.foundation.context.ServiceContextHolder;
import cn.herodotus.engine.message.core.constants.MessageConstants;
import cn.herodotus.engine.message.core.definition.domain.StreamMessage;
import cn.herodotus.engine.message.core.definition.domain.WebSocketMessage;
import cn.herodotus.engine.message.core.definition.event.StreamMessageSendingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Description: Web Socket 多实例服务端消息发送器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/10/26 23:29
 */
public class MultipleInstanceMessageSender extends SingleInstanceMessageSender {

    private static final Logger log = LoggerFactory.getLogger(MultipleInstanceMessageSender.class);

    public MultipleInstanceMessageSender(WebSocketMessagingTemplate webSocketMessagingTemplate) {
        super(webSocketMessagingTemplate);
    }

    @Override
    public void toUser(String user, String destination, Object payload) {
        syncToUserMessage(user, destination, payload);
        super.toUser(user, destination, payload);
    }

    @Override
    public void toAll(String destination, Object payload) {
        syncToAllMessage(destination, payload);
        super.toAll(destination, payload);
    }

    private void syncMessageToOtherInstants(WebSocketMessage webSocketMessage) {
        StreamMessage streamMessage = new StreamMessage();
        streamMessage.setBindingName(MessageConstants.MULTIPLE_INSTANCE_OUTPUT);
        streamMessage.setData(webSocketMessage);

        log.debug("[Herodotus] |- Sync message to other WebSocket instance.");
        ServiceContextHolder.getInstance().publishEvent(new StreamMessageSendingEvent<>(streamMessage));
    }

    private void syncToUserMessage(String user, String destination, Object payload) {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setUser(user);
        webSocketMessage.setDestination(destination);
        webSocketMessage.setPayload(payload);
        syncMessageToOtherInstants(webSocketMessage);
    }

    private void syncToAllMessage(String destination, Object payload) {
        syncToUserMessage(MessageConstants.MESSAGE_TO_ALL, destination, payload);
    }
}
