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

package cn.herodotus.engine.assistant.core.utils.http;

import cn.herodotus.engine.assistant.definition.constants.BaseConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.server.ServerHttpRequest;

import java.util.List;

/**
 * <p>Description: Http Header 工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/9/2 16:39
 */
public class HeaderUtils {

    public static final String X_HERODOTUS_SESSION_ID = "X-Herodotus-Session-id";
    public static final String X_HERODOTUS_FROM_IN = "X-Herodotus-From-In";
    public static final String X_HERODOTUS_TENANT_ID = "X-Herodotus-Tenant-Id";
    public static final String X_HERODOTUS_OPEN_ID = "X-Herodotus-Open-Id";

    /**
     * 获取头信息
     *
     * @param httpHeaders {@link HttpHeaders}
     * @param name        头名称
     * @return 头信息值
     */
    public static List<String> getHeaders(HttpHeaders httpHeaders, String name) {
        return httpHeaders.get(name);
    }

    /**
     * 获取头信息
     *
     * @param serverHttpRequest {@link ServerHttpRequest}
     * @param name              名称
     * @return 头信息值
     */
    public static List<String> getHeaders(ServerHttpRequest serverHttpRequest, String name) {
        return getHeaders(serverHttpRequest.getHeaders(), name);
    }

    /**
     * 获取第一条头信息
     *
     * @param httpHeaders {@link HttpHeaders}
     * @param name        头名称
     * @return 如果存在就返回第一条头信息值，如果不存在就返回空。
     */
    public static String getHeader(HttpHeaders httpHeaders, String name) {
        List<String> values = getHeaders(httpHeaders, name);
        return CollectionUtils.isNotEmpty(values) ? values.get(0) : null;
    }

    /**
     * 获取第一条头信息
     *
     * @param serverHttpRequest {@link ServerHttpRequest}
     * @param name              名称
     * @return 如果存在就返回第一条头信息值，如果不存在就返回空。
     */
    public static String getHeader(ServerHttpRequest serverHttpRequest, String name) {
        return getHeader(serverHttpRequest.getHeaders(), name);
    }

    /**
     * 获取头信息
     *
     * @param httpServletRequest {@link HttpServletRequest}
     * @param name               名称
     * @return 头信息值
     */
    public static String getHeader(HttpServletRequest httpServletRequest, String name) {
        return httpServletRequest.getHeader(name);
    }

    /**
     * 请求头中是否存在某个 Header
     *
     * @param httpHeaders {@link HttpHeaders}
     * @param name        头名称
     * @return true 存在，false 不存在
     */
    public static boolean hasHeader(HttpHeaders httpHeaders, String name) {
        return httpHeaders.containsKey(name);
    }

    /**
     * 请求头中是否存在某个 Header
     *
     * @param httpServletRequest {@link HttpServletRequest}
     * @param name               名称
     * @return true 存在，false 不存在
     */
    public static Boolean hasHeader(HttpServletRequest httpServletRequest, String name) {
        return StringUtils.isNotBlank(getHeader(httpServletRequest, name));
    }

    /**
     * 请求头中是否存在某个 Header
     *
     * @param serverHttpRequest {@link ServerHttpRequest}
     * @param name              名称
     * @return true 存在，false 不存在
     */
    public static Boolean hasHeader(ServerHttpRequest serverHttpRequest, String name) {
        return hasHeader(serverHttpRequest.getHeaders(), name);
    }

    /**
     * 获取自定义 X_HERODOTUS_SESSION_ID 头信息
     *
     * @param httpServletRequest {@link HttpServletRequest}
     * @return X_HERODOTUS_SESSION_ID 头信息
     */
    public static String getHerodotusSessionId(HttpServletRequest httpServletRequest) {
        return getHeader(httpServletRequest, X_HERODOTUS_SESSION_ID);
    }

    /**
     * 获取自定义 X_HERODOTUS_SESSION_ID 请求头内容
     *
     * @param serverHttpRequest {@link ServerHttpRequest}
     * @return X_HERODOTUS_SESSION_ID 请求头内容
     */
    public static String getHerodotusSessionId(ServerHttpRequest serverHttpRequest) {
        return getHeader(serverHttpRequest, X_HERODOTUS_SESSION_ID);
    }

    /**
     * 获取自定义 X_HERODOTUS_SESSION_ID 请求头内容
     *
     * @param httpInputMessage {@link HttpInputMessage}
     * @return X_HERODOTUS_SESSION_ID 请求头内容
     */
    public static String getHerodotusSessionId(HttpInputMessage httpInputMessage) {
        return getHeader(httpInputMessage.getHeaders(), X_HERODOTUS_SESSION_ID);
    }

