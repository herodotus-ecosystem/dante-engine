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

package cn.herodotus.engine.assistant.core.enums;

import cn.herodotus.engine.assistant.core.definition.constants.ErrorCodes;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 统一异常处理器
 * 1**	信息，服务器收到请求，需要请求者继续执行操作
 * 2**	成功，操作被成功接收并处理
 * 3**	重定向，需要进一步的操作以完成请求
 * 4**	客户端错误，请求包含语法错误或无法完成请求
 * 5**	服务器错误，服务器在处理请求的过程中发生了错误
 * <p>
 * 1开头的状态码
 * 100	Continue	继续。客户端应继续其请求
 * 101	Switching Protocols	切换协议。服务器根据客户端的请求切换协议。只能切换到更高级的协议，例如，切换到HTTP的新版本协议
 * <p>
 * 2开头的状态码
 * 200	OK	请求成功。一般用于GET与POST请求
 * 201	Created	已创建。成功请求并创建了新的资源
 * 202	Accepted	已接受。已经接受请求，但未处理完成
 * 203	Non-Authoritative Information	非授权信息。请求成功。但返回的meta信息不在原始的服务器，而是一个副本
 * 204	No Content	无内容。服务器成功处理，但未返回内容。在未更新网页的情况下，可确保浏览器继续显示当前文档
 * 205	Reset Content	重置内容。服务器处理成功，用户终端（例如：浏览器）应重置文档视图。可通过此返回码清除浏览器的表单域
 * 206	Partial Content	部分内容。服务器成功处理了部分GET请求
 * <p>
 * 3开头的状态码
 * 300	Multiple Choices	多种选择。请求的资源可包括多个位置，相应可返回一个资源特征与地址的列表用于用户终端（例如：浏览器）选择
 * 301	Moved Permanently	永久移动。请求的资源已被永久的移动到新URI，返回信息会包括新的URI，浏览器会自动定向到新URI。今后任何新的请求都应使用新的URI代替
 * 302	Found	临时移动。与301类似。但资源只是临时被移动。客户端应继续使用原有URI
 * 303	See Other	查看其它地址。与301类似。使用GET和POST请求查看
 * 304	Not Modified	未修改。所请求的资源未修改，服务器返回此状态码时，不会返回任何资源。客户端通常会缓存访问过的资源，通过提供一个头信息指出客户端希望只返回在指定日期之后修改的资源
 * 305	Use Proxy	使用代理。所请求的资源必须通过代理访问
 * 306	Unused	已经被废弃的HTTP状态码
 * 307	Temporary Redirect	临时重定向。与302类似。使用GET请求重定向
 * <p>
 * 4开头的状态码
 * 400	Bad Request	客户端请求的语法错误，服务器无法理解
 * 401	Unauthorized	请求要求用户的身份认证
 * 402	Payment Required	保留，将来使用
 * 403	Forbidden	服务器理解请求客户端的请求，但是拒绝执行此请求
 * 404	Not Found	服务器无法根据客户端的请求找到资源（网页）。通过此代码，网站设计人员可设置"您所请求的资源无法找到"的个性页面
 * 405	Method Not Allowed	客户端请求中的方法被禁止
 * 406	Not Acceptable	服务器无法根据客户端请求的内容特性完成请求
 * 407	Proxy Authentication Required	请求要求代理的身份认证，与401类似，但请求者应当使用代理进行授权
 * 408	Request Time-out	服务器等待客户端发送的请求时间过长，超时
 * 409	Conflict	服务器完成客户端的PUT请求是可能返回此代码，服务器处理请求时发生了冲突
 * 410	Gone	客户端请求的资源已经不存在。410不同于404，如果资源以前有现在被永久删除了可使用410代码，网站设计人员可通过301代码指定资源的新位置
 * 411	Length Required	服务器无法处理客户端发送的不带Content-Length的请求信息
 * 412	Precondition Failed	客户端请求信息的先决条件错误
 * 413	Request Entity Too Large	由于请求的实体过大，服务器无法处理，因此拒绝请求。为防止客户端的连续请求，服务器可能会关闭连接。如果只是服务器暂时无法处理，则会包含一个Retry-After的响应信息
 * 414	Request-URI Too Large	请求的URI过长（URI通常为网址），服务器无法处理
 * 415	Unsupported Media Type	服务器无法处理请求附带的媒体格式
 * 416	Requested range not satisfiable	客户端请求的范围无效
 * 417	Expectation Failed	服务器无法满足Expect的请求头信息
 * <p>
 * 5开头的状态码
 * 500	Internal Server Error	服务器内部错误，无法完成请求
 * 501	Not Implemented	服务器不支持请求的功能，无法完成请求
 * 502	Bad Gateway	充当网关或代理的服务器，从远端服务器接收到了一个无效的请求
 * 503	Service Unavailable	由于超载或系统维护，服务器暂时的无法处理客户端的请求。延时的长度可包含在服务器的Retry-After头信息中
 * 504	Gateway Time-out	充当网关或代理的服务器，未及时从远端服务器获取请求
 * 505	HTTP Version not supported	服务器不支持请求的HTTP协议的版本，无法完成处理
 * <p>
 * --- 自定义返回码 ---
 * <p>
 * 主要分类说明：
 * 2**.**   成功，操作被成功接收并处理
 * 3**.**	需要后续操作，需要进一步的操作以完成请求
 * 4**.**	HTTP请求错误，请求包含语法错误或无法完成请求，
 * 5**.**   平台错误，平台相关组件运行及操作错误。
 * 6**.**	关系数据库错误，服务器在处理请求的过程中发生了数据SQL操作等底层错误
 * 600.**	JDBC错误，服务器在处理请求的过程中发生了JDBC底层错误。
 * 601.**	JPA错误，服务器在处理请求的过程中发生了JPA错误。
 * 602.**	Hibernate错误，服务器在处理请求的过程中发生了Hibernate操作错误。
 * 603.**   接口参数Validation错误
 * <p>
 * 其它内容逐步补充
 *
 * @author gengwei.zheng
 */
