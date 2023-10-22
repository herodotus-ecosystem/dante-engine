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

package cn.herodotus.engine.supplier.upms.logic.entity.security;

import cn.herodotus.engine.data.core.entity.BaseSysEntity;
import cn.herodotus.engine.supplier.upms.logic.constants.UpmsConstants;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

/**
 * <p>Description: 系统权限实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/25 15:06
 */
@Schema(name = "系统权限")
@Entity
@Table(name = "sys_permission", indexes = {@Index(name = "sys_permission_id_idx", columnList = "permission_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_SYS_PERMISSION)
public class SysPermission extends BaseSysEntity {

    @Schema(name = "权限ID")
    @Id
    @UuidGenerator
    @Column(name = "permission_id", length = 64)
    private String permissionId;

    @Schema(name = "权限代码")
    @Column(name = "permission_code", length = 128)
    private String permissionCode;

    @Schema(name = "权限名称")
    @Column(name = "permission_name", length = 1024)
    private String permissionName;

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysPermission that = (SysPermission) o;
        return Objects.equal(permissionId, that.permissionId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(permissionId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("permissionId", permissionId)
                .add("permissionCode", permissionCode)
                .add("permissionName", permissionName)
                .toString();
    }
}
