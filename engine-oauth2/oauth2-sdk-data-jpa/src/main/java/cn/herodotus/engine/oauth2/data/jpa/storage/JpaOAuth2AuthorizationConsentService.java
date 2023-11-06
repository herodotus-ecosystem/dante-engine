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

package cn.herodotus.engine.oauth2.data.jpa.storage;

import cn.herodotus.engine.oauth2.data.jpa.converter.HerodotusToOAuth2AuthorizationConsentConverter;
import cn.herodotus.engine.oauth2.data.jpa.converter.OAuth2ToHerodotusAuthorizationConsentConverter;
import cn.herodotus.engine.oauth2.data.jpa.entity.HerodotusAuthorizationConsent;
import cn.herodotus.engine.oauth2.data.jpa.service.HerodotusAuthorizationConsentService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

/**
 * <p>Description: 基于 JPA 的 OAuth2 认证服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/25 22:15
 */
public class JpaOAuth2AuthorizationConsentService implements OAuth2AuthorizationConsentService {

    private final HerodotusAuthorizationConsentService herodotusAuthorizationConsentService;
    private final Converter<HerodotusAuthorizationConsent, OAuth2AuthorizationConsent> herodotusToOAuth2Converter;
    private final Converter<OAuth2AuthorizationConsent, HerodotusAuthorizationConsent> oauth2ToherodotusConverter;

    public JpaOAuth2AuthorizationConsentService(HerodotusAuthorizationConsentService herodotusAuthorizationConsentService, RegisteredClientRepository registeredClientRepository) {
        this.herodotusAuthorizationConsentService = herodotusAuthorizationConsentService;
        this.herodotusToOAuth2Converter = new HerodotusToOAuth2AuthorizationConsentConverter(registeredClientRepository);
        this.oauth2ToherodotusConverter = new OAuth2ToHerodotusAuthorizationConsentConverter();
    }

    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {
        this.herodotusAuthorizationConsentService.save(toEntity(authorizationConsent));
    }

    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {
        this.herodotusAuthorizationConsentService.deleteByRegisteredClientIdAndPrincipalName(
                authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());
    }

    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        return this.herodotusAuthorizationConsentService.findByRegisteredClientIdAndPrincipalName(
                registeredClientId, principalName).map(this::toObject).orElse(null);
    }

    private OAuth2AuthorizationConsent toObject(HerodotusAuthorizationConsent authorizationConsent) {
        return herodotusToOAuth2Converter.convert(authorizationConsent);
    }

    private HerodotusAuthorizationConsent toEntity(OAuth2AuthorizationConsent authorizationConsent) {
        return oauth2ToherodotusConverter.convert(authorizationConsent);
    }
}
