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

package cn.herodotus.engine.oauth2.metadata.configuration;

import cn.herodotus.engine.cache.jetcache.enhance.JetCacheCreateCacheFactory;
import cn.herodotus.engine.oauth2.metadata.listener.RemoteSecurityMetadataSyncListener;
import cn.herodotus.engine.oauth2.metadata.processor.ExpressionSecurityMetadataParser;
import cn.herodotus.engine.oauth2.metadata.processor.SecurityMetadataAnalysisProcessor;
import cn.herodotus.engine.oauth2.metadata.storage.SecurityMetadataLocalStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import jakarta.annotation.PostConstruct;

/**
 * <p>Description: SecurityMetadata 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/23 15:42
 */
@Configuration(proxyBeanMethods = false)
@EnableMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SecurityMetadataConfiguration extends GlobalMethodSecurityConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SecurityMetadataConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- SDK [Rocket Security Metadata] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityMetadataLocalStorage securityMetadataLocalStorage() {
        SecurityMetadataLocalStorage securityMetadataLocalStorage = new SecurityMetadataLocalStorage();
        log.trace("[Herodotus] |- Bean [Security Metadata Local Storage] Auto Configure.");
        return securityMetadataLocalStorage;
    }

    @Bean
    @ConditionalOnMissingBean
    public ExpressionSecurityMetadataParser securityMetadataExpressionParser() {
        ExpressionSecurityMetadataParser expressionSecurityMetadataParser = new ExpressionSecurityMetadataParser(this.getExpressionHandler().getExpressionParser());
        log.trace("[Herodotus] |- Bean [Security Metadata Expression Parser] Auto Configure.");
        return expressionSecurityMetadataParser;
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityMetadataAnalysisProcessor securityMetadataProcessService(SecurityMetadataLocalStorage securityMetadataLocalStorage, ExpressionSecurityMetadataParser expressionSecurityMetadataParser) {
        SecurityMetadataAnalysisProcessor securityMetadataProcessService = new SecurityMetadataAnalysisProcessor(securityMetadataLocalStorage, expressionSecurityMetadataParser);
        log.trace("[Herodotus] |- Bean [Security Metadata Process Service] Auto Configure.");
        return securityMetadataProcessService;
    }

    @Bean
    @ConditionalOnMissingBean
    public RemoteSecurityMetadataSyncListener remoteSecurityMetadataSyncListener(SecurityMetadataAnalysisProcessor securityMetadataProcessService, ServiceMatcher serviceMatcher) {
        RemoteSecurityMetadataSyncListener remoteSecurityMetadataSyncListener = new RemoteSecurityMetadataSyncListener(securityMetadataProcessService, serviceMatcher);
        log.trace("[Herodotus] |- Bean [Security Metadata Refresh Listener] Auto Configure.");
        return remoteSecurityMetadataSyncListener;
    }
}
