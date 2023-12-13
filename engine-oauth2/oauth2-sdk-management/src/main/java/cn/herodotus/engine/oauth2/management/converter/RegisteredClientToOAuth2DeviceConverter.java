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

package cn.herodotus.engine.oauth2.management.converter;

import cn.herodotus.engine.oauth2.core.enums.SignatureJwsAlgorithm;
import cn.herodotus.engine.oauth2.core.enums.TokenFormat;
import cn.herodotus.engine.oauth2.core.enums.AllJwsAlgorithm;
import cn.herodotus.engine.oauth2.management.entity.OAuth2Device;
import cn.herodotus.engine.oauth2.management.entity.OAuth2Scope;
import cn.herodotus.engine.oauth2.management.service.OAuth2ScopeService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.dromara.hutool.core.date.DateUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Description: OAuth2Device 转 RegisteredClient 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/21 22:05
 */
public class RegisteredClientToOAuth2DeviceConverter implements Converter<RegisteredClient, OAuth2Device> {

    private final OAuth2ScopeService scopeService;

    public RegisteredClientToOAuth2DeviceConverter(OAuth2ScopeService scopeService) {
        this.scopeService = scopeService;
    }

    @Override
    public OAuth2Device convert(RegisteredClient registeredClient) {

        OAuth2Device device = new OAuth2Device();
        device.setDeviceId(registeredClient.getId());
        device.setDeviceName(registeredClient.getClientName());
        device.setProductId("");
        device.setScopes(getOAuth2Scopes(registeredClient.getScopes()));
        device.setClientId(registeredClient.getClientId());
        device.setClientSecret(registeredClient.getClientSecret());
        device.setClientIdIssuedAt(DateUtil.toLocalDateTime(registeredClient.getClientIdIssuedAt()));
        device.setClientSecretExpiresAt(DateUtil.toLocalDateTime(registeredClient.getClientSecretExpiresAt()));
        device.setClientAuthenticationMethods(StringUtils.collectionToCommaDelimitedString(registeredClient.getClientAuthenticationMethods()));
        device.setAuthorizationGrantTypes(StringUtils.collectionToCommaDelimitedString(registeredClient.getAuthorizationGrantTypes().stream().map(AuthorizationGrantType::getValue).collect(Collectors.toSet())));
        device.setRedirectUris(StringUtils.collectionToCommaDelimitedString(registeredClient.getRedirectUris()));
        device.setPostLogoutRedirectUris(StringUtils.collectionToCommaDelimitedString(registeredClient.getRedirectUris()));

        ClientSettings clientSettings = registeredClient.getClientSettings();
        device.setRequireProofKey(clientSettings.isRequireProofKey());
        device.setRequireAuthorizationConsent(clientSettings.isRequireAuthorizationConsent());
        device.setJwkSetUrl(clientSettings.getJwkSetUrl());
        if (ObjectUtils.isNotEmpty(clientSettings.getTokenEndpointAuthenticationSigningAlgorithm())) {
            device.setAuthenticationSigningAlgorithm(AllJwsAlgorithm.valueOf(clientSettings.getTokenEndpointAuthenticationSigningAlgorithm().getName()));
        }

        TokenSettings tokenSettings = registeredClient.getTokenSettings();
        device.setAuthorizationCodeValidity(tokenSettings.getAuthorizationCodeTimeToLive());
        device.setAccessTokenValidity(tokenSettings.getAccessTokenTimeToLive());
        device.setDeviceCodeValidity(tokenSettings.getDeviceCodeTimeToLive());
        device.setRefreshTokenValidity(tokenSettings.getRefreshTokenTimeToLive());
        device.setAccessTokenFormat(TokenFormat.get(tokenSettings.getAccessTokenFormat().getValue()));
        device.setReuseRefreshTokens(tokenSettings.isReuseRefreshTokens());
        device.setIdTokenSignatureAlgorithm(SignatureJwsAlgorithm.valueOf(tokenSettings.getIdTokenSignatureAlgorithm().getName()));

        return device;
    }

    private Set<OAuth2Scope> getOAuth2Scopes(Set<String> scopes) {
        if (CollectionUtils.isNotEmpty(scopes)) {
            List<String> scopeCodes = new ArrayList<>(scopes);
            List<OAuth2Scope> result = scopeService.findByScopeCodeIn(scopeCodes);
            if (CollectionUtils.isNotEmpty(result)) {
                return new HashSet<>(result);
            }
        }
        return new HashSet<>();
    }
}
