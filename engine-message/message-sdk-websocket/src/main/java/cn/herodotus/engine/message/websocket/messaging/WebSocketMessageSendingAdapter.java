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

import cn.herodotus.engine.message.core.definition.MessageSendingAdapter;
import cn.herodotus.engine.message.core.definition.domain.WebSocketMessage;
import cn.herodotus.engine.message.core.definition.event.TemplateMessageSendingEvent;
import cn.herodotus.engine.message.websocket.definition.WebSocketMessageSender;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>Description: WebSocket 消息发送适配器 </p>
 * <p>
 * 用于模块外部，统一门面方式发送消息使用。
 *
 * @author : gengwei.zheng
 * @date : 2023/10/26 20:18
 */
public class WebSocketMessageSendingAdapter implements MessageSendingAdapter<WebSocketMessage, TemplateMessageSendingEvent<WebSocketMessage>> {

    private final WebSocketMessageSender webSocketMessageSender;

    public WebSocketMessageSendingAdapter(WebSocketMessageSender webSocketMessageSender) {
        this.webSocketMessageSender = webSocketMessageSender;
    }


    @Override
    public void onApplicationEvent(TemplateMessageSendingEvent<WebSocketMessage> event) {
        WebSocketMessage message = event.getData();

        if (StringUtils.isNotBlank(message.getUser())) {
            webSocketMessageSender.toUser(message.getUser(), message.getDestination(), message.getPayload());
        } else {
            webSocketMessageSender.toAll(message.getDestination(), message.getPayload());
        }
    }
}
