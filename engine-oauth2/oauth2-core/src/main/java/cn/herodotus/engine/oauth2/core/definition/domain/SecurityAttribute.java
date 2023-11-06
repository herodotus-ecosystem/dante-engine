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

package cn.herodotus.engine.oauth2.core.definition.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * <p>Description: Security Metadata 传输数据实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/8 15:51
 */
public class SecurityAttribute implements Serializable {

    private String attributeId;

    private String attributeCode;

    private String attributeName;

    private String webExpression;

    private String permissions;

    private String url;

    private String requestMethod;

    private String serviceId;

    public SecurityAttribute() {
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeCode() {
        return attributeCode;
    }

    public void setAttributeCode(String attributeCode) {
        this.attributeCode = attributeCode;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getWebExpression() {
        return webExpression;
    }

    public void setWebExpression(String webExpression) {
        this.webExpression = webExpression;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SecurityAttribute that = (SecurityAttribute) o;
        return Objects.equal(attributeId, that.attributeId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attributeId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("attributeId", attributeId)
                .add("attributeCode", attributeCode)
                .add("attributeName", attributeName)
                .add("authorities", webExpression)
                .add("permissions", permissions)
                .add("url", url)
                .add("requestMethod", requestMethod)
                .add("serviceId", serviceId)
                .toString();
    }
}
