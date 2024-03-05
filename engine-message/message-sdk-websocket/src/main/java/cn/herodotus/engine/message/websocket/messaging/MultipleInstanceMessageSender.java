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

package cn.herodotus.engine.message.websocket.messaging;

import cn.herodotus.engine.assistant.core.context.ServiceContextHolder;
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