    /**
     * 获取自定义 X_HERODOTUS_TENANT_ID 请求头内容
     *
     * @param httpServletRequest {@link HttpServletRequest}
     * @return X_HERODOTUS_TENANT_ID 请求头内容
     */
    public static String getHerodotusTenantId(HttpServletRequest httpServletRequest) {
        return getHeader(httpServletRequest, X_HERODOTUS_TENANT_ID);
    }

    /**
     * 获取自定义 X_HERODOTUS_FROM_IN 请求头内容
     *
     * @param httpServletRequest {@link HttpServletRequest}
     * @return X_HERODOTUS_FROM_IN 请求头内容
     */
    public static String getHerodotusFromIn(HttpServletRequest httpServletRequest) {
        return getHeader(httpServletRequest, X_HERODOTUS_FROM_IN);
    }

    /**
     * 请求中包含 X_HERODOTUS_SESSION_ID 请求头
     *
     * @param httpServletRequest {@link HttpServletRequest}
     * @return 是否包含 X_HERODOTUS_SESSION_ID 请求头
     */
    public static boolean hasHerodotusSessionIdHeader(HttpServletRequest httpServletRequest) {
        return hasHeader(httpServletRequest, X_HERODOTUS_SESSION_ID);
    }

    /**
     * 请求中包含 X_HERODOTUS_SESSION_ID 请求头
     *
     * @param serverHttpRequest {@link ServerHttpRequest}
     * @return 是否包含 X_HERODOTUS_SESSION_ID 请求头
     */
    public static boolean hasHerodotusSessionIdHeader(ServerHttpRequest serverHttpRequest) {
        return hasHeader(serverHttpRequest, X_HERODOTUS_SESSION_ID);
    }

    /**
     * 请求中包含 X_HERODOTUS_SESSION_ID 请求头
     *
     * @param httpInputMessage {@link HttpInputMessage}
     * @return 是否包含 X_HERODOTUS_SESSION_ID 请求头
     */
    public static boolean hasHerodotusSessionIdHeader(HttpInputMessage httpInputMessage) {
        return hasHeader(httpInputMessage.getHeaders(), X_HERODOTUS_SESSION_ID);
    }

    /**
     * 获取 COOKIE 头请求头内容
     *
     * @param httpServletRequest {@link HttpServletRequest}
     * @return COOKIE 请求头内容
     */
    public static String getCookie(HttpServletRequest httpServletRequest) {
        return getHeader(httpServletRequest, HttpHeaders.COOKIE);
    }

    /**
     * 获取 COOKIE 请求头内容
     *
     * @param serverHttpRequest {@link ServerHttpRequest}
     * @return COOKIE 请求头内容
     */
    public static String getCookie(ServerHttpRequest serverHttpRequest) {
        return getHeader(serverHttpRequest, HttpHeaders.COOKIE);
    }

    /**
     * 获取 COOKIE 请求头内容
     *
     * @param httpInputMessage {@link HttpInputMessage}
     * @return COOKIE 请求头内容
     */
    public static String getCookie(HttpInputMessage httpInputMessage) {
        return getHeader(httpInputMessage.getHeaders(), HttpHeaders.COOKIE);
    }

    /**
     * 获取 AUTHORIZATION 请求头内容
     *
     * @param httpServletRequest {@link HttpServletRequest}
     * @return AUTHORIZATION 请求头或者为空
     */
    public static String getAuthorization(HttpServletRequest httpServletRequest) {
        return getHeader(httpServletRequest, HttpHeaders.AUTHORIZATION);
    }

    /**
     * 获取 Bearer Token 的值
     *
     * @param request {@link HttpServletRequest}
     * @return 如果 AUTHORIZATION 不存在，或者 Token 不是以 “Bearer ” 开头，则返回 null。如果 AUTHORIZATION 存在，而且是以 “Bearer ” 开头，那么返回 “Bearer ” 后面的值。
     */
    public static String getBearerToken(HttpServletRequest request) {
        String header = getAuthorization(request);
        if (StringUtils.isNotBlank(header) && StringUtils.startsWith(header, BaseConstants.BEARER_TOKEN)) {
            return StringUtils.remove(header, BaseConstants.BEARER_TOKEN);
        } else {
            return null;
        }
    }

    /**
     * 获取 ORIGIN 请求头内容
     *
     * @param httpServletRequest {@link HttpServletRequest}
     * @return ORIGIN 请求头或者为空
     */
    public static String getOrigin(HttpServletRequest httpServletRequest) {
        return getHeader(httpServletRequest, HttpHeaders.ORIGIN);
    }


}
