/*
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君) (herodotus@aliyun.com & www.herodotus.cn)
 *
 * Dante Engine licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.gnu.org/licenses/lgpl.html>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.herodotus.engine.rest.autoconfigure;

import cn.herodotus.engine.rest.protect.configuration.SecureConfiguration;
import cn.herodotus.engine.rest.protect.configuration.TenantConfiguration;
import cn.herodotus.engine.rest.protect.secure.interceptor.AccessLimitedInterceptor;
import cn.herodotus.engine.rest.protect.secure.interceptor.IdempotentInterceptor;
import cn.herodotus.engine.rest.protect.tenant.MultiTenantInterceptor;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;

/**
 * <p>Description: WebMvcAutoConfiguration </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/4 11:00
 */
@AutoConfiguration
@Import({
        SecureConfiguration.class,
        TenantConfiguration.class
})
@EnableWebMvc
public class RestWebMvcAutoConfiguration implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(RestWebMvcAutoConfiguration.class);

    private final IdempotentInterceptor idempotentInterceptor;
    private final AccessLimitedInterceptor accessLimitedInterceptor;
    private final MultiTenantInterceptor multiTenantInterceptor;

    public RestWebMvcAutoConfiguration(IdempotentInterceptor idempotentInterceptor, AccessLimitedInterceptor accessLimitedInterceptor, MultiTenantInterceptor multiTenantInterceptor) {
        this.idempotentInterceptor = idempotentInterceptor;
        this.accessLimitedInterceptor = accessLimitedInterceptor;
        this.multiTenantInterceptor = multiTenantInterceptor;
    }

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Module [Rest WebMvc] Auto Configure.");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessLimitedInterceptor);
        registry.addInterceptor(idempotentInterceptor);
        registry.addInterceptor(multiTenantInterceptor);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                .resourceChain(false)
                .addResolver(new WebJarsResourceResolver());
    }

}
