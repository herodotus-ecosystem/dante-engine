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

package cn.herodotus.engine.oauth2.management.response;

import cn.herodotus.engine.oauth2.management.service.OAuth2DeviceService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.oidc.OidcClientRegistration;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcClientRegistrationAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.oidc.http.converter.OidcClientRegistrationHttpMessageConverter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * <p>Description: 客户端自动注册成功后续逻辑处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/23 17:37
 */
public class OidcClientRegistrationResponseHandler implements AuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(OidcClientRegistrationResponseHandler.class);

    private final OAuth2DeviceService deviceService;

    private final HttpMessageConverter<OidcClientRegistration> clientRegistrationHttpMessageConverter =
            new OidcClientRegistrationHttpMessageConverter();

    public OidcClientRegistrationResponseHandler(OAuth2DeviceService deviceService) {
        this.deviceService = deviceService;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OidcClientRegistrationAuthenticationToken clientRegistrationAuthenticationToken =
                (OidcClientRegistrationAuthenticationToken) authentication;

        OidcClientRegistration clientRegistration = clientRegistrationAuthenticationToken.getClientRegistration();

        boolean success = deviceService.sync(clientRegistration);
        if (success) {
            log.info("[Herodotus] |- Sync oidcClientRegistration to device succeed.");
        } else {
            log.warn("[Herodotus] |- Sync oidcClientRegistration to device failed!");
        }

        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        if (HttpMethod.POST.name().equals(request.getMethod())) {
            httpResponse.setStatusCode(HttpStatus.CREATED);
        } else {
            httpResponse.setStatusCode(HttpStatus.OK);
        }
        this.clientRegistrationHttpMessageConverter.write(clientRegistration, null, httpResponse);
    }
}
