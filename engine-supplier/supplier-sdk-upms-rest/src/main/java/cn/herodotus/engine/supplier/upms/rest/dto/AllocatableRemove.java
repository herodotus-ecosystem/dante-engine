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

package cn.herodotus.engine.supplier.upms.rest.dto;

import cn.herodotus.engine.assistant.definition.domain.base.AbstractDto;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * <p>Description: 删除人员归属参数BO对象 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/9/24 21:48
 */
@Schema(description = "删除人员归属参数BO对象")
public class AllocatableRemove extends AbstractDto {

    @NotNull(message = "单位ID不能为空")
    @Schema(description = "单位ID")
    private String organizationId;

    @NotNull(message = "部门ID不能为空")
    @Schema(description = "部门ID")
    private String departmentId;

    @NotNull(message = "人员ID不能为空")
    @Schema(description = "人员ID")
    private String employeeId;

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
                .add("organizationId", organizationId)
                .add("departmentId", departmentId)
                .add("employeeId", employeeId)
                .toString();
    }
}
