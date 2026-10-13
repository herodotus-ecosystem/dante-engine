/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 是 Dante Cloud 系统核心组件库，采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dante.oauth2.authorization.autoconfigure;

import cn.herodotus.dante.messaging.strategy.AttributeCollectionEventManager;
import cn.herodotus.dante.messaging.strategy.EnumDictionaryCollectionEventManager;
import cn.herodotus.dante.messaging.strategy.MessageSendingEventManager;
import cn.herodotus.dante.messaging.strategy.OAuth2SupportedScopeDistributionEventManager;
import cn.herodotus.dante.oauth2.authorization.autoconfigure.listener.RemoteAttributeDistributionListener;
import cn.herodotus.dante.oauth2.authorization.autoconfigure.strategy.DefaultAttributeCollectionEventManager;
import cn.herodotus.dante.oauth2.authorization.autoconfigure.strategy.DefaultEnumDictionaryCollectionEventManager;
import cn.herodotus.dante.oauth2.authorization.autoconfigure.strategy.DefaultMessageSendingEventManager;
import cn.herodotus.dante.oauth2.authorization.autoconfigure.strategy.DefaultOAuth2SupportedScopeDistributionEventManager;
import cn.herodotus.dante.oauth2.commons.definition.ResourceServiceSecurityAttributeManager;
import cn.herodotus.dante.spring.condition.ConditionalOnArchitecture;
import cn.herodotus.dante.spring.enums.Architecture;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.cloud.bus.StreamBusBridge;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * <p>Description: 资源型服务消息配置  </p>
 * <p>
 * 本配置类配置所有服务均需要用到的消息相关内容配置。
 *
 * @author : gengwei.zheng
 * @date : 2024/8/21 17:54
 */
@AutoConfiguration
public class ResourceServiceMessageAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ResourceServiceMessageAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Auto [Resource Service Message] Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public AttributeCollectionEventManager servletAttributeCollectionEventManager(ResourceServiceSecurityAttributeManager resourceServiceSecurityAttributeManager) {
        DefaultAttributeCollectionEventManager manager = new DefaultAttributeCollectionEventManager(resourceServiceSecurityAttributeManager, EnableWebSecurity.class);
        log.trace("[Herodotus] |- Bean [Servlet Attribute Collection Manager] Configure.");
        return manager;
    }

    @Bean
    public EnumDictionaryCollectionEventManager enumDictionaryCollectionEventManager() {
        DefaultEnumDictionaryCollectionEventManager manager = new DefaultEnumDictionaryCollectionEventManager();
        log.trace("[Herodotus] |- Bean [Enum Dictionary Collection Manager] Configure.");
        return manager;
    }

    @Bean
    public MessageSendingEventManager messageSendingEventManager() {
        DefaultMessageSendingEventManager manager = new DefaultMessageSendingEventManager();
        log.trace("[Herodotus] |- Bean [Unified Message Sending Event Manager] Configure.");
        return manager;
    }

    @Bean
    public OAuth2SupportedScopeDistributionEventManager oauth2SupportedScopeDistributionEventManager() {
        DefaultOAuth2SupportedScopeDistributionEventManager manager = new DefaultOAuth2SupportedScopeDistributionEventManager();
        log.trace("[Herodotus] |- Bean [OAuth2 Supported Scope Distribution Event Manager] Configure.");
        return manager;
    }


    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(StreamBusBridge.class)
    @ConditionalOnArchitecture(Architecture.DISTRIBUTED)
    @RemoteApplicationEventScan({
            "cn.herodotus.dante.oauth2.authorization.autoconfigure.bus"
    })
    static class BusMessageConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public RemoteAttributeDistributionListener remoteAttributeDistributionListener(ResourceServiceSecurityAttributeManager resourceServiceSecurityAttributeManager, ServiceMatcher serviceMatcher) {
            RemoteAttributeDistributionListener listener = new RemoteAttributeDistributionListener(resourceServiceSecurityAttributeManager, serviceMatcher);
            log.trace("[Herodotus] |- Bean [Remote Attribute Distribution Listener] Configure.");
            return listener;
        }
    }
}
