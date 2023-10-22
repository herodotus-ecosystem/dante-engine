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

package cn.herodotus.engine.oauth2.core.constants;

/**
 * <p>Description: 扩展 OAuth2 错误代码 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/9 12:59
 */
public interface OAuth2ErrorKeys {

    String INVALID_REQUEST = "invalid_request";
    String UNAUTHORIZED_CLIENT = "unauthorized_client";
    String ACCESS_DENIED = "access_denied";
    String UNSUPPORTED_RESPONSE_TYPE = "unsupported_response_type";
    String INVALID_SCOPE = "invalid_scope";
    String INSUFFICIENT_SCOPE = "insufficient_scope";
    String INVALID_TOKEN = "invalid_token";
    String SERVER_ERROR = "server_error";
    String TEMPORARILY_UNAVAILABLE = "temporarily_unavailable";
    String INVALID_CLIENT = "invalid_client";
    String INVALID_GRANT = "invalid_grant";
    String UNSUPPORTED_GRANT_TYPE = "unsupported_grant_type";
    String UNSUPPORTED_TOKEN_TYPE = "unsupported_token_type";
    String INVALID_REDIRECT_URI = "invalid_redirect_uri";

    String ACCOUNT_EXPIRED = "AccountExpiredException";
    String ACCOUNT_DISABLED = "DisabledException";
    String ACCOUNT_LOCKED = "LockedException";
    String ACCOUNT_ENDPOINT_LIMITED = "AccountEndpointLimitedException";
    String BAD_CREDENTIALS = "BadCredentialsException";
    String CREDENTIALS_EXPIRED = "CredentialsExpiredException";
    String USERNAME_NOT_FOUND = "UsernameNotFoundException";

    String SESSION_EXPIRED = "SessionExpiredException";

}
