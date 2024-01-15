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

package cn.herodotus.engine.message.websocket.interceptor;

import cn.herodotus.engine.assistant.definition.constants.BaseConstants;
import cn.herodotus.engine.assistant.definition.domain.oauth2.PrincipalDetails;
import cn.herodotus.engine.message.websocket.domain.WebSocketPrincipal;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * <p>Description: 设置认证用户信息 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/24 18:52
 */
public class WebSocketPrincipalHandshakeHandler extends DefaultHandshakeHandler {

    private static final Logger log = LoggerFactory.getLogger(WebSocketPrincipalHandshakeHandler.class);

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {

        Object object = attributes.get(BaseConstants.PRINCIPAL);

        if (ObjectUtils.isNotEmpty(object) && object instanceof PrincipalDetails details) {
            WebSocketPrincipal webSocketPrincipal = new WebSocketPrincipal(details);
            log.debug("[Herodotus] |- Determine user by request parameter, userId is  [{}].", webSocketPrincipal.getUserId());
            return webSocketPrincipal;
        }

        Principal principal = request.getPrincipal();
        if (ObjectUtils.isNotEmpty(principal)) {
            log.debug("[Herodotus] |- Determine user from request, value is  [{}].", principal.getName());
            return principal;
        }

        log.warn("[Herodotus] |- Can not determine user from request.");
        return null;
    }
}
