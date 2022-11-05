/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
 * 2.请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.oauth2.authorization.authentication;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.endpoint.PkceParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;

/**
 * <p>Description: 迁移的CodeVerifierAuthenticator </p>
 *
 * 主要解决 CodeVerifierAuthenticator 为包级可访问问题，支持
 *
 * @author : gengwei.zheng
 * @date : 2022/4/2 13:41
 */
final class CodeVerifierAuthenticator {
    private static final OAuth2TokenType AUTHORIZATION_CODE_TOKEN_TYPE = new OAuth2TokenType(OAuth2ParameterNames.CODE);
    private final OAuth2AuthorizationService authorizationService;

    CodeVerifierAuthenticator(OAuth2AuthorizationService authorizationService) {
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        this.authorizationService = authorizationService;
    }

    void authenticateRequired(OAuth2ClientAuthenticationToken clientAuthentication,
                              RegisteredClient registeredClient) {
        if (!authenticate(clientAuthentication, registeredClient)) {
            throwInvalidGrant(PkceParameterNames.CODE_VERIFIER);
        }
    }

    void authenticateIfAvailable(OAuth2ClientAuthenticationToken clientAuthentication,
                                 RegisteredClient registeredClient) {
        authenticate(clientAuthentication, registeredClient);
    }

    private boolean authenticate(OAuth2ClientAuthenticationToken clientAuthentication,
                                 RegisteredClient registeredClient) {

        Map<String, Object> parameters = clientAuthentication.getAdditionalParameters();
        if (!authorizationCodeGrant(parameters)) {
            return false;
        }

        OAuth2Authorization authorization = this.authorizationService.findByToken(
                (String) parameters.get(OAuth2ParameterNames.CODE),
                AUTHORIZATION_CODE_TOKEN_TYPE);
        if (authorization == null) {
            throwInvalidGrant(OAuth2ParameterNames.CODE);
        }

        OAuth2AuthorizationRequest authorizationRequest = authorization.getAttribute(
                OAuth2AuthorizationRequest.class.getName());

        String codeChallenge = (String) authorizationRequest.getAdditionalParameters()
                .get(PkceParameterNames.CODE_CHALLENGE);
        if (!StringUtils.hasText(codeChallenge)) {
            if (registeredClient.getClientSettings().isRequireProofKey()) {
                throwInvalidGrant(PkceParameterNames.CODE_CHALLENGE);
            } else {
                return false;
            }
        }

        String codeChallengeMethod = (String) authorizationRequest.getAdditionalParameters()
                .get(PkceParameterNames.CODE_CHALLENGE_METHOD);
        String codeVerifier = (String) parameters.get(PkceParameterNames.CODE_VERIFIER);
        if (!codeVerifierValid(codeVerifier, codeChallenge, codeChallengeMethod)) {
            throwInvalidGrant(PkceParameterNames.CODE_VERIFIER);
        }

        return true;
    }

    private static boolean authorizationCodeGrant(Map<String, Object> parameters) {
        return AuthorizationGrantType.AUTHORIZATION_CODE.getValue().equals(
                parameters.get(OAuth2ParameterNames.GRANT_TYPE)) &&
                parameters.get(OAuth2ParameterNames.CODE) != null;
    }

    private static boolean codeVerifierValid(String codeVerifier, String codeChallenge, String codeChallengeMethod) {
        if (!StringUtils.hasText(codeVerifier)) {
            return false;
        } else if (!StringUtils.hasText(codeChallengeMethod) || "plain".equals(codeChallengeMethod)) {
            return codeVerifier.equals(codeChallenge);
        } else if ("S256".equals(codeChallengeMethod)) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] digest = md.digest(codeVerifier.getBytes(StandardCharsets.US_ASCII));
                String encodedVerifier = Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
                return encodedVerifier.equals(codeChallenge);
            } catch (NoSuchAlgorithmException ex) {
                // It is unlikely that SHA-256 is not available on the server. If it is not available,
                // there will likely be bigger issues as well. We default to SERVER_ERROR.
            }
        }
        throw new OAuth2AuthenticationException(OAuth2ErrorCodes.SERVER_ERROR);
    }

    private static void throwInvalidGrant(String parameterName) {
        OAuth2Error error = new OAuth2Error(
                OAuth2ErrorCodes.INVALID_GRANT,
                "Client authentication failed: " + parameterName,
                null
        );
        throw new OAuth2AuthenticationException(error);
    }

}