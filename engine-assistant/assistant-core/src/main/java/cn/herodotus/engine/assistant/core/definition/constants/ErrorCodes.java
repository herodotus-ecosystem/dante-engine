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

package cn.herodotus.engine.assistant.core.definition.constants;

import org.apache.hc.core5.http.HttpStatus;

/**
 * <p>Description: 错误代码计数器 </p>
 * <p>
 * 都需要手动控制错误代码，通过代码的继承关系，方便的查看以及编写。
 * <p>
 *
 * <p>
 *
 * @author : gengwei.zheng
 * @date : 2022/5/2 0:22
 */
public interface ErrorCodes {

    /* ---------- 200 ---------- */
    int OK = HttpStatus.SC_OK * 100;

    /* ---------- 204 ---------- */
    int NO_CONTENT = HttpStatus.SC_NO_CONTENT * 100;

    /* ---------- 401 ---------- */
    int UNAUTHORIZED = HttpStatus.SC_UNAUTHORIZED * 100;

    int ACCESS_DENIED = UNAUTHORIZED + 1;
    int ACCOUNT_DISABLED = ACCESS_DENIED + 1;
    int ACCOUNT_ENDPOINT_LIMITED = ACCOUNT_DISABLED + 1;
    int ACCOUNT_EXPIRED = ACCOUNT_ENDPOINT_LIMITED + 1;
    int ACCOUNT_LOCKED = ACCOUNT_EXPIRED + 1;
    int BAD_CREDENTIALS = ACCOUNT_LOCKED + 1;
    int CREDENTIALS_EXPIRED = BAD_CREDENTIALS + 1;
    int INVALID_CLIENT = CREDENTIALS_EXPIRED + 1;
    int INVALID_TOKEN = INVALID_CLIENT + 1;
    int INVALID_GRANT = INVALID_TOKEN + 1;
    int UNAUTHORIZED_CLIENT = INVALID_TOKEN + 1;
    int USERNAME_NOT_FOUND = UNAUTHORIZED_CLIENT + 1;
    int SESSION_EXPIRED = USERNAME_NOT_FOUND + 1;

    /* ---------- 403 ---------- */
    int FORBIDDEN = HttpStatus.SC_FORBIDDEN * 100;

    int INSUFFICIENT_SCOPE = FORBIDDEN + 1;
    int SQL_INJECTION_REQUEST = INSUFFICIENT_SCOPE + 1;

    /* ---------- 403 ---------- */
    int NOT_FOUND = HttpStatus.SC_NOT_FOUND * 100;

    int EMQX_MODULE_404_BEGIN = NOT_FOUND;
    int EMQX_MODULE_404_END = EMQX_MODULE_404_BEGIN + 1;

    /* ---------- 405 ---------- */
    int METHOD_NOT_ALLOWED = HttpStatus.SC_METHOD_NOT_ALLOWED * 100;

    int HTTP_REQUEST_METHOD_NOT_SUPPORTED = METHOD_NOT_ALLOWED + 1;

    /* ---------- 406 ---------- */
    int NOT_ACCEPTABLE = HttpStatus.SC_NOT_ACCEPTABLE * 100;

    int UNSUPPORTED_GRANT_TYPE = NOT_ACCEPTABLE + 1;
    int UNSUPPORTED_RESPONSE_TYPE = UNSUPPORTED_GRANT_TYPE + 1;
    int UNSUPPORTED_TOKEN_TYPE = UNSUPPORTED_RESPONSE_TYPE + 1;

    int CACHE_MODULE_406_BEGIN = UNSUPPORTED_TOKEN_TYPE;
    int CACHE_MODULE_406_END = CACHE_MODULE_406_BEGIN + 4;
    int CAPTCHA_MODULE_406_BEGIN = CACHE_MODULE_406_END;
    int CAPTCHA_MODULE_406_END = CAPTCHA_MODULE_406_BEGIN + 6;
    int OAUTH2_MODULE_406_BEGIN = CAPTCHA_MODULE_406_END;
    int OAUTH2_MODULE_406_END = OAUTH2_MODULE_406_BEGIN + 1;
    int REST_MODULE_406_BEGIN = OAUTH2_MODULE_406_END;
    int REST_MODULE_406_END = REST_MODULE_406_BEGIN + 3;
    int MESSAGE_MODULE_406_BEGIN = REST_MODULE_406_END;
    int MESSAGE_MODULE_406_END = MESSAGE_MODULE_406_BEGIN + 2;

