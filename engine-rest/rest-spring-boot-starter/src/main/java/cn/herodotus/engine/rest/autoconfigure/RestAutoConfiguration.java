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

package cn.herodotus.engine.rest.autoconfigure;

import cn.herodotus.engine.assistant.core.context.ServiceContextHolder;
import cn.herodotus.engine.assistant.core.definition.exception.ErrorCodeMapperBuilderCustomizer;
import cn.herodotus.engine.rest.autoconfigure.customizer.Jackson2XssObjectMapperBuilderCustomizer;
import cn.herodotus.engine.rest.autoconfigure.customizer.RestErrorCodeMapperBuilderCustomizer;
import cn.herodotus.engine.rest.autoconfigure.initializer.ServiceContextHolderBuilder;
import cn.herodotus.engine.rest.autoconfigure.properties.EndpointProperties;
import cn.herodotus.engine.rest.autoconfigure.properties.PlatformProperties;
import cn.herodotus.engine.rest.core.constants.RestPropertyFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;

/**
 * <p>Description: Rest 自动注入 </p>
 *
 * {@code RestAutoConfiguration} 注入的时机比较早，所以在此进行 {@link ServiceContextHolder} 的初始化。
 * 如果在其它地方注入 {@link ServiceContextHolder} 因为时机过完，会导致未完成初始化即被使用，出现抛错的问题。
 *
 * @author : gengwei.zheng
 * @date : 2022/1/19 23:16
 */
@AutoConfiguration
@EnableConfigurationProperties({EndpointProperties.class, PlatformProperties.class})
public class RestAutoConfiguration implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(RestAutoConfiguration.class);

    private final ServiceContextHolder serviceContextHolder;

    public RestAutoConfiguration(EndpointProperties endpointProperties, PlatformProperties platformProperties, ServerProperties serverProperties) {
        this.serviceContextHolder = ServiceContextHolderBuilder.builder()
                .endpointProperties(endpointProperties)
                .platformProperties(platformProperties)
                .serverProperties(serverProperties)
                .build();
        log.info("[Herodotus] |- Module [Rest Starter] Auto Configure.");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.serviceContextHolder.setApplicationContext(applicationContext);
        this.serviceContextHolder.setApplicationName(RestPropertyFinder.getApplicationName(applicationContext.getEnvironment()));
        log.debug("[Herodotus] |- HERODOTUS ApplicationContext initialization completed.");
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer xssObjectMapperBuilderCustomizer() {
        Jackson2XssObjectMapperBuilderCustomizer customizer = new Jackson2XssObjectMapperBuilderCustomizer();
        log.debug("[Herodotus] |- Strategy [Jackson2 Xss ObjectMapper Builder Customizer] Auto Configure.");
        return customizer;
    }

    @Bean
    public ErrorCodeMapperBuilderCustomizer restErrorCodeMapperBuilderCustomizer() {
        RestErrorCodeMapperBuilderCustomizer customizer = new RestErrorCodeMapperBuilderCustomizer();
        log.debug("[Herodotus] |- Strategy [Rest ErrorCodeMapper Builder Customizer] Auto Configure.");
        return customizer;
    }
}
