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

package cn.herodotus.engine.captcha.autoconfigure;

import cn.herodotus.engine.captcha.core.definition.enums.CaptchaCategory;
import cn.herodotus.engine.captcha.core.provider.ResourceProvider;
import cn.herodotus.engine.captcha.graphic.renderer.*;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

/**
 * <p>Description: 图形验证码配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/18 20:56
 */
@AutoConfiguration
public class GraphicCaptchaAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(GraphicCaptchaAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Module [Captcha Graphic] Auto Configure.");
    }

    @Bean(CaptchaCategory.ARITHMETIC_CAPTCHA)
    @ConditionalOnBean(ResourceProvider.class)
    public ArithmeticCaptchaRenderer arithmeticCaptchaRenderer(ResourceProvider resourceProvider) {
        ArithmeticCaptchaRenderer arithmeticCaptchaRenderer = new ArithmeticCaptchaRenderer();
        arithmeticCaptchaRenderer.setResourceProvider(resourceProvider);
        log.trace("[Herodotus] |- Bean [Arithmetic Captcha Renderer] Auto Configure.");
        return arithmeticCaptchaRenderer;
    }

    @Bean(CaptchaCategory.CHINESE_CAPTCHA)
    @ConditionalOnBean(ResourceProvider.class)
    public ChineseCaptchaRenderer chineseCaptchaRenderer(ResourceProvider resourceProvider) {
        ChineseCaptchaRenderer chineseCaptchaRenderer = new ChineseCaptchaRenderer();
        chineseCaptchaRenderer.setResourceProvider(resourceProvider);
        log.trace("[Herodotus] |- Bean [Chinese Captcha Renderer] Auto Configure.");
        return chineseCaptchaRenderer;
    }

    @Bean(CaptchaCategory.CHINESE_GIF_CAPTCHA)
    @ConditionalOnBean(ResourceProvider.class)
    public ChineseGifCaptchaRenderer chineseGifCaptchaRenderer(ResourceProvider resourceProvider) {
        ChineseGifCaptchaRenderer chineseGifCaptchaRenderer = new ChineseGifCaptchaRenderer();
        chineseGifCaptchaRenderer.setResourceProvider(resourceProvider);
        log.trace("[Herodotus] |- Bean [Chinese Gif Captcha Renderer] Auto Configure.");
        return chineseGifCaptchaRenderer;
    }

    @Bean(CaptchaCategory.SPEC_GIF_CAPTCHA)
    @ConditionalOnBean(ResourceProvider.class)
    public SpecGifCaptchaRenderer specGifCaptchaRenderer(ResourceProvider resourceProvider) {
        SpecGifCaptchaRenderer specGifCaptchaRenderer = new SpecGifCaptchaRenderer();
        specGifCaptchaRenderer.setResourceProvider(resourceProvider);
        log.trace("[Herodotus] |- Bean [Spec Gif Captcha Renderer] Auto Configure.");
        return specGifCaptchaRenderer;
    }

    @Bean(CaptchaCategory.SPEC_CAPTCHA)
    @ConditionalOnBean(ResourceProvider.class)
    public SpecCaptchaRenderer specCaptchaRenderer(ResourceProvider resourceProvider) {
        SpecCaptchaRenderer specCaptchaRenderer = new SpecCaptchaRenderer();
        specCaptchaRenderer.setResourceProvider(resourceProvider);
        log.trace("[Herodotus] |- Bean [Spec Captcha Renderer] Auto Configure.");
        return specCaptchaRenderer;
    }
}
