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

package cn.herodotus.engine.supplier.upms.logic.domain.event;

import cn.herodotus.engine.message.core.definition.event.AbstractApplicationEvent;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysAttribute;

import java.time.Clock;

/**
 * <p>Description: SysSecurityAttribute实体数据变更事件 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/4 22:18
 */
public class SysAttributeChangeEvent extends AbstractApplicationEvent<SysAttribute> {

    public SysAttributeChangeEvent(SysAttribute data) {
        super(data);
    }

    public SysAttributeChangeEvent(SysAttribute data, Clock clock) {
        super(data, clock);
    }
}
