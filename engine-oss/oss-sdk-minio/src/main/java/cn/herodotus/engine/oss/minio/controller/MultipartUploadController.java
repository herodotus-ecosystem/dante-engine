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

package cn.herodotus.engine.oss.minio.controller;

import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.oss.minio.domain.MultipartUploadCreate;
import cn.herodotus.engine.oss.minio.dto.logic.CompleteMultipartUploadDto;
import cn.herodotus.engine.oss.minio.dto.logic.CreateMultipartUpload;
import cn.herodotus.engine.oss.minio.processor.MultipartUploadProcessor;
import cn.herodotus.engine.rest.core.annotation.Idempotent;
import cn.herodotus.engine.rest.core.controller.Controller;
import io.minio.ObjectWriteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: Minio 分片上传接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/4 15:02
 */
@RestController
@RequestMapping("/oss/minio/multipart")
@Tags({
        @Tag(name = "对象存储管理接口"),
        @Tag(name = "Minio 对象存储管理接口"),
        @Tag(name = "Minio 对象存储分片上传接口")
})
public class MultipartUploadController implements Controller {

    private final MultipartUploadProcessor multipartUploadProcessor;

    public MultipartUploadController(MultipartUploadProcessor multipartUploadProcessor) {
        this.multipartUploadProcessor = multipartUploadProcessor;
    }

    @Idempotent
    @Operation(summary = "创建分片上传信息", description = "创建分片上传信息",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "是否成功", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "domain", required = true, description = "Create Multipart Upload 请求实体", schema = @Schema(implementation = CreateMultipartUpload.class))
    })
    @PostMapping("/create")
    public Result<MultipartUploadCreate> createMultipartUpload(@Validated @RequestBody CreateMultipartUpload domain) {
        MultipartUploadCreate result = multipartUploadProcessor.createMultipartUpload(domain.getBucketName(), domain.getObjectName(), domain.getSize());
        return result(result);
    }

    @Idempotent
    @Operation(summary = "完成分片上传", description = "完成分片上传，Minio将上传完成的分片信息进行合并",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "是否成功", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "bucketName", required = true, description = "存储桶名称"),
            @Parameter(name = "objectName", required = true, description = "文件名称"),
            @Parameter(name = "objectName", required = true, description = "文件名称"),
    })
    @PostMapping("/complete")
    public Result<Boolean> completeMultipartUpload(@Validated @RequestBody CompleteMultipartUploadDto domain) {
        ObjectWriteResponse objectWriteResponse = multipartUploadProcessor.completeMultipartUpload(domain.getBucketName(), domain.getObjectName(), domain.getUploadId());
        if (ObjectUtils.isNotEmpty(objectWriteResponse)) {
            return result(true);
        } else {
            return result(false);
        }
    }

}
