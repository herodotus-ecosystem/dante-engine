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

package cn.herodotus.stirrup.oauth2.authorization.autoconfigure.stream;

import cn.herodotus.engine.message.core.definition.MessageSendingAdapter;
import cn.herodotus.engine.message.core.definition.domain.StreamMessage;
import cn.herodotus.engine.message.core.definition.event.StreamMessageSendingEvent;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.cloud.stream.function.StreamBridge;

/**
 * <p>Description: Spring Cloud Stream 消息发送适配器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/10/26 18:01
 */
public class StreamMessageSendingAdapter implements MessageSendingAdapter<StreamMessage, StreamMessageSendingEvent<StreamMessage>> {

    private final StreamBridge streamBridge;

    public StreamMessageSendingAdapter(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void onApplicationEvent(StreamMessageSendingEvent<StreamMessage> event) {
        StreamMessage message = event.getData();

        if (ObjectUtils.isEmpty(message.getBinderName())) {
            if (ObjectUtils.isEmpty(message.getOutputContentType())) {
                streamBridge.send(message.getBindingName(), message.getBinderName(), message.getData());
            } else {
                streamBridge.send(message.getBindingName(), message.getBinderName(), message.getData(), message.getOutputContentType());
            }
        } else {
            if (ObjectUtils.isEmpty(message.getOutputContentType())) {
                streamBridge.send(message.getBindingName(), message.getData());
            } else {
                streamBridge.send(message.getBindingName(), message.getData(), message.getOutputContentType());
            }
        }
    }
}
