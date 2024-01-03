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

package cn.herodotus.engine.assistant.definition.constants;

import cn.herodotus.engine.assistant.definition.feedback.*;

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
public interface ErrorCodes {

    /**
     * 200
     */
    OkFeedback OK = new OkFeedback("成功");
    /**
     * 204
     */
    NoContentFeedback NO_CONTENT = new NoContentFeedback("无内容");
    /**
     * 401.** 未经授权 Unauthorized	请求要求用户的身份认证
     */
    UnauthorizedFeedback UNAUTHORIZED = new UnauthorizedFeedback("未经授权");
    UnauthorizedFeedback ACCESS_DENIED = new UnauthorizedFeedback("您没有权限，拒绝访问");
    UnauthorizedFeedback ACCOUNT_DISABLED = new UnauthorizedFeedback("该账户已经被禁用");
    UnauthorizedFeedback ACCOUNT_ENDPOINT_LIMITED = new UnauthorizedFeedback("您已经使用其它终端登录,请先退出其它终端");
    UnauthorizedFeedback ACCOUNT_EXPIRED = new UnauthorizedFeedback("该账户已经过期");
    UnauthorizedFeedback ACCOUNT_LOCKED = new UnauthorizedFeedback("该账户已经被锁定");
    UnauthorizedFeedback BAD_CREDENTIALS = new UnauthorizedFeedback("用户名或密码错误");
    UnauthorizedFeedback CREDENTIALS_EXPIRED = new UnauthorizedFeedback("该账户密码凭证已过期");
    UnauthorizedFeedback INVALID_CLIENT = new UnauthorizedFeedback("客户端身份验证失败或数据库未初始化");
    UnauthorizedFeedback INVALID_TOKEN = new UnauthorizedFeedback("提供的访问令牌已过期、吊销、格式错误或无效");
    UnauthorizedFeedback INVALID_GRANT = new UnauthorizedFeedback("提供的授权授予或刷新令牌无效、已过期或已撤销");
    UnauthorizedFeedback UNAUTHORIZED_CLIENT = new UnauthorizedFeedback("客户端无权使用此方法请求授权码或访问令牌");
    UnauthorizedFeedback USERNAME_NOT_FOUND = new UnauthorizedFeedback("用户名或密码错误");
    UnauthorizedFeedback SESSION_EXPIRED = new UnauthorizedFeedback("Session 已过期，请刷新页面后再使用");

    /**
     * 403.** 禁止的请求，与403对应
     */
    ForbiddenFeedback FORBIDDEN = new ForbiddenFeedback("禁止的请求");
    ForbiddenFeedback INSUFFICIENT_SCOPE = new ForbiddenFeedback("TOKEN权限不足，您需要更高级别的权限");
    ForbiddenFeedback SQL_INJECTION_REQUEST = new ForbiddenFeedback("疑似SQL注入请求");

    /**
     * 405.** 方法不允许 与405对应
     */
    MethodNotAllowedFeedback METHOD_NOT_ALLOWED = new MethodNotAllowedFeedback("方法不允许");
    MethodNotAllowedFeedback HTTP_REQUEST_METHOD_NOT_SUPPORTED = new MethodNotAllowedFeedback("请求使用的方法类型不支持");

    /**
     * 406.** 不接受的请求，与406对应
     */
    NotAcceptableFeedback NOT_ACCEPTABLE = new NotAcceptableFeedback("不接受的请求");
    NotAcceptableFeedback UNSUPPORTED_GRANT_TYPE = new NotAcceptableFeedback("授权服务器不支持授权授予类型");
    NotAcceptableFeedback UNSUPPORTED_RESPONSE_TYPE = new NotAcceptableFeedback("授权服务器不支持使用此方法获取授权代码或访问令牌");
    NotAcceptableFeedback UNSUPPORTED_TOKEN_TYPE = new NotAcceptableFeedback("授权服务器不支持撤销提供的令牌类型");

    /**
     * 412.* 未经授权 Precondition Failed 客户端请求信息的先决条件错误
     */
    PreconditionFailedFeedback PRECONDITION_FAILED = new PreconditionFailedFeedback("户端请求信息的先决条件错误");
    PreconditionFailedFeedback INVALID_REDIRECT_URI = new PreconditionFailedFeedback("OAuth2 URI 重定向的值无效");
    PreconditionFailedFeedback INVALID_REQUEST = new PreconditionFailedFeedback("无效的请求，参数使用错误或无效.");
    PreconditionFailedFeedback INVALID_SCOPE = new PreconditionFailedFeedback("授权范围错误");
    PreconditionFailedFeedback METHOD_ARGUMENT_NOT_VALID = new PreconditionFailedFeedback("接口参数校验失败，参数使用错误或者未接收到参数");

