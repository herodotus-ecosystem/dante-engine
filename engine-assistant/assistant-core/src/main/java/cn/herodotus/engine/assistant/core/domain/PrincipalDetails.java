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

package cn.herodotus.engine.assistant.core.domain;

import cn.herodotus.engine.assistant.core.definition.constants.BaseConstants;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>Description: 用户登录额外信息 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/13 14:31
 */
public class PrincipalDetails {

    private String openId;

    private String userName;

    private Set<String> roles;

    private String employeeId;

    private String avatar;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(BaseConstants.OPEN_ID, this.openId);
        map.put(BaseConstants.USERNAME, this.userName);
        map.put(BaseConstants.ROLES, this.roles);
        map.put(BaseConstants.EMPLOYEE_ID, this.employeeId);
        map.put(BaseConstants.AVATAR, this.avatar);
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PrincipalDetails that = (PrincipalDetails) o;
        return Objects.equal(openId, that.openId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(openId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", openId)
                .add("userName", userName)
                .add("roles", roles)
                .add("employeeId", employeeId)
                .add("avatar", avatar)
                .toString();
    }
}
