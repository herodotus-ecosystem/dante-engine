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

package cn.herodotus.dante.oauth2.commons.domain;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Objects;

/**
 * <p>Description: 自定义 AntPathRequestMatcher </p>
 * <p>
 * 基于 AntPathRequestMatcher 代码，扩展了一些方法，解决原有 AntPathRequestMatcher 使用不方便问题。
 *
 * @author : gengwei.zheng
 * @date : 2022/3/9 10:47
 */
public final class HerodotusRequest implements Serializable {

    private String pattern;

    private String httpMethod;

    private String version;

    /**
     * 无参构造函数，解决 Jackson 序列化出错问题
     */
    public HerodotusRequest() {
    }

    /**
     * Creates a matcher with the specific pattern which will match all HTTP methods in a
     * case sensitive manner.
     *
     * @param pattern the ant pattern to use for matching
     */
    public HerodotusRequest(String pattern) {
        this(pattern, null);
    }

    public HerodotusRequest(String pattern, String httpMethod) {
        this(pattern, httpMethod, null);
    }

    /**
     * Creates a matcher with the supplied pattern which will match the specified Http
     * method
     *
     * @param pattern    the ant pattern to use for matching
     * @param httpMethod the HTTP method. The {@code matches} method will return false if
     *                   the incoming request doesn't have the same method.
     */
    public HerodotusRequest(String pattern, String httpMethod, String version) {
        Assert.hasText(pattern, "Pattern cannot be null or empty");
        this.pattern = pattern;
        this.httpMethod = checkHttpMethod(httpMethod);
        this.version = version;
    }

    public String getPattern() {
        return pattern;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getVersion() {
        return version;
    }

    private String checkHttpMethod(String method) {
        if (StringUtils.isNotBlank(method)) {
            HttpMethod httpMethod = HttpMethod.valueOf(method);
            if (ObjectUtils.isNotEmpty(httpMethod)) {
                return httpMethod.name();
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HerodotusRequest that = (HerodotusRequest) o;
        return Strings.CS.equals(pattern, that.pattern) && Strings.CS.equals(httpMethod, that.httpMethod) && Strings.CS.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pattern, httpMethod, version);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("pattern", pattern)
                .add("httpMethod", httpMethod)
                .add("version", version)
                .toString();
    }
}