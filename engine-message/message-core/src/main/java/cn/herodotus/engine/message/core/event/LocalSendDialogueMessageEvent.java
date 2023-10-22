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

package cn.herodotus.engine.message.core.event;

import cn.herodotus.engine.message.core.definition.LocalApplicationEvent;
import cn.herodotus.engine.message.core.domain.DialogueMessage;

import java.time.Clock;

/**
 * <p>Description: 本地发送对话消息事件 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/3/11 18:40
 */
public class LocalSendDialogueMessageEvent extends LocalApplicationEvent<DialogueMessage> {

    public LocalSendDialogueMessageEvent(DialogueMessage data) {
        super(data);
    }

    public LocalSendDialogueMessageEvent(DialogueMessage data, Clock clock) {
        super(data, clock);
    }
}
