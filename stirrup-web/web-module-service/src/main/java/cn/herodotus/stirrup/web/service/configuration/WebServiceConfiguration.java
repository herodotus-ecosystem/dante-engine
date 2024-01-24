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

package cn.herodotus.stirrup.web.service.configuration;

import cn.herodotus.stirrup.core.definition.function.ErrorCodeMapperBuilderCustomizer;
import cn.herodotus.stirrup.web.service.customizer.Jackson2XssObjectMapperBuilderCustomizer;
import cn.herodotus.stirrup.web.service.customizer.WebErrorCodeMapperBuilderCustomizer;
import cn.herodotus.stirrup.web.service.initializer.ServiceContextHolderBuilder;
import cn.herodotus.stirrup.web.service.properties.EndpointProperties;
import cn.herodotus.stirrup.web.service.properties.PlatformProperties;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: Web 服务通用配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/1/24 16:34
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({EndpointProperties.class, PlatformProperties.class})
public class WebServiceConfiguration {

    private static final Logger log = LoggerFactory.getLogger(WebServiceConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Web Service] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public ServiceContextHolderBuilder serviceContextHolderBuilder(PlatformProperties platformProperties, EndpointProperties endpointProperties, ServerProperties serverProperties) {
        ServiceContextHolderBuilder builder = new ServiceContextHolderBuilder(platformProperties, endpointProperties, serverProperties);
        log.debug("[Herodotus] |- Bean [Service Context Holder Builder] Auto Configure.");
        return builder;
    }

    @Bean
    @ConditionalOnMissingBean
    public Jackson2ObjectMapperBuilderCustomizer xssObjectMapperBuilderCustomizer() {
        Jackson2XssObjectMapperBuilderCustomizer customizer = new Jackson2XssObjectMapperBuilderCustomizer();
        log.debug("[Herodotus] |- Strategy [Jackson2 Xss ObjectMapper Builder Customizer] Auto Configure.");
        return customizer;
    }

    @Bean
    @ConditionalOnMissingBean
    public ErrorCodeMapperBuilderCustomizer webErrorCodeMapperBuilderCustomizer() {
        WebErrorCodeMapperBuilderCustomizer customizer = new WebErrorCodeMapperBuilderCustomizer();
        log.debug("[Herodotus] |- Strategy [Web ErrorCodeMapper Builder Customizer] Auto Configure.");
        return customizer;
    }
}
