/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.message.websocket.controller;

import cn.herodotus.engine.assistant.core.json.jackson2.utils.JacksonUtils;
import cn.herodotus.engine.message.core.constants.MessageConstants;
import cn.herodotus.engine.message.websocket.domain.WebSocketMessage;
import cn.herodotus.engine.message.websocket.domain.WebSocketPrincipal;
import cn.herodotus.engine.message.websocket.processor.WebSocketMessageSender;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>Description: 前端使用的 publish 响应接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/5 17:49
 */
@RestController
public class WebSocketPublishMessageController {

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
     * 然后服务器你就把资源给我就行了，别的用户就不用你广播推送了，简单点，就是我请求，你就推送给我
     * 如果一个帐号打开了多个浏览器窗口，也就是打开了多个websocket session通道，
     * 这时，spring webscoket默认会把消息推送到同一个帐号不同的session，
     * 可以利用broadcast = false把避免推送到所有的session中
     *
     * @param data           前端数据
     * @param headerAccessor 在WebSocketChannelInterceptor拦截器中绑定上的对象
     * @return
     */
    @MessageMapping("/private/message")
    @SendToUser(value = MessageConstants.WEBSOCKET_DESTINATION_PERSONAL_MESSAGE)
    public Map<String, Object> sendPrivateMessage(String data, StompHeaderAccessor headerAccessor) {
        // 这里拿到的user对象是在WebSocketChannelInterceptor拦截器中绑定上的对象
        WebSocketPrincipal user = (WebSocketPrincipal) headerAccessor.getUser();

        Map<String, Object> result = JacksonUtils.toMap(data, String.class, Object.class);

        log.debug("[Herodotus] |- Send to personal [{}] at session [{}] with data [{}]", user.getName(), headerAccessor.getSessionId(), data);

        return result;
    }

    /**
     * 根据ID 把消息推送给指定用户
     * 1. 这里用了 @SendToUser 和 返回值 其意义是可以在发送成功后回执给发送放其信息发送成功
     * 2. 非必须，如果实际业务不需要关心此，可以不用@SendToUser注解，方法返回值为void
     * 3. 这里接收人的参数是用restful风格带过来了，websocket把参数带到后台的方式很多，除了url路径，
     * 还可以在header中封装用@Header或者@Headers去取等多种方式
     *
     * @param accountId      消息接收人ID
     * @param data           前端数据
     * @param headerAccessor 在WebSocketChannelInterceptor拦截器中绑定上的对象
     * @return
     */
    @MessageMapping("/private/message/{accountId}")
    public Map<String, Object> sendMessageToUser(@DestinationVariable(value = "accountId") String accountId, String data, StompHeaderAccessor headerAccessor) {
        WebSocketPrincipal user = (WebSocketPrincipal) headerAccessor.getUser();

        Map<String, Object> result = JacksonUtils.toMap(data, String.class, Object.class);

        log.debug("[Herodotus] |- Send to user [{}] at session [{}] with data [{}]", user.getName(), headerAccessor.getSessionId(), data);

        WebSocketMessage<String> message = new WebSocketMessage<>();
        message.setChannel(MessageConstants.WEBSOCKET_DESTINATION_PERSONAL_MESSAGE);
        message.setTo(accountId);
        message.setPayload("hello world");

        webSocketMessageSender.toUser(message);

        return result;
    }
}
