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

package cn.herodotus.engine.assistant.core.exception.properties;

import cn.herodotus.stirrup.kernel.definition.constants.ErrorCodes;
import cn.herodotus.stirrup.kernel.definition.domain.Feedback;
import cn.herodotus.stirrup.kernel.definition.exception.PlatformRuntimeException;

/**
 * <p>Description: Url 格式错误 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/3/6 12:49
 */
public class UrlFormatIncorrectException extends PlatformRuntimeException {

    public UrlFormatIncorrectException() {
        super();
    }

    public UrlFormatIncorrectException(String message) {
        super(message);
    }

    public UrlFormatIncorrectException(String message, Throwable cause) {
        super(message, cause);
    }

    public UrlFormatIncorrectException(Throwable cause) {
        super(cause);
    }

    protected UrlFormatIncorrectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return ErrorCodes.URL_FORMAT_INCORRECT_EXCEPTION;
    }
}
