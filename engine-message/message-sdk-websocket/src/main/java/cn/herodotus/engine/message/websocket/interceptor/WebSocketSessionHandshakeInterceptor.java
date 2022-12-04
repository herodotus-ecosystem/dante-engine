/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.message.websocket.interceptor;

import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * <p>Description: WebSocketHandler 拦截器 </p>
 * <p>
 * 不是开启websocket的必要步骤，根据自身的业务逻辑决定是否添加拦截器
 *
 * @author : gengwei.zheng
 * @date : 2022/12/4 21:34
 */
public class WebSocketSessionHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    private static final Logger log = LoggerFactory.getLogger(WebSocketSessionHandshakeInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // websocket握手建立前调用，获取httpsession
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequset = (ServletServerHttpRequest) request;

            // 这里从request中获取session,获取不到不创建，可以根据业务处理此段
            HttpSession httpSession = servletRequset.getServletRequest().getSession(false);
            if (ObjectUtils.isNotEmpty(httpSession)) {
                // 这里打印一下session id 方便等下对比和springMVC获取到httpsession是不是同一个
                log.info("[Herodotus] |- WebSocket session id is : [{}]" + httpSession.getId());

                // 获取到httpsession后，可以根据自身业务，操作其中的信息，这里只是单纯的和websocket进行关联
                attributes.put("SESSION", httpSession);
            } else {
                log.warn("[Herodotus] |- WebSocket session id is null");
            }
        }

        // 调用父类方法
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        log.info("[Herodotus] |- WebSocket handshake success!");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
