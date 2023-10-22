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

package cn.herodotus.engine.supplier.upms.logic.domain.listener;

import cn.herodotus.engine.rest.core.definition.context.AbstractApplicationContextAware;
import cn.herodotus.engine.supplier.upms.logic.domain.event.SysAttributeChangeEvent;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysAttribute;
import jakarta.persistence.PostUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Description: SysAttribute实体数据变更监听 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/4 16:54
 */
public class SysAttributeEntityListener extends AbstractApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(SysAttributeEntityListener.class);

    @PostUpdate
    protected void postUpdate(SysAttribute entity) {
        log.debug("[Herodotus] |- [1] SysAttribute entity @PostUpdate activated, value is : [{}]. Trigger SysAttribute change event.", entity.toString());
        publishEvent(new SysAttributeChangeEvent(entity));
    }
}
