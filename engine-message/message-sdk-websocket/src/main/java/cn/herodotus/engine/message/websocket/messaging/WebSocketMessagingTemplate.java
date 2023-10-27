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

package cn.herodotus.engine.message.websocket.messaging;

import cn.herodotus.engine.message.websocket.definition.WebSocketMessageSender;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;

/**
 * <p>Description: WebSocket 消息发送模版 </p>
 * <p>
 * 单独抽取一个 MessagingTemplate 用作 WebSocket 消息发送的基本操作类。
 * {@link MultipleInstanceMessageSender}、{@link SingleInstanceMessageSender} 和 {@link MultipleInstanceMessageSyncConsumer} 三个类均使用该类进行基础操作。
 * 这样做的原因是多实例情况下还包含消息的同步，发送消息同步的实例既是同步消息的生产者，又是消费者。如果统一使用 {@link WebSocketMessageSender} 注入的 Bean。就不好区分各种情况，就会出现发送同步消息，存在循环发送消息的风险。
 *
 * @author : gengwei.zheng
 * @date : 2023/10/26 23:26
 */
public class WebSocketMessagingTemplate {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final SimpUserRegistry simpUserRegistry;

    public WebSocketMessagingTemplate(SimpMessagingTemplate simpMessagingTemplate, SimpUserRegistry simpUserRegistry) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.simpUserRegistry = simpUserRegistry;
    }

    /**
     * 发送 WebSocket 点对点消息。发送信息给指定用户
     *
     * @param user        用户唯一标识
     * @param destination 消息同奥
     * @param payload     消息内容
     */
    public void pointToPoint(String user, String destination, Object payload) {
        if (isUserExist(user)) {
            simpMessagingTemplate.convertAndSendToUser(user, destination, payload);
        }
    }

    /**
     * 发送 WebSocket 广播消息。发送全员信息
     *
     * @param destination 消息同奥
     * @param payload     消息内容
     */
    public void broadcast(String destination, Object payload) {
        simpMessagingTemplate.convertAndSend(destination, payload);
    }

    /**
     * 根据用户 ID 获取到对应的 WebSocket 用户
     *
     * @param userId 系统用户ID
     * @return WebSocket 用户 {@link SimpUser}
     */
    public SimpUser getUser(String userId) {
        return simpUserRegistry.getUser(userId);
    }

    /**
     * 判断 WebSocket用户是否存在。
     * <p>
     * 注意：只能查询到当前所在 WebSocket实例中的实时 WebSocket 用户信息。如果实时用户在不同的实例中，则查询不到。
     *
     * @param userId 用户ID
     * @return true 用户存在，false 用户不存在
     */
    public boolean isUserExist(String userId) {
        return ObjectUtils.isNotEmpty(getUser(userId));
    }

    public SimpMessagingTemplate getSimpMessagingTemplate() {
        return simpMessagingTemplate;
    }

    public SimpUserRegistry getSimpUserRegistry() {
        return simpUserRegistry;
    }
}
