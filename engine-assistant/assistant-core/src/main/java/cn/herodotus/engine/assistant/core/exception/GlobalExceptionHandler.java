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

package cn.herodotus.engine.assistant.core.exception;

import cn.herodotus.engine.assistant.definition.constants.ErrorCodes;
import cn.herodotus.engine.assistant.definition.domain.Feedback;
import cn.herodotus.engine.assistant.definition.domain.Result;
import cn.herodotus.engine.assistant.definition.exception.HerodotusException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一异常处理器
 *
 * @author gengwei.zheng
 */
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final Map<String, Feedback> EXCEPTION_DICTIONARY = new HashMap<>();

    static {
        EXCEPTION_DICTIONARY.put("AccessDeniedException", ErrorCodes.ACCESS_DENIED);
        EXCEPTION_DICTIONARY.put("InsufficientAuthenticationException", ErrorCodes.ACCESS_DENIED);
        EXCEPTION_DICTIONARY.put("HttpRequestMethodNotSupportedException", ErrorCodes.HTTP_REQUEST_METHOD_NOT_SUPPORTED);
        EXCEPTION_DICTIONARY.put("HttpMediaTypeNotAcceptableException", ErrorCodes.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE);
        EXCEPTION_DICTIONARY.put("IllegalArgumentException", ErrorCodes.ILLEGAL_ARGUMENT_EXCEPTION);
        EXCEPTION_DICTIONARY.put("NullPointerException", ErrorCodes.NULL_POINTER_EXCEPTION);
        EXCEPTION_DICTIONARY.put("IOException", ErrorCodes.IO_EXCEPTION);
        EXCEPTION_DICTIONARY.put("HttpMessageNotReadableException", ErrorCodes.HTTP_MESSAGE_NOT_READABLE_EXCEPTION);
        EXCEPTION_DICTIONARY.put("TypeMismatchException", ErrorCodes.TYPE_MISMATCH_EXCEPTION);
        EXCEPTION_DICTIONARY.put("MissingServletRequestParameterException", ErrorCodes.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION);
        EXCEPTION_DICTIONARY.put("ProviderNotFoundException", ErrorCodes.PROVIDER_NOT_FOUND);
        EXCEPTION_DICTIONARY.put("CookieTheftException", ErrorCodes.COOKIE_THEFT);
        EXCEPTION_DICTIONARY.put("InvalidCookieException", ErrorCodes.INVALID_COOKIE);
        EXCEPTION_DICTIONARY.put("BadSqlGrammarException", ErrorCodes.BAD_SQL_GRAMMAR);
        EXCEPTION_DICTIONARY.put("DataIntegrityViolationException", ErrorCodes.DATA_INTEGRITY_VIOLATION);
        EXCEPTION_DICTIONARY.put("TransactionRollbackException", ErrorCodes.TRANSACTION_ROLLBACK);
        EXCEPTION_DICTIONARY.put("BindException", ErrorCodes.METHOD_ARGUMENT_NOT_VALID);
        EXCEPTION_DICTIONARY.put("MethodArgumentNotValidException", ErrorCodes.METHOD_ARGUMENT_NOT_VALID);
        EXCEPTION_DICTIONARY.put("RedisPipelineException", ErrorCodes.PIPELINE_INVALID_COMMANDS);
    }

    public static Result<String> resolveException(Exception ex, String path) {

        log.trace("[Herodotus] |- Global Exception Handler, Path : [{}], Exception：", path, ex);

        if (ex instanceof HerodotusException exception) {
            Result<String> result = exception.getResult();
            result.path(path);
            log.error("[Herodotus] |- Global Exception Handler, Error is : {}", result);
            return result;
        } else {
            Result<String> result = Result.failure();
            String exceptionName = ex.getClass().getSimpleName();
            if (StringUtils.isNotEmpty(exceptionName) && EXCEPTION_DICTIONARY.containsKey(exceptionName)) {
                Feedback feedback = EXCEPTION_DICTIONARY.get(exceptionName);
                result = Result.failure(feedback, exceptionName);
            } else {
                log.warn("[Herodotus] |- Global Exception Handler,  Can not find the exception name [{}] in dictionary, please do optimize ", exceptionName);
            }

            result.path(path);
            result.stackTrace(ex.getStackTrace());
            result.detail(ex.getMessage());

            log.error("[Herodotus] |- Global Exception Handler, Error is : {}", result);
            return result;
        }
    }
}
