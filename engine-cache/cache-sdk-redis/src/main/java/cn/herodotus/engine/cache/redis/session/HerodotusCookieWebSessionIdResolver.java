/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
 * 2.请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.cache.redis.session;

import cn.hutool.core.codec.Base64;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpCookie;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.session.CookieWebSessionIdResolver;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Description: Redis Web Session 扩展 </p>
 * <p>
 * 覆盖webSession中读取sessionId的写法，将SESSION信息进行base64解码，默认实现中是没有base64解码的，sessionId传到下游时不一致，会导致session不共享：
 *
 * @author : gengwei.zheng
 * @date : 2022/5/23 22:52
 */
public class HerodotusCookieWebSessionIdResolver extends CookieWebSessionIdResolver {

    private static final Logger log = LoggerFactory.getLogger(HerodotusCookieWebSessionIdResolver.class);

    /**
     * 处理 session id，进行解密，防止前后端处理不一致。
     * <p>
     * {@link org.springframework.session.web.http.DefaultCookieSerializer#readCookieValues(HttpServletRequest)}
     *
     * @param exchange Webflux Content
     * @return Cookie 内容
     */
    @Override
    public List<String> resolveSessionIds(ServerWebExchange exchange) {
        MultiValueMap<String, HttpCookie> cookieMap = exchange.getRequest().getCookies();
        // 获取SESSION
        List<HttpCookie> cookies = cookieMap.get(getCookieName());
        if (cookies == null) {
            return Collections.emptyList();
        }
        return cookies.stream().map(HttpCookie::getValue).map(this::base64Decode).collect(Collectors.toList());
    }

    private String base64Decode(String base64Value) {
        String result = Base64.decodeStr(base64Value);
        log.debug("[Herodotus] |- Webflux decode session id to: [{}]" + result);
        return result;
    }
}
