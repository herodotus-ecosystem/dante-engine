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

package cn.herodotus.engine.captcha.autoconfigure.customizer;

import cn.herodotus.engine.assistant.definition.constants.ErrorCodeMapperBuilderOrdered;
import cn.herodotus.engine.assistant.definition.function.ErrorCodeMapperBuilderCustomizer;
import cn.herodotus.engine.assistant.definition.support.ErrorCodeMapperBuilder;
import cn.herodotus.engine.captcha.core.constants.CaptchaErrorCodes;
import org.springframework.core.Ordered;

/**
 * <p>Description: Captcha 错误代码映射定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/9/25 15:31
 */
public class CaptchaErrorCodeMapperBuilderCustomizer implements ErrorCodeMapperBuilderCustomizer, Ordered {
    @Override
    public void customize(ErrorCodeMapperBuilder builder) {
        builder.notAcceptable(
                CaptchaErrorCodes.CAPTCHA_CATEGORY_IS_INCORRECT,
                CaptchaErrorCodes.CAPTCHA_HANDLER_NOT_EXIST,
                CaptchaErrorCodes.CAPTCHA_HAS_EXPIRED,
                CaptchaErrorCodes.CAPTCHA_IS_EMPTY,
                CaptchaErrorCodes.CAPTCHA_MISMATCH,
                CaptchaErrorCodes.CAPTCHA_PARAMETER_ILLEGAL
        );
    }

    @Override
    public int getOrder() {
        return ErrorCodeMapperBuilderOrdered.CAPTCHA;
    }
}
