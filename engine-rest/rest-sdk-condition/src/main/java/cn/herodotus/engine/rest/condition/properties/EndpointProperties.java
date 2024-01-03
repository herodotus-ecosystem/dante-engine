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

package cn.herodotus.engine.rest.condition.properties;

import cn.herodotus.engine.assistant.definition.constants.DefaultConstants;
import cn.herodotus.engine.assistant.core.utils.WellFormedUtils;
import cn.herodotus.engine.rest.condition.constants.RestConstants;
import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: 平台端点属性 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/3/6 12:00
 */
@ConfigurationProperties(prefix = RestConstants.PROPERTY_PREFIX_ENDPOINT)
public class EndpointProperties {

    /**
     * 认证中心服务名称
     */
    private String uaaServiceName;
    /**
     * 用户中心服务名称
     */
    private String upmsServiceName;
    /**
     * 消息服务名称
     */
    private String messageServiceName;
    /**
     * 对象存储服务名称
     */
    private String ossServiceName;

    /**
     * 统一网关服务地址。可以是IP+端口，可以是域名
     */
    private String gatewayServiceUri;

    /**
     * 统一认证中心服务地址
     */
    private String uaaServiceUri;
    /**
     * 统一权限管理服务地址
     */
    private String upmsServiceUri;
    /**
     * 统一消息服务地址
     */
    private String messageServiceUri;
    /**
     * 对象存储服务地址
     */
    private String ossServiceUri;
    /**
     * OAuth2 Authorization Code 模式认证端点，/oauth2/authorize uri 地址，可修改为自定义地址
     */
    private String authorizationUri;
    /**
     * OAuth2 Authorization Code 模式认证端点，/oauth2/authorize端点地址，可修改为自定义地址
     */
    private String authorizationEndpoint = DefaultConstants.AUTHORIZATION_ENDPOINT;
    /**
     * OAuth2 /oauth2/token 申请 Token uri 地址，可修改为自定义地址
     */
    private String accessTokenUri;
    /**
     * OAuth2 /oauth2/token 申请 Token 端点地址，可修改为自定义地址
     */
    private String accessTokenEndpoint = DefaultConstants.TOKEN_ENDPOINT;
    /**
     * OAuth2 /oauth2/jwks uri 地址，可修改为自定义地址
     */
    private String jwkSetUri;
    /**
     * OAuth2 /oauth2/jwks 端点地址，可修改为自定义地址
     */
    private String jwkSetEndpoint = DefaultConstants.JWK_SET_ENDPOINT;
    /**
     * OAuth2 /oauth2/revoke 撤销 Token uri 地址，可修改为自定义地址
     */
    private String tokenRevocationUri;
    /**
     * OAuth2 /oauth2/revoke 撤销 Token 端点地址，可修改为自定义地址
     */
    private String tokenRevocationEndpoint = DefaultConstants.TOKEN_REVOCATION_ENDPOINT;
    /**
     * OAuth2 /oauth2/introspect 查看 Token uri地址，可修改为自定义地址
     */
    private String tokenIntrospectionUri;
    /**
     * OAuth2 /oauth2/introspect 查看 Token 端点地址，可修改为自定义地址
     */
    private String tokenIntrospectionEndpoint = DefaultConstants.TOKEN_INTROSPECTION_ENDPOINT;
    /**
     * OAuth2 /oauth2/device_authorization 设备授权认证 uri地址，可修改为自定义地址
     */
    private String deviceAuthorizationUri;
    /**
     * OAuth2 /oauth2/device_authorization 设备授权认证端点地址，可修改为自定义地址
     */
    private String deviceAuthorizationEndpoint = DefaultConstants.DEVICE_AUTHORIZATION_ENDPOINT;
    /**
     * OAuth2 /oauth2/device_verification 设备授权校验 uri地址，可修改为自定义地址
     */
    private String deviceVerificationUri;
    /**
     * OAuth2 /oauth2/device_verification 设备授权校验端点地址，可修改为自定义地址
     */
    private String deviceVerificationEndpoint = DefaultConstants.DEVICE_VERIFICATION_ENDPOINT;
    /**
     * OAuth2 OIDC /connect/register uri 地址，可修改为自定义地址
     */
    private String oidcClientRegistrationUri;
    /**
     * OAuth2 OIDC /connect/register 端点地址，可修改为自定义地址
     */
    private String oidcClientRegistrationEndpoint = DefaultConstants.OIDC_CLIENT_REGISTRATION_ENDPOINT;
    /**
     * OAuth2 OIDC /connect/logout uri 地址，可修改为自定义地址
     */
    private String oidcLogoutUri;
    /**
     * OAuth2 OIDC /connect/logout 端点地址，可修改为自定义地址
     */
    private String oidcLogoutEndpoint = DefaultConstants.OIDC_LOGOUT_ENDPOINT;
    /**
     * OAuth2 OIDC /userinfo uri 地址，可修改为自定义地址
     */
    private String oidcUserInfoUri;
    /**
     * OAuth2 OIDC /userinfo 端点地址，可修改为自定义地址
     */
    private String oidcUserInfoEndpoint = DefaultConstants.OIDC_USER_INFO_ENDPOINT;
    /**
     * Spring Authorization Server Issuer Url
     */
    private String issuerUri;

