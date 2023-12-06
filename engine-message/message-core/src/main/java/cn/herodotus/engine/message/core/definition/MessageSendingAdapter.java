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

package cn.herodotus.engine.message.core.definition;

import cn.herodotus.engine.message.core.definition.domain.Message;
import cn.herodotus.engine.message.core.definition.event.AbstractApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * <p>Description: 消息发送适配器 </p>
 * <p>
 * 各种类型消息发送组件，基于该接口实现各自的消息发送。
 *
 * @author : gengwei.zheng
 * @date : 2023/10/26 16:46
 */
public interface MessageSendingAdapter<D extends Message, E extends AbstractApplicationEvent<D>> extends ApplicationListener<E> {

}
