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

package cn.herodotus.engine.message.core.logic.event;

import cn.herodotus.engine.message.core.definition.event.HerodotusApplicationEvent;
import cn.herodotus.engine.message.core.logic.domain.UserStatus;

import java.time.Clock;

/**
 * <p>Description: 本地用户状态变更事件 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/10 16:15
 */
public class ChangeUserStatusEvent extends HerodotusApplicationEvent<UserStatus> {

    public ChangeUserStatusEvent(UserStatus data) {
        super(data);
    }

    public ChangeUserStatusEvent(UserStatus data, Clock clock) {
        super(data, clock);
    }
}