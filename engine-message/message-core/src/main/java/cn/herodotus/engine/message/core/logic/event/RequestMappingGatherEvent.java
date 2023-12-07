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

import cn.herodotus.engine.message.core.definition.event.AbstractApplicationEvent;
import cn.herodotus.engine.message.core.logic.domain.RequestMapping;

import java.time.Clock;
import java.util.List;

/**
 * <p>Description: 本地RequestMapping收集事件 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/8 21:55
 */
public class RequestMappingGatherEvent extends AbstractApplicationEvent<List<RequestMapping>> {

    public RequestMappingGatherEvent(List<RequestMapping> data) {
        super(data);
    }

    public RequestMappingGatherEvent(List<RequestMapping> data, Clock clock) {
        super(data, clock);
    }
}
