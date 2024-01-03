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

package cn.herodotus.engine.captcha.autoconfigure;

import cn.herodotus.engine.assistant.definition.function.ErrorCodeMapperBuilderCustomizer;
import cn.herodotus.engine.captcha.autoconfigure.customizer.CaptchaErrorCodeMapperBuilderCustomizer;
import cn.herodotus.engine.captcha.core.processor.CaptchaRendererFactory;
import cn.herodotus.engine.captcha.core.properties.CaptchaProperties;
import cn.herodotus.engine.captcha.core.provider.ResourceProvider;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * <p>Description: 验证码自动注入 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/18 21:12
 */
@AutoConfiguration
@EnableConfigurationProperties(CaptchaProperties.class)
public class CaptchaAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CaptchaAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Module [Captcha Starter] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public ResourceProvider resourceProvider(CaptchaProperties captchaProperties) {
        ResourceProvider resourceProvider = new ResourceProvider(captchaProperties);
        log.trace("[Herodotus] |- Bean [Resource Provider] Auto Configure.");
        return resourceProvider;
    }

    @Bean
    @ConditionalOnMissingBean
    public CaptchaRendererFactory captchaRendererFactory() {
        CaptchaRendererFactory captchaRendererFactory = new CaptchaRendererFactory();
        log.trace("[Herodotus] |- Bean [Captcha Renderer Factory] Auto Configure.");
        return captchaRendererFactory;
    }

    @Bean
    public ErrorCodeMapperBuilderCustomizer captchaErrorCodeMapperBuilderCustomizer() {
        CaptchaErrorCodeMapperBuilderCustomizer customizer = new CaptchaErrorCodeMapperBuilderCustomizer();
        log.debug("[Herodotus] |- Strategy [Captcha ErrorCodeMapper Builder Customizer] Auto Configure.");
        return customizer;
    }
}
