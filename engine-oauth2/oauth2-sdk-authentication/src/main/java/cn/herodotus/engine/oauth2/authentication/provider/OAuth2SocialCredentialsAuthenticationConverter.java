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

package cn.herodotus.engine.oauth2.authentication.provider;

import cn.herodotus.engine.assistant.definition.constants.BaseConstants;
import cn.herodotus.engine.assistant.core.enums.AccountType;
import cn.herodotus.engine.oauth2.authentication.utils.OAuth2EndpointUtils;
import cn.herodotus.engine.oauth2.core.definition.HerodotusGrantType;
import cn.herodotus.engine.rest.protect.crypto.processor.HttpCryptoProcessor;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

/**
 * <p>Description: 社交认证 Converter </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/3/31 14:16
 */
public class OAuth2SocialCredentialsAuthenticationConverter extends AbstractAuthenticationConverter {

    public OAuth2SocialCredentialsAuthenticationConverter(HttpCryptoProcessor httpCryptoProcessor) {
        super(httpCryptoProcessor);
    }

    @Override
    public Authentication convert(HttpServletRequest request) {
        // grant_type (REQUIRED)
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!HerodotusGrantType.SOCIAL.getValue().equals(grantType)) {
            return null;
        }

        MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getParameters(request);

        // scope (OPTIONAL)
        String scope = OAuth2EndpointUtils.checkOptionalParameter(parameters, OAuth2ParameterNames.SCOPE);

        // source (REQUIRED)
        String source = OAuth2EndpointUtils.checkRequiredParameter(parameters, BaseConstants.SOURCE);
        // others (REQUIRED)
        // TODO：2022-03-31 这里主要是作为参数的检查，社交登录内容比较多，后续根据实际情况添加
        if (StringUtils.hasText(source)) {
            AccountType accountType = AccountType.getAccountType(source);
            if (ObjectUtils.isNotEmpty(accountType)) {
                switch (accountType.getHandler()) {
                    case AccountType.PHONE_NUMBER_HANDLER:
                        OAuth2EndpointUtils.checkRequiredParameter(parameters, "mobile");
                        OAuth2EndpointUtils.checkRequiredParameter(parameters, "code");
                        break;
                    case AccountType.WECHAT_MINI_APP_HANDLER:
                        OAuth2EndpointUtils.checkRequiredParameter(parameters, "appId");
                        OAuth2EndpointUtils.checkRequiredParameter(parameters, "sessionKey");
                        OAuth2EndpointUtils.checkRequiredParameter(parameters, "encryptedData");
                        OAuth2EndpointUtils.checkRequiredParameter(parameters, "iv");
                        break;
                    default:
                        break;
                }
            }
        }

        return new OAuth2SocialCredentialsAuthenticationToken(getClientPrincipal(), getRequestedScopes(scope), getAdditionalParameters(request, parameters));
    }
}
