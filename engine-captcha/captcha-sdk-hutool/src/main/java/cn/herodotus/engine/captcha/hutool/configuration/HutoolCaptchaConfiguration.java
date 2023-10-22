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

package cn.herodotus.engine.captcha.hutool.configuration;

import cn.herodotus.engine.captcha.core.definition.enums.CaptchaCategory;
import cn.herodotus.engine.captcha.core.provider.ResourceProvider;
import cn.herodotus.engine.captcha.hutool.renderer.CircleCaptchaRenderer;
import cn.herodotus.engine.captcha.hutool.renderer.GifCaptchaRenderer;
import cn.herodotus.engine.captcha.hutool.renderer.LineCaptchaRenderer;
import cn.herodotus.engine.captcha.hutool.renderer.ShearCaptchaRenderer;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

/**
 * <p>Description: Hutool 验证码配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/18 20:57
 */
@AutoConfiguration
public class HutoolCaptchaConfiguration {

    private static final Logger log = LoggerFactory.getLogger(HutoolCaptchaConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Captcha Hutool] Auto Configure.");
    }

    @Bean(CaptchaCategory.HUTOOL_LINE_CAPTCHA)
    @ConditionalOnBean(ResourceProvider.class)
    public LineCaptchaRenderer lineCaptchaRenderer(ResourceProvider resourceProvider) {
        LineCaptchaRenderer lineCaptchaRenderer = new LineCaptchaRenderer();
        lineCaptchaRenderer.setResourceProvider(resourceProvider);
        log.trace("[Herodotus] |- Bean [Hutool Line Captcha Renderer] Auto Configure.");
        return lineCaptchaRenderer;
    }

    @Bean(CaptchaCategory.HUTOOL_CIRCLE_CAPTCHA)
    @ConditionalOnBean(ResourceProvider.class)
    public CircleCaptchaRenderer circleCaptchaRenderer(ResourceProvider resourceProvider) {
        CircleCaptchaRenderer circleCaptchaRenderer = new CircleCaptchaRenderer();
        circleCaptchaRenderer.setResourceProvider(resourceProvider);
        log.trace("[Herodotus] |- Bean [Hutool Circle Captcha Renderer] Auto Configure.");
        return circleCaptchaRenderer;
    }

    @Bean(CaptchaCategory.HUTOOL_SHEAR_CAPTCHA)
    @ConditionalOnBean(ResourceProvider.class)
    public ShearCaptchaRenderer shearCaptchaRenderer(ResourceProvider resourceProvider) {
        ShearCaptchaRenderer shearCaptchaRenderer = new ShearCaptchaRenderer();
        shearCaptchaRenderer.setResourceProvider(resourceProvider);
        log.trace("[Herodotus] |- Bean [Hutool Shear Captcha Renderer] Auto Configure.");
        return shearCaptchaRenderer;
    }

    @Bean(CaptchaCategory.HUTOOL_GIF_CAPTCHA)
    @ConditionalOnBean(ResourceProvider.class)
    public GifCaptchaRenderer gifCaptchaRenderer(ResourceProvider resourceProvider) {
        GifCaptchaRenderer gifCaptchaRenderer = new GifCaptchaRenderer();
        gifCaptchaRenderer.setResourceProvider(resourceProvider);
        log.trace("[Herodotus] |- Bean [Hutool Gif Captcha Renderer] Auto Configure.");
        return gifCaptchaRenderer;
    }
}
