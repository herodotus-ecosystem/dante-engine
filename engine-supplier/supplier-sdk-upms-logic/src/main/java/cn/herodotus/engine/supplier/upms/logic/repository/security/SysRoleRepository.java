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

import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysRole;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.QueryHints;

/**
 * <p>Description: SysRoleRepository </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/11 21:27
 */
public interface SysRoleRepository extends BaseRepository<SysRole, String> {

    /**
     * 根据用户名查找SysUser
     *
     * @param roleCode 角色代码
     * @return {@link SysRole}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    SysRole findByRoleCode(String roleCode);

    /**
     * 根据角色ID查询角色
     *
     * @param roleId 角色ID
     * @return {@link SysRole}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    SysRole findByRoleId(String roleId);
}
