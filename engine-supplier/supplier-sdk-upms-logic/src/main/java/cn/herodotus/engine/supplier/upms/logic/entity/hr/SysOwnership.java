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

package cn.herodotus.engine.supplier.upms.logic.entity.hr;

import cn.herodotus.stirrup.data.kernel.entity.BaseSysEntity;
import cn.herodotus.engine.supplier.upms.logic.constants.UpmsConstants;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

/**
 * <p>Description: 人事归属 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/15 12:16
 */
@Schema(title = "人事归属")
@Entity
@Table(name = "sys_ownership", indexes = {
        @Index(name = "sys_ownership_id_idx", columnList = "ownership_id"),
        @Index(name = "sys_ownership_oid_idx", columnList = "organization_id"),
        @Index(name = "sys_ownership_did_idx", columnList = "department_id"),
        @Index(name = "sys_ownership_eid_idx", columnList = "employee_id")
})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_SYS_OWNERSHIP)
public class SysOwnership extends BaseSysEntity {

    @Schema(title = "人员ID")
    @Id
    @UuidGenerator
    @Column(name = "ownership_id", length = 64)
    private String ownershipId;

    @Schema(title = "所属单位ID")
    @Column(name = "organization_id", length = 64)
    private String organizationId;

    @Schema(title = "所属部门ID")
    @Column(name = "department_id", length = 64)
    private String departmentId;

    @Column(name = "employee_id", length = 64)
    private String employeeId;

    public String getOwnershipId() {
        return ownershipId;
    }

    public void setOwnershipId(String ownershipId) {
        this.ownershipId = ownershipId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("ownershipId", ownershipId)
                .add("organizationId", organizationId)
                .add("departmentId", departmentId)
                .add("employeeId", employeeId)
                .toString();
    }
}
