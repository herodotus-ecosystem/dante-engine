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

package cn.herodotus.engine.message.core;

import cn.herodotus.engine.assistant.core.context.ServiceContextHolder;
import cn.herodotus.engine.message.core.definition.domain.StreamMessage;
import cn.herodotus.engine.message.core.definition.domain.TemplateMessage;
import cn.herodotus.engine.message.core.definition.domain.WebSocketMessage;
import cn.herodotus.engine.message.core.definition.event.HerodotusApplicationEvent;
import cn.herodotus.engine.message.core.definition.event.StreamMessageSendingEvent;
import cn.herodotus.engine.message.core.definition.event.TemplateMessageSendingEvent;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>Description: 统一消息发送门面 </p>
 * <p>
 * 将 Spring Event、Spring Cloud Stream、WebSocket 消息队列等多种消息发送方式融合，构建统一的消息发送门面，以统一的接口支持各种类型的消息发送
 *
 * @author : gengwei.zheng
 * @date : 2023/10/26 11:38
 */
class MessageSendingFacade {

    /**
     * 发送消息
     *
     * @param event {@link HerodotusApplicationEvent}
     */
    private static <T> void postProcess(HerodotusApplicationEvent<T> event) {
        ServiceContextHolder.getInstance().publishEvent(event);
    }

    /**
     * 发送事件类型消息
     *
     * @param event 消息
     * @param <T>   消息实体
     */
    public static <T> void event(HerodotusApplicationEvent<T> event) {
        postProcess(event);
    }

    /**
     * 发送 MessageTemplate 类型消息
     *
     * @param message 消息
     * @param <T>     继承 {@link TemplateMessage} 类型消息实体
     */
    public static <T extends TemplateMessage> void template(T message) {
        postProcess(new TemplateMessageSendingEvent<>(message));
    }

    /**
     * 发送 Spring Cloud Stream 类型消息
     *
     * @param message 消息
     * @param <T>     继承 {@link StreamMessage} 类型消息实体
     */
    public static <T extends StreamMessage> void stream(T message) {
        postProcess(new StreamMessageSendingEvent<>(message));
    }

    /**
     * 发送 WebSocket 点对点消息
     *
     * @param user        用户唯一标识
     * @param destination 消息同奥
     * @param payload     消息内容
     * @param <T>         消息内容类型
     */
    public static <T> void pointToPoint(String user, String destination, T payload) {
        WebSocketMessage message = new WebSocketMessage();
        if (StringUtils.isNotBlank(user)) {
            message.setUser(user);
        }
        message.setDestination(destination);
        message.setPayload(payload);
        template(message);
    }

    /**
     * 发送 WebSocket 广播消息
     *
     * @param destination 消息同奥
     * @param payload     消息内容
     * @param <T>         消息内容类型
     */
    public static <T> void broadcast(String destination, T payload) {
        pointToPoint(null, destination, payload);
    }
}
