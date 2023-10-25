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

import cn.herodotus.engine.captcha.behavior.renderer.JigsawCaptchaRenderer;
import cn.herodotus.engine.captcha.behavior.renderer.WordClickCaptchaRenderer;
import cn.herodotus.engine.captcha.core.definition.enums.CaptchaCategory;
import cn.herodotus.engine.captcha.core.provider.ResourceProvider;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

/**
 * <p>Description: 行为验证码配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/18 20:57
 */
@AutoConfiguration
public class BehaviorCaptchaAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(BehaviorCaptchaAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Module [Captcha Behavior] Auto Configure.");
    }

    @Bean(CaptchaCategory.JIGSAW_CAPTCHA)
    @ConditionalOnBean(ResourceProvider.class)
    public JigsawCaptchaRenderer jigsawCaptchaRenderer(ResourceProvider resourceProvider) {
        JigsawCaptchaRenderer jigsawCaptchaRenderer = new JigsawCaptchaRenderer();
        jigsawCaptchaRenderer.setResourceProvider(resourceProvider);
        log.trace("[Herodotus] |- Bean [Jigsaw Captcha Renderer] Auto Configure.");
        return jigsawCaptchaRenderer;
    }

    @Bean(CaptchaCategory.WORD_CLICK_CAPTCHA)
    @ConditionalOnBean(ResourceProvider.class)
    public WordClickCaptchaRenderer wordClickCaptchaRenderer(ResourceProvider resourceProvider) {
        WordClickCaptchaRenderer wordClickCaptchaRenderer = new WordClickCaptchaRenderer();
        wordClickCaptchaRenderer.setResourceProvider(resourceProvider);
        log.trace("[Herodotus] |- Bean [Word Click Captcha Renderer] Auto Configure.");
        return wordClickCaptchaRenderer;
    }
}
