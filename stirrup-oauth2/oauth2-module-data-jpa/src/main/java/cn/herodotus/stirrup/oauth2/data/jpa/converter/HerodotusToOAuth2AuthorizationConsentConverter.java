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

package cn.herodotus.stirrup.oauth2.data.jpa.converter;

import cn.herodotus.engine.oauth2.core.definition.domain.HerodotusGrantedAuthority;
import cn.herodotus.stirrup.oauth2.data.jpa.entity.HerodotusAuthorizationConsent;
import org.springframework.core.convert.converter.Converter;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.util.StringUtils;

/**
 * <p>Description: HerodotusAuthorizationConsent 转 OAuth2AuthorizationConsent 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/21 21:03
 */
public class HerodotusToOAuth2AuthorizationConsentConverter implements Converter<HerodotusAuthorizationConsent, OAuth2AuthorizationConsent> {

    private final RegisteredClientRepository registeredClientRepository;

    public HerodotusToOAuth2AuthorizationConsentConverter(RegisteredClientRepository registeredClientRepository) {
        this.registeredClientRepository = registeredClientRepository;
    }

    @Override
    public OAuth2AuthorizationConsent convert(HerodotusAuthorizationConsent authorizationConsent) {
        String registeredClientId = authorizationConsent.getRegisteredClientId();
        RegisteredClient registeredClient = this.registeredClientRepository.findById(registeredClientId);
        if (registeredClient == null) {
            throw new DataRetrievalFailureException(
                    "The RegisteredClient with id '" + registeredClientId + "' was not found in the RegisteredClientRepository.");
        }

        OAuth2AuthorizationConsent.Builder builder = OAuth2AuthorizationConsent.withId(
                registeredClientId, authorizationConsent.getPrincipalName());
        if (authorizationConsent.getAuthorities() != null) {
            for (String authority : StringUtils.commaDelimitedListToSet(authorizationConsent.getAuthorities())) {
                builder.authority(new HerodotusGrantedAuthority(authority));
            }
        }

        return builder.build();
    }
}
