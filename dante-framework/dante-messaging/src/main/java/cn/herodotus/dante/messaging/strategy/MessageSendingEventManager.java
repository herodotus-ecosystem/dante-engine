/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 是 Dante Cloud 系统核心组件库，采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dante.messaging.strategy;

import cn.herodotus.dante.messaging.definition.Message;
import cn.herodotus.dante.messaging.domain.BroadcastMessage;
import cn.herodotus.dante.messaging.domain.MqttMessage;
import cn.herodotus.dante.messaging.domain.UserMessage;
import cn.herodotus.dante.messaging.enums.MessageCategory;
import cn.herodotus.dante.spring.event.DestinationStrategyEventManager;

/**
 * <p>Description: 统一消息发送事件管理器定义 </p>
 * <p>
 * {@link MessageSendingEventManager} 目前只有 OAuth2 相关的扩展业务逻辑在使用，逻辑上将其放到 stirrup-oauth2-commons 模块中，才更加内聚。
 * 在实践中，将其放到 stirrup-oauth2-commons 中，其他代码在引用时就会多了一层依赖，并间接引入不需要引入的依赖。因此最终还是将其放回到 stirrup-message-commons 模块中
 *
 * @author : gengwei.zheng
 * @date : 2024/10/25 14:59
 */
public interface MessageSendingEventManager extends DestinationStrategyEventManager<Message<?>> {

    /**
     * 向 Mqtt Broker 发送消息
     *
     * @param mqttMessage {@link MqttMessage}
     */
    default void mqtt(MqttMessage mqttMessage) {
        postProcess(new Message<>(MessageCategory.MQTT, mqttMessage));
    }

    /**
     * 发送 Websocket 广播消息
     * <p>
     * 当 Message 服务为 Servlet 模式时，可使用该方法发送基于 Websocket 的广播消息。
     * <p>
     * 注意：因为 Websocket 的特性使然，只有当有客户端已经连接了 Websocket Server（例如：使用前端登录某个或者某些用户，Websocket 服务内存中已经有了用户信息）才能接收到消息。
     *
     * @param broadcastMessage {@link BroadcastMessage}
     */
    default void websocketBroadcast(BroadcastMessage broadcastMessage) {
        postProcess(new Message<>(MessageCategory.WEBSOCKET_BROADCAST, broadcastMessage));
    }

    /**
     * 向某个在线用户发送基于 Websocket 的消息
     * <p>
     * 当 Message 服务为 Servlet 模式时，可使用该方法发送基于 Websocket 的广播消息。
     * <p>
     * 注意：因为 Websocket 的特性使然，只有当有客户端已经连接了 Websocket Server（例如：使用前端登录某个或者某些用户，Websocket 服务内存中已经有了用户信息）才能接收到消息。
     *
     * @param userMessage {@link UserMessage}
     */
    default void websocketToUser(UserMessage userMessage) {
        postProcess(new Message<>(MessageCategory.WEBSOCKET_USER, userMessage));
    }
}
