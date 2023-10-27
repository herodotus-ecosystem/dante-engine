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

package cn.herodotus.engine.message.websocket.definition;

import cn.herodotus.engine.message.core.constants.MessageConstants;
import cn.herodotus.engine.message.core.definition.domain.WebSocketMessage;

/**
 * <p>Description: WebSocket 消息发送操作定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/10/26 23:32
 */
public interface WebSocketMessageSender {

    /**
     * 发送 WebSocket 点对点消息。发送信息给指定用户
     *
     * @param user        用户唯一标识
     * @param destination 消息通道
     * @param payload     消息内容
     */
    void toUser(String user, String destination, Object payload);

    /**
     * 送 WebSocket 点对点消息。发送信息给指定用户
     *
     * @param webSocketMessage 消息实体 {@link WebSocketMessage}
     */
    default void toUser(WebSocketMessage webSocketMessage) {
        toUser(webSocketMessage.getUser(), webSocketMessage.getDestination(), webSocketMessage.getPayload());
    }

    /**
     * 发送 WebSocket 广播消息。发送全员信息
     *
     * @param destination 消息通道
     * @param payload     消息内容
     */
    void toAll(String destination, Object payload);

    /**
     * 发送公告信息
     *
     * @param payload 消息内容
     */
    default void announcement(Object payload) {
        toAll(MessageConstants.WEBSOCKET_DESTINATION_BROADCAST_NOTICE, payload);
    }

    /**
     * 发送实时在线用户统计信息
     *
     * @param payload 消息内容
     */
    default void online(Object payload) {
        toAll(MessageConstants.WEBSOCKET_DESTINATION_BROADCAST_ONLINE, payload);
    }
}
