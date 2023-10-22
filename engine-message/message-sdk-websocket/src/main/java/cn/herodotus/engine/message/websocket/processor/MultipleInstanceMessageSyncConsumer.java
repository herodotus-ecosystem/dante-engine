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
import cn.herodotus.engine.message.websocket.definition.WebSocketMessageSender;
import cn.herodotus.engine.message.websocket.domain.WebSocketMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * <p>Description: WebSocket 点对点消息跨实例处理 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/9/14 17:06
 */
public class MultipleInstanceMessageSyncConsumer<T> implements Consumer<WebSocketMessage<T>> {

    private static final Logger log = LoggerFactory.getLogger(MultipleInstanceMessageSyncConsumer.class);

    private final WebSocketMessageSender webSocketMessageSender;

    public MultipleInstanceMessageSyncConsumer(WebSocketMessageSender webSocketMessageSender) {
        this.webSocketMessageSender = webSocketMessageSender;
    }

    @Override
    public void accept(WebSocketMessage<T> webSocketMessage) {
        if (StringUtils.equals(webSocketMessage.getTo(), MessageConstants.MESSAGE_TO_ALL)) {
            if (StringUtils.equals(webSocketMessage.getChannel(), MessageConstants.WEBSOCKET_DESTINATION_BROADCAST_NOTICE)) {
                webSocketMessageSender.sendNoticeToAll(webSocketMessage.getPayload());
            } else {
                webSocketMessageSender.sendOnlineToAll(webSocketMessage.getPayload());
            }
        } else {
            // 如果当前 WebSocket 实例用，可以找到该用户就 WebSocket 消息
            if (webSocketMessageSender.isUserExist(webSocketMessage.getTo())) {
                log.debug("[Herodotus] |- Web socket send message to user [{}].", webSocketMessage.getTo());
                // 注意：这里不要直接使用 webSocketMessageSender 中的 toUser 方法。这里会注入 MultipleInstanceMessageSender。
                // MultipleInstanceMessageSender 中的 toUser 方法中包含多实例同步消息的发送。如果调用了这个方法，发现用户不再该实例中，就会发送同步消息，存在循环发送消息的风险。
                // 正常情况下不会出现消息循环，就怕不小心改错了
                webSocketMessageSender.getSimpMessagingTemplate().convertAndSendToUser(webSocketMessage.getTo(), webSocketMessage.getChannel(), webSocketMessage.getPayload());
            }

            log.warn("[Herodotus] |- Current web socket instance can not found user [{}].", webSocketMessage.getTo());
        }

    }
}
