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

package cn.herodotus.engine.rest.core.utils;

import cn.herodotus.engine.assistant.core.json.jackson2.utils.Jackson2Utils;
import com.google.common.net.HttpHeaders;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * <p>Description:  Http与Servlet工具类. </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/4 11:39
 */
public class WebUtils extends org.springframework.web.util.WebUtils {

    private static final Logger log = LoggerFactory.getLogger(WebUtils.class);

    private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    private static final PathMatcher pathMatcher = new AntPathMatcher();

    public static PathMatcher getPathMatcher() {
        return pathMatcher;
    }

    /**
     * 判断是否为静态资源
     *
     * @param uri 请求 URL
     * @return 是否为静态资源
     */
    public static boolean isStaticResources(String uri) {
        ResourceUrlProvider resourceUrlProvider = SpringUtil.getBean(ResourceUrlProvider.class);
        String staticUri = resourceUrlProvider.getForLookupPath(uri);
        return staticUri != null;
    }

    /**
     * 判断路径是否与路径模式匹配
     *
     * @param patterns 路径模式字符串List
     * @param path     url
     * @return 是否匹配
     */
    public static boolean isPathMatch(List<String> patterns, String path) {
        PathMatcher pathMatcher = getPathMatcher();
        for (String pattern : patterns) {
            if (pathMatcher.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断请求是否与设定的模式匹配
     *
     * @param patterns 路径匹配模式
     * @param request  请求
     * @return 是否匹配
     */
    public static boolean isRequestMatched(List<String> patterns, HttpServletRequest request) {
        String url = request.getRequestURI();
        return isPathMatch(patterns, url);
    }

    /**
     * 获取 HttpServletRequest
     *
     * @return {HttpServletRequest}
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取 HttpServletResponse
     *
     * @return {HttpServletResponse}
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 客户端返回JSON字符串
     *
     * @param response HttpServletResponse
     * @param object   需要转换的对象
     */
    public static void renderJson(HttpServletResponse response, Object object) {
        renderJson(response, Jackson2Utils.toJson(object), MediaType.APPLICATION_JSON.toString());
    }

    /**
     * 客户端返回字符串
     *
     * @param response HttpServletResponse
     * @param string   需要绘制的信息
     */
    public static void renderJson(HttpServletResponse response, String string, String type) {
        try {
            response.setContentType(type);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            response.getWriter().print(string);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            log.error("[Herodotus] |- Render response to Json error!");
        }
    }


    public static HttpServletRequest toHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    public static HttpServletResponse toHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }


    private static boolean isHeaderContainMediaType(ServletRequest request, String headerType, String mediaType) {
        String header = toHttp(request).getHeader(headerType);
        return StringUtils.isNotEmpty(header) && header.contains(mediaType);
    }

    private static boolean isHeaderContainJson(ServletRequest request, String headerType) {
        return isHeaderContainMediaType(request, headerType, MediaType.APPLICATION_JSON_VALUE);
    }

    private static boolean isContentTypeHeaderContainJson(ServletRequest request) {
        return isHeaderContainJson(request, HttpHeaders.CONTENT_TYPE);
    }

    private static boolean isAcceptHeaderContainJson(ServletRequest request) {
        return isHeaderContainJson(request, HttpHeaders.ACCEPT);
    }

    private static boolean isContainAjaxFlag(ServletRequest request) {
        String xRequestedWith = WebUtils.toHttp(request).getHeader(HttpHeaders.X_REQUESTED_WITH);
        return XML_HTTP_REQUEST.equalsIgnoreCase(xRequestedWith);
    }

    public static boolean isAjaxRequest(ServletRequest request) {

        //使用HttpServletRequest中的header检测请求是否为ajax, 如果是ajax则返回json, 如果为非ajax则返回view(即ModelAndView)
        if (isContentTypeHeaderContainJson(request) || isAcceptHeaderContainJson(request) || isContainAjaxFlag(request)) {
            log.trace("[Herodotus] |- Is Ajax Request!!!!!");
            return true;
        }

        log.trace("[Herodotus] |- Not a Ajax Request!!!!!");
        return false;
    }

    public static boolean isStaticResourcesRequest(HttpServletRequest request) {
        String requestPath = request.getServletPath();
        if (StringUtils.endsWith(requestPath, "html")) {
            return false;
        } else {
            return isStaticResources(requestPath);
        }
    }


    /**
     * 从Map中获取url匹配的对象。
     *
     * @param path 传入的url
     * @param map  以路径匹配pattern为key的map
     * @param <T>  返回的对象
     * @return T
     */
    public static <T> T getPathMatchedObject(String path, Map<String, T> map) {
        PathMatcher pathMatcher = getPathMatcher();
        for (String key : map.keySet()) {
            if (pathMatcher.match(key, path)) {
                return map.get(key);
            }
        }

        return null;
    }

    public static String getRequestPayload(ServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = request.getReader();) {
            char[] buffer = new char[1024];
            int length;
            while ((length = reader.read(buffer)) != -1) {
                stringBuilder.append(buffer, 0, length);
            }
        } catch (IOException e) {
            log.error("[Herodotus] |- Get Request Payload Error!");
        }

        return stringBuilder.toString();
    }
}
