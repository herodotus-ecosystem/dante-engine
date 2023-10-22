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

import cn.herodotus.engine.cache.redis.utils.RedisBitMapUtils;
import cn.herodotus.engine.message.core.constants.MessageConstants;
import cn.herodotus.engine.message.websocket.definition.AbstractWebSocketListener;
import cn.herodotus.engine.message.websocket.definition.WebSocketMessageSender;
import cn.herodotus.engine.message.websocket.domain.WebSocketPrincipal;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * <p>Description: WebSocketUserDisconnectListener </p>
 * <p>
 * 使用监听方式，主要为了将 WebSocket 基本配置，与应用操作解耦。避免产生注入循环。
 *
 * @author : gengwei.zheng
 * @date : 2022/12/29 22:30
 */
@Component
public class WebSocketDisconnectListener extends AbstractWebSocketListener<SessionDisconnectEvent> {

    private static final Logger log = LoggerFactory.getLogger(WebSocketDisconnectListener.class);

    public WebSocketDisconnectListener(WebSocketMessageSender webSocketMessageSender) {
        super(webSocketMessageSender);
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        WebSocketPrincipal principal = (WebSocketPrincipal) event.getUser();

        if (ObjectUtils.isNotEmpty(principal)) {

            RedisBitMapUtils.setBit(MessageConstants.REDIS_CURRENT_ONLINE_USER, principal.getName(), false);

            log.debug("[Herodotus] |- WebSocket user [{}] Offline.", principal);

            this.syncUserCountToAll();
        }
    }
}
