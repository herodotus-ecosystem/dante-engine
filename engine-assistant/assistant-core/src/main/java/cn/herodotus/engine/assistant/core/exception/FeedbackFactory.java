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

package cn.herodotus.engine.assistant.core.exception;

import cn.herodotus.engine.assistant.core.domain.Feedback;
import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.assistant.core.enums.ResultErrorCodes;
import org.apache.http.HttpStatus;

/**
 * <p>Description: FeedbackFactory </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/1 10:37
 */
public class FeedbackFactory {

    /**
     * 200	OK	请求成功。一般用于GET与POST请求
     *
     * @param code    自定义错误代码
     * @param message 错误描述信息
     * @return {@link Feedback}
     */
    public static Feedback ok(int code, String message) {
        return new Feedback(code, message, HttpStatus.SC_OK);
    }

    /**
     * 204	No Content	无内容。服务器成功处理，但未返回内容。在未更新网页的情况下，可确保浏览器继续显示当前文档
     *
     * @param code    自定义错误代码
     * @param message 错误描述信息
     * @return {@link Feedback}
     */
    public static Feedback noContent(int code, String message) {
        return new Feedback(code, message, HttpStatus.SC_NO_CONTENT);
    }

    /**
     * 401 Unauthorized 请求要求用户的身份认证
     *
     * @param code    自定义错误代码
     * @param message 错误描述信息
     * @return {@link Feedback}
     */
    public static Feedback unauthorized(int code, String message) {
        return new Feedback(code, message, HttpStatus.SC_UNAUTHORIZED);
    }

    /**
     * 403	Forbidden	服务器理解请求客户端的请求，但是拒绝执行此请求
     *
     * @param code    自定义错误代码
     * @param message 错误描述信息
     * @return {@link Feedback}
     */
    public static Feedback forbidden(int code, String message) {
        return new Feedback(code, message, HttpStatus.SC_FORBIDDEN);
    }

    /**
     * 405	Method Not Allowed	客户端请求中的方法被禁止
     *
     * @param code    自定义错误代码
     * @param message 错误描述信息
     * @return {@link Feedback}
     */
    public static Feedback methodNotAllowed(int code, String message) {
        return new Feedback(code, message, HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    /**
     * 406	Not Acceptable	服务器无法根据客户端请求的内容特性完成请求
     *
     * @param code    自定义错误代码
     * @param message 错误描述信息
     * @return {@link Feedback}
     */
    public static Feedback notAcceptable(int code, String message) {
        return new Feedback(code, message, HttpStatus.SC_NOT_ACCEPTABLE);
    }

    /**
     * 412	Precondition Failed	客户端请求信息的先决条件错误
     *
     * @param code    自定义错误代码
     * @param message 错误描述信息
     * @return {@link Feedback}
     */
    public static Feedback preconditionFailed(int code, String message) {
        return new Feedback(code, message, HttpStatus.SC_PRECONDITION_FAILED);
    }

    /**
     * 415	Unsupported Media Type	服务器无法处理请求附带的媒体格式
     *
     * @param code    自定义错误代码
     * @param message 错误描述信息
     * @return {@link Feedback}
     */
    public static Feedback unsupportedMediaType(int code, String message) {
        return new Feedback(code, message, HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * 500	Internal Server Error	服务器内部错误，无法完成请求
     *
     * @param code    自定义错误代码
     * @param message 错误描述信息
     * @return {@link Feedback}
     */
    public static Feedback internalServerError(int code, String message) {
        return new Feedback(code, message, HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    /**
     * 501	Not Implemented	服务器不支持请求的功能，无法完成请求
     *
     * @param code    自定义错误代码
     * @param message 错误描述信息
     * @return {@link Feedback}
     */
    public static Feedback notImplemented(int code, String message) {
        return new Feedback(code, message, HttpStatus.SC_NOT_IMPLEMENTED);
    }

    /**
     * 503	Service Unavailable	由于超载或系统维护，服务器暂时的无法处理客户端的请求。延时的长度可包含在服务器的Retry-After头信息中
     *
     * @param code    自定义错误代码
     * @param message 错误描述信息
     * @return {@link Feedback}
     */
    public static Feedback serviceUnavailable(int code, String message) {
        return new Feedback(code, message, HttpStatus.SC_SERVICE_UNAVAILABLE);
    }


    private static Result<String> getResult(ResultErrorCodes resultErrorCodes, int httpStatus) {
        return Result.failure(resultErrorCodes.getMessage(), resultErrorCodes.getCode(), httpStatus, null);
    }

    /**
     * 401	Unauthorized	请求要求用户的身份认证
     *
     * @param resultCode 401
     * @return {@link Result}
     */
    public static Result<String> getUnauthorizedResult(ResultErrorCodes resultCode) {
        return getResult(resultCode, HttpStatus.SC_UNAUTHORIZED);
    }

    /**
     * 403	Forbidden	服务器理解请求客户端的请求，但是拒绝执行此请求
     *
     * @param resultCode 403
     * @return {@link Result}
     */
    public static Result<String> getForbiddenResult(ResultErrorCodes resultCode) {
        return getResult(resultCode, HttpStatus.SC_FORBIDDEN);
    }

    /**
     * 405	Method Not Allowed	客户端请求中的方法被禁止
     *
     * @param resultCode 403
     * @return {@link Result}
     */
    public static Result<String> getMethodNotAllowedResult(ResultErrorCodes resultCode) {
        return getResult(resultCode, HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    /**
     * 406	Not Acceptable	服务器无法根据客户端请求的内容特性完成请求
     *
     * @param resultCode 406
     * @return {@link Result}
     */
    public static Result<String> getNotAcceptableResult(ResultErrorCodes resultCode) {
        return getResult(resultCode, HttpStatus.SC_NOT_ACCEPTABLE);
    }

    /**
     * 412 Precondition Failed	客户端请求信息的先决条件错误
     *
     * @param resultCode 412
     * @return {@link Result}
     */
    public static Result<String> getPreconditionFailedResult(ResultErrorCodes resultCode) {
        return getResult(resultCode, HttpStatus.SC_PRECONDITION_FAILED);
    }

    /**
     * 415	Unsupported Media Type	服务器无法处理请求附带的媒体格式
     *
     * @param resultCode 415
     * @return {@link Result}
     */
    public static Result<String> getUnsupportedMediaTypeResult(ResultErrorCodes resultCode) {
        return getResult(resultCode, HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * 500	Internal Server Error	服务器内部错误，无法完成请求
     *
     * @param resultCode 500
     * @return {@link Result}
     */
    public static Result<String> getInternalServerErrorResult(ResultErrorCodes resultCode) {
        return getResult(resultCode, HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    /**
     * 503	Service Unavailable	由于超载或系统维护，服务器暂时的无法处理客户端的请求。延时的长度可包含在服务器的Retry-After头信息中
     *
     * @param resultCode 503
     * @return {@link Result}
     */
    public static Result<String> getServiceUnavailableResult(ResultErrorCodes resultCode) {
        return getResult(resultCode, HttpStatus.SC_SERVICE_UNAVAILABLE);
    }
}
