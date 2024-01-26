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

import cn.herodotus.engine.access.core.enums.AccountCategory;
import cn.herodotus.engine.data.core.entity.BaseSysEntity;
import cn.herodotus.engine.oauth2.core.definition.domain.SocialUserDetails;
import cn.herodotus.engine.supplier.upms.logic.constants.UpmsConstants;
import cn.herodotus.engine.supplier.upms.logic.domain.deserializer.SysUserEmptyToNull;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysUser;
import cn.herodotus.engine.supplier.upms.logic.enums.Gender;
import cn.herodotus.engine.supplier.upms.logic.enums.Identity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: 人员信息 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/19 16:41
 */
@Schema(title = "人员")
@Entity
@NamedEntityGraph(
        name = "SysEmployeeWithSysUser.Graph",
        attributeNodes = {
                @NamedAttributeNode(value = "user", subgraph = "SysUser.SubGraph")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "SysUser.SubGraph",
                        attributeNodes = {
                                @NamedAttributeNode(value = "userId")
                        }
                )
        }
)
@Table(name = "sys_employee", indexes = {@Index(name = "sys_employee_id_idx", columnList = "employee_id")})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "employeeId")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_SYS_EMPLOYEE)
public class SysEmployee extends BaseSysEntity implements SocialUserDetails {

    @Schema(title = "人员ID")
    @Id
    @UuidGenerator
    @Column(name = "employee_id", length = 64)
    private String employeeId;

    @Schema(title = "姓名")
    @Column(name = "employee_name", length = 50)
    private String employeeName;

    @Schema(title = "工号")
    @Column(name = "employee_no", length = 50)
    private String employeeNo;

    @Schema(title = "手机号码")
    @Column(name = "mobile_phone_number", length = 50)
    private String mobilePhoneNumber;

    @Schema(title = "办公电话")
    @Column(name = "office_phone_number", length = 50)
    private String officePhoneNumber;

    @Schema(title = "电子邮箱")
    @Column(name = "email", length = 100)
    private String email;

    @Schema(title = "PKI电子邮箱")
    @Column(name = "pki_email", length = 100)
    private String pkiEmail;

    @Schema(title = "4A标准人员ID")
    @Column(name = "a4_biz_emp_id", length = 256)
    private String a4BizEmpId;

    @Schema(title = "头像")
    @Column(name = "avatar", length = 1000)
    private String avatar;

    @Schema(title = "生日")
    @Column(name = "birth_day")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Schema(title = "性别")
    @Column(name = "gender")
    @Enumerated(EnumType.ORDINAL)
    private Gender gender = Gender.MAN;

    @Schema(title = "身份")
    @Column(name = "identity")
    @Enumerated(EnumType.ORDINAL)
    private Identity identity = Identity.STAFF;

    /**
     * 为了尽量保证与工作流用户体系一致，这里采用Employee与Department多对多模式。
     * <p>
     * 为了提升访问效率，使用@Fetch(FetchMode.SUBSELECT)，这会让数据一并查出来。
     * 从业务角度分析，一方面没有采用双向查询，避免使用部门的时候查出大量人员数据；另一方面，以人员为主，维护关联数据。
     */
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_SYS_DEPARTMENT)
    @ManyToMany
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "sys_employee_department",
            joinColumns = {@JoinColumn(name = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "department_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"employee_id", "department_id"})},
            indexes = {@Index(name = "sys_employee_department_eid_idx", columnList = "employee_id"), @Index(name = "sys_employee_department_did_idx", columnList = "department_id")})
    private Set<SysDepartment> departments = new HashSet<>();

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_SYS_USER)
    @JsonDeserialize(using = SysUserEmptyToNull.class)
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private SysUser user;

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getOfficePhoneNumber() {
        return officePhoneNumber;
    }

    public void setOfficePhoneNumber(String officePhoneNumber) {
        this.officePhoneNumber = officePhoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPkiEmail() {
        return pkiEmail;
    }

    public void setPkiEmail(String pkiEmail) {
        this.pkiEmail = pkiEmail;
    }

    public String getA4BizEmpId() {
        return a4BizEmpId;
    }

    public void setA4BizEmpId(String a4BizEmpId) {
        this.a4BizEmpId = a4BizEmpId;
    }

    @Override
    public String getUuid() {
        return this.employeeId;
    }

    @Override
    public String getSource() {
        return AccountCategory.INSTITUTION.getKey();
    }

    @Override
    public String getPhoneNumber() {
        return this.getMobilePhoneNumber();
    }

    @Override
    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String getUsername() {
        return this.getEmployeeName();
    }

    @Override
    public String getNickname() {
        return this.getEmail();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public Set<SysDepartment> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<SysDepartment> departments) {
        this.departments = departments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysEmployee that = (SysEmployee) o;

        return new EqualsBuilder()
                .append(getEmployeeId(), that.getEmployeeId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getEmployeeId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("employeeId", employeeId)
                .add("employeeName", employeeName)
                .add("employeeNo", employeeNo)
                .add("mobilePhoneNumber", mobilePhoneNumber)
                .add("officePhoneNumber", officePhoneNumber)
                .add("email", email)
                .add("pkiEmail", pkiEmail)
                .add("a4BizEmpId", a4BizEmpId)
                .add("avatar", avatar)
                .add("birthday", birthday)
                .add("gender", gender)
                .add("identity", identity)
                .toString();
    }
}
