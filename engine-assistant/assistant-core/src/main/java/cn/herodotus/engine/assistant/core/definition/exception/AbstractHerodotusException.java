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

package cn.herodotus.engine.assistant.core.definition.exception;

import cn.herodotus.engine.assistant.core.domain.Result;

/**
 * <p>Description: 自定义错误基础类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/3/4 18:31
 */
public abstract class AbstractHerodotusException extends RuntimeException implements HerodotusException {

    public AbstractHerodotusException() {
        super();
    }

    public AbstractHerodotusException(String message) {
        super(message);
    }

    public AbstractHerodotusException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbstractHerodotusException(Throwable cause) {
        super(cause);
    }

    protected AbstractHerodotusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Result<String> getResult() {
        Result<String> result = Result.failure(getFeedback());
        result.stackTrace(super.getStackTrace());
        result.detail(super.getMessage());
        return result;
    }
}
