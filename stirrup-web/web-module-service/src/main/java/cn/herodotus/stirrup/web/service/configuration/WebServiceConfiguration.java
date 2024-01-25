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
import cn.herodotus.stirrup.core.foundation.context.ServiceContextHolder;
import cn.herodotus.stirrup.web.core.support.WebPropertyFinder;
import cn.herodotus.stirrup.web.service.customizer.Jackson2XssObjectMapperBuilderCustomizer;
import cn.herodotus.stirrup.web.service.customizer.WebErrorCodeMapperBuilderCustomizer;
import cn.herodotus.stirrup.web.service.initializer.ServiceContextHolderBuilder;
import cn.herodotus.stirrup.web.service.properties.EndpointProperties;
import cn.herodotus.stirrup.web.service.properties.PlatformProperties;
import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
public class WebServiceConfiguration implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(WebServiceConfiguration.class);

    private final ServiceContextHolder serviceContextHolder;

    /**
     * 尝试过几种 ServiceContextHolder 的初始化的方式，但都是会出现“时机”的不正确，导致 ServiceContextHolder 没有正常初始化，而导致抛错
     * 1. 利用 @AutoConfiguration 控制 Configuration 的前后顺序。这种方式始终会出现 WebSocket 先一步ServiceContextHolder初始化导致抛错。
     * 2. 利用 @AutoConfigureOrder 指定 Configuration 顺序，也是同样问题。
     * 3. 使用 Bean 的方式来构建 ServiceContextHolderBuilder，并且完成 ServiceContextHolder 的初始化的方式，始终不成功会出现时机不对的问题，导致抛错。跟踪过代码，发现使用 Bean 的方式，构建 ServiceContextHolderBuilder 根本就不执行。
     * <p>
     * 最终使用构造函数的方式，可以确保时机正确，几个参数对象设置正确，最终保证 ServiceContextHolder 的初始化时机合理
     *
     * @param platformProperties {@link PlatformProperties}
     * @param endpointProperties {@link EndpointProperties}
     * @param serverProperties   {@link ServerProperties}
     */
    public WebServiceConfiguration(PlatformProperties platformProperties, EndpointProperties endpointProperties, ServerProperties serverProperties) {
        this.serviceContextHolder = ServiceContextHolderBuilder.builder()
                .endpointProperties(endpointProperties)
                .platformProperties(platformProperties)
                .serverProperties(serverProperties)
                .build();
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Web Service] Auto Configure.");
    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        this.serviceContextHolder.setApplicationContext(applicationContext);
        this.serviceContextHolder.setApplicationName(WebPropertyFinder.getApplicationName(applicationContext));
        log.debug("[Herodotus] |- HERODOTUS ApplicationContext initialization completed.");
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer xssObjectMapperBuilderCustomizer() {
        Jackson2XssObjectMapperBuilderCustomizer customizer = new Jackson2XssObjectMapperBuilderCustomizer();
        log.debug("[Herodotus] |- Strategy [Jackson2 Xss ObjectMapper Builder Customizer] Auto Configure.");
        return customizer;
    }

    @Bean
    public ErrorCodeMapperBuilderCustomizer webErrorCodeMapperBuilderCustomizer() {
        WebErrorCodeMapperBuilderCustomizer customizer = new WebErrorCodeMapperBuilderCustomizer();
        log.debug("[Herodotus] |- Strategy [Web ErrorCodeMapper Builder Customizer] Auto Configure.");
        return customizer;
    }
}
