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

import cn.herodotus.engine.assistant.core.definition.constants.SymbolConstants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.util.WebUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>Description: Cookie 操作工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/9/2 16:38
 */
public class CookieUtils {

    /**
     * 解析 Cookie 头的值解析为 Map
     *
     * @param cookie Cookie 头的值
     * @return Cookie 键值对。
     */
    private static Map<String, String> rawCookieToMap(String cookie) {
        if (StringUtils.isNotBlank(cookie)) {
            return Stream.of(cookie.split(SymbolConstants.SEMICOLON_AND_SPACE))
                    .map(pair -> pair.split(SymbolConstants.EQUAL))
                    .collect(Collectors.toMap(kv -> kv[0], kv -> kv[1]));
        } else {
            return Collections.emptyMap();
        }
    }

    /**
     * 获取多个 Cookie 请求头中的属性值
     *
     * @param cookie Cookie 请求头值
     * @param name   Cookie中的属性名
     * @return cookie 中属性值的集合
     */
    public static List<String> get(String cookie, String... name) {
        Map<String, String> cookies = rawCookieToMap(cookie);
        return Stream.of(name).map(cookies::get).toList();
    }

    /**
     * 从 Cookie 请求头中，找到给定任意给定属性的值
     *
     * @param cookie Cookie 请求头值
     * @param name   Cookie中的属性名
     * @return cookie 中属性值的集合
     */
    public static String getAny(String cookie, String... name) {
        List<String> result = get(cookie, name);
        return CollectionUtils.isNotEmpty(result) ? result.get(0) : null;
    }

    /**
     * 获取 Cookie 请求头中的属性值
     *
     * @param cookie Cookie 请求头值
     * @param name   Cookie中的属性名
     * @return cookie 中属性值
     */
    public static String get(String cookie, String name) {
        Map<String, String> cookies = rawCookieToMap(cookie);
        return cookies.get(name);
    }

    /**
     * 获取 Cookie 对象
     *
     * @param httpServletRequest {@link HttpServletRequest}
     * @param name               Cookie中的属性名
     * @return {@link Cookie} 对象
     */
    public static Cookie get(HttpServletRequest httpServletRequest, String name) {
        return WebUtils.getCookie(httpServletRequest, name);
    }

    /**
     * 获取 Cookie 属性值
     *
     * @param httpServletRequest {@link HttpServletRequest}
     * @param name               Cookie中的属性名
     * @return 如果 Cookie 存在属性名就返回对应的值，如果不存在则返回null
     */
    public static String getValue(HttpServletRequest httpServletRequest, String name) {
        Cookie cookie = get(httpServletRequest, name);
        return ObjectUtils.isNotEmpty(cookie) ? cookie.getValue() : null;
    }

    /**
     * 获取 Cookie 请求头中，某个属性的值
     *
     * @param httpServletRequest {@link HttpServletRequest}
     * @param name               Cookie中的属性名
     * @return 如果 Cookie 存在属性名就返回对应的值，如果不存在则返回null
     */
    public static String getFromHeader(HttpServletRequest httpServletRequest, String name) {
        String cookie = HeaderUtils.getCookie(httpServletRequest);
        return get(cookie, name);
    }

    /**
     * 获取 Cookie 请求头中，某个属性的值
     *
     * @param serverHttpRequest {@link ServerHttpRequest}
     * @param name              Cookie中的属性名
     * @return 如果 Cookie 存在属性名就返回对应的值，如果不存在则返回null
     */
    public static String getFromHeader(ServerHttpRequest serverHttpRequest, String name) {
        String cookie = HeaderUtils.getCookie(serverHttpRequest);
        return get(cookie, name);
    }

    /**
     * 获取 Cookie 请求头中，某个属性的值
     *
     * @param httpInputMessage {@link HttpInputMessage}
     * @param name             Cookie中的属性名
     * @return 如果 Cookie 存在属性名就返回对应的值，如果不存在则返回null
     */
    public static String getFromHeader(HttpInputMessage httpInputMessage, String name) {
        String cookie = HeaderUtils.getCookie(httpInputMessage);
        return get(cookie, name);
    }

    /**
     * 从 Cookie 请求头中，找到给定任意给定属性的值
     *
     * @param httpServletRequest {@link HttpServletRequest}
     * @param name               Cookie中的属性名
     * @return 如果 Cookie 存在属性名就返回对应的值，如果不存在则返回null
     */
    public static String getAnyFromHeader(HttpServletRequest httpServletRequest, String... name) {
        String cookie = HeaderUtils.getCookie(httpServletRequest);
        return getAny(cookie, name);
    }

    /**
     * 从 Cookie 请求头中，找到给定任意给定属性的值
     *
     * @param serverHttpRequest {@link ServerHttpRequest}
     * @param name              Cookie中的属性名
     * @return 如果 Cookie 存在属性名就返回对应的值，如果不存在则返回null
     */
    public static String getAnyFromHeader(ServerHttpRequest serverHttpRequest, String... name) {
        String cookie = HeaderUtils.getCookie(serverHttpRequest);
        return getAny(cookie, name);
    }

    /**
     * 从 Cookie 请求头中，找到给定任意给定属性的值
     *
     * @param httpInputMessage {@link HttpInputMessage}
     * @param name             Cookie中的属性名
     * @return 如果 Cookie 存在属性名就返回对应的值，如果不存在则返回null
     */
    public static String getAnyFromHeader(HttpInputMessage httpInputMessage, String... name) {
        String cookie = HeaderUtils.getCookie(httpInputMessage);
        return getAny(cookie, name);
    }

    /**
     * 清除 某个指定的cookie
     *
     * @param response HttpServletResponse
     * @param key      cookie key
     */
    public static void removeCookie(HttpServletResponse response, String key) {
        setCookie(response, key, null, 0);
    }

    /**
     * 设置cookie
     *
     * @param response        HttpServletResponse
     * @param name            cookie name
     * @param value           cookie value
     * @param maxAgeInSeconds maxage
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int maxAgeInSeconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAgeInSeconds);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
