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

package cn.herodotus.engine.oauth2.authentication.response;

import cn.herodotus.engine.oauth2.core.constants.OAuth2ErrorKeys;
import cn.herodotus.engine.oauth2.core.exception.AccountEndpointLimitedException;
import cn.herodotus.engine.oauth2.core.exception.SessionExpiredException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

/**
 * <p>Description: 扩展的 DefaultAuthenticationEventPublisher </p>
 * <p>
 * 支持 OAuth2AuthenticationException 解析
 *
 * @author : gengwei.zheng
 * @date : 2022/7/9 13:47
 */
public class DefaultOAuth2AuthenticationEventPublisher extends DefaultAuthenticationEventPublisher {

    public DefaultOAuth2AuthenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        super(applicationEventPublisher);
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        super.publishAuthenticationFailure(convert(exception), authentication);
    }

    private AuthenticationException convert(AuthenticationException exception) {
        if (exception instanceof OAuth2AuthenticationException authenticationException) {
            OAuth2Error error = authenticationException.getError();

            return switch (error.getErrorCode()) {
                case OAuth2ErrorKeys.ACCOUNT_EXPIRED ->
                        new AccountExpiredException(exception.getMessage(), exception.getCause());
                case OAuth2ErrorKeys.CREDENTIALS_EXPIRED ->
                        new CredentialsExpiredException(exception.getMessage(), exception.getCause());
                case OAuth2ErrorKeys.ACCOUNT_DISABLED ->
                        new DisabledException(exception.getMessage(), exception.getCause());
                case OAuth2ErrorKeys.ACCOUNT_LOCKED ->
                        new LockedException(exception.getMessage(), exception.getCause());
                case OAuth2ErrorKeys.ACCOUNT_ENDPOINT_LIMITED ->
                        new AccountEndpointLimitedException(exception.getMessage(), exception.getCause());
                case OAuth2ErrorKeys.USERNAME_NOT_FOUND ->
                        new UsernameNotFoundException(exception.getMessage(), exception.getCause());
                case OAuth2ErrorKeys.SESSION_EXPIRED ->
                        new SessionExpiredException(exception.getMessage(), exception.getCause());
                default -> new BadCredentialsException(exception.getMessage(), exception.getCause());
            };
        }

        return exception;
    }
}
