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
 * <p>Description: OAuth2 支持范围 </p>
 * <p>
 * OAuth 2.0 Protected Resource Metadata 数据储存层实体定义。
 * <p>
 * 之所以放在 UPMS（统一权限管理系统） 中，主要原因：
 * 1. PRM 本身也是权限的一种，放入 UPMS 更合理
 * 2. PRM 需要与 Permission 进行关联。
 * 3. PRM 是由资源服务器提供而非授权服务器本身。放入 UPMS 可以借用已有的通道进行分发。
 *
 * @author : gengwei_zheng
 * @date : 2026/9/20 13:19
 */
@Schema(name = "OAuth2 支持范围实体")
@Entity
@Table(name = "oauth2_supported_scope", indexes = {
        @Index(name = "oauth2_supported_scope_id_idx", columnList = "scope_id"),
        @Index(name = "oauth2_supported_scope_c_idx", columnList = "scope_code")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION__OAUTH2_SUPPORTED_SCOPE)
public class OAuth2SupportedScope extends AbstractSysEntity {

    @Id
    @UuidGenerator
    @Column(name = "scope_id", length = 64)
    private String scopeId;

    @Column(name = "scope_code", length = 128, unique = true)
    private String scopeCode;

    @Column(name = "scope_name", length = 128)
    private String scopeName;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION__OAUTH2_RESOURCE)
    @ManyToOne
    @JoinColumn(name = "resource_id", nullable = false)
    private OAuth2Resource resource;

    public String getScopeId() {
        return scopeId;
    }

    public void setScopeId(String scopeId) {
        this.scopeId = scopeId;
    }

    public String getScopeCode() {
        return scopeCode;
    }

    public void setScopeCode(String scopeCode) {
        this.scopeCode = scopeCode;
    }

    public String getScopeName() {
        return scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }

    public OAuth2Resource getResource() {
        return resource;
    }

    public void setResource(OAuth2Resource resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("scopeId", scopeId)
                .add("scopeCode", scopeCode)
                .add("scopeName", scopeName)
                .add("resource", resource)
                .addValue(super.toString())
                .toString();
    }
}
