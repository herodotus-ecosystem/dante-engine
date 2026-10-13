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

import cn.herodotus.dante.logic.upms.annotation.EnableHerodotusLogicUpms;
import cn.herodotus.dante.messaging.processor.AccountStatusTransferProcessor;
import cn.herodotus.dante.messaging.processor.AttributeTransferProcessor;
import cn.herodotus.dante.messaging.processor.EnumDictionaryTransferProcessor;
import cn.herodotus.dante.messaging.strategy.AttributeDistributionEventManager;
import cn.herodotus.dante.oauth2.authorization.autoconfigure.condition.ConditionalOnUpmsService;
import cn.herodotus.dante.oauth2.authorization.autoconfigure.listener.*;
import cn.herodotus.dante.oauth2.authorization.autoconfigure.strategy.DefaultAttributeDistributionEventManager;
import cn.herodotus.dante.oauth2.commons.definition.AuthorizationSecurityAttributeManager;
import cn.herodotus.dante.security.definition.OAuth2ProtectedResourceMetadataStorage;
import cn.herodotus.dante.spring.condition.ConditionalOnArchitecture;
import cn.herodotus.dante.spring.enums.Architecture;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: Upms 服务消息配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/10/27 23:46
 */
@AutoConfiguration
@ConditionalOnUpmsService
@EnableHerodotusLogicUpms
public class UpmsServiceMessageAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(UpmsServiceMessageAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Auto [OAuth2 Upms Service] Configure.");
    }

    @Bean
    public AttributeDistributionEventManager attributeDistributionEventManager(AuthorizationSecurityAttributeManager authorizationSecurityAttributeManager) {
        DefaultAttributeDistributionEventManager manager = new DefaultAttributeDistributionEventManager(authorizationSecurityAttributeManager);
        log.trace("[Herodotus] |- Bean [Attribute Distribution Event Manager] Configure.");
        return manager;
    }

    @Configuration(proxyBeanMethods = false)
    public static class UpmsLocalListenerConfiguration {

        @Bean
        public LocalAccountStatusChangeListener localAccountStatusChangeListener(AccountStatusTransferProcessor accountStatusTransferProcessor) {
            LocalAccountStatusChangeListener listener = new LocalAccountStatusChangeListener(accountStatusTransferProcessor);
            log.trace("[Herodotus] |- Bean [Local Account Status Change Listener] Configure.");
            return listener;
        }

        @Bean
        public LocalEnumDictionaryCollectionListener localEnumDictionaryCollectionListener(EnumDictionaryTransferProcessor enumDictionaryTransferProcessor) {
            LocalEnumDictionaryCollectionListener listener = new LocalEnumDictionaryCollectionListener(enumDictionaryTransferProcessor);
            log.trace("[Herodotus] |- Bean [Local Enum Dictionary Collection Listener] Configure.");
            return listener;
        }

        @Bean
        public LocalAttributeCollectionListener localAttributeCollectionListener(AttributeTransferProcessor attributeTransferProcessor, AttributeDistributionEventManager attributeDistributionEventManager) {
            LocalAttributeCollectionListener listener = new LocalAttributeCollectionListener(attributeTransferProcessor, attributeDistributionEventManager);
            log.trace("[Herodotus] |- Bean [Local Mapping Attribute Collection Listener] Configure.");
            return listener;
        }

        @Bean
        public LocalOAuth2SupportedScopeDistributionListener localOAuth2SupportedScopeDistributionListener(OAuth2ProtectedResourceMetadataStorage oauth2ProtectedResourceMetadataStorage) {
            LocalOAuth2SupportedScopeDistributionListener listener = new LocalOAuth2SupportedScopeDistributionListener(oauth2ProtectedResourceMetadataStorage);
            log.trace("[Herodotus] |- Bean [Local OAuth2 Supported Scope Distribution Listener] Configure.");
            return listener;
        }

        @Bean
        public SysAttributeChangeListener sysAttributeChangeListener(AttributeTransferProcessor attributeTransferProcessor, AttributeDistributionEventManager attributeDistributionEventManager) {
            SysAttributeChangeListener listener = new SysAttributeChangeListener(attributeTransferProcessor, attributeDistributionEventManager);
            log.trace("[Herodotus] |- Bean [SysAttribute Change Listener] Configure.");
            return listener;
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnArchitecture(Architecture.DISTRIBUTED)
    public static class UpmsRemoteListenerConfiguration {

        @Bean
        public RemoteAccountStatusChangeListener remoteAccountStatusChangeListener(AccountStatusTransferProcessor accountStatusTransferProcessor) {
            RemoteAccountStatusChangeListener listener = new RemoteAccountStatusChangeListener(accountStatusTransferProcessor);
            log.trace("[Herodotus] |- Bean [Remote Account Status Change Listener] Configure.");
            return listener;
        }

        @Bean
        public RemoteEnumDictionaryCollectionListener remoteEnumDictionaryCollectionListener(EnumDictionaryTransferProcessor enumDictionaryTransferProcessor) {
            RemoteEnumDictionaryCollectionListener listener = new RemoteEnumDictionaryCollectionListener(enumDictionaryTransferProcessor);
            log.trace("[Herodotus] |- Bean [Remote Enum Dictionary Collection Listener] Configure.");
            return listener;
        }

        @Bean
        public RemoteAttributeCollectionListener remoteAttributeCollectionListener(AttributeTransferProcessor attributeTransferProcessor, AttributeDistributionEventManager attributeDistributionEventManager) {
            RemoteAttributeCollectionListener listener = new RemoteAttributeCollectionListener(attributeTransferProcessor, attributeDistributionEventManager);
            log.trace("[Herodotus] |- Bean [Remote Mapping Attribute Collection Listener] Configure.");
            return listener;
        }

        @Bean
        public RemoteOAuth2SupportedScopeDistributionListener remoteOAuth2SupportedScopeDistributionListener(OAuth2ProtectedResourceMetadataStorage oauth2ProtectedResourceMetadataStorage) {
            RemoteOAuth2SupportedScopeDistributionListener listener = new RemoteOAuth2SupportedScopeDistributionListener(oauth2ProtectedResourceMetadataStorage);
            log.trace("[Herodotus] |- Bean [Remote OAuth2 Supported Scope Distribution Listener] Configure.");
            return listener;
        }
    }
}
