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

package cn.herodotus.engine.assistant.autoconfigure.customizer;

import cn.herodotus.engine.assistant.definition.constants.ErrorCodeMapperBuilderOrdered;
import cn.herodotus.engine.assistant.definition.constants.ErrorCodes;
import cn.herodotus.engine.assistant.definition.function.ErrorCodeMapperBuilderCustomizer;
import cn.herodotus.engine.assistant.definition.support.ErrorCodeMapperBuilder;
import org.springframework.core.Ordered;

/**
 * <p>Description: 标准内置错误代码 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/9/25 0:00
 */
public class StandardErrorCodeMapperBuilderCustomizer implements ErrorCodeMapperBuilderCustomizer, Ordered {
    @Override
    public void customize(ErrorCodeMapperBuilder builder) {
        builder
                .unauthorized(ErrorCodes.ACCESS_DENIED,
                        ErrorCodes.ACCOUNT_DISABLED,
                        ErrorCodes.ACCOUNT_ENDPOINT_LIMITED,
                        ErrorCodes.ACCOUNT_EXPIRED,
                        ErrorCodes.ACCOUNT_LOCKED,
                        ErrorCodes.BAD_CREDENTIALS,
                        ErrorCodes.CREDENTIALS_EXPIRED,
                        ErrorCodes.INVALID_CLIENT,
                        ErrorCodes.INVALID_TOKEN,
                        ErrorCodes.INVALID_GRANT,
                        ErrorCodes.UNAUTHORIZED_CLIENT,
                        ErrorCodes.USERNAME_NOT_FOUND,
                        ErrorCodes.SESSION_EXPIRED)
                .forbidden(ErrorCodes.INSUFFICIENT_SCOPE, ErrorCodes.SQL_INJECTION_REQUEST)
                .methodNotAllowed(ErrorCodes.HTTP_REQUEST_METHOD_NOT_SUPPORTED)
                .notAcceptable(ErrorCodes.UNSUPPORTED_GRANT_TYPE, ErrorCodes.UNSUPPORTED_RESPONSE_TYPE, ErrorCodes.UNSUPPORTED_TOKEN_TYPE)
                .preconditionFailed(ErrorCodes.INVALID_REDIRECT_URI, ErrorCodes.INVALID_REQUEST, ErrorCodes.INVALID_SCOPE, ErrorCodes.METHOD_ARGUMENT_NOT_VALID)
                .unsupportedMediaType(ErrorCodes.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE)
                .internalServerError(ErrorCodes.SERVER_ERROR,
                        ErrorCodes.HTTP_MESSAGE_NOT_READABLE_EXCEPTION,
                        ErrorCodes.ILLEGAL_ARGUMENT_EXCEPTION,
                        ErrorCodes.IO_EXCEPTION,
                        ErrorCodes.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION,
                        ErrorCodes.NULL_POINTER_EXCEPTION,
                        ErrorCodes.TYPE_MISMATCH_EXCEPTION)
                .notImplemented(ErrorCodes.PROPERTY_VALUE_IS_NOT_SET_EXCEPTION, ErrorCodes.URL_FORMAT_INCORRECT_EXCEPTION, ErrorCodes.ILLEGAL_SYMMETRIC_KEY, ErrorCodes.DISCOVERED_UNRECORDED_ERROR_EXCEPTION)
                .serviceUnavailable(ErrorCodes.COOKIE_THEFT, ErrorCodes.INVALID_COOKIE, ErrorCodes.PROVIDER_NOT_FOUND, ErrorCodes.TEMPORARILY_UNAVAILABLE, ErrorCodes.SEARCH_IP_LOCATION)
                .customize(ErrorCodes.TRANSACTION_ROLLBACK,
                        ErrorCodes.BAD_SQL_GRAMMAR,
                        ErrorCodes.DATA_INTEGRITY_VIOLATION,
                        ErrorCodes.PIPELINE_INVALID_COMMANDS);
    }

    @Override
    public int getOrder() {
        return ErrorCodeMapperBuilderOrdered.STANDARD;
    }
}
