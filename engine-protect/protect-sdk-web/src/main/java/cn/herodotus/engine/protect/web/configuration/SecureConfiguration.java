/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine Licensed under the Apache License, Version 2.0 (the "License");
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

package cn.herodotus.engine.protect.web.configuration;

import cn.herodotus.engine.protect.core.properties.SecureProperties;
import cn.herodotus.engine.protect.web.secure.interceptor.AccessLimitedInterceptor;
import cn.herodotus.engine.protect.web.secure.interceptor.IdempotentInterceptor;
import cn.herodotus.engine.protect.web.secure.interceptor.XssHttpServletFilter;
import cn.herodotus.engine.protect.web.secure.stamp.AccessLimitedStampManager;
import cn.herodotus.engine.protect.web.secure.stamp.IdempotentStampManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

/**
 * <p>Description: 接口安全配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/4 17:28
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({SecureProperties.class})
public class SecureConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SecureConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Protect Secure] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public IdempotentStampManager idempotentStampManager(SecureProperties secureProperties) {
        IdempotentStampManager idempotentStampManager = new IdempotentStampManager(secureProperties);
        log.trace("[Herodotus] |- Bean [Idempotent Stamp Manager] Auto Configure.");
        return idempotentStampManager;
    }

    @Bean
    @ConditionalOnMissingBean
    public AccessLimitedStampManager accessLimitedStampManager(SecureProperties secureProperties) {
        AccessLimitedStampManager accessLimitedStampManager = new AccessLimitedStampManager(secureProperties);
        log.trace("[Herodotus] |- Bean [Access Limited Stamp Manager] Auto Configure.");
        return accessLimitedStampManager;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(IdempotentStampManager.class)
    public IdempotentInterceptor idempotentInterceptor(IdempotentStampManager idempotentStampManager) {
        IdempotentInterceptor idempotentInterceptor = new IdempotentInterceptor();
        idempotentInterceptor.setIdempotentStampManager(idempotentStampManager);
        log.trace("[Herodotus] |- Bean [Idempotent Interceptor] Auto Configure.");
        return idempotentInterceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(AccessLimitedStampManager.class)
    public AccessLimitedInterceptor accessLimitedInterceptor(AccessLimitedStampManager accessLimitedStampManager) {
        AccessLimitedInterceptor accessLimitedInterceptor = new AccessLimitedInterceptor();
        accessLimitedInterceptor.setAccessLimitedStampManager(accessLimitedStampManager);
        log.trace("[Herodotus] |- Bean [Access Limited Interceptor] Auto Configure.");
        return accessLimitedInterceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    public XssHttpServletFilter xssHttpServletFilter() {
        XssHttpServletFilter xssHttpServletFilter = new XssHttpServletFilter();
        log.trace("[Herodotus] |- Bean [Xss Http Servlet Filter] Auto Configure.");
        return xssHttpServletFilter;
    }
}
