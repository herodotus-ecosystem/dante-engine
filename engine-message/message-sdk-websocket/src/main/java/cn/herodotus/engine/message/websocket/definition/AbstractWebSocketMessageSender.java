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

import cn.herodotus.engine.message.websocket.messaging.WebSocketMessagingTemplate;

/**
 * <p>Description: WebSocketMessageSender 抽象实现 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/10/26 23:45
 */
public abstract class AbstractWebSocketMessageSender implements WebSocketMessageSender {

    private final WebSocketMessagingTemplate webSocketMessagingTemplate;

    protected AbstractWebSocketMessageSender(WebSocketMessagingTemplate webSocketMessagingTemplate) {
        this.webSocketMessagingTemplate = webSocketMessagingTemplate;
    }

    @Override
    public void toUser(String user, String destination, Object payload) {
        webSocketMessagingTemplate.pointToPoint(user, destination, payload);
    }

    @Override
    public void toAll(String destination, Object payload) {
        webSocketMessagingTemplate.broadcast(destination, payload);
    }
}
