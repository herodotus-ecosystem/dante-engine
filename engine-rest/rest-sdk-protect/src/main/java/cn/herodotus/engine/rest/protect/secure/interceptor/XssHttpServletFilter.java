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

package cn.herodotus.engine.rest.protect.secure.interceptor;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <p>Description: Xss 过滤器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/30 23:34
 */
@Component
public class XssHttpServletFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(XssHttpServletFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(request);
        log.trace("[Herodotus] |- XssHttpServletFilter wrapper request for [{}].", request.getRequestURI());
        filterChain.doFilter(xssRequest, servletResponse);
    }
}
