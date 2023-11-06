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

package cn.herodotus.engine.message.websocket.definition;

import cn.herodotus.engine.cache.redis.utils.RedisBitMapUtils;
import cn.herodotus.engine.message.core.constants.MessageConstants;
import cn.herodotus.engine.message.websocket.domain.WebSocketPrincipal;
import cn.herodotus.engine.message.websocket.utils.WebSocketUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * <p>Description: 公共 WebSocketUserListener </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/29 22:20
 */
public abstract class AbstractWebSocketStatusListener<E extends ApplicationEvent> implements ApplicationListener<E> {

    private static final Logger log = LoggerFactory.getLogger(AbstractWebSocketStatusListener.class);

    private final WebSocketMessageSender webSocketMessageSender;

    public AbstractWebSocketStatusListener(WebSocketMessageSender webSocketMessageSender) {
        this.webSocketMessageSender = webSocketMessageSender;
    }

    private void changeStatus(WebSocketPrincipal principal, boolean isOnline) {
        if (ObjectUtils.isNotEmpty(principal)) {

            RedisBitMapUtils.setBit(MessageConstants.REDIS_CURRENT_ONLINE_USER, principal.getName(), isOnline);

            String status = isOnline ? "Online" : "Offline";

            log.debug("[Herodotus] |- WebSocket user [{}] is [{}].", principal, status);

            int count = WebSocketUtils.getOnlineCount();

            webSocketMessageSender.online(count);
        }
    }

    protected void connected(WebSocketPrincipal principal) {
        changeStatus(principal, true);
    }

    protected void disconnected(WebSocketPrincipal principal) {
        changeStatus(principal, false);
    }
}
