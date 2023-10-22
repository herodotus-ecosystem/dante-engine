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

package cn.herodotus.engine.oauth2.authentication.provider;

import cn.herodotus.engine.assistant.core.utils.type.ListUtils;
import cn.herodotus.engine.oauth2.authentication.utils.OAuth2EndpointUtils;
import cn.herodotus.engine.oauth2.core.constants.OAuth2ErrorKeys;
import cn.herodotus.engine.rest.core.exception.SessionInvalidException;
import cn.herodotus.engine.rest.protect.crypto.processor.HttpCryptoProcessor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.AuthenticationConverter;

import java.util.List;

/**
 * <p>Description: 抽象的认证 Converter </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/21 6:23
 */
public abstract class AbstractAuthenticationConverter implements AuthenticationConverter {

    private final HttpCryptoProcessor httpCryptoProcessor;

    public AbstractAuthenticationConverter(HttpCryptoProcessor httpCryptoProcessor) {
        this.httpCryptoProcessor = httpCryptoProcessor;
    }

    protected String[] decrypt(String sessionId, List<String> parameters) {
        if (StringUtils.isNotBlank(sessionId) && CollectionUtils.isNotEmpty(parameters)) {
            List<String> result = parameters.stream().map(item -> decrypt(sessionId, item)).toList();
            return ListUtils.toStringArray(result);
        }

        return ListUtils.toStringArray(parameters);
    }

    protected String decrypt(String sessionId, String parameter) {
        if (StringUtils.isNotBlank(sessionId) && StringUtils.isNotBlank(parameter)) {
            try {
                return httpCryptoProcessor.decrypt(sessionId, parameter);
            } catch (SessionInvalidException e) {
                OAuth2EndpointUtils.throwError(
                        OAuth2ErrorKeys.SESSION_EXPIRED,
                        e.getMessage(),
                        OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
            }
        }
        return parameter;
    }
}
