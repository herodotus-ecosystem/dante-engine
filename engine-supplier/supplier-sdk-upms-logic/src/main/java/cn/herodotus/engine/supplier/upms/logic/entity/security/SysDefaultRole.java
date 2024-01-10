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

package cn.herodotus.engine.supplier.upms.logic.entity.security;

import cn.herodotus.engine.assistant.core.enums.AccountType;
import cn.herodotus.stirrup.data.kernel.entity.BaseSysEntity;
import cn.herodotus.engine.supplier.upms.logic.constants.UpmsConstants;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

/**
 * <p>Description: 系统默认角色 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/16 17:01
 */
@Entity
@Table(name = "sys_default_role", uniqueConstraints = {@UniqueConstraint(columnNames = {"default_id", "scene"})},
        indexes = {
                @Index(name = "sys_default_role_did_idx", columnList = "default_id"),
                @Index(name = "sys_default_role_rid_idx", columnList = "role_id")}
)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_SYS_DEFAULT_ROLE)
public class SysDefaultRole extends BaseSysEntity {

    @Id
    @UuidGenerator
    @Column(name = "default_id", length = 64)
    private String defaultId;

    @Schema(title = "场景")
    @Column(name = "scene", unique = true)
    @Enumerated(EnumType.STRING)
    private AccountType scene = AccountType.INSTITUTION;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_SYS_ROLE)
    @Schema(title = "角色ID")
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private SysRole role;

    public String getDefaultId() {
        return defaultId;
    }

    public void setDefaultId(String defaultId) {
        this.defaultId = defaultId;
    }

    public AccountType getScene() {
        return scene;
    }

    public void setScene(AccountType scene) {
        this.scene = scene;
    }

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("defaultId", defaultId)
                .add("supplierType", scene)
                .add("role", role)
                .toString();
    }
}
