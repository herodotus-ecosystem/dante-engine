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

package cn.herodotus.engine.sms.core.exception;

import cn.herodotus.engine.assistant.definition.domain.Feedback;
import cn.herodotus.engine.assistant.definition.exception.PlatformRuntimeException;
import cn.herodotus.engine.sms.core.constants.SmsErrorCodes;

/**
 * <p>Description: Recluse SMS 执行错误 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/25 20:34
 */
public class SmsRecluseExecuteException extends PlatformRuntimeException {

    public SmsRecluseExecuteException() {
        super();
    }

    public SmsRecluseExecuteException(String message) {
        super(message);
    }

    public SmsRecluseExecuteException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmsRecluseExecuteException(Throwable cause) {
        super(cause);
    }

    protected SmsRecluseExecuteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return SmsErrorCodes.SMS_RECLUSE_EXECUTE_ERROR;
    }
}
