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

package cn.herodotus.engine.captcha.core.exception;

import cn.herodotus.stirrup.core.definition.domain.Feedback;
import cn.herodotus.stirrup.core.definition.exception.PlatformRuntimeException;
import cn.herodotus.engine.captcha.core.constants.CaptchaErrorCodes;

/**
 * <p>Description: 验证码已过期 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/15 18:06
 */
public class CaptchaHasExpiredException extends PlatformRuntimeException {

    public CaptchaHasExpiredException() {
        super();
    }

    public CaptchaHasExpiredException(String message) {
        super(message);
    }

    public CaptchaHasExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaHasExpiredException(Throwable cause) {
        super(cause);
    }

    protected CaptchaHasExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return CaptchaErrorCodes.CAPTCHA_HAS_EXPIRED;
    }
}
