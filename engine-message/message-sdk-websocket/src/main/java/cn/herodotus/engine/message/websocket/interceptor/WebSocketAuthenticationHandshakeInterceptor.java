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

import cn.herodotus.engine.assistant.core.support.BearerTokenResolver;
import cn.herodotus.engine.assistant.definition.constants.BaseConstants;
import cn.herodotus.engine.assistant.definition.constants.SymbolConstants;
import cn.herodotus.engine.assistant.definition.domain.oauth2.PrincipalDetails;
import cn.herodotus.engine.message.websocket.utils.WebSocketUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * <p>Description: WebSocketSessionHandshakeInterceptor </p>
 * <p>
 * 不是开启websocket的必要步骤，根据自身的业务逻辑决定是否添加拦截器
 * <p>
 * 当前主要处理 Token 获取，以及 Token 的验证。如果验证成功，使用返回的用户名进行下一步，如果验证失败返回 false 终止握手。
 *
 * @author : gengwei.zheng
 * @date : 2022/12/4 21:34
 */
public class WebSocketAuthenticationHandshakeInterceptor implements HandshakeInterceptor {

    private static final Logger log = LoggerFactory.getLogger(WebSocketAuthenticationHandshakeInterceptor.class);

    private static final String SEC_WEBSOCKET_PROTOCOL = com.google.common.net.HttpHeaders.SEC_WEBSOCKET_PROTOCOL;

    private final BearerTokenResolver bearerTokenResolver;

    public WebSocketAuthenticationHandshakeInterceptor(BearerTokenResolver bearerTokenResolver) {
        this.bearerTokenResolver = bearerTokenResolver;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        HttpServletRequest httpServletRequest = WebSocketUtils.getHttpServletRequest(request);

        if (ObjectUtils.isNotEmpty(httpServletRequest)) {

            String protocol = httpServletRequest.getHeader(SEC_WEBSOCKET_PROTOCOL);

            String token = determineToken(protocol);

            if (StringUtils.isNotBlank(token)) {
                PrincipalDetails details = bearerTokenResolver.resolve(token);
                attributes.put(BaseConstants.PRINCIPAL, details);
                log.debug("[Herodotus] |- WebSocket fetch the token is [{}].", token);
            } else {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                log.info("[Herodotus] |- Token is invalid for WebSocket, stop handshake.");
                return false;
            }
        }

        return true;
    }

    private String determineToken(String protocol) {
        if (StringUtils.contains(protocol, SymbolConstants.COMMA)) {
            String[] protocols = StringUtils.split(protocol, SymbolConstants.COMMA);
            for (String item : protocols) {
                if (!StringUtils.endsWith(item, ".stomp")) {
                    return item;
                }
            }
        }
        return null;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {

        HttpServletRequest httpServletRequest = WebSocketUtils.getHttpServletRequest(request);
        HttpServletResponse httpServletResponse = WebSocketUtils.getHttpServletResponse(response);

        if (ObjectUtils.isNotEmpty(httpServletRequest) && ObjectUtils.isNotEmpty(httpServletResponse)) {
            httpServletResponse.setHeader(SEC_WEBSOCKET_PROTOCOL, "v10.stomp");
        }

        log.info("[Herodotus] |- WebSocket handshake success!");
    }
}
