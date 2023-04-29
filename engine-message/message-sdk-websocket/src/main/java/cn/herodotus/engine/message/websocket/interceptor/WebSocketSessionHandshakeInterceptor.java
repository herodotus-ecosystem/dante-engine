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

package cn.herodotus.engine.message.websocket.interceptor;

import cn.herodotus.engine.assistant.core.definition.BearerTokenResolver;
import cn.herodotus.engine.assistant.core.definition.constants.BaseConstants;
import cn.herodotus.engine.assistant.core.definition.constants.SymbolConstants;
import cn.herodotus.engine.assistant.core.domain.PrincipalDetails;
import cn.herodotus.engine.message.websocket.utils.WebSocketUtils;
import cn.herodotus.engine.rest.core.utils.WebUtils;
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
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

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
public class WebSocketSessionHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    private static final Logger log = LoggerFactory.getLogger(WebSocketSessionHandshakeInterceptor.class);

    private static final String SEC_WEBSOCKET_PROTOCOL = com.google.common.net.HttpHeaders.SEC_WEBSOCKET_PROTOCOL;

    private final BearerTokenResolver bearerTokenResolver;

    public WebSocketSessionHandshakeInterceptor(BearerTokenResolver bearerTokenResolver) {
        this.bearerTokenResolver = bearerTokenResolver;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        HttpServletRequest httpServletRequest = WebSocketUtils.getHttpServletRequest(request);

        if (ObjectUtils.isNotEmpty(httpServletRequest)) {

            String protocol = httpServletRequest.getHeader(SEC_WEBSOCKET_PROTOCOL);

            String token = determineToken(protocol);

            if (StringUtils.isNotBlank(token)) {
                log.debug("[Herodotus] |- WebSocket fetch the token is [{}].", token);

                PrincipalDetails details = bearerTokenResolver.resolve(token);
                if (ObjectUtils.isNotEmpty(details)) {
                    attributes.put(BaseConstants.PRINCIPAL, details);
                    attributes.put(HttpSessionHandshakeInterceptor.HTTP_SESSION_ID_ATTR_NAME, WebUtils.getSession(httpServletRequest));
                } else {
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    log.info("[Herodotus] |- Token is invalid for WebSocket, stop handshake.");
                    return false;
                }
            }
        }

        // 调用父类方法
        return super.beforeHandshake(request, response, wsHandler, attributes);
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
