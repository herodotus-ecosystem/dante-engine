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

package cn.herodotus.engine.oauth2.data.jpa.storage;

import cn.herodotus.engine.oauth2.data.jpa.converter.HerodotusToOAuth2AuthorizationConverter;
import cn.herodotus.engine.oauth2.data.jpa.converter.OAuth2ToHerodotusAuthorizationConverter;
import cn.herodotus.engine.oauth2.data.jpa.entity.HerodotusAuthorization;
import cn.herodotus.engine.oauth2.data.jpa.jackson2.OAuth2JacksonProcessor;
import cn.herodotus.engine.oauth2.data.jpa.service.HerodotusAuthorizationService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>Description: 基于 JPA 的 OAuth2 认证服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/25 22:16
 */
public class JpaOAuth2AuthorizationService implements OAuth2AuthorizationService {

    private static final Logger log = LoggerFactory.getLogger(JpaOAuth2AuthorizationService.class);

    private final HerodotusAuthorizationService herodotusAuthorizationService;
    private final Converter<HerodotusAuthorization, OAuth2Authorization> herodotusToOAuth2Converter;
    private final Converter<OAuth2Authorization, HerodotusAuthorization> oauth2ToHerodotusConverter;

    public JpaOAuth2AuthorizationService(HerodotusAuthorizationService herodotusAuthorizationService, RegisteredClientRepository registeredClientRepository) {
        this.herodotusAuthorizationService = herodotusAuthorizationService;

        OAuth2JacksonProcessor jacksonProcessor = new OAuth2JacksonProcessor();
        this.herodotusToOAuth2Converter = new HerodotusToOAuth2AuthorizationConverter(jacksonProcessor, registeredClientRepository);
        this.oauth2ToHerodotusConverter = new OAuth2ToHerodotusAuthorizationConverter(jacksonProcessor);

    }

    @Override
    public void save(OAuth2Authorization authorization) {
        this.herodotusAuthorizationService.saveAndFlush(toEntity(authorization));
    }

    @Transactional
    @Override
    public void remove(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        this.herodotusAuthorizationService.deleteById(authorization.getId());
        log.debug("[Herodotus] |- Jpa OAuth2 Authorization Service remove entity.");
        // TODO： 后期还是考虑改为异步任务的形式，先临时放在这里。
        this.herodotusAuthorizationService.clearHistoryToken();
        log.debug("[Herodotus] |- Jpa OAuth2 Authorization Service clear history token.");
    }

    @Override
    public OAuth2Authorization findById(String id) {
        HerodotusAuthorization herodotusAuthorization = this.herodotusAuthorizationService.findById(id);
        if (ObjectUtils.isNotEmpty(herodotusAuthorization)) {
            return toObject(herodotusAuthorization);
        } else {
            return null;
        }
    }

    public int findAuthorizationCount(String registeredClientId, String principalName) {
        int count = this.herodotusAuthorizationService.findAuthorizationCount(registeredClientId, principalName);
        log.debug("[Herodotus] |- Jpa OAuth2 Authorization Service findAuthorizationCount.");
        return count;
    }

    public List<OAuth2Authorization> findAvailableAuthorizations(String registeredClientId, String principalName) {
        List<HerodotusAuthorization> authorizations = this.herodotusAuthorizationService.findAvailableAuthorizations(registeredClientId, principalName);
        if (CollectionUtils.isNotEmpty(authorizations)) {
            return authorizations.stream().map(this::toObject).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");

        Optional<HerodotusAuthorization> result;
        if (tokenType == null) {
            result = this.herodotusAuthorizationService.findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValueOrOidcIdTokenValueOrUserCodeValueOrDeviceCodeValue(token);
        } else if (OAuth2ParameterNames.STATE.equals(tokenType.getValue())) {
            result = this.herodotusAuthorizationService.findByState(token);
        } else if (OAuth2ParameterNames.CODE.equals(tokenType.getValue())) {
            result = this.herodotusAuthorizationService.findByAuthorizationCode(token);
        } else if (OAuth2ParameterNames.ACCESS_TOKEN.equals(tokenType.getValue())) {
            result = this.herodotusAuthorizationService.findByAccessToken(token);
        } else if (OAuth2ParameterNames.REFRESH_TOKEN.equals(tokenType.getValue())) {
            result = this.herodotusAuthorizationService.findByRefreshToken(token);
        } else if (OidcParameterNames.ID_TOKEN.equals(tokenType.getValue())) {
            result = this.herodotusAuthorizationService.findByOidcIdTokenValue(token);
        } else if (OAuth2ParameterNames.USER_CODE.equals(tokenType.getValue())) {
            result = this.herodotusAuthorizationService.findByUserCodeValue(token);
        } else if (OAuth2ParameterNames.DEVICE_CODE.equals(tokenType.getValue())) {
            result = this.herodotusAuthorizationService.findByDeviceCodeValue(token);
        } else {
            result = Optional.empty();
        }

        return result.map(this::toObject).orElse(null);
    }

    private OAuth2Authorization toObject(HerodotusAuthorization entity) {
        return herodotusToOAuth2Converter.convert(entity);
    }

    private HerodotusAuthorization toEntity(OAuth2Authorization authorization) {
        return oauth2ToHerodotusConverter.convert(authorization);
    }
}
