/*
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君) (herodotus@aliyun.com & www.herodotus.cn)
 *
 * Dante Engine licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.gnu.org/licenses/lgpl.html>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.herodotus.engine.oauth2.authentication.configurer;

import cn.herodotus.engine.oauth2.authentication.provider.OAuth2FormLoginAuthenticationToken;
import cn.herodotus.engine.oauth2.core.utils.SymmetricUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

/**
 * <p>Description: OAuth2 表单登录过滤器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/4/12 11:08
 */
public class OAuth2FormLoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(OAuth2FormLoginAuthenticationFilter.class);
    private boolean postOnly = true;

    public OAuth2FormLoginAuthenticationFilter() {
        super();
    }

    public OAuth2FormLoginAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        OAuth2FormLoginAuthenticationToken authRequest = getAuthenticationToken(request);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private OAuth2FormLoginAuthenticationToken getAuthenticationToken(
            HttpServletRequest request) {

        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String key = request.getParameter("symmetric");

        if (StringUtils.isBlank(username)) {
            username = "";
        }

        if (StringUtils.isBlank(password)) {
            password = "";
        }

        if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            byte[] byteKey = SymmetricUtils.getDecryptedSymmetricKey(key);
            username = SymmetricUtils.decrypt(username, byteKey);
            password = SymmetricUtils.decrypt(password, byteKey);
            log.debug("[Herodotus] |- Decrypt Username is : [{}], Password is : [{}]", username, password);
        }

        return new OAuth2FormLoginAuthenticationToken(username, password);
    }

    @Override
    public void setPostOnly(boolean postOnly) {
        super.setPostOnly(postOnly);
        this.postOnly = postOnly;
    }

    /**
     * 重写该方法，避免在日志Debug级别会输出错误信息的问题。
     *
     * @param request  请求
     * @param response 响应
     * @param failed   失败内容
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        getRememberMeServices().loginFail(request, response);
        getFailureHandler().onAuthenticationFailure(request, response, failed);
    }
}
