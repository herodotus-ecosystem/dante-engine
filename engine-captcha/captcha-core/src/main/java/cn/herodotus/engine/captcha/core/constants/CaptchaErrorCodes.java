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

package cn.herodotus.engine.captcha.core.constants;

import cn.herodotus.engine.assistant.definition.feedback.NotAcceptableFeedback;

/**
 * <p>Description: Captcha 错误代码 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/1 11:32
 */
public interface CaptchaErrorCodes {

    NotAcceptableFeedback CAPTCHA_CATEGORY_IS_INCORRECT = new NotAcceptableFeedback("验证码分类错误");
    NotAcceptableFeedback CAPTCHA_HANDLER_NOT_EXIST = new NotAcceptableFeedback("验证码处理器不存在");
    NotAcceptableFeedback CAPTCHA_HAS_EXPIRED = new NotAcceptableFeedback("验证码已过期");
    NotAcceptableFeedback CAPTCHA_IS_EMPTY = new NotAcceptableFeedback("验证码不能为空");
    NotAcceptableFeedback CAPTCHA_MISMATCH = new NotAcceptableFeedback("验证码不匹配");
    NotAcceptableFeedback CAPTCHA_PARAMETER_ILLEGAL = new NotAcceptableFeedback("验证码参数格式错误");

}