    public String getUaaServiceName() {
        return uaaServiceName;
    }

    public void setUaaServiceName(String uaaServiceName) {
        this.uaaServiceName = uaaServiceName;
    }

    public String getUpmsServiceName() {
        return upmsServiceName;
    }

    public void setUpmsServiceName(String upmsServiceName) {
        this.upmsServiceName = upmsServiceName;
    }

    public String getMessageServiceName() {
        return messageServiceName;
    }

    public void setMessageServiceName(String messageServiceName) {
        this.messageServiceName = messageServiceName;
    }

    public String getOssServiceName() {
        return ossServiceName;
    }

    public void setOssServiceName(String ossServiceName) {
        this.ossServiceName = ossServiceName;
    }

    public String getGatewayServiceUri() {
        return gatewayServiceUri;
    }

    public void setGatewayServiceUri(String gatewayServiceUri) {
        this.gatewayServiceUri = gatewayServiceUri;
    }

    public String getUaaServiceUri() {
        return WellFormedUtils.serviceUri(uaaServiceUri, getUaaServiceName(), getGatewayServiceUri(), "UAA");
    }

    public void setUaaServiceUri(String uaaServiceUri) {
        this.uaaServiceUri = uaaServiceUri;
    }

    public String getUpmsServiceUri() {
        return WellFormedUtils.serviceUri(upmsServiceUri, getUpmsServiceName(), getGatewayServiceUri(), "UPMS");
    }

    public void setUpmsServiceUri(String upmsServiceUri) {
        this.upmsServiceUri = upmsServiceUri;
    }

    public String getMessageServiceUri() {
        return WellFormedUtils.serviceUri(messageServiceUri, getMessageServiceName(), getGatewayServiceUri(), "MESSAGE");
    }

    public void setMessageServiceUri(String messageServiceUri) {
        this.messageServiceUri = messageServiceUri;
    }

    public String getOssServiceUri() {
        return WellFormedUtils.serviceUri(ossServiceUri, getOssServiceName(), getGatewayServiceUri(), "OSS");
    }

    public void setOssServiceUri(String ossServiceUri) {
        this.ossServiceUri = ossServiceUri;
    }

    public String getAuthorizationUri() {
        return WellFormedUtils.sasUri(authorizationUri, getAuthorizationEndpoint(), getIssuerUri());
    }

    public void setAuthorizationUri(String authorizationUri) {
        this.authorizationUri = authorizationUri;
    }

    public String getAccessTokenUri() {
        return WellFormedUtils.sasUri(accessTokenUri, getAccessTokenEndpoint(), getIssuerUri());
    }

    public void setAccessTokenUri(String accessTokenUri) {
        this.accessTokenUri = accessTokenUri;
    }

    public String getJwkSetUri() {
        return WellFormedUtils.sasUri(jwkSetUri, getJwkSetEndpoint(), getIssuerUri());
    }

    public void setJwkSetUri(String jwkSetUri) {
        this.jwkSetUri = jwkSetUri;
    }

    public String getTokenRevocationUri() {
        return WellFormedUtils.sasUri(tokenRevocationUri, getTokenRevocationEndpoint(), getIssuerUri());
    }

    public void setTokenRevocationUri(String tokenRevocationUri) {
        this.tokenRevocationUri = tokenRevocationUri;
    }

    public String getTokenIntrospectionUri() {
        return WellFormedUtils.sasUri(tokenIntrospectionUri, getTokenIntrospectionEndpoint(), getIssuerUri());
    }

    public void setTokenIntrospectionUri(String tokenIntrospectionUri) {
        this.tokenIntrospectionUri = tokenIntrospectionUri;
    }

    public String getDeviceAuthorizationUri() {
        return WellFormedUtils.sasUri(deviceAuthorizationUri, getDeviceAuthorizationEndpoint(), getIssuerUri());
    }

    public void setDeviceAuthorizationUri(String deviceAuthorizationUri) {
        this.deviceAuthorizationUri = deviceAuthorizationUri;
    }

    public String getDeviceVerificationUri() {
        return WellFormedUtils.sasUri(deviceVerificationUri, getDeviceVerificationEndpoint(), getIssuerUri());
    }

    public void setDeviceVerificationUri(String deviceVerificationUri) {
        this.deviceVerificationUri = deviceVerificationUri;
    }

    public String getOidcClientRegistrationUri() {
        return WellFormedUtils.sasUri(oidcClientRegistrationUri, getOidcClientRegistrationEndpoint(), getIssuerUri());
    }

    public void setOidcClientRegistrationUri(String oidcClientRegistrationUri) {
        this.oidcClientRegistrationUri = oidcClientRegistrationUri;
    }

    public String getOidcLogoutUri() {
        return WellFormedUtils.sasUri(oidcLogoutUri, getOidcLogoutEndpoint(), getIssuerUri());
    }