    /* ---------- 412 ---------- */
    int PRECONDITION_FAILED = HttpStatus.SC_PRECONDITION_FAILED * 100;

    int INVALID_REDIRECT_URI = PRECONDITION_FAILED + 1;
    int INVALID_REQUEST = INVALID_REDIRECT_URI + 1;
    int INVALID_SCOPE = INVALID_REQUEST + 1;

    int ACCESS_MODULE_406_BEGIN = INVALID_SCOPE;

    /* ---------- 415 ---------- */
    int UNSUPPORTED_MEDIA_TYPE = HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE * 100;

    int HTTP_MEDIA_TYPE_NOT_ACCEPTABLE = UNSUPPORTED_MEDIA_TYPE + 1;

    /* ---------- 500 ---------- */
    int INTERNAL_SERVER_ERROR = HttpStatus.SC_INTERNAL_SERVER_ERROR * 100;

    int SERVER_ERROR = INTERNAL_SERVER_ERROR + 1;
    int HTTP_MESSAGE_NOT_READABLE_EXCEPTION = SERVER_ERROR + 1;
    int ILLEGAL_ARGUMENT_EXCEPTION = HTTP_MESSAGE_NOT_READABLE_EXCEPTION + 1;
    int IO_EXCEPTION = ILLEGAL_ARGUMENT_EXCEPTION + 1;
    int MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION = IO_EXCEPTION + 1;
    int NULL_POINTER_EXCEPTION = MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION + 1;
    int TYPE_MISMATCH_EXCEPTION = NULL_POINTER_EXCEPTION + 1;

    int OSS_MODULE_500_BEGIN = TYPE_MISMATCH_EXCEPTION;
    int OSS_MODULE_500_END = OSS_MODULE_500_BEGIN + 14;
    int SMS_MODULE_500_BEGIN = OSS_MODULE_500_END;
    int SMS_MODULE_500_END = OSS_MODULE_500_END + 2;

    /* ---------- 501 ---------- */
    int NOT_IMPLEMENTED = HttpStatus.SC_NOT_IMPLEMENTED * 100;

    int PROPERTY_VALUE_IS_NOT = NOT_IMPLEMENTED + 1;
    int URL_FORMAT_INCORRECT = NOT_IMPLEMENTED + 1;
    int ILLEGAL_SYMMETRIC_KEY = URL_FORMAT_INCORRECT + 1;

    /* ---------- 503 ---------- */
    int SERVICE_UNAVAILABLE = HttpStatus.SC_SERVICE_UNAVAILABLE * 100;

    int COOKIE_THEFT = SERVICE_UNAVAILABLE + 1;
    int INVALID_COOKIE = COOKIE_THEFT + 1;
    int PROVIDER_NOT_FOUND = INVALID_COOKIE + 1;
    int TEMPORARILY_UNAVAILABLE = PROVIDER_NOT_FOUND + 1;

    int REST_MODULE_503_BEGIN = TEMPORARILY_UNAVAILABLE;
    int REST_MODULE_503_END = REST_MODULE_503_BEGIN + 1;
    int OSS_MODULE_503_BEGIN = REST_MODULE_503_END;
    int OSS_MODULE_503_END = OSS_MODULE_503_BEGIN + 1;

    /* ---------- 600 ---------- */
    int DATABASE = 60000;

    int TRANSACTION_ROLLBACK = DATABASE + 1;

    /* ---------- 601 数据库操作运行前校验 ---------- */
    int DATABASE_VALIDATION = DATABASE + 100;
    int METHOD_ARGUMENT_NOT_VALID = DATABASE_VALIDATION + 1;

    /* ---------- 602 数据库操作执行 ---------- */
    int DATABASE_EXECUTION = DATABASE_VALIDATION + 100;
    int BAD_SQL_GRAMMAR = DATABASE_EXECUTION + 1;
    int DATA_INTEGRITY_VIOLATION = BAD_SQL_GRAMMAR + 1;


    /* ---------- 700 基础设施交互错误 ---------- */
    int BASIC_FACILITIES = DATABASE + 10000;

    /* ---------- 701 Redis 相关错误 ---------- */
    int REDIS = BASIC_FACILITIES + 100;
    int PIPELINE_INVALID_COMMANDS = REDIS + 1;
}
