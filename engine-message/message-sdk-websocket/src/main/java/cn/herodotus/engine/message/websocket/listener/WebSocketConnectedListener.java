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

package cn.herodotus.engine.message.websocket.listener;

import cn.herodotus.engine.message.websocket.definition.AbstractWebSocketStatusListener;
import cn.herodotus.engine.message.websocket.definition.WebSocketMessageSender;
import cn.herodotus.engine.message.websocket.domain.WebSocketPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

/**
 * <p>Description: WebSocketUserOnlineListener </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/29 22:17
 */
@Component
public class WebSocketConnectedListener extends AbstractWebSocketStatusListener<SessionConnectedEvent> {

    public WebSocketConnectedListener(WebSocketMessageSender webSocketMessageSender) {
        super(webSocketMessageSender);
    }

    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        WebSocketPrincipal principal = (WebSocketPrincipal) event.getUser();

        connected(principal);
    }
}
