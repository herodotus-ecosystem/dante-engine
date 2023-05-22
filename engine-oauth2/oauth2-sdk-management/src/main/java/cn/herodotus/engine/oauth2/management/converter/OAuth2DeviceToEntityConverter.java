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
 * <p>Description: OAuth2ApplicationDto 转 OAuth2Application 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/22 13:28
 */
public class OAuth2DeviceToEntityConverter implements Converter<OAuth2DeviceDto, OAuth2Device> {
    @Override
    public OAuth2Device convert(OAuth2DeviceDto dto) {
        OAuth2Device entity = new OAuth2Device();
        entity.setDeviceId(dto.getDeviceId());
        entity.setDeviceName(dto.getDeviceName());
        entity.setProductId(dto.getProductId());
        entity.setClientId(dto.getClientId());
        entity.setClientIdIssuedAt(dto.getClientIdIssuedAt());
        entity.setClientSecret(dto.getClientSecret());
        entity.setClientSecretExpiresAt(dto.getClientSecretExpiresAt());
        entity.setClientAuthenticationMethods(StringUtils.collectionToCommaDelimitedString(dto.getClientAuthenticationMethods()));
        entity.setAuthorizationGrantTypes(StringUtils.collectionToCommaDelimitedString(dto.getAuthorizationGrantTypes()));
        entity.setRedirectUris(dto.getRedirectUris());
        entity.setPostLogoutRedirectUris(dto.getPostLogoutRedirectUris());
        entity.setRequireProofKey(dto.getRequireProofKey());
        entity.setRequireAuthorizationConsent(dto.getRequireAuthorizationConsent());
        entity.setJwkSetUrl(dto.getJwkSetUrl());
        entity.setAuthenticationSigningAlgorithm(dto.getAuthenticationSigningAlgorithm());
        entity.setAccessTokenValidity(dto.getAccessTokenValidity());
        entity.setAuthorizationCodeValidity(dto.getAuthorizationCodeValidity());
        entity.setRefreshTokenValidity(dto.getRefreshTokenValidity());
        entity.setDeviceCodeValidity(dto.getDeviceCodeValidity());
        entity.setReuseRefreshTokens(dto.getReuseRefreshTokens());
        entity.setIdTokenSignatureAlgorithm(dto.getIdTokenSignatureAlgorithm());
        entity.setAccessTokenFormat(dto.getAccessTokenFormat());
        entity.setIdTokenSignatureAlgorithm(dto.getIdTokenSignatureAlgorithm());
        entity.setScopes(dto.getScopes());
        entity.setReserved(dto.getReserved());
        entity.setDescription(dto.getDescription());
        entity.setReversion(dto.getReversion());
        entity.setRanking(dto.getRanking());
        entity.setStatus(dto.getStatus());
        return entity;
    }
}
