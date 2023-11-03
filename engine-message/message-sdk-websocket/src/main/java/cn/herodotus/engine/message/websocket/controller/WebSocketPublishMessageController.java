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

package cn.herodotus.engine.message.websocket.controller;

import cn.herodotus.engine.message.core.constants.MessageConstants;
import cn.herodotus.engine.message.core.definition.domain.WebSocketMessage;
import cn.herodotus.engine.message.core.logic.domain.DialogueMessage;
import cn.herodotus.engine.message.core.logic.event.SendDialogueMessageEvent;
import cn.herodotus.engine.message.websocket.definition.WebSocketMessageSender;
import cn.herodotus.engine.message.websocket.domain.WebSocketPrincipal;
import cn.herodotus.engine.rest.core.definition.context.AbstractApplicationContextAware;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: 前端使用的 publish 响应接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/5 17:49
 */
@RestController
public class WebSocketPublishMessageController extends AbstractApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(WebSocketPublishMessageController.class);

    private final WebSocketMessageSender webSocketMessageSender;

    public WebSocketPublishMessageController(WebSocketMessageSender webSocketMessageSender) {
        this.webSocketMessageSender = webSocketMessageSender;
    }

    @MessageMapping("/public/notice")
    @SendTo(MessageConstants.WEBSOCKET_DESTINATION_BROADCAST_NOTICE)
    public String notice(String message, StompHeaderAccessor headerAccessor) {
        System.out.println("---message---" + message);
        if (ObjectUtils.isNotEmpty(headerAccessor)) {
            System.out.println("---id---" + headerAccessor.getUser().getName());
        }

        return message;
    }

    /**
     * 发送私信消息。
     *
     * @param detail         前端数据 {@link DialogueMessage}
     * @param headerAccessor 在WebSocketChannelInterceptor拦截器中绑定上的对象
     */
    @MessageMapping("/private/message")
    public void sendPrivateMessage(@Payload DialogueMessage detail, StompHeaderAccessor headerAccessor) {

        WebSocketMessage response = new WebSocketMessage();
        response.setUser(detail.getReceiverId());
        response.setDestination(MessageConstants.WEBSOCKET_DESTINATION_PERSONAL_MESSAGE);

        if (StringUtils.isNotBlank(detail.getReceiverId()) && StringUtils.isNotBlank(detail.getReceiverName())) {
            if (StringUtils.isBlank(detail.getSenderId()) && StringUtils.isBlank(detail.getSenderName())) {
                WebSocketPrincipal sender = (WebSocketPrincipal) headerAccessor.getUser();
                detail.setSenderId(sender.getUserId());
                detail.setSenderName(sender.getUsername());
                detail.setSenderAvatar(sender.getAvatar());
            }

            this.publishEvent(new SendDialogueMessageEvent(detail));

            response.setPayload("私信发送成功");
        } else {
            response.setPayload("私信发送失败，参数错误");
        }

        webSocketMessageSender.toUser(response);
    }
}
