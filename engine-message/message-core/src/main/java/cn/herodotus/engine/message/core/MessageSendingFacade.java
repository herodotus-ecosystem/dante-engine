/*
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君) (herodotus@aliyun.com & www.herodotus.cn)
 *
 * Dante Engine licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.gnu.org/licenses/lgpl-3.0.html>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.herodotus.engine.message.core;

import cn.herodotus.engine.assistant.core.context.ServiceContextHolder;
import cn.herodotus.engine.message.core.definition.domain.StreamMessage;
import cn.herodotus.engine.message.core.definition.domain.TemplateMessage;
import cn.herodotus.engine.message.core.definition.domain.WebSocketMessage;
import cn.herodotus.engine.message.core.definition.event.AbstractApplicationEvent;
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
     * @param event {@link AbstractApplicationEvent}
     */
    private static <T> void postProcess(AbstractApplicationEvent<T> event) {
        ServiceContextHolder.getInstance().publishEvent(event);
    }

    /**
     * 发送事件类型消息
     *
     * @param event 消息
     * @param <T>   消息实体
     */
    public static <T> void event(AbstractApplicationEvent<T> event) {
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
