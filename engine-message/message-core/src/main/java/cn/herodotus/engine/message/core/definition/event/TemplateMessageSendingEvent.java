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

package cn.herodotus.engine.message.core.definition.event;

import cn.herodotus.engine.message.core.definition.domain.TemplateMessage;

import java.time.Clock;

/**
 * <p>Description: Spring 框架 MessageTemplate 类型消息发送事件 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/10/26 15:16
 */
public class TemplateMessageSendingEvent<T extends TemplateMessage> extends AbstractApplicationEvent<T> {
    public TemplateMessageSendingEvent(T data) {
        super(data);
    }

    public TemplateMessageSendingEvent(T data, Clock clock) {
        super(data, clock);
    }
}
