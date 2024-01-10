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

package cn.herodotus.engine.supplier.upms.logic.repository.security;

import cn.herodotus.engine.assistant.core.enums.AccountType;
import cn.herodotus.stirrup.data.kernel.repository.BaseRepository;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysDefaultRole;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.QueryHints;

/**
 * <p>Description: 默认角色管理Repository </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/21 16:08
 */
public interface SysDefaultRoleRepository extends BaseRepository<SysDefaultRole, String> {

    /**
     * 根据场景查询当前场景对应的默认角色
     *
     * @param scene 场景 {@link AccountType}
     * @return {@link SysDefaultRole}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    SysDefaultRole findByScene(AccountType scene);

    /**
     * 根据默认角色ID查询默认角色
     *
     * @param defaultId 默认角色ID
     * @return {@link SysDefaultRole}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    SysDefaultRole findByDefaultId(String defaultId);
}
