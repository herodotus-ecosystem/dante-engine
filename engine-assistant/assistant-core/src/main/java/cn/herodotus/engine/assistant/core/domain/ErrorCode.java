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

package cn.herodotus.engine.assistant.core.domain;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Map;

/**
 * <p>Description: 错误码实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/9/25 16:54
 */
public class ErrorCode {

    /**
     * 自定义错误码
     */
    private final Integer code;
    /**
     * HttpStatus 状态码
     */
    private final Integer status;

    public ErrorCode(Integer status) {
        this(status, null);
    }

    public ErrorCode(Integer status, Integer sequence) {
        this.status = status;
        if (ObjectUtils.isEmpty(sequence)) {
            this.code = this.status * 100;
        } else {
            this.code = this.status * 100 + sequence;
        }
    }

    public Integer getCode() {
        return code;
    }

    public Integer getStatus() {
        return status;
    }

    public static ErrorCode create(Integer status) {
        return create(status, null);
    }

    public static ErrorCode create(Integer status, Integer sequence) {
        return new ErrorCode(status, sequence);
    }

    public static void create(Map<String, ErrorCode> container, String key, Integer status) {
        container.put(key, create(status, container.size()));
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("code", code)
                .add("status", status)
                .toString();
    }
}
