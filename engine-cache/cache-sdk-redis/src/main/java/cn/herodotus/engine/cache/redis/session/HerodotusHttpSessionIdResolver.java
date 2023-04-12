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

package cn.herodotus.engine.cache.redis.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HttpSessionIdResolver;

import java.util.Collections;
import java.util.List;

/**
 * <p>Description: HerodotusHttpSessionIdResolver </p>
 * <p>
 * 扩展的 HttpSessionIdResolver, 以同时支持页面和接口的 Session 共享
 *
 * @author : gengwei.zheng
 * @date : 2022/12/2 21:37
 */
public class HerodotusHttpSessionIdResolver implements HttpSessionIdResolver {

    private static final Logger log = LoggerFactory.getLogger(HerodotusHttpSessionIdResolver.class);

    private static final String WRITTEN_SESSION_ID_ATTR = CookieHttpSessionIdResolver.class.getName()
            .concat(".WRITTEN_SESSION_ID_ATTR");

    private final String headerName;
    private CookieSerializer cookieSerializer = new DefaultCookieSerializer();


    public HerodotusHttpSessionIdResolver(String headerName) {
        if (StringUtils.isBlank(headerName)) {
            throw new IllegalArgumentException("headerName cannot be null");
        }
        this.headerName = headerName;
    }

    private String resolveHeaderSessionId(HttpServletRequest request) {
        String headerValue = request.getHeader(this.headerName);
        if (StringUtils.isNotBlank(headerValue)) {
            log.debug("[Herodotus] |- Resolve http session id [{}] from header in request [{}]", headerValue, request.getRequestURI());
            return headerValue;
        }
        return null;
    }

    private List<String> resolveHeaderSessionIds(HttpServletRequest request) {
        String id = resolveHeaderSessionId(request);
        return StringUtils.isNotBlank(id) ? Collections.singletonList(id) : Collections.emptyList();
    }

    @Override
    public List<String> resolveSessionIds(HttpServletRequest request) {

        List<String> idsInHeader = resolveHeaderSessionIds(request);
        if (CollectionUtils.isNotEmpty(idsInHeader)) {
            return idsInHeader;
        } else {
            return this.cookieSerializer.readCookieValues(request);
        }
    }

    private void changeSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {
        this.cookieSerializer.writeCookieValue(new CookieSerializer.CookieValue(request, response, sessionId));
        response.setHeader(this.headerName, sessionId);
    }

    @Override
    public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {

        if (sessionId.equals(request.getAttribute(WRITTEN_SESSION_ID_ATTR))) {
            return;
        }

        String id = sessionId;
        String herodotusSessionId = resolveHeaderSessionId(request);
        if (StringUtils.isNotBlank(herodotusSessionId)) {
            id = herodotusSessionId;
        }

        request.setAttribute(WRITTEN_SESSION_ID_ATTR, id);
        changeSessionId(request, response, id);
    }

    @Override
    public void expireSession(HttpServletRequest request, HttpServletResponse response) {
        changeSessionId(request, response, "");
    }

    /**
     * Sets the {@link CookieSerializer} to be used.
     *
     * @param cookieSerializer the cookieSerializer to set. Cannot be null.
     */
    public void setCookieSerializer(CookieSerializer cookieSerializer) {
        if (cookieSerializer == null) {
            throw new IllegalArgumentException("cookieSerializer cannot be null");
        }
        this.cookieSerializer = cookieSerializer;
    }
}
