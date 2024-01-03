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

package cn.herodotus.engine.rest.service.feign;

import cn.herodotus.engine.assistant.definition.domain.Result;
import cn.herodotus.engine.assistant.definition.exception.PlatformRuntimeException;

/**
 * <p>Description: Feign Fallback 错误统一封装器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/5/30 11:31
 */
public class FeignRemoteCallExceptionWrapper extends PlatformRuntimeException {

    private Result<String> result;

    public FeignRemoteCallExceptionWrapper(Result<String> result) {
        this.result = result;
    }

    public FeignRemoteCallExceptionWrapper(String message, Result<String> result) {
        super(message);
        this.result = result;
    }

    public FeignRemoteCallExceptionWrapper(String message, Throwable cause, Result<String> result) {
        super(message, cause);
        this.result = result;
    }

    public FeignRemoteCallExceptionWrapper(Throwable cause, Result<String> result) {
        super(cause);
        this.result = result;
    }

    public FeignRemoteCallExceptionWrapper(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Result<String> result) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.result = result;
    }

    @Override
    public Result<String> getResult() {
        return result;
    }
}
