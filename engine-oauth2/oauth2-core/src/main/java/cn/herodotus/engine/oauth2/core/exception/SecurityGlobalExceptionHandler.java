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

import cn.herodotus.engine.assistant.definition.constants.ErrorCodes;
import cn.herodotus.engine.assistant.definition.domain.Feedback;
import cn.herodotus.engine.assistant.definition.domain.Result;
import cn.herodotus.engine.assistant.core.exception.GlobalExceptionHandler;
import cn.herodotus.engine.assistant.definition.exception.PlatformRuntimeException;
import cn.herodotus.engine.oauth2.core.constants.OAuth2ErrorKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: 统一异常处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/18 8:12
 */
@RestControllerAdvice
public class SecurityGlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(SecurityGlobalExceptionHandler.class);

    private static final Map<String, Feedback> EXCEPTION_DICTIONARY = new HashMap<>();

    static {
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.ACCESS_DENIED, ErrorCodes.ACCESS_DENIED);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.INSUFFICIENT_SCOPE, ErrorCodes.INSUFFICIENT_SCOPE);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.INVALID_CLIENT, ErrorCodes.INVALID_CLIENT);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.INVALID_GRANT, ErrorCodes.INVALID_GRANT);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.INVALID_REDIRECT_URI, ErrorCodes.INVALID_REDIRECT_URI);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.INVALID_REQUEST, ErrorCodes.INVALID_REQUEST);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.INVALID_SCOPE, ErrorCodes.INVALID_SCOPE);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.INVALID_TOKEN, ErrorCodes.INVALID_TOKEN);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.SERVER_ERROR, ErrorCodes.SERVER_ERROR);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.TEMPORARILY_UNAVAILABLE, ErrorCodes.TEMPORARILY_UNAVAILABLE);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.UNAUTHORIZED_CLIENT, ErrorCodes.UNAUTHORIZED_CLIENT);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.UNSUPPORTED_GRANT_TYPE, ErrorCodes.UNSUPPORTED_GRANT_TYPE);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.UNSUPPORTED_RESPONSE_TYPE, ErrorCodes.UNSUPPORTED_RESPONSE_TYPE);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.UNSUPPORTED_TOKEN_TYPE, ErrorCodes.UNSUPPORTED_TOKEN_TYPE);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.ACCOUNT_EXPIRED, ErrorCodes.ACCOUNT_EXPIRED);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.BAD_CREDENTIALS, ErrorCodes.BAD_CREDENTIALS);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.CREDENTIALS_EXPIRED, ErrorCodes.CREDENTIALS_EXPIRED);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.ACCOUNT_DISABLED, ErrorCodes.ACCOUNT_DISABLED);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.ACCOUNT_LOCKED, ErrorCodes.ACCOUNT_LOCKED);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.ACCOUNT_ENDPOINT_LIMITED, ErrorCodes.ACCOUNT_ENDPOINT_LIMITED);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.USERNAME_NOT_FOUND, ErrorCodes.USERNAME_NOT_FOUND);
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.SESSION_EXPIRED, ErrorCodes.SESSION_EXPIRED);
    }

    /**
     * Rest Template 错误处理
     *
     * @param ex       错误
     * @param request  请求
     * @param response 响应
     * @return Result 对象
     * @see <a href="https://www.baeldung.com/spring-rest-template-error-handling">baeldung</a>
     */
    @ExceptionHandler({HttpClientErrorException.class, HttpServerErrorException.class})
    public static Result<String> restTemplateException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        Result<String> result = resolveException(ex, request.getRequestURI());
        response.setStatus(result.getStatus());
        return result;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public static Result<String> validationMethodArgumentException(MethodArgumentNotValidException ex, HttpServletRequest request, HttpServletResponse response) {
        return validationBindException(ex, request, response);
    }

    @ExceptionHandler({BindException.class})
    public static Result<String> validationBindException(BindException ex, HttpServletRequest request, HttpServletResponse response) {
        Result<String> result = resolveException(ex, request.getRequestURI());

        BindingResult bindingResult = ex.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        //返回第一个错误的信息
        if (ObjectUtils.isNotEmpty(fieldError)) {
            result.validation(fieldError.getDefaultMessage(), fieldError.getCode(), fieldError.getField());
        }

        response.setStatus(result.getStatus());
        return result;
    }

    /**
     * 统一异常处理
     * AuthenticationException
     *
     * @param ex       错误
     * @param request  请求
     * @param response 响应
     * @return Result 对象
     */
    @ExceptionHandler({AuthenticationException.class, PlatformAuthenticationException.class})
    public static Result<String> authenticationException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        Result<String> result = resolveSecurityException(ex, request.getRequestURI());
        response.setStatus(result.getStatus());
        return result;
    }

    @ExceptionHandler({OAuth2AuthenticationException.class})
    public static Result<String> oAuth2AuthenticationException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        Result<String> result = resolveSecurityException(ex, request.getRequestURI());
        response.setStatus(result.getStatus());
        return result;
    }

    @ExceptionHandler({Exception.class, PlatformRuntimeException.class})
    public static Result<String> exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        Result<String> result = resolveException(ex, request.getRequestURI());
        response.setStatus(result.getStatus());
        return result;
    }


    public static Result<String> resolveException(Exception ex, String path) {
        return GlobalExceptionHandler.resolveException(ex, path);
    }

    /**
     * 静态解析认证异常
     *
     * @param exception 错误信息
     * @return Result 对象
     */
    public static Result<String> resolveSecurityException(Exception exception, String path) {

        Exception reason = new Exception();
        if (exception instanceof OAuth2AuthenticationException oAuth2AuthenticationException) {
            OAuth2Error oAuth2Error = oAuth2AuthenticationException.getError();
            if (EXCEPTION_DICTIONARY.containsKey(oAuth2Error.getErrorCode())) {
                Feedback feedback = EXCEPTION_DICTIONARY.get(oAuth2Error.getErrorCode());
                Result<String> result = Result.failure(feedback, oAuth2Error.getErrorCode());
                result.path(oAuth2Error.getUri());
                result.stackTrace(exception.getStackTrace());
                result.detail(exception.getMessage());
                return result;
            }
        } else if (exception instanceof InsufficientAuthenticationException) {
            Throwable throwable = exception.getCause();
            if (ObjectUtils.isNotEmpty(throwable)) {
                reason = new Exception(throwable);
            } else {
                reason = exception;
            }
            log.debug("[Herodotus] |- InsufficientAuthenticationException cause content is [{}]", reason.getClass().getSimpleName());
        } else {
            String exceptionName = exception.getClass().getSimpleName();
            if (StringUtils.isNotEmpty(exceptionName) && EXCEPTION_DICTIONARY.containsKey(exceptionName)) {
                Feedback feedback = EXCEPTION_DICTIONARY.get(exceptionName);
                return Result.failure(feedback);
            } else {
                reason = exception;
            }
        }

        return resolveException(reason, path);
    }
}
