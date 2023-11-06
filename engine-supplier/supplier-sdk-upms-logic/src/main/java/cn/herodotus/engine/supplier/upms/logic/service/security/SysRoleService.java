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

package cn.herodotus.engine.supplier.upms.logic.service.security;

import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.engine.data.core.service.BaseService;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysPermission;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysRole;
import cn.herodotus.engine.supplier.upms.logic.repository.security.SysRoleRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: SysRoleService </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/25 11:07
 */
@Service
public class SysRoleService extends BaseService<SysRole, String> {

    private final SysRoleRepository sysRoleRepository;

    public SysRoleService(SysRoleRepository sysRoleRepository) {
        this.sysRoleRepository = sysRoleRepository;
    }

    @Override
    public BaseRepository<SysRole, String> getRepository() {
        return this.sysRoleRepository;
    }

    public SysRole assign(String roleId, String[] permissions) {

        Set<SysPermission> sysPermissions = new HashSet<>();
        for (String permission : permissions) {
            SysPermission sysPermission = new SysPermission();
            sysPermission.setPermissionId(permission);
            sysPermissions.add(sysPermission);
        }

        SysRole sysRole = findById(roleId);
        sysRole.setPermissions(sysPermissions);

        return saveAndFlush(sysRole);
    }

    public SysRole findByRoleCode(String roleCode) {
        return sysRoleRepository.findByRoleCode(roleCode);
    }

    public SysRole findByRoleId(String roleId) {
        return sysRoleRepository.findByRoleId(roleId);
    }
}
