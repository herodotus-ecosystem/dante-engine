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

package cn.herodotus.engine.assistant.definition.constants;

import org.dromara.hutool.core.date.DatePattern;

/**
 * <p>Description: 默认常量合集 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/30 10:27
 */
public interface DefaultConstants {

    String AUTHORIZATION_ENDPOINT = "/oauth2/authorize";
    String TOKEN_ENDPOINT = "/oauth2/token";
    String TOKEN_REVOCATION_ENDPOINT = "/oauth2/revoke";
    String TOKEN_INTROSPECTION_ENDPOINT = "/oauth2/introspect";
    String DEVICE_AUTHORIZATION_ENDPOINT = "/oauth2/device_authorization";
    String DEVICE_VERIFICATION_ENDPOINT = "/oauth2/device_verification";
    String JWK_SET_ENDPOINT = "/oauth2/jwks";
    String OIDC_CLIENT_REGISTRATION_ENDPOINT = "/connect/register";
    String OIDC_LOGOUT_ENDPOINT = "/connect/logout";
    String OIDC_USER_INFO_ENDPOINT = "/userinfo";

    String AUTHORIZATION_CONSENT_URI = "/oauth2/consent";
    String DEVICE_ACTIVATION_URI = "/oauth2/device_activation";
    String DEVICE_VERIFICATION_SUCCESS_URI = "/device_activated";

    /**
     * 默认租户ID
     */
    String TENANT_ID = "public";
    /**
     * 默认树形结构根节点
     */
    String TREE_ROOT_ID = SymbolConstants.ZERO;
    /**
     * 默认的时间日期格式
     */
    String DATE_TIME_FORMAT = DatePattern.NORM_DATETIME_PATTERN;
}