@Schema(title = "响应结果状态", description = "自定义错误码以及对应的、友好的错误信息")
public enum ResultErrorCodes {

    /**
     * 401.** 未经授权 Unauthorized	请求要求用户的身份认证
     */
    ACCESS_DENIED(ErrorCodes.ACCESS_DENIED, "您没有权限，拒绝访问"),
    ACCOUNT_DISABLED(ErrorCodes.ACCOUNT_DISABLED, "该账户已经被禁用"),
    ACCOUNT_ENDPOINT_LIMITED(ErrorCodes.ACCOUNT_ENDPOINT_LIMITED, "您已经使用其它终端登录,请先退出其它终端"),
    ACCOUNT_EXPIRED(ErrorCodes.ACCOUNT_EXPIRED, "该账户已经过期"),
    ACCOUNT_LOCKED(ErrorCodes.ACCOUNT_LOCKED, "该账户已经被锁定"),
    BAD_CREDENTIALS(ErrorCodes.BAD_CREDENTIALS, "用户名或密码错误"),
    CREDENTIALS_EXPIRED(ErrorCodes.CREDENTIALS_EXPIRED, "该账户密码凭证已过期"),
    INVALID_CLIENT(ErrorCodes.INVALID_CLIENT, "客户端身份验证失败"),
    INVALID_TOKEN(ErrorCodes.INVALID_TOKEN, "提供的访问令牌已过期、吊销、格式错误或无效"),
    INVALID_GRANT(ErrorCodes.INVALID_GRANT, "提供的授权授予或刷新令牌无效、已过期或已撤销"),
    UNAUTHORIZED_CLIENT(ErrorCodes.UNAUTHORIZED_CLIENT, "客户端无权使用此方法请求授权码或访问令牌"),
    USERNAME_NOT_FOUND(ErrorCodes.USERNAME_NOT_FOUND, "用户名或密码错误"),
    SESSION_EXPIRED(ErrorCodes.SESSION_EXPIRED, "Session 已过期，请刷新页面后再使用"),

    /**
     * 403.** 禁止的请求，与403对应
     */
    INSUFFICIENT_SCOPE(ErrorCodes.INSUFFICIENT_SCOPE, "TOKEN权限不足，您需要更高级别的权限"),
    SQL_INJECTION_REQUEST(ErrorCodes.SQL_INJECTION_REQUEST, "疑似SQL注入请求"),

    /**
     * 405.** 方法不允许 与405对应
     */
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(ErrorCodes.HTTP_REQUEST_METHOD_NOT_SUPPORTED, "请求使用的方法类型不支持"),

