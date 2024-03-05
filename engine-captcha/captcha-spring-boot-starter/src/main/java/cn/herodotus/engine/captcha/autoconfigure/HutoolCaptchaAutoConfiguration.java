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

package cn.herodotus.engine.captcha.autoconfigure;

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
public class HutoolCaptchaAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(HutoolCaptchaAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Module [Captcha Hutool] Auto Configure.");
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
