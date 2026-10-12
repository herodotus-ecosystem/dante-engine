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

package cn.herodotus.dante.logic.upms.entity.hr;

import cn.herodotus.dante.data.jpa.entity.AbstractSysEntity;
import cn.herodotus.dante.logic.upms.constant.LogicUpmsConstants;
import cn.herodotus.dante.logic.upms.enums.OrganizationCategory;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

/**
 * <p>Description: 单位信息 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/19 16:41
 */
@Schema(name = "单位")
@Entity
@Table(name = "sys_organization", indexes = {@Index(name = "sys_organization_id_idx", columnList = "organization_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION__SYS_ORGANIZATION)
public class SysOrganization extends AbstractSysEntity {

    @Schema(name = "单位ID")
    @Id
    @UuidGenerator
    @Column(name = "organization_id", length = 64)
    private String organizationId;

    @Schema(name = "单位名称")
    @Column(name = "organization_name", length = 1000)
    private String organizationName;

    @Schema(name = "4A标准单位ID")
    @Column(name = "a4_biz_org_id", length = 64)
    private String a4BizOrgId;

    @Schema(name = "标准单位代码")
    @Column(name = "biz_org_code", length = 60)
    private String bizOrgCode;

    @Schema(name = "标准单位说明")
    @Column(name = "biz_org_desc", length = 256)
    private String bizOrgDesc;

    @Schema(name = "标准单位ID")
    @Column(name = "biz_org_id", length = 64)
    private String bizOrgId;

    @Schema(name = "标准单位名称")
    @Column(name = "biz_org_name", length = 200)
    private String bizOrgName;

    @Schema(name = "标准单位类型")
    @Column(name = "biz_org_type", length = 30)
    private String bizOrgType;

    @Schema(name = "分区代码")
    @Column(name = "partition_code", length = 256)
    private String partitionCode;

    @Schema(name = "单位简称")
    @Column(name = "short_name", length = 200)
    private String shortName;

    @Schema(name = "上级单位ID")
    @Column(name = "parent_id", length = 64)
    private String parentId;

    @Schema(name = "机构类别")
    @Column(name = "category")
    @Enumerated(EnumType.ORDINAL)
    private OrganizationCategory category = OrganizationCategory.ENTERPRISE;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getA4BizOrgId() {
        return a4BizOrgId;
    }

    public void setA4BizOrgId(String a4BizOrgId) {
        this.a4BizOrgId = a4BizOrgId;
    }

    public String getBizOrgCode() {
        return bizOrgCode;
    }

    public void setBizOrgCode(String bizOrgCode) {
        this.bizOrgCode = bizOrgCode;
    }

    public String getBizOrgDesc() {
        return bizOrgDesc;
    }

    public void setBizOrgDesc(String bizOrgDesc) {
        this.bizOrgDesc = bizOrgDesc;
    }

    public String getBizOrgId() {
        return bizOrgId;
    }

    public void setBizOrgId(String bizOrgId) {
        this.bizOrgId = bizOrgId;
    }

    public String getBizOrgName() {
        return bizOrgName;
    }

    public void setBizOrgName(String bizOrgName) {
        this.bizOrgName = bizOrgName;
    }

    public String getBizOrgType() {
        return bizOrgType;
    }

    public void setBizOrgType(String bizOrgType) {
        this.bizOrgType = bizOrgType;
    }

    public String getPartitionCode() {
        return partitionCode;
    }

    public void setPartitionCode(String partitionCode) {
        this.partitionCode = partitionCode;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public OrganizationCategory getCategory() {
        return category;
    }

    public void setCategory(OrganizationCategory category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("organizationId", organizationId)
                .add("organizationName", organizationName)
                .add("a4BizOrgId", a4BizOrgId)
                .add("bizOrgCode", bizOrgCode)
                .add("bizOrgDesc", bizOrgDesc)
                .add("bizOrgId", bizOrgId)
                .add("bizOrgName", bizOrgName)
                .add("bizOrgType", bizOrgType)
                .add("partitionCode", partitionCode)
                .add("shortName", shortName)
                .add("parentId", parentId)
                .add("category", category)
                .toString();
    }
}
