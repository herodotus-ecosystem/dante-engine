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

import cn.herodotus.engine.access.core.enums.AccountCategory;
import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.engine.data.core.service.BaseService;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysDefaultRole;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysRole;
import cn.herodotus.engine.supplier.upms.logic.repository.security.SysDefaultRoleRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
 * <p>Description: 系统默认角色Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/21 16:09
 */
@Service
public class SysDefaultRoleService extends BaseService<SysDefaultRole, String> {

    private final SysDefaultRoleRepository sysDefaultRoleRepository;
    private final SysRoleService sysRoleService;

    public SysDefaultRoleService(SysDefaultRoleRepository sysDefaultRoleRepository, SysRoleService sysRoleService) {
        this.sysDefaultRoleRepository = sysDefaultRoleRepository;
        this.sysRoleService = sysRoleService;
    }

    @Override
    public BaseRepository<SysDefaultRole, String> getRepository() {
        return this.sysDefaultRoleRepository;
    }

    public SysDefaultRole findByScene(AccountCategory scene) {
        return this.sysDefaultRoleRepository.findByScene(scene);
    }


    public SysDefaultRole assign(String defaultId, String roleId) {
        SysRole sysRole = sysRoleService.findByRoleId(roleId);
        SysDefaultRole sysDefaultRole = sysDefaultRoleRepository.findByDefaultId(defaultId);
        if (ObjectUtils.isNotEmpty(sysDefaultRole) && ObjectUtils.isNotEmpty(sysRole)) {
            sysDefaultRole.setRole(sysRole);
            return sysDefaultRoleRepository.saveAndFlush(sysDefaultRole);
        }

        return null;
    }
}
