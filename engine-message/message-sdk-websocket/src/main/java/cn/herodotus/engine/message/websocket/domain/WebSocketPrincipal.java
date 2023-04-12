/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.apache.org/licenses/LICENSE-2.0>
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
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.message.websocket.domain;

import cn.herodotus.engine.assistant.core.domain.PrincipalDetails;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.security.Principal;
import java.util.Set;

/**
 * <p>Description: Websocket登录连接对象 </p>
 * <p>
 * 用于保存websocket连接过程中需要存储的业务参数
 *
 * @author : gengwei.zheng
 * @date : 2021/10/24 18:43
 */
public class WebSocketPrincipal implements Principal {

    private String userId;
    private String userName;

    private String employeeId;

    private String avatar;

    private Set<String> roles;

    public WebSocketPrincipal(PrincipalDetails details) {
        this.userId = details.getOpenId();
        this.userName = details.getUserName();
        this.employeeId = details.getEmployeeId();
        this.avatar = details.getAvatar();
        this.roles = details.getRoles();
    }

    public WebSocketPrincipal(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 这里的 Name 是发送信息目标的标识。
     * <p>
     * 使用 UserName 可控度不高，使用也不方便。直接用ID
     *
     * @return WebSocket 用户的唯一标识
     */
    @Override
    public String getName() {
        return this.userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
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
        WebSocketPrincipal that = (WebSocketPrincipal) o;
        return Objects.equal(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("userName", userName)
                .add("employeeId", employeeId)
                .add("avatar", avatar)
                .toString();
    }
}
