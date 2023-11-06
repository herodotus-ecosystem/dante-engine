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
import cn.herodotus.engine.supplier.upms.logic.repository.security.SysPermissionRepository;
import org.springframework.stereotype.Service;

/**
 * <p>Description: SysPermissionService </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/25 16:11
 */
@Service
public class SysPermissionService extends BaseService<SysPermission, String> {

    private final SysPermissionRepository sysPermissionRepository;

    public SysPermissionService(SysPermissionRepository sysPermissionRepository) {
        this.sysPermissionRepository = sysPermissionRepository;
    }

    @Override
    public BaseRepository<SysPermission, String> getRepository() {
        return this.sysPermissionRepository;
    }
}
