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

package cn.herodotus.engine.oss.minio.request.object;

import cn.herodotus.engine.oss.minio.converter.RequestToCopySourceConverter;
import cn.herodotus.engine.oss.minio.definition.request.ObjectWriteRequest;
import cn.herodotus.engine.oss.minio.request.domain.CopySourceRequest;
import io.minio.CopyObjectArgs;
import io.minio.CopySource;
import io.minio.Directive;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * <p>Description: 拷贝对象 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/31 14:53
 */
public class CopyObjectRequest extends ObjectWriteRequest<CopyObjectArgs.Builder, CopyObjectArgs> {

    private final Converter<CopySourceRequest, CopySource> requestTo = new RequestToCopySourceConverter();

    @NotNull(message = "source 对象不能为空")
    private CopySourceRequest source;
    private String metadataDirective;
    private String taggingDirective;

    public CopyObjectRequest(ComposeObjectRequest request) {
        this.setExtraHeaders(request.getExtraHeaders());
        this.setExtraQueryParams(request.getExtraQueryParams());
        this.setBucketName(request.getBucketName());
        this.setRegion(request.getRegion());
        this.setObjectName(request.getObjectName());
        this.setHeaders(request.getHeaders());
        this.setUserMetadata(request.getUserMetadata());
        this.setServerSideEncryption(request.getServerSideEncryption());
        this.setTags(request.getTags());
        this.setRetention(request.getRetention());
        this.setLegalHold(request.getLegalHold());
        this.source = new CopySourceRequest(request.getSources().get(0));
    }

    public CopySourceRequest getSource() {
        return source;
    }

    public void setSource(CopySourceRequest source) {
        this.source = source;
    }

    public String getMetadataDirective() {
        return metadataDirective;
    }

    public void setMetadataDirective(String metadataDirective) {
        this.metadataDirective = metadataDirective;
    }

    public String getTaggingDirective() {
        return taggingDirective;
    }

    public void setTaggingDirective(String taggingDirective) {
        this.taggingDirective = taggingDirective;
    }

    @Override
    public void prepare(CopyObjectArgs.Builder builder) {
        builder.source(requestTo.convert(getSource()));
        if (StringUtils.isNotBlank(getMetadataDirective())) {
            builder.metadataDirective(Directive.valueOf(getMetadataDirective()));
        }
        if (StringUtils.isNotBlank(getTaggingDirective())) {
            builder.taggingDirective(Directive.valueOf(getTaggingDirective()));
        }
        super.prepare(builder);
    }

    @Override
    public CopyObjectArgs.Builder getBuilder() {
        return CopyObjectArgs.builder();
    }
}
