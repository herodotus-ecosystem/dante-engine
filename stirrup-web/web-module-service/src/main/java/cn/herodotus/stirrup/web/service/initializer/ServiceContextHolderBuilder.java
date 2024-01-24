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

package cn.herodotus.stirrup.web.service.initializer;

import cn.herodotus.stirrup.core.foundation.context.ServiceContextHolder;
import cn.herodotus.stirrup.core.foundation.utils.WellFormedUtils;
import cn.herodotus.stirrup.web.core.support.WebPropertyFinder;
import cn.herodotus.stirrup.web.service.properties.EndpointProperties;
import cn.herodotus.stirrup.web.service.properties.PlatformProperties;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <p>Description: ServiceContextHolder 构建器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/1/24 17:24
 */
public class ServiceContextHolderBuilder implements ApplicationContextAware, InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(ServiceContextHolderBuilder.class);

    private final PlatformProperties platformProperties;
    private final EndpointProperties endpointProperties;
    private final ServerProperties serverProperties;

    public ServiceContextHolderBuilder(PlatformProperties platformProperties, EndpointProperties endpointProperties, ServerProperties serverProperties) {
        this.platformProperties = platformProperties;
        this.endpointProperties = endpointProperties;
        this.serverProperties = serverProperties;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        ServiceContextHolder serviceContextHolder = ServiceContextHolder.getInstance();
        toServiceContextHolder(platformProperties, serviceContextHolder);
        toServiceContextHolder(endpointProperties, serviceContextHolder, serviceContextHolder.isDistributedArchitecture());
        serviceContextHolder.setPort(String.valueOf(this.getPort()));
        serviceContextHolder.setIp(getHostAddress());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ServiceContextHolder serviceContextHolder = ServiceContextHolder.getInstance();
        serviceContextHolder.setApplicationContext(applicationContext);
        serviceContextHolder.setApplicationName(WebPropertyFinder.getApplicationName(applicationContext));
        log.debug("[Herodotus] |- HERODOTUS ApplicationContext initialization completed.");
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
