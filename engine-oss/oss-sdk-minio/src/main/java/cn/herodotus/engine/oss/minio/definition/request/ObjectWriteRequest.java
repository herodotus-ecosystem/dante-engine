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
import cn.herodotus.engine.oss.minio.domain.request.RetentionRequest;
import io.minio.ObjectWriteArgs;
import io.minio.messages.Retention;
import io.minio.messages.RetentionMode;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.time.ZonedDateTime;
import java.util.Map;

/**
 * <p>Description: ObjectWriteRequest </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/2 21:58
 */
public abstract class ObjectWriteRequest<B extends ObjectWriteArgs.Builder<B, A>, A extends ObjectWriteArgs> extends ObjectRequest<B, A> {

    private Map<String, String> headers;

    private Map<String, String> userMetadata;

    private Map<String, String> tags;

    private RetentionRequest retention;

    private Boolean legalHold;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getUserMetadata() {
        return userMetadata;
    }

    public void setUserMetadata(Map<String, String> userMetadata) {
        this.userMetadata = userMetadata;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public RetentionRequest getRetention() {
        return retention;
    }

    public void setRetention(RetentionRequest retention) {
        this.retention = retention;
    }

    public Boolean getLegalHold() {
        return legalHold;
    }

    public void setLegalHold(Boolean legalHold) {
        this.legalHold = legalHold;
    }

    @Override
    protected void prepare(B builder) {
        if (MapUtils.isNotEmpty(getHeaders())) {
            builder.headers(getHeaders());
        }

        if (MapUtils.isNotEmpty(getUserMetadata())) {
            builder.headers(getUserMetadata());
        }

        if (MapUtils.isNotEmpty(getTags())) {
            builder.headers(getTags());
        }

        if (ObjectUtils.isNotEmpty(getRetention())) {
            RetentionMode mode = RetentionMode.valueOf(getRetention().getMode());
            ZonedDateTime retainUntilDate = DateTimeUtils.stringToZonedDateTime(getRetention().getRetainUntilDate());
            builder.retention(new Retention(mode, retainUntilDate));
        }

        super.prepare(builder);
    }
}
