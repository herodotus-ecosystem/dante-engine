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

package cn.herodotus.engine.data.tenant.entity;

import cn.herodotus.engine.data.core.constants.DataConstants;
import cn.herodotus.engine.data.core.entity.BaseSysEntity;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

/**
 * <p>Description: 租户数据源管理 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/3/28 21:45
 */
@Schema(title = "多租户数据源管理")
@Entity
@Table(name = "sys_tenant_datasource",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"tenant_id"})},
        indexes = {@Index(name = "sys_tenant_datasource_id_idx", columnList = "datasource_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = DataConstants.REGION_SYS_TENANT_DATASOURCE)
public class SysTenantDataSource extends BaseSysEntity {

    @Schema(name = "租户数据源主键")
    @Id
    @UuidGenerator
    @Column(name = "datasource_id", length = 64)
    private String datasourceId;

    @Schema(name = "租户ID", description = "租户的唯一标识")
    @Column(name = "tenant_id", length = 64, unique = true)
    private String tenantId;

    @Schema(name = "数据库用户名")
    @Column(name = "username", length = 100)
    private String username;

    @Schema(name = "数据库密码")
    @Column(name = "password", length = 100)
    private String password;

    @Schema(name = "数据库驱动")
    @Column(name = "driver_class_name", length = 64)
    private String driverClassName;

    @Schema(name = "数据库连接")
    @Column(name = "url", length = 1000)
    private String url;

    @Schema(name = "是否已经初始化", description = "默认值 false")
    private Boolean initialize = false;

    public String getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getInitialize() {
        return initialize;
    }

    public void setInitialize(Boolean initialize) {
        this.initialize = initialize;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("datasourceId", datasourceId)
                .add("tenantId", tenantId)
                .add("username", username)
                .add("password", password)
                .add("driverClassName", driverClassName)
                .add("url", url)
                .add("initialize", initialize)
                .toString();
    }
}
