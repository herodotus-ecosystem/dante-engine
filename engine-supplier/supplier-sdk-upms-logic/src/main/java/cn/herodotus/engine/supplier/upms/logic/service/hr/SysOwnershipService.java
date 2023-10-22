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

package cn.herodotus.engine.supplier.upms.logic.service.hr;

import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.engine.data.core.service.BaseService;
import cn.herodotus.engine.supplier.upms.logic.entity.hr.SysOwnership;
import cn.herodotus.engine.supplier.upms.logic.repository.hr.SysOwnershipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>Description: 人事归属服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/15 16:30
 */
@Service
public class SysOwnershipService extends BaseService<SysOwnership, String> {

    private static final Logger log = LoggerFactory.getLogger(SysOwnershipService.class);

    private final SysOwnershipRepository sysOwnershipRepository;

    public SysOwnershipService(SysOwnershipRepository sysOwnershipRepository) {
        this.sysOwnershipRepository = sysOwnershipRepository;
    }

    @Override
    public BaseRepository<SysOwnership, String> getRepository() {
        return this.sysOwnershipRepository;
    }

    public void deleteByOrganizationId(String organizationId) {
        sysOwnershipRepository.deleteByOrganizationId(organizationId);
    }

    public void deleteByDepartmentId(String departmentId) {
        sysOwnershipRepository.deleteByDepartmentId(departmentId);
    }

    public void deleteByEmployeeId(String employeeId) {
        sysOwnershipRepository.deleteByEmployeeId(employeeId);
    }

    public void delete(String organizationId, String departmentId, String employeeId) {
        sysOwnershipRepository.deleteByOrganizationIdAndDepartmentIdAndEmployeeId(organizationId, departmentId, employeeId);
    }
}
