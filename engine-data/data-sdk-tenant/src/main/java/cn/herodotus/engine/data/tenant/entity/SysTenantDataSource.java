/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.data.tenant.entity;

import cn.herodotus.engine.assistant.core.definition.domain.AbstractEntity;
import cn.herodotus.engine.data.core.constants.DataConstants;
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
public class SysTenantDataSource extends AbstractEntity {

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
