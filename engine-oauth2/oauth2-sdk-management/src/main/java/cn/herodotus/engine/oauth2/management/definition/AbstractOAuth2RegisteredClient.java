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

package cn.herodotus.engine.oauth2.management.definition;

import cn.herodotus.engine.oauth2.core.enums.Signature;
import cn.herodotus.engine.oauth2.core.enums.TokenFormat;
import cn.herodotus.engine.oauth2.data.jpa.definition.domain.AbstractRegisteredClient;
import cn.herodotus.engine.oauth2.management.entity.OAuth2Scope;
import cn.hutool.core.util.IdUtil;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;

import java.time.Duration;
import java.util.Set;

/**
 * <p>Description: 应用对象转 RegisteredClient 共性属性 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/21 17:46
 */
@MappedSuperclass
public abstract class AbstractOAuth2RegisteredClient extends AbstractRegisteredClient {

    @Column(name = "client_id", length = 100)
    private String clientId = IdUtil.fastSimpleUUID();

    @Column(name = "client_secret", length = 100)
    private String clientSecret = IdUtil.fastSimpleUUID();

    /* --- ClientSettings Begin --- */
    @Column(name = "require_proof_key")
    private Boolean requireProofKey = Boolean.FALSE;

    @Column(name = "require_authorization_consent")
    private Boolean requireAuthorizationConsent = Boolean.TRUE;

    @Column(name = "jwk_set_url", length = 1000)
    private String jwkSetUrl;

    @Column(name = "signing_algorithm")
    @Enumerated(EnumType.ORDINAL)
    private Signature authenticationSigningAlgorithm;
    /* --- ClientSettings End --- */


    /* --- TokenSettings Begin --- */
    @Column(name = "authorization_code_validity")
    private Duration authorizationCodeValidity = Duration.ofMinutes(5);

    @Column(name = "access_token_validity")
    private Duration accessTokenValidity = Duration.ofMinutes(5);

    @Column(name = "device_code_validity")
    private Duration deviceCodeValidity = Duration.ofMinutes(5);

    @Column(name = "refresh_token_validity")
    private Duration refreshTokenValidity = Duration.ofMinutes(60);

    @Column(name = "access_token_format")
    @Enumerated(EnumType.ORDINAL)
    private TokenFormat accessTokenFormat = TokenFormat.REFERENCE;

    @Column(name = "reuse_refresh_tokens")
    private Boolean reuseRefreshTokens = Boolean.TRUE;

    @Column(name = "signature_algorithm")
    @Enumerated(EnumType.ORDINAL)
    private Signature idTokenSignatureAlgorithm = Signature.RS256;
    /* --- TokenSettings End --- */

    public abstract Set<OAuth2Scope> getScopes();

    @Override
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Boolean getRequireProofKey() {
        return requireProofKey;
    }

    public void setRequireProofKey(Boolean requireProofKey) {
        this.requireProofKey = requireProofKey;
    }

    public Boolean getRequireAuthorizationConsent() {
        return requireAuthorizationConsent;
    }

    public void setRequireAuthorizationConsent(Boolean requireAuthorizationConsent) {
        this.requireAuthorizationConsent = requireAuthorizationConsent;
    }

    public String getJwkSetUrl() {
        return jwkSetUrl;
    }

    public void setJwkSetUrl(String jwkSetUrl) {
        this.jwkSetUrl = jwkSetUrl;
    }

    public Signature getAuthenticationSigningAlgorithm() {
        return authenticationSigningAlgorithm;
    }

    public void setAuthenticationSigningAlgorithm(Signature authenticationSigningAlgorithm) {
        this.authenticationSigningAlgorithm = authenticationSigningAlgorithm;
    }

    public Duration getAuthorizationCodeValidity() {
        return authorizationCodeValidity;
    }

    public void setAuthorizationCodeValidity(Duration authorizationCodeValidity) {
        this.authorizationCodeValidity = authorizationCodeValidity;
    }

    public Duration getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Duration accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Duration getDeviceCodeValidity() {
        return deviceCodeValidity;
    }

    public void setDeviceCodeValidity(Duration deviceCodeValidity) {
        this.deviceCodeValidity = deviceCodeValidity;
    }

    public Duration getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Duration refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public TokenFormat getAccessTokenFormat() {
        return accessTokenFormat;
    }

    public void setAccessTokenFormat(TokenFormat accessTokenFormat) {
        this.accessTokenFormat = accessTokenFormat;
    }

    public Boolean getReuseRefreshTokens() {
        return reuseRefreshTokens;
    }

    public void setReuseRefreshTokens(Boolean reuseRefreshTokens) {
        this.reuseRefreshTokens = reuseRefreshTokens;
    }

    public Signature getIdTokenSignatureAlgorithm() {
        return idTokenSignatureAlgorithm;
    }

    public void setIdTokenSignatureAlgorithm(Signature idTokenSignatureAlgorithm) {
        this.idTokenSignatureAlgorithm = idTokenSignatureAlgorithm;
    }
}
