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

package cn.herodotus.engine.captcha.core.processor;

import cn.herodotus.engine.captcha.core.definition.Renderer;
import cn.herodotus.engine.captcha.core.definition.enums.CaptchaCategory;
import cn.herodotus.engine.captcha.core.dto.Captcha;
import cn.herodotus.engine.captcha.core.dto.Verification;
import cn.herodotus.engine.captcha.core.exception.CaptchaCategoryIsIncorrectException;
import cn.herodotus.engine.captcha.core.exception.CaptchaHandlerNotExistException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Description: Captcha 工厂 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/14 14:38
 */
@Component
public class CaptchaRendererFactory {

    @Autowired
    private final Map<String, Renderer> handlers = new ConcurrentHashMap<>(8);

    public Renderer getRenderer(String category) {
        CaptchaCategory captchaCategory = CaptchaCategory.getCaptchaCategory(category);

        if (ObjectUtils.isEmpty(captchaCategory)) {
            throw new CaptchaCategoryIsIncorrectException("Captcha category is incorrect.");
        }

        Renderer renderer = handlers.get(captchaCategory.getConstant());
        if (ObjectUtils.isEmpty(renderer)) {
            throw new CaptchaHandlerNotExistException();
        }

        return renderer;
    }

    public Captcha getCaptcha(String identity, String category) {
        Renderer renderer = getRenderer(category);
        return renderer.getCapcha(identity);
    }

    public boolean verify(Verification verification) {
        Renderer renderer = getRenderer(verification.getCategory());
        return renderer.verify(verification);
    }
}
