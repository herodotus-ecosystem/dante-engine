/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
 * 2.请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.assistant.core.domain;


import cn.herodotus.engine.assistant.core.enums.ResultErrorCodes;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: 统一响应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/29 14:50
 */
@Schema(title = "统一响应返回实体", description = "所有Rest接口统一返回的实体定义", example = "new Result<T>().ok().message(\"XXX\")")
public class Result<T> implements Serializable {

    @Schema(title = "自定义响应编码")
    private int code = 0;

    @Schema(title = "响应返回信息")
    private String message;

    @Schema(title = "请求路径")
    private String path;

    @Schema(title = "响应返回数据")
    private T data;

    @Schema(title = "http状态码")
    private int status;

    @Schema(title = "链路追踪TraceId")
    private String traceId;

    @Schema(title = "响应时间戳", pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp = new Date();

    @Schema(title = "校验错误信息")
    private Error error = new Error();

    public Result() {
        super();
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public T getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public String getTraceId() {
        return traceId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Error getError() {
        return error;
    }

    public Result<T> code(int code) {
        this.code = code;
        return this;
    }

    public Result<T> message(String message) {
        this.message = message;
        return this;
    }

    public Result<T> data(T data) {
        this.data = data;
        return this;
    }

    public Result<T> path(String path) {
        this.path = path;
        return this;
    }

    public Result<T> type(ResultErrorCodes resultErrorCodes) {
        this.code = resultErrorCodes.getCode();
        this.message = resultErrorCodes.getMessage();
        return this;
    }

    public Result<T> status(int httpStatus) {
        this.status = httpStatus;
        return this;
    }

    public Result<T> traceId(String traceId) {
        this.traceId = traceId;
        return this;
    }

    public Result<T> stackTrace(StackTraceElement[] stackTrace) {
        this.error.setStackTrace(stackTrace);
        return this;
    }

    public Result<T> detail(String detail) {
        this.error.setDetail(detail);
        return this;
    }

    public Result<T> validation(String message, String code, String field) {
        this.error.setMessage(message);
        this.error.setCode(code);
        this.error.setField(field);
        return this;
    }

    private static <T> Result<T> create(String message, String detail, int code, int status, T data, StackTraceElement[] stackTrace) {
        Result<T> result = new Result<>();
        if (StringUtils.isNotBlank(message)) {
            result.message(message);
        }

        if (StringUtils.isNotBlank(detail)) {
            result.detail(detail);
        }

        result.code(code);
        result.status(status);

        if (ObjectUtils.isNotEmpty(data)) {
            result.data(data);
        }

        if (ArrayUtils.isNotEmpty(stackTrace)) {
            result.stackTrace(stackTrace);
        }

        return result;
    }

    public static <T> Result<T> success(String message, int code, int status, T data) {
        return create(message, null, code, status, data, null);
    }

    public static <T> Result<T> success(String message, int code, T data) {
        return success(message, code, HttpStatus.SC_OK, data);
    }

    public static <T> Result<T> success(ResultErrorCodes resultErrorCodes, T data) {
        return success(resultErrorCodes.getMessage(), resultErrorCodes.getCode(), data);
    }

    public static <T> Result<T> success(Feedback feedback, T data) {
        return success(feedback.getMessage(), feedback.getCode(), feedback.getStatus(), data);
    }

    public static <T> Result<T> success(String message, T data) {
        return success(message, Feedback.OK.getCode(), data);
    }

    public static <T> Result<T> success(String message) {
        return success(message, null);
    }

    public static <T> Result<T> success() {
        return success("操作成功！");
    }

    public static <T> Result<T> content(T data) {
        return success("操作成功！", data);
    }

    public static <T> Result<T> failure(String message, String detail, int code, int status, T data, StackTraceElement[] stackTrace) {
        return create(message, detail, code, status, data, stackTrace);
    }

    public static <T> Result<T> failure(String message, String detail, int code, int status, T data) {
        return failure(message, detail, code, status, data, null);
    }

    public static <T> Result<T> failure(String message, int code, int status, T data) {
        return failure(message, message, code, status, data);
    }

    public static <T> Result<T> failure(String message, String detail, int code, T data) {
        return failure(message, detail, code, HttpStatus.SC_INTERNAL_SERVER_ERROR, data);
    }

    public static <T> Result<T> failure(String message, int code, T data) {
        return failure(message, message, code, data);
    }

    public static <T> Result<T> failure(ResultErrorCodes resultErrorCodes, T data) {
        return failure(resultErrorCodes.getMessage(), resultErrorCodes.getCode(), data);
    }

    public static <T> Result<T> failure(Feedback feedback, T data) {
        return failure(feedback.getMessage(), feedback.getCode(), feedback.getStatus(), data);
    }

    public static <T> Result<T> failure(String message, T data) {
        return failure(message, Feedback.ERROR.getCode(), data);
    }

    public static <T> Result<T> failure(String message) {
        return failure(message, null);
    }

    public static <T> Result<T> failure() {
        return failure("操作失败！");
    }

    public static <T> Result<T> empty(String message, int code, int status) {
        return create(message, null, code, status, null, null);
    }

    public static <T> Result<T> empty(String message, int code) {
        return empty(message, code, Feedback.NO_CONTENT.getStatus());
    }

    public static <T> Result<T> empty(Feedback feedback) {
        return empty(feedback.getMessage(), feedback.getCode(), feedback.getStatus());
    }

    public static <T> Result<T> empty(ResultErrorCodes resultErrorCodes) {
        return empty(resultErrorCodes.getMessage(), resultErrorCodes.getCode());
    }

    public static <T> Result<T> empty(String message) {
        return empty(message, Feedback.NO_CONTENT.getCode());
    }

    public static <T> Result<T> empty() {
        return empty("未查询到相关内容！");
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("code", code)
                .add("message", message)
                .add("path", path)
                .add("data", data)
                .add("status", status)
                .add("timestamp", timestamp)
                .add("error", error)
                .toString();
    }

    public Map<String, Object> toModel() {
        Map<String, Object> result = new HashMap<>(8);
        result.put("code", code);
        result.put("message", message);
        result.put("path", path);
        result.put("data", data);
        result.put("status", status);
        result.put("timestamp", timestamp);
        result.put("error", error);
        return result;
    }
}
