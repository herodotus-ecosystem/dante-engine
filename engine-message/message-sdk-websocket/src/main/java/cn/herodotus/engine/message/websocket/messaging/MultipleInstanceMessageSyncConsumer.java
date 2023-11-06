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

package cn.herodotus.engine.message.websocket.messaging;

import cn.herodotus.engine.message.core.constants.MessageConstants;
import cn.herodotus.engine.message.core.definition.domain.WebSocketMessage;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Consumer;

/**
 * <p>Description: WebSocket 点对点消息跨实例处理 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/9/14 17:06
 */
public class MultipleInstanceMessageSyncConsumer implements Consumer<WebSocketMessage> {

    private final WebSocketMessagingTemplate webSocketMessagingTemplate;

    public MultipleInstanceMessageSyncConsumer(WebSocketMessagingTemplate webSocketMessagingTemplate) {
        this.webSocketMessagingTemplate = webSocketMessagingTemplate;
    }

    @Override
    public void accept(WebSocketMessage webSocketMessage) {
        if (StringUtils.equals(webSocketMessage.getUser(), MessageConstants.MESSAGE_TO_ALL)) {
            webSocketMessagingTemplate.broadcast(webSocketMessage.getDestination(), webSocketMessage.getPayload());
        } else {
            webSocketMessagingTemplate.pointToPoint(webSocketMessage.getUser(), webSocketMessage.getDestination(), webSocketMessage.getPayload());
        }
    }
}
