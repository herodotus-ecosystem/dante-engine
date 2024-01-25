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

package cn.herodotus.stirrup.oauth2.authorization.autoconfigure;

import cn.herodotus.engine.message.core.logic.strategy.RequestMappingScanEventManager;
import cn.herodotus.engine.oauth2.authorization.configuration.OAuth2AuthorizationConfiguration;
import cn.herodotus.engine.oauth2.authorization.processor.SecurityMetadataSourceAnalyzer;
import cn.herodotus.engine.oauth2.core.exception.SecurityGlobalExceptionHandler;
import cn.herodotus.stirrup.oauth2.authorization.autoconfigure.metadata.RemoteSecurityMetadataSyncListener;
import cn.herodotus.stirrup.oauth2.authorization.autoconfigure.scan.DefaultRequestMappingScanEventManager;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * <p>Description: 资源服务器必要组件自动配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/1/25 13:17
 */
@AutoConfiguration
@EnableAsync
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@Import({
        OAuth2AuthorizationConfiguration.class
})
@ComponentScan(basePackageClasses = SecurityGlobalExceptionHandler.class)
@RemoteApplicationEventScan({
        "cn.herodotus.engine.oauth2.resource.autoconfigure.bus"
})
public class OAuth2AuthorizationAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OAuth2AuthorizationAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [OAuth2 Authorization] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public RemoteSecurityMetadataSyncListener remoteSecurityMetadataSyncListener(SecurityMetadataSourceAnalyzer securityMetadataSourceAnalyzer, ServiceMatcher serviceMatcher) {
        RemoteSecurityMetadataSyncListener listener = new RemoteSecurityMetadataSyncListener(securityMetadataSourceAnalyzer, serviceMatcher);
        log.trace("[Herodotus] |- Bean [Security Metadata Refresh Listener] Auto Configure.");
        return listener;
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestMappingScanEventManager requestMappingScanEventManager(SecurityMetadataSourceAnalyzer securityMetadataSourceAnalyzer) {
        DefaultRequestMappingScanEventManager manager = new DefaultRequestMappingScanEventManager(securityMetadataSourceAnalyzer);
        log.trace("[Herodotus] |- Bean [Request Mapping Scan Manager] Auto Configure.");
        return manager;
    }
}
