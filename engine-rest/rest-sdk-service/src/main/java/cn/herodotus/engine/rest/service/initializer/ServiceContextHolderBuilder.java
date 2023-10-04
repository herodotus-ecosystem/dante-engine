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

package cn.herodotus.engine.rest.service.initializer;

import cn.herodotus.engine.assistant.core.context.ServiceContextHolder;
import cn.herodotus.engine.assistant.core.utils.WellFormedUtils;
import cn.herodotus.engine.rest.service.properties.EndpointProperties;
import cn.herodotus.engine.rest.service.properties.PlatformProperties;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.ServerProperties;

/**
 * <p>Description: ServiceContextHolder 构建器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/10/2 18:41
 */
public class ServiceContextHolderBuilder{

    private PlatformProperties platformProperties;
    private EndpointProperties endpointProperties;
    private ServerProperties serverProperties;

    private ServiceContextHolderBuilder() {

    }

    public static ServiceContextHolderBuilder builder() {
        return new ServiceContextHolderBuilder();
    }

    public ServiceContextHolderBuilder platformProperties(PlatformProperties platformProperties) {
        this.platformProperties = platformProperties;
        return this;
    }

    public ServiceContextHolderBuilder endpointProperties(EndpointProperties endpointProperties) {
        this.endpointProperties = endpointProperties;
        return this;
    }

    public ServiceContextHolderBuilder serverProperties(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
        return this;
    }

    public ServiceContextHolder build() {
        ServiceContextHolder holder = ServiceContextHolder.getInstance();
        toServiceContextHolder(platformProperties, holder);
        toServiceContextHolder(endpointProperties, holder, holder.isDistributedArchitecture());
        holder.setPort(String.valueOf(this.getPort()));
        holder.setIp(getHostAddress());
        return holder;
    }

    private String getHostAddress() {
        String address = WellFormedUtils.getHostAddress();
        if (ObjectUtils.isNotEmpty(serverProperties.getAddress())) {
            address = serverProperties.getAddress().getHostAddress();
        }

        if (StringUtils.isNotBlank(address)) {
            return address;
        } else {
            return "localhost";
        }
    }

    private Integer getPort() {
        Integer port = serverProperties.getPort();
        if (ObjectUtils.isNotEmpty(port)) {
            return port;
        } else {
            return 8080;
        }
    }

    private void toServiceContextHolder(PlatformProperties platformProperties, ServiceContextHolder serviceContextHolder) {
        serviceContextHolder.setArchitecture(platformProperties.getArchitecture());
        serviceContextHolder.setDataAccessStrategy(platformProperties.getDataAccessStrategy());
        serviceContextHolder.setProtocol(platformProperties.getProtocol());
    }

    private void toServiceContextHolder(EndpointProperties endpointProperties, ServiceContextHolder serviceContextHolder, boolean isDistributedArchitecture) {
        if (!isDistributedArchitecture) {
            String issuerUri = endpointProperties.getIssuerUri();
            serviceContextHolder.setGatewayServiceUri(issuerUri);
            serviceContextHolder.setUaaServiceUri(issuerUri);
            serviceContextHolder.setUpmsServiceUri(issuerUri);
            serviceContextHolder.setMessageServiceUri(issuerUri);
            serviceContextHolder.setOssServiceUri(issuerUri);
        } else {
            serviceContextHolder.setUaaServiceName(endpointProperties.getUaaServiceName());
            serviceContextHolder.setUpmsServiceName(endpointProperties.getUpmsServiceName());
            serviceContextHolder.setMessageServiceName(endpointProperties.getMessageServiceName());
            serviceContextHolder.setOssServiceName(endpointProperties.getOssServiceName());
            serviceContextHolder.setGatewayServiceUri(endpointProperties.getGatewayServiceUri());
            serviceContextHolder.setUaaServiceUri(endpointProperties.getUaaServiceUri());
            serviceContextHolder.setUpmsServiceUri(endpointProperties.getUpmsServiceUri());
            serviceContextHolder.setMessageServiceUri(endpointProperties.getMessageServiceUri());
            serviceContextHolder.setOssServiceUri(endpointProperties.getOssServiceUri());
        }

        serviceContextHolder.setAuthorizationUri(endpointProperties.getAuthorizationUri());
        serviceContextHolder.setAuthorizationEndpoint(endpointProperties.getAuthorizationEndpoint());
        serviceContextHolder.setAccessTokenUri(endpointProperties.getAccessTokenUri());
        serviceContextHolder.setAccessTokenEndpoint(endpointProperties.getAccessTokenEndpoint());
        serviceContextHolder.setJwkSetUri(endpointProperties.getJwkSetUri());
        serviceContextHolder.setJwkSetEndpoint(endpointProperties.getJwkSetEndpoint());
        serviceContextHolder.setTokenRevocationUri(endpointProperties.getTokenRevocationUri());
        serviceContextHolder.setTokenRevocationEndpoint(endpointProperties.getTokenRevocationEndpoint());
        serviceContextHolder.setTokenIntrospectionUri(endpointProperties.getTokenIntrospectionUri());
        serviceContextHolder.setTokenIntrospectionEndpoint(endpointProperties.getTokenIntrospectionEndpoint());
        serviceContextHolder.setDeviceAuthorizationUri(endpointProperties.getDeviceAuthorizationUri());
        serviceContextHolder.setDeviceAuthorizationEndpoint(endpointProperties.getDeviceAuthorizationEndpoint());
        serviceContextHolder.setDeviceVerificationUri(endpointProperties.getDeviceVerificationUri());
        serviceContextHolder.setDeviceVerificationEndpoint(endpointProperties.getDeviceVerificationEndpoint());
        serviceContextHolder.setOidcClientRegistrationUri(endpointProperties.getOidcClientRegistrationUri());
        serviceContextHolder.setOidcClientRegistrationEndpoint(endpointProperties.getOidcClientRegistrationEndpoint());
        serviceContextHolder.setOidcLogoutUri(endpointProperties.getOidcLogoutUri());
        serviceContextHolder.setOidcLogoutEndpoint(endpointProperties.getOidcLogoutEndpoint());
        serviceContextHolder.setOidcUserInfoUri(endpointProperties.getOidcUserInfoUri());
        serviceContextHolder.setOidcUserInfoEndpoint(endpointProperties.getOidcUserInfoEndpoint());
        serviceContextHolder.setIssuerUri(endpointProperties.getIssuerUri());
    }
}