    /**
     * 415.* Unsupported Media Type	服务器无法处理请求附带的媒体格式
     */
    UnsupportedMediaTypeFeedback UNSUPPORTED_MEDIA_TYPE = new UnsupportedMediaTypeFeedback("服务器无法处理请求附带的媒体格式");
    UnsupportedMediaTypeFeedback HTTP_MEDIA_TYPE_NOT_ACCEPTABLE = new UnsupportedMediaTypeFeedback("不支持的 Media Type");

    /**
     * 500.* Internal Server Error	服务器内部错误，无法完成请求
     */
    InternalServerErrorFeedback INTERNAL_SERVER_ERROR = new InternalServerErrorFeedback("服务器内部错误，无法完成请求");
    InternalServerErrorFeedback SERVER_ERROR = new InternalServerErrorFeedback("授权服务器遇到意外情况，无法满足请求");
    InternalServerErrorFeedback HTTP_MESSAGE_NOT_READABLE_EXCEPTION = new InternalServerErrorFeedback("JSON字符串反序列化为实体出错！");
    InternalServerErrorFeedback ILLEGAL_ARGUMENT_EXCEPTION = new InternalServerErrorFeedback("参数不合法错误，请仔细确认参数使用是否正确。");
    InternalServerErrorFeedback IO_EXCEPTION = new InternalServerErrorFeedback("IO异常");
    InternalServerErrorFeedback MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION = new InternalServerErrorFeedback("接口参数使用错误或必要参数缺失，请查阅接口文档！");
    InternalServerErrorFeedback NULL_POINTER_EXCEPTION = new InternalServerErrorFeedback("后台代码执行过程中出现了空值");
    InternalServerErrorFeedback TYPE_MISMATCH_EXCEPTION = new InternalServerErrorFeedback("类型不匹配");
    InternalServerErrorFeedback BORROW_OBJECT_FROM_POOL_ERROR_EXCEPTION = new InternalServerErrorFeedback("从对象池中获取对象错误");

    /**
     * 501. Not Implemented	服务器不支持请求的功能，无法完成请求
     */
    NotImplementedFeedback NOT_IMPLEMENTED = new NotImplementedFeedback("服务器不支持请求的功能，无法完成请求");
    NotImplementedFeedback DISCOVERED_UNRECORDED_ERROR_EXCEPTION = new NotImplementedFeedback("发现尚未记录的 Exception 错误,建议在错误体系中添加或者提 ISSUE 给原作者");
    NotImplementedFeedback PROPERTY_VALUE_IS_NOT_SET_EXCEPTION = new NotImplementedFeedback("必要的Property配置属性值没有设置");
    NotImplementedFeedback URL_FORMAT_INCORRECT_EXCEPTION = new NotImplementedFeedback("URL格式错误或者缺少Http协议头");
    NotImplementedFeedback ILLEGAL_SYMMETRIC_KEY = new NotImplementedFeedback("静态AES加密算法KEY非法");

    /**
     * 503.* Service Unavailable	由于超载或系统维护，服务器暂时的无法处理客户端的请求。延时的长度可包含在服务器的Retry-After头信息中
     */
    ServiceUnavailableFeedback SERVICE_UNAVAILABLE = new ServiceUnavailableFeedback("服务不可用");
    ServiceUnavailableFeedback COOKIE_THEFT = new ServiceUnavailableFeedback("Cookie 信息不安全");
    ServiceUnavailableFeedback INVALID_COOKIE = new ServiceUnavailableFeedback("不可用的 Cookie 信息");
    ServiceUnavailableFeedback PROVIDER_NOT_FOUND = new ServiceUnavailableFeedback("授权服务器代码逻辑配置错误");
    ServiceUnavailableFeedback TEMPORARILY_UNAVAILABLE = new ServiceUnavailableFeedback("由于服务器临时超载或维护，授权服务器当前无法处理该请求");
    ServiceUnavailableFeedback SEARCH_IP_LOCATION = new ServiceUnavailableFeedback("搜索 IP 定位出现读取错误，服务器当前无法处理该请求");
    ServiceUnavailableFeedback OPEN_API_REQUEST_FAILURE = new ServiceUnavailableFeedback("基础设施 Open Api 调用请求失败");

    /**
     * 6*.* 为数据操作相关错误
     */
    CustomizeFeedback TRANSACTION_ROLLBACK = new CustomizeFeedback("数据事务处理失败，数据回滚", 6);
    CustomizeFeedback BAD_SQL_GRAMMAR = new CustomizeFeedback("低级SQL语法错误，检查SQL能否正常运行或者字段名称是否正确", 6);
    CustomizeFeedback DATA_INTEGRITY_VIOLATION = new CustomizeFeedback("该数据正在被其它数据引用，请先删除引用关系，再进行数据删除操作", 6);

    /**
     * 7*.* 基础设施交互错误
     * 71.* Redis 操作出现错误
     * 72.* Cache 操作出现错误
     */
    CustomizeFeedback PIPELINE_INVALID_COMMANDS = new CustomizeFeedback("Redis管道包含一个或多个无效命令", 7);
}
