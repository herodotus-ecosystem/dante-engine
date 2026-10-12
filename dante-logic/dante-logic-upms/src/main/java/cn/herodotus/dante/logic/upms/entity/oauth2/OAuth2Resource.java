/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 是 Dante Cloud 系统核心组件库，采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dante.logic.upms.entity.oauth2;

import cn.herodotus.dante.data.jpa.entity.AbstractSysEntity;
import cn.herodotus.dante.logic.upms.constant.LogicUpmsConstants;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

/**
 * <p>Description: OAuth2 资源 </p>
 * <p>
 * OAuth 2.0 协议中的 Resource Indicators 数据储存层实体定义。
 * <p>
 * 之所以放在 UPMS（统一权限管理系统） 中，主要原因，是方便与 {@link OAuth2SupportedScope} 进行关联
 *
 * @author : gengwei_zheng
 * @date : 2026/9/20 13:19
 */
@Schema(name = "OAuth2 资源实体")
@Entity
@Table(name = "oauth2_resource", indexes = {
        @Index(name = "oauth2_resource_id_idx", columnList = "resource_id"),
        @Index(name = "oauth2_resource_c_idx", columnList = "resource_code")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION__OAUTH2_RESOURCE)
public class OAuth2Resource extends AbstractSysEntity {

    @Id
    @UuidGenerator
    @Column(name = "resource_id", length = 64)
    private String resourceId;

    /**
     * Resource Indicator 值。
     * <p>
     * 规范中可以为：1. 资源服务器访问 url 地址；2. 资源服务器名称。本系统中建议使用资源服务器的名称
     */
    @Column(name = "resource_code", length = 256, unique = true, nullable = false)
    private String resourceCode;

    @Column(name = "resource_name", length = 128)
    private String resourceName;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("resourceId", resourceId)
                .add("resourceCode", resourceCode)
                .add("resourceName", resourceName)
                .addValue(super.toString())
                .toString();
    }
}