    /**
     * 406.** 不接受的请求，与406对应
     */
    UNSUPPORTED_GRANT_TYPE(ErrorCodes.UNSUPPORTED_GRANT_TYPE, "授权服务器不支持授权授予类型"),
    UNSUPPORTED_RESPONSE_TYPE(ErrorCodes.UNSUPPORTED_RESPONSE_TYPE, "授权服务器不支持使用此方法获取授权代码或访问令牌"),
    UNSUPPORTED_TOKEN_TYPE(ErrorCodes.UNSUPPORTED_TOKEN_TYPE, "授权服务器不支持撤销提供的令牌类型"),

    /**
     * 412.* 未经授权 Precondition Failed 客户端请求信息的先决条件错误
     */
    INVALID_REDIRECT_URI(ErrorCodes.INVALID_REDIRECT_URI, "OAuth2 URI 重定向的值无效"),
    INVALID_REQUEST(ErrorCodes.INVALID_REQUEST, "无效的请求，参数使用错误或无效."),
    INVALID_SCOPE(ErrorCodes.INVALID_SCOPE, "授权范围错误"),

    /**
     * 415.* Unsupported Media Type	服务器无法处理请求附带的媒体格式
     */
    HTTP_MEDIA_TYPE_NOT_ACCEPTABLE(ErrorCodes.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE, "不支持的 Media Type"),

    /**
     * 500.* Internal Server Error	服务器内部错误，无法完成请求
     */
    SERVER_ERROR(ErrorCodes.SERVER_ERROR, "授权服务器遇到意外情况，无法满足请求"),
    HTTP_MESSAGE_NOT_READABLE_EXCEPTION(ErrorCodes.HTTP_MESSAGE_NOT_READABLE_EXCEPTION, "JSON字符串反序列化为实体出错！"),
    ILLEGAL_ARGUMENT_EXCEPTION(ErrorCodes.ILLEGAL_ARGUMENT_EXCEPTION, "参数不合法错误，请仔细确认参数使用是否正确。"),
    IO_EXCEPTION(ErrorCodes.IO_EXCEPTION, "IO异常"),
    MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION(ErrorCodes.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION, "接口参数使用错误或必要参数缺失，请查阅接口文档！"),
    NULL_POINTER_EXCEPTION(ErrorCodes.NULL_POINTER_EXCEPTION, "后台代码执行过程中出现了空值"),
    TYPE_MISMATCH_EXCEPTION(ErrorCodes.TYPE_MISMATCH_EXCEPTION, "类型不匹配"),

    /**
     * 503.* Service Unavailable	由于超载或系统维护，服务器暂时的无法处理客户端的请求。延时的长度可包含在服务器的Retry-After头信息中
     */
    SERVICE_UNAVAILABLE(ErrorCodes.SERVICE_UNAVAILABLE, "服务不可用"),
    TEMPORARILY_UNAVAILABLE(ErrorCodes.TEMPORARILY_UNAVAILABLE, "由于服务器临时超载或维护，授权服务器当前无法处理该请求"),
    PROVIDER_NOT_FOUND(ErrorCodes.PROVIDER_NOT_FOUND, "授权服务器代码逻辑配置错误"),
    COOKIE_THEFT(ErrorCodes.COOKIE_THEFT, "Cookie 信息不安全"),
    INVALID_COOKIE(ErrorCodes.INVALID_COOKIE, "不可用的 Cookie 信息"),

    /**
     * 6*.* 为数据操作相关错误
     */
    METHOD_ARGUMENT_NOT_VALID(ErrorCodes.METHOD_ARGUMENT_NOT_VALID, "接口参数校验失败，参数使用错误或者未接收到参数"),
    BAD_SQL_GRAMMAR(ErrorCodes.BAD_SQL_GRAMMAR, "低级SQL语法错误，检查SQL能否正常运行或者字段名称是否正确"),
    DATA_INTEGRITY_VIOLATION(ErrorCodes.DATA_INTEGRITY_VIOLATION, "该数据正在被其它数据引用，请先删除引用关系，再进行数据删除操作"),
    TRANSACTION_ROLLBACK(ErrorCodes.TRANSACTION_ROLLBACK, "数据事务处理失败，数据回滚"),

    /**
     * 7*.* 基础设施交互错误
     * 71.* Redis 操作出现错误
     * 72.* Cache 操作出现错误
     */
    PIPELINE_INVALID_COMMANDS(71000, "Redis管道包含一个或多个无效命令");


    @Schema(title = "结果代码")
    private final int code;
    @Schema(title = "结果信息")
    private final String message;


    ResultErrorCodes(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
