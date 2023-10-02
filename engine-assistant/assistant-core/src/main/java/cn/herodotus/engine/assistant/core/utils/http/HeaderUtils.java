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

package cn.herodotus.engine.assistant.core.utils.http;

import cn.herodotus.engine.assistant.core.definition.constants.BaseConstants;
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
     * 获取自定义 X_HERODOTUS_SESSION 头信息
     *
     * @param httpServletRequest {@link HttpServletRequest}
     * @return X_HERODOTUS_SESSION 头信息
     */
    public static String getHerodotusSession(HttpServletRequest httpServletRequest) {
        return getHeader(httpServletRequest, X_HERODOTUS_SESSION_ID);
    }

    /**
     * 获取自定义 X_HERODOTUS_SESSION 请求头内容
     *
     * @param serverHttpRequest {@link ServerHttpRequest}
     * @return X_HERODOTUS_SESSION 请求头内容
     */
    public static String getHerodotusSession(ServerHttpRequest serverHttpRequest) {
        return getHeader(serverHttpRequest, X_HERODOTUS_SESSION_ID);
    }

    /**
     * 获取自定义 X_HERODOTUS_SESSION 请求头内容
     *
     * @param httpInputMessage {@link HttpInputMessage}
     * @return X_HERODOTUS_SESSION 请求头内容
     */
    public static String getHerodotusSession(HttpInputMessage httpInputMessage) {
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
