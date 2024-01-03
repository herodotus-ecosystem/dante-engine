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

package cn.herodotus.engine.assistant.definition.exception;

import cn.herodotus.engine.assistant.definition.constants.ErrorCodes;
import cn.herodotus.engine.assistant.definition.domain.Feedback;

/**
 * <p>Description: 平台基础Exception </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/12/18 15:31
 */
public class PlatformRuntimeException extends AbstractRuntimeException {

    public PlatformRuntimeException() {
        super();
    }

    public PlatformRuntimeException(String message) {
        super(message);
    }

    public PlatformRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlatformRuntimeException(Throwable cause) {
        super(cause);
    }

    protected PlatformRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return ErrorCodes.INTERNAL_SERVER_ERROR;
    }
}
