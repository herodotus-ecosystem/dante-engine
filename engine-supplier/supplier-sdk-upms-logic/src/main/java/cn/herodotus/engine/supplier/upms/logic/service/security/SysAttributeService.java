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
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysAttribute;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysPermission;
import cn.herodotus.engine.supplier.upms.logic.repository.security.SysAttributeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>Description: SysAttributeService </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/4 6:48
 */
@Service
public class SysAttributeService extends BaseService<SysAttribute, String> {

    private final SysAttributeRepository sysAttributeRepository;

    public SysAttributeService(SysAttributeRepository sysAttributeRepository) {
        this.sysAttributeRepository = sysAttributeRepository;
    }

    @Override
    public BaseRepository<SysAttribute, String> getRepository() {
        return this.sysAttributeRepository;
    }

    public SysAttribute assign(String attributeId, String[] permissionIds) {

        Set<SysPermission> sysPermissions = new HashSet<>();
        for (String permissionId : permissionIds) {
            SysPermission sysPermission = new SysPermission();
            sysPermission.setPermissionId(permissionId);
            sysPermissions.add(sysPermission);
        }

        SysAttribute sysAttribute = findById(attributeId);
        sysAttribute.setPermissions(sysPermissions);

        return saveAndFlush(sysAttribute);
    }

    public List<SysAttribute> findAllByServiceId(String serviceId) {
        return sysAttributeRepository.findAllByServiceId(serviceId);
    }

    public List<SysAttribute> findByAttributeIdIn(List<String> ids) {
        return sysAttributeRepository.findByAttributeIdIn(ids);
    }
}
