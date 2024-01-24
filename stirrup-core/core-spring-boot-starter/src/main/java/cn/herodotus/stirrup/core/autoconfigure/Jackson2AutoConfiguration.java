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

package cn.herodotus.stirrup.core.autoconfigure;

import cn.herodotus.stirrup.core.autoconfigure.customizer.Jackson2DefaultObjectMapperBuilderCustomizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * <p>Description: Jackson2 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/1 0:09
 */
@AutoConfiguration(after = JacksonAutoConfiguration.class)
public class Jackson2AutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(Jackson2AutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Jackson2] Auto Configure.");
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer defaultObjectMapperBuilderCustomizer() {
        Jackson2DefaultObjectMapperBuilderCustomizer customizer = new Jackson2DefaultObjectMapperBuilderCustomizer();
        log.debug("[Herodotus] |- Strategy [Jackson2 Default ObjectMapper Builder Customizer] Auto Configure.");
        return customizer;
    }

    /**
     * 转换器全局配置
     *
     * @return MappingJackson2HttpMessageConverter
     */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        log.trace("[Herodotus] |- Bean [Jackson2 Http Message Converter] Auto Configure.");
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }

    @Configuration(proxyBeanMethods = false)
    @ComponentScan({
            "cn.herodotus.stirrup.core.foundation.json.jackson2.utils"
    })
    static class JacksonUtilsConfiguration {

    }
}
