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
import cn.herodotus.engine.message.core.exception.IllegalChannelException;
import cn.herodotus.engine.message.core.exception.PrincipalNotFoundException;
import cn.herodotus.engine.message.websocket.domain.WebSocketMessage;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;

/**
 * <p>Description: WebSocket 消息发送器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/9/14 16:34
 */
public interface WebSocketMessageSender {

    /**
     * 发送给指定用户信息
     *
     * @param webSocketMessage 发送内容参数实体 {@link WebSocketMessage}
     * @param <T>              payload 内容对象类型
     * @throws IllegalChannelException    Web Socket 通道设置错误
     * @throws PrincipalNotFoundException 该服务中无法找到与 identity 对应的用户 Principal
     */
    <T> void toUser(WebSocketMessage<T> webSocketMessage) throws IllegalChannelException, PrincipalNotFoundException;

    /**
     * 发送全员消息
     *
     * @param channel 消息通道
     * @param payload 内容
     * @param <T>     payload 内容对象类型
     */
    <T> void toAll(String channel, T payload);

    /**
     * 广播 WebSocket 信息
     *
     * @param payload 发送的内容
     * @param <T>     payload 内容对象类型
     */
    default <T> void sendNoticeToAll(T payload) {
        toAll(MessageConstants.WEBSOCKET_DESTINATION_BROADCAST_NOTICE, payload);
    }

    /**
     * 广播 WebSocket 信息
     *
     * @param payload 发送的内容
     * @param <T>     payload 内容对象类型
     */
    default <T> void sendOnlineToAll(T payload) {
        toAll(MessageConstants.WEBSOCKET_DESTINATION_BROADCAST_ONLINE, payload);
    }

    /**
     * 根据用户ID获取 WebSocket 用户
     *
     * @param userId 用户ID
     * @return {@link SimpUser}
     */
    SimpUser getUser(String userId);

    /**
     * 判断用户是否存在
     *
     * @param userId 用户ID
     * @return true 用户存在，false 用户不存在
     */
    default boolean isUserExist(String userId) {
        return ObjectUtils.isNotEmpty(getUser(userId));
    }

    /**
     * 获取到 SimpMessagingTemplate
     *
     * @return {@link SimpMessagingTemplate}
     */
    SimpMessagingTemplate getSimpMessagingTemplate();
}
