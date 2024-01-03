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

package cn.herodotus.engine.oauth2.core.exception;

import cn.herodotus.engine.assistant.definition.domain.Feedback;
import cn.herodotus.engine.captcha.core.constants.CaptchaErrorCodes;

/**
 * <p>Description: Oauth2 使用的验证码不匹配错误 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/24 12:08
 */
public class OAuth2CaptchaMismatchException extends OAuth2CaptchaException {

    public OAuth2CaptchaMismatchException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public OAuth2CaptchaMismatchException(String msg) {
        super(msg);
    }

    @Override
    public Feedback getFeedback() {
        return CaptchaErrorCodes.CAPTCHA_MISMATCH;
    }
}
