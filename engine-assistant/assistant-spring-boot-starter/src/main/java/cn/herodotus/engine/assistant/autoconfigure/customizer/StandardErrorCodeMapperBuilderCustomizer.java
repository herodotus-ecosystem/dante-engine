/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.assistant.autoconfigure.customizer;

import cn.herodotus.engine.assistant.core.definition.exception.ErrorCodeMapperBuilderCustomizer;
import cn.herodotus.engine.assistant.core.definition.exception.ErrorCodes;
import cn.herodotus.engine.assistant.core.exception.ErrorCodeMapperBuilder;

/**
 * <p>Description: 标准内置错误代码 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/9/25 0:00
 */
public class StandardErrorCodeMapperBuilderCustomizer implements ErrorCodeMapperBuilderCustomizer {
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
                .preconditionFailed(ErrorCodes.INVALID_REDIRECT_URI, ErrorCodes.INVALID_REQUEST, ErrorCodes.INVALID_SCOPE)
                .unsupportedMediaType(ErrorCodes.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE)
                .internalServerError(ErrorCodes.SERVER_ERROR,
                        ErrorCodes.HTTP_MESSAGE_NOT_READABLE_EXCEPTION,
                        ErrorCodes.ILLEGAL_ARGUMENT_EXCEPTION,
                        ErrorCodes.IO_EXCEPTION,
                        ErrorCodes.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION,
                        ErrorCodes.NULL_POINTER_EXCEPTION,
                        ErrorCodes.TYPE_MISMATCH_EXCEPTION)
                .notImplemented(ErrorCodes.PROPERTY_VALUE_IS_NOT_SET, ErrorCodes.URL_FORMAT_INCORRECT, ErrorCodes.ILLEGAL_SYMMETRIC_KEY)
                .serviceUnavailable(ErrorCodes.COOKIE_THEFT, ErrorCodes.INVALID_COOKIE, ErrorCodes.PROVIDER_NOT_FOUND, ErrorCodes.TEMPORARILY_UNAVAILABLE);
    }
}
