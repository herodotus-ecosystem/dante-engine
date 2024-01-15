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

package cn.herodotus.engine.assistant.definition.domain.oauth2;

import cn.herodotus.engine.assistant.definition.constants.BaseConstants;
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

    private String username;

    private Set<String> roles;

    private String employeeId;

    private String avatar;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        map.put(BaseConstants.USERNAME, this.username);
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
                .add("username", username)
                .add("roles", roles)
                .add("employeeId", employeeId)
                .add("avatar", avatar)
                .toString();
    }
}