    public void setOidcLogoutUri(String oidcLogoutUri) {
        this.oidcLogoutUri = oidcLogoutUri;
    }

    public String getOidcUserInfoUri() {
        return WellFormedUtils.sasUri(oidcUserInfoUri, getOidcUserInfoEndpoint(), getIssuerUri());
    }

    public void setOidcUserInfoUri(String oidcUserInfoUri) {
        this.oidcUserInfoUri = oidcUserInfoUri;
    }

    public String getIssuerUri() {
        return this.issuerUri;
    }

    public void setIssuerUri(String issuerUri) {
        this.issuerUri = issuerUri;
    }

    public String getAuthorizationEndpoint() {
        return authorizationEndpoint;
    }

    public void setAuthorizationEndpoint(String authorizationEndpoint) {
        this.authorizationEndpoint = authorizationEndpoint;
    }

    public String getAccessTokenEndpoint() {
        return accessTokenEndpoint;
    }

    public void setAccessTokenEndpoint(String accessTokenEndpoint) {
        this.accessTokenEndpoint = accessTokenEndpoint;
    }

    public String getJwkSetEndpoint() {
        return jwkSetEndpoint;
    }

    public void setJwkSetEndpoint(String jwkSetEndpoint) {
        this.jwkSetEndpoint = jwkSetEndpoint;
    }

    public String getTokenRevocationEndpoint() {
        return tokenRevocationEndpoint;
    }

    public void setTokenRevocationEndpoint(String tokenRevocationEndpoint) {
        this.tokenRevocationEndpoint = tokenRevocationEndpoint;
    }

    public String getTokenIntrospectionEndpoint() {
        return tokenIntrospectionEndpoint;
    }

    public void setTokenIntrospectionEndpoint(String tokenIntrospectionEndpoint) {
        this.tokenIntrospectionEndpoint = tokenIntrospectionEndpoint;
    }

    public String getDeviceAuthorizationEndpoint() {
        return deviceAuthorizationEndpoint;
    }

    public void setDeviceAuthorizationEndpoint(String deviceAuthorizationEndpoint) {
        this.deviceAuthorizationEndpoint = deviceAuthorizationEndpoint;
    }

    public String getDeviceVerificationEndpoint() {
        return deviceVerificationEndpoint;
    }

    public void setDeviceVerificationEndpoint(String deviceVerificationEndpoint) {
        this.deviceVerificationEndpoint = deviceVerificationEndpoint;
    }

    public String getOidcClientRegistrationEndpoint() {
        return oidcClientRegistrationEndpoint;
    }

    public void setOidcClientRegistrationEndpoint(String oidcClientRegistrationEndpoint) {
        this.oidcClientRegistrationEndpoint = oidcClientRegistrationEndpoint;
    }

    public String getOidcUserInfoEndpoint() {
        return oidcUserInfoEndpoint;
    }

    public void setOidcUserInfoEndpoint(String oidcUserInfoEndpoint) {
        this.oidcUserInfoEndpoint = oidcUserInfoEndpoint;
    }

    public String getOidcLogoutEndpoint() {
        return oidcLogoutEndpoint;
    }

    public void setOidcLogoutEndpoint(String oidcLogoutEndpoint) {
        this.oidcLogoutEndpoint = oidcLogoutEndpoint;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("uaaServiceName", uaaServiceName)
                .add("upmsServiceName", upmsServiceName)
                .add("gatewayServiceUri", gatewayServiceUri)
                .add("uaaServiceUri", uaaServiceUri)
                .add("upmsServiceUri", upmsServiceUri)
                .add("authorizationUri", authorizationUri)
                .add("authorizationEndpoint", authorizationEndpoint)
                .add("accessTokenUri", accessTokenUri)
                .add("accessTokenEndpoint", accessTokenEndpoint)
                .add("jwkSetUri", jwkSetUri)
                .add("jwkSetEndpoint", jwkSetEndpoint)
                .add("tokenRevocationUri", tokenRevocationUri)
                .add("tokenRevocationEndpoint", tokenRevocationEndpoint)
                .add("tokenIntrospectionUri", tokenIntrospectionUri)
                .add("tokenIntrospectionEndpoint", tokenIntrospectionEndpoint)
                .add("deviceAuthorizationUri", deviceAuthorizationUri)
                .add("deviceAuthorizationEndpoint", deviceAuthorizationEndpoint)
                .add("deviceVerificationUri", deviceVerificationUri)
                .add("deviceVerificationEndpoint", deviceVerificationEndpoint)
                .add("oidcClientRegistrationUri", oidcClientRegistrationUri)
                .add("oidcClientRegistrationEndpoint", oidcClientRegistrationEndpoint)
                .add("oidcLogoutUri", oidcLogoutUri)
                .add("oidcLogoutEndpoint", oidcLogoutEndpoint)
                .add("oidcUserInfoUri", oidcUserInfoUri)
                .add("oidcUserInfoEndpoint", oidcUserInfoEndpoint)
                .add("issuerUri", issuerUri)
                .toString();
    }
}
