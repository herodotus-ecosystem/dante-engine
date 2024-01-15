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

package cn.herodotus.engine.message.core.logic.domain;

import cn.herodotus.engine.assistant.definition.domain.base.AbstractEntity;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * <p>Description: Controller 请求注解元数据封装实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/6/2 19:52
 */
public class RequestMapping extends AbstractEntity {

    private String mappingId;

    private String mappingCode;

    private String requestMethod;

    private String serviceId;

    private String className;

    private String methodName;

    private String url;

    private String description;

    public RequestMapping() {
    }

    public String getMappingId() {
        return mappingId;
    }

    public void setMappingId(String mappingId) {
        this.mappingId = mappingId;
    }

    public String getMappingCode() {
        return mappingCode;
    }

    public void setMappingCode(String mappingCode) {
        this.mappingCode = mappingCode;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestMapping that = (RequestMapping) o;
        return Objects.equal(mappingId, that.mappingId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mappingId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mappingId", mappingId)
                .add("mappingCode", mappingCode)
                .add("requestMethod", requestMethod)
                .add("serviceId", serviceId)
                .add("className", className)
                .add("methodName", methodName)
                .add("url", url)
                .add("description", description)
                .toString();
    }
}
