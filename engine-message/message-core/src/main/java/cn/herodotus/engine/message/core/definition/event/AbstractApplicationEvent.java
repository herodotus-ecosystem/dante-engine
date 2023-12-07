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

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * <p>Description: 自定义 Application Event 基础类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/4 15:14
 */
public abstract class AbstractApplicationEvent<T> extends ApplicationEvent {

    private final T data;

    public AbstractApplicationEvent(T data) {
        super(data);
        this.data = data;
    }

    public AbstractApplicationEvent(T data, Clock clock) {
        super(data, clock);
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
