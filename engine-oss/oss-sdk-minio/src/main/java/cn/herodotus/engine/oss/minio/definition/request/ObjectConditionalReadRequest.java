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

package cn.herodotus.engine.oss.minio.definition.request;

import cn.herodotus.engine.assistant.core.utils.DateTimeUtils;
import io.minio.ObjectConditionalReadArgs;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>Description: ObjectConditionalReadRequest </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/30 23:32
 */
public abstract class ObjectConditionalReadRequest<B extends ObjectConditionalReadArgs.Builder<B, A>, A extends ObjectConditionalReadArgs> extends ObjectReadRequest<B, A>{

    @NotNull(message = "offset 参数不能为空")
    @DecimalMin(value = "0",message = "offset 参数不能小于 0")
    private Long offset;

    @NotNull(message = "length 参数不能为空")
    @DecimalMin(value = "0",message = "length 参数不能小于 0")
    private Long length;

    @NotBlank(message = "matchETag 不能为null或者空")
    private String matchETag;

    @NotBlank(message = "notMatchETag 不能为null或者空")
    private String notMatchETag;
    private String modifiedSince;
    private String unmodifiedSince;

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public String getMatchETag() {
        return matchETag;
    }

    public void setMatchETag(String matchETag) {
        this.matchETag = matchETag;
    }

    public String getNotMatchETag() {
        return notMatchETag;
    }

    public void setNotMatchETag(String notMatchETag) {
        this.notMatchETag = notMatchETag;
    }

    public String getModifiedSince() {
        return modifiedSince;
    }

    public void setModifiedSince(String modifiedSince) {
        this.modifiedSince = modifiedSince;
    }

    public String getUnmodifiedSince() {
        return unmodifiedSince;
    }

    public void setUnmodifiedSince(String unmodifiedSince) {
        this.unmodifiedSince = unmodifiedSince;
    }

    @Override
    protected void prepare(B builder) {
        builder.length(getLength());
        builder.offset(getOffset());
        builder.matchETag(getMatchETag());
        builder.notMatchETag(getNotMatchETag());
        if (StringUtils.isNotBlank(getModifiedSince())) {
            builder.modifiedSince(DateTimeUtils.stringToZonedDateTime(getModifiedSince()));
        }
        if (StringUtils.isNotBlank(getUnmodifiedSince())) {
            builder.unmodifiedSince(DateTimeUtils.stringToZonedDateTime(getUnmodifiedSince()));
        }
        super.prepare(builder);
    }
}
