/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.supplier.upms.logic.entity.hr;

import cn.herodotus.engine.data.core.entity.BaseSysEntity;
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
