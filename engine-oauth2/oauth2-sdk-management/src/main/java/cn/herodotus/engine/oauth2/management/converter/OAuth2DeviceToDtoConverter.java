/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.oauth2.management.converter;

import cn.herodotus.engine.oauth2.management.dto.OAuth2ApplicationDto;
import cn.herodotus.engine.oauth2.management.dto.OAuth2DeviceDto;
import cn.herodotus.engine.oauth2.management.entity.OAuth2Application;
import cn.herodotus.engine.oauth2.management.entity.OAuth2Device;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

/**
 * <p>Description: OAuth2Application 转 OAuth2ApplicationDto 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/22 13:27
 */
public class OAuth2DeviceToDtoConverter implements Converter<OAuth2Device, OAuth2DeviceDto> {

    @Override
    public OAuth2DeviceDto convert(OAuth2Device entity) {
        OAuth2DeviceDto dto = new OAuth2DeviceDto();
        dto.setDeviceId(entity.getDeviceId());
        dto.setDeviceName(entity.getDeviceName());
        dto.setProductId(entity.getProductId());
        dto.setClientId(entity.getClientId());
        dto.setClientIdIssuedAt(entity.getClientIdIssuedAt());
        dto.setClientSecret(entity.getClientSecret());
        dto.setClientSecretExpiresAt(entity.getClientSecretExpiresAt());
        dto.setClientAuthenticationMethods(StringUtils.commaDelimitedListToSet(entity.getClientAuthenticationMethods()));
        dto.setAuthorizationGrantTypes(StringUtils.commaDelimitedListToSet(entity.getAuthorizationGrantTypes()));
        dto.setRedirectUris(entity.getRedirectUris());
        dto.setPostLogoutRedirectUris(entity.getPostLogoutRedirectUris());
        dto.setRequireProofKey(entity.getRequireProofKey());
        dto.setRequireAuthorizationConsent(entity.getRequireAuthorizationConsent());
        dto.setJwkSetUrl(entity.getJwkSetUrl());
        dto.setAuthenticationSigningAlgorithm(entity.getAuthenticationSigningAlgorithm());
        dto.setAccessTokenValidity(entity.getAccessTokenValidity());
        dto.setAuthorizationCodeValidity(entity.getAuthorizationCodeValidity());
        dto.setDeviceCodeValidity(entity.getDeviceCodeValidity());
        dto.setReuseRefreshTokens(entity.getReuseRefreshTokens());
        dto.setAccessTokenFormat(entity.getAccessTokenFormat());
        dto.setRefreshTokenValidity(entity.getRefreshTokenValidity());
        dto.setIdTokenSignatureAlgorithm(entity.getIdTokenSignatureAlgorithm());
        dto.setScopes(entity.getScopes());
        dto.setReserved(entity.getReserved());
        dto.setDescription(entity.getDescription());
        dto.setReversion(entity.getReversion());
        dto.setRanking(entity.getRanking());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
