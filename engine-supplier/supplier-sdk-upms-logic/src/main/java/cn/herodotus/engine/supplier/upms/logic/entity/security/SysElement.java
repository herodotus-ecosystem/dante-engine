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

import cn.herodotus.engine.data.core.entity.BaseSysEntity;
import cn.herodotus.engine.supplier.upms.logic.constants.UpmsConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: 前端系统菜单 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/12 12:05
 */
@Entity
@Table(name = "sys_element", indexes = {@Index(name = "sys_element_id_idx", columnList = "element_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_SYS_ELEMENT)
public class SysElement extends BaseSysEntity {

    @Id
    @UuidGenerator
    @Column(name = "element_id", length = 64)
    private String elementId;

    @Column(name = "parent_id", length = 64)
    private String parentId;

    @Column(name = "path", length = 512)
    private String path;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "component", length = 512)
    private String component;

    @Column(name = "redirect", length = 512)
    private String redirect;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "icon", length = 100)
    private String icon;

    @JsonProperty("isHaveChild")
    @Column(name = "have_child")
    private Boolean haveChild = false;

    @JsonProperty("isNotKeepAlive")
    @Column(name = "not_keep_alive")
    private Boolean notKeepAlive = false;

    @JsonProperty("isHideAllChild")
    @Column(name = "hide_all_child")
    private Boolean hideAllChild = false;

    @JsonProperty("isDetailContent")
    @Column(name = "detail_content")
    private Boolean detailContent = false;

    @JsonProperty("isIgnoreAuth")
    @Column(name = "ignore_auth")
    private Boolean ignoreAuth = false;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_SYS_ROLE)
    @Schema(title = "元素角色")
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "sys_element_role",
            joinColumns = {@JoinColumn(name = "element_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"element_id", "role_id"})},
            indexes = {@Index(name = "sys_element_role_eid_idx", columnList = "element_id"), @Index(name = "sys_element_role_rid_idx", columnList = "role_id")})
    private Set<SysRole> roles = new HashSet<>();

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getHaveChild() {
        return haveChild;
    }

    public void setHaveChild(Boolean haveChild) {
        this.haveChild = haveChild;
    }

    public Boolean getNotKeepAlive() {
        return notKeepAlive;
    }

    public void setNotKeepAlive(Boolean notKeepAlive) {
        this.notKeepAlive = notKeepAlive;
    }

    public Boolean getHideAllChild() {
        return hideAllChild;
    }

    public void setHideAllChild(Boolean hideAllChild) {
        this.hideAllChild = hideAllChild;
    }

    public Boolean getDetailContent() {
        return detailContent;
    }

    public void setDetailContent(Boolean detailContent) {
        this.detailContent = detailContent;
    }

    public Boolean getIgnoreAuth() {
        return ignoreAuth;
    }

    public void setIgnoreAuth(Boolean ignoreAuth) {
        this.ignoreAuth = ignoreAuth;
    }

    public Set<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<SysRole> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysElement sysMenu = (SysElement) o;
        return Objects.equal(elementId, sysMenu.elementId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(elementId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("elementId", elementId)
                .add("parentId", parentId)
                .add("path", path)
                .add("name", name)
                .add("component", component)
                .add("redirect", redirect)
                .add("title", title)
                .add("type", type)
                .add("icon", icon)
                .add("haveChild", haveChild)
                .add("notKeepAlive", notKeepAlive)
                .add("hideAllChild", hideAllChild)
                .add("detailContent", detailContent)
                .add("ignoreAuth", ignoreAuth)
                .add("roles", roles)
                .toString();
    }
}
