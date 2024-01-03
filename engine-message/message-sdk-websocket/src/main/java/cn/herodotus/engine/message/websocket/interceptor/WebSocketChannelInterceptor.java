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
import cn.herodotus.engine.message.websocket.domain.WebSocketPrincipal;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.util.List;

/**
 * <p>Description: Websocket消息监听 </p>
 * <p>
 * 用于监听websocket用户连接情况
 *
 * @author : gengwei.zheng
 * @date : 2021/10/24 18:50
 */
public class WebSocketChannelInterceptor implements ChannelInterceptor {

    private static final Logger log = LoggerFactory.getLogger(WebSocketChannelInterceptor.class);

    /**
     * 在消息发送之前调用，方法中可以对消息进行修改，如果此方法返回值为空，则不会发生实际的消息发送调用
     *
     * @param message {@link Message}
     * @param channel {@link MessageChannel}
     * @return {@link Message}
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        WebSocketPrincipal principal = (WebSocketPrincipal) accessor.getUser();

        if (ObjectUtils.isEmpty(principal)) {
            log.warn("[Herodotus] |- WebSocket channel cannot fetch user principal.");
            return null;
        }

        StompCommand command = accessor.getCommand();
        if (ObjectUtils.isNotEmpty(command)) {
            switch (command) {
                /*
                 * 判断是否为首次连接请求，如果已经连接过，直接返回message
                 */
                case CONNECT -> {
                    /*
                     * 1. 如果是老版本 stomp-client，这里可以获取到 stompClient.connect(headers, function (frame){.......}) 中header的信息
                     * 2. 如果是新版本 @stomp/stompjs， 这里可以获取到 connectHeaders : {} 中的 header
                     */
                    List<String> tokenHeaders = accessor.getNativeHeader(HttpHeaders.AUTHORIZATION);
                    String token = null;
                    if (CollectionUtils.isNotEmpty(tokenHeaders)) {
                        String temp = tokenHeaders.get(0);
                        if (StringUtils.isNotBlank(temp) && StringUtils.startsWith(temp, BaseConstants.BEARER_TOKEN)) {
                            token = StringUtils.removeStartIgnoreCase(temp, BaseConstants.BEARER_TOKEN);
                        }
                    }

                    /*
                     *1. 这里直接封装到StompHeaderAccessor 中，可以根据自身业务进行改变
                     * 2. 封装StompHeaderAccessor中后，可以在@Controller / @MessageMapping注解的方法中直接带上StompHeaderAccessor 就可以通过方法提供的 getUser()方法获取到这里封装user对象
                     * 3. 例如可以在这里拿到前端的信息进行登录鉴权
                     */
                    WebSocketPrincipal user = (WebSocketPrincipal) accessor.getUser();

                    log.debug("[Herodotus] |- Authentication user [{}] transmit token [{}] from frontend.", user.getName(), token);
                }
                case DISCONNECT -> {
                }
                default -> {
                }
            }
        }

        return message;
    }

    /**
     * 在消息发送后立刻调用
     *
     * @param message {@link Message}
     * @param channel {@link MessageChannel}
     * @param sent    boolean值参数表示该调用的返回值
     */
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {

        /*
         * 拿到消息头对象后，我们可以做一系列业务操作
         * 1. 通过getSessionAttributes()方法获取到websocketSession， 就可以取到我们在WebSocketHandshakeInterceptor拦截器中存在session中的信息
         * 2. 我们也可以获取到当前连接的状态，做一些统计，例如统计在线人数，或者缓存在线人数对应的令牌，方便后续业务调用
         */
    }

    /**
     * 1. 在消息发送完成后调用，而不管消息发送是否产生异常，在此方法中，我们可以做一些资源释放清理的工作
     * 2. 此方法的触发必须是preSend方法执行成功，且返回值不为null,发生了实际的消息推送，才会触发
     *
     * @param message {@link Message}
     * @param channel {@link MessageChannel}
     * @param sent    boolean值参数表示该调用的返回值
     * @param ex      失败时抛出的 HerodotusException
     */
    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {

    }

    /**
     * 在消息被实际检索之前调用, 只适用于(PollableChannels, 轮询场景)，在websocket的场景中用不到
     *
     * @param channel channel {@link MessageChannel}
     * @return 如果返回false, 则不会对检索任何消息
     */
    @Override
    public boolean preReceive(MessageChannel channel) {
        return true;
    }

    /**
     * 在检索到消息之后，返回调用方之前调用，可以进行信息修改。适用于PollableChannels，轮询场景
     *
     * @param message {@link Message}
     * @param channel {@link MessageChannel}
     * @return 如果返回null, 就不会进行下一步操作
     */
    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        return message;
    }

    /**
     * 1. 在消息接收完成之后调用，不管发生什么异常，可以用于消息发送后的资源清理
     * 2. 只有当preReceive 执行成功，并返回true才会调用此方法
     * 3. 适用于PollableChannels，轮询场景
     *
     * @param message {@link Message}
     * @param channel {@link MessageChannel}
     * @param ex      失败时抛出的 HerodotusException
     */
    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {

    }
}
