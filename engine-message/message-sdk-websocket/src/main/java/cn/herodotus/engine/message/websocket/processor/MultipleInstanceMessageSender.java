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

package cn.herodotus.engine.message.websocket.processor;

import cn.herodotus.engine.message.core.constants.MessageConstants;
import cn.herodotus.engine.message.core.exception.IllegalChannelException;
import cn.herodotus.engine.message.core.exception.PrincipalNotFoundException;
import cn.herodotus.engine.message.websocket.domain.WebSocketMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;

/**
 * <p>Description: Web Socket 多实例服务端消息发送 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/24 18:47
 */
public class MultipleInstanceMessageSender extends SingleInstanceMessageSender {

    private static final Logger log = LoggerFactory.getLogger(MultipleInstanceMessageSender.class);

    private final StreamBridge streamBridge;

    public MultipleInstanceMessageSender(SimpMessagingTemplate simpMessagingTemplate, SimpUserRegistry simpUserRegistry, StreamBridge streamBridge) {
        super(simpMessagingTemplate, simpUserRegistry);
        this.streamBridge = streamBridge;
    }

    /**
     * 发送给指定用户信息。
     *
     * @param webSocketMessage 发送内容参数实体 {@link WebSocketMessage}
     * @param <T>              指定 payload 类型
     * @throws IllegalChannelException    Web Socket 通道设置错误
     * @throws PrincipalNotFoundException 该服务中无法找到与 identity 对应的用户 Principal
     */
    @Override
    public <T> void toUser(WebSocketMessage<T> webSocketMessage) throws IllegalChannelException, PrincipalNotFoundException {
        // 用户在当前实例中
        if (isUserExist(webSocketMessage.getTo())) {
            super.toUser(webSocketMessage);
        } else {
            syncMessageToOtherInstances(webSocketMessage);
        }
    }

    @Override
    public <T> void sendNoticeToAll(T payload) {
        syncBroadcastMessageToOtherInstances(MessageConstants.WEBSOCKET_DESTINATION_BROADCAST_NOTICE, payload);
        super.sendNoticeToAll(payload);
    }

    @Override
    public <T> void sendOnlineToAll(T payload) {
        syncBroadcastMessageToOtherInstances(MessageConstants.WEBSOCKET_DESTINATION_BROADCAST_ONLINE, payload);
        super.sendOnlineToAll(payload);
    }

    private <T> void syncMessageToOtherInstances(WebSocketMessage<T> webSocketMessage) {
        log.debug("[Herodotus] |- Sync MESSAGE to other web socket instance.");
        streamBridge.send(MessageConstants.MULTIPLE_INSTANCE_OUTPUT, webSocketMessage);
    }

    private <T> void syncBroadcastMessageToOtherInstances(String channel, T payload) {
        WebSocketMessage<T> webSocketMessage = new WebSocketMessage<>();
        webSocketMessage.setTo(MessageConstants.MESSAGE_TO_ALL);
        webSocketMessage.setChannel(channel);
        webSocketMessage.setPayload(payload);

        syncMessageToOtherInstances(webSocketMessage);
    }
}
