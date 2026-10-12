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

package cn.herodotus.dante.logic.upms.config;

import cn.herodotus.dante.assistant.access.config.AssistantAccessConfiguration;
import cn.herodotus.dante.assistant.access.factory.AccessHandlerStrategyFactory;
import cn.herodotus.dante.core.function.EnumDictionaryBuilderCustomizer;
import cn.herodotus.dante.logic.upms.customizer.UpmsEnumDictionaryBuilderCustomizer;
import cn.herodotus.dante.logic.upms.definition.SocialAuthenticationHandler;
import cn.herodotus.dante.logic.upms.handler.DefaultSocialAuthenticationHandler;
import cn.herodotus.dante.logic.upms.handler.OAuth2SupportedScopeDistributionHandler;
import cn.herodotus.dante.logic.upms.manager.security.SysAttributeManager;
import cn.herodotus.dante.logic.upms.manager.security.SysDictionaryManager;
import cn.herodotus.dante.logic.upms.manager.security.SysUserManager;
import cn.herodotus.dante.logic.upms.service.oauth2.OAuth2SupportedScopeService;
import cn.herodotus.dante.logic.upms.service.security.*;
import cn.herodotus.dante.messaging.processor.AccountStatusTransferProcessor;
import cn.herodotus.dante.messaging.processor.AttributeTransferProcessor;
import cn.herodotus.dante.messaging.processor.EnumDictionaryTransferProcessor;
import cn.herodotus.dante.messaging.strategy.OAuth2SupportedScopeDistributionEventManager;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * <p>Description: UPMS SDK 模块配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/16 19:00
 */
@Configuration(proxyBeanMethods = false)
@EntityScan(basePackages = {
        "cn.herodotus.dante.logic.upms.entity.security",
        "cn.herodotus.dante.logic.upms.entity.hr",
        "cn.herodotus.dante.logic.upms.entity.oauth2",
})
@EnableJpaRepositories(basePackages = {
        "cn.herodotus.dante.logic.upms.repository.security",
        "cn.herodotus.dante.logic.upms.repository.hr",
        "cn.herodotus.dante.logic.upms.repository.oauth2",
})
@ComponentScan(basePackages = {
        "cn.herodotus.dante.logic.upms.service.security",
        "cn.herodotus.dante.logic.upms.service.hr",
        "cn.herodotus.dante.logic.upms.service.oauth2",
})
@Import({AssistantAccessConfiguration.class})
public class LogicUpmsConfiguration {

    private static final Logger log = LoggerFactory.getLogger(LogicUpmsConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Logic Upms] Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public SocialAuthenticationHandler socialAuthenticationHandler(SysUserService sysUserService, SysSocialUserService sysSocialUserService, AccessHandlerStrategyFactory accessHandlerStrategyFactory) {
        DefaultSocialAuthenticationHandler defaultSocialAuthenticationHandler = new DefaultSocialAuthenticationHandler(sysUserService, sysSocialUserService, accessHandlerStrategyFactory);
        log.trace("[Herodotus] |- Bean [Default Social Authentication Handler] Configure.");
        return defaultSocialAuthenticationHandler;
    }

    @Bean
    public EnumDictionaryBuilderCustomizer upmsEnumDictionaryBuilderCustomizer() {
        UpmsEnumDictionaryBuilderCustomizer customizer = new UpmsEnumDictionaryBuilderCustomizer();
        log.debug("[Herodotus] |- Strategy [Upms EnumDictionary Builder Customizer] Configure.");
        return customizer;
    }

    @Bean
    public OAuth2SupportedScopeDistributionHandler oauth2SupportedScopeDistributionHandler(OAuth2SupportedScopeService oauth2SupportedScopeService, OAuth2SupportedScopeDistributionEventManager oauth2SupportedScopeDistributionEventManager) {
        OAuth2SupportedScopeDistributionHandler handler = new OAuth2SupportedScopeDistributionHandler(oauth2SupportedScopeService, oauth2SupportedScopeDistributionEventManager);
        log.trace("[Herodotus] |- Bean [OAuth2 Supported Scope Distribution Handler] Configure.");
        return handler;
    }

    @Bean
    public AttributeTransferProcessor attributeTransferProcessor(SysAttributeService sysAttributeService, SysInterfaceService sysInterfaceService) {
        SysAttributeManager manager = new SysAttributeManager(sysAttributeService, sysInterfaceService);
        log.trace("[Herodotus] |- Bean [Attribute Transfer Processor] Configure.");
        return manager;
    }

    @Bean
    public EnumDictionaryTransferProcessor enumDictionaryTransferProcessor(SysEnumService sysEnumService, SysDictionaryService sysDictionaryService) {
        SysDictionaryManager manager = new SysDictionaryManager(sysEnumService, sysDictionaryService);
        log.trace("[Herodotus] |- Bean [Enum Dictionary Transfer Processor] Configure.");
        return manager;
    }

    @Bean
    public AccountStatusTransferProcessor accountStatusTransferProcessor(SysUserService sysUserService) {
        SysUserManager manager = new SysUserManager(sysUserService);
        log.trace("[Herodotus] |- Bean [SysUser Manager] Configure.");
        return manager;
    }
}
