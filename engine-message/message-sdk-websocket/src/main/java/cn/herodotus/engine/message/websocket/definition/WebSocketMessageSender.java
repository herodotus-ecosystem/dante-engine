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
