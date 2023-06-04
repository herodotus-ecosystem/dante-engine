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

import cn.herodotus.engine.assistant.core.definition.constants.DefaultConstants;
import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.oss.minio.entity.MultipartCreateEntity;
import cn.herodotus.engine.oss.minio.entity.ObjectWriteEntity;
import cn.herodotus.engine.oss.minio.processor.MinioPresignedObjectUrlProxy;
import cn.herodotus.engine.oss.minio.processor.MultipartUploadProcessor;
import cn.herodotus.engine.oss.minio.request.multipart.MultipartUploadCompleteRequest;
import cn.herodotus.engine.oss.minio.request.multipart.MultipartUploadCreateRequest;
import cn.herodotus.engine.rest.core.annotation.Idempotent;
import cn.herodotus.engine.rest.core.controller.Controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>Description: Minio 分片上传接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/4 15:02
 */
@RestController
@RequestMapping(DefaultConstants.MINIO_MULTIPART_REQUEST_MAPPING)
@Tags({
        @Tag(name = "对象存储管理接口"),
        @Tag(name = "Minio 对象存储管理接口"),
        @Tag(name = "Minio 对象存储分片上传接口")
})
public class MultipartUploadController implements Controller {

    private final MultipartUploadProcessor multipartUploadProcessor;
    private final MinioPresignedObjectUrlProxy presignedObjectUrlDelegate;

    public MultipartUploadController(MultipartUploadProcessor multipartUploadProcessor, MinioPresignedObjectUrlProxy presignedObjectUrlDelegate) {
        this.multipartUploadProcessor = multipartUploadProcessor;
        this.presignedObjectUrlDelegate = presignedObjectUrlDelegate;
    }

    @Idempotent
    @Operation(summary = "创建分片上传信息", description = "创建分片上传信息",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "uploadId 和 预下载地址", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MultipartCreateEntity.class))),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "204", description = "无结果"),
                    @ApiResponse(responseCode = "500", description = "操作失败")
            })
    @Parameters({
            @Parameter(name = "request", required = true, description = "MultipartUploadCreateRequest参数实体", schema = @Schema(implementation = MultipartUploadCreateRequest.class))
    })
    @PostMapping("/create")
    public Result<MultipartCreateEntity> createMultipartUpload(@Validated @RequestBody MultipartUploadCreateRequest request) {
        MultipartCreateEntity result = multipartUploadProcessor.createMultipartUpload(request.getBucketName(), request.getObjectName(), request.getSize());
        return result(result);
    }

    @Idempotent
    @Operation(summary = "完成分片上传", description = "完成分片上传，Minio将上传完成的分片信息进行合并",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "操作结果", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ObjectWriteEntity.class))),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "204", description = "无结果"),
                    @ApiResponse(responseCode = "500", description = "操作失败")
            })
    @Parameters({
            @Parameter(name = "request", required = true, description = "MultipartUploadCompleteRequest参数实体", schema = @Schema(implementation = MultipartUploadCompleteRequest.class))
    })
    @PostMapping("/complete")
    public Result<ObjectWriteEntity> completeMultipartUpload(@Validated @RequestBody MultipartUploadCompleteRequest request) {
        ObjectWriteEntity entity = multipartUploadProcessor.completeMultipartUpload(request.getBucketName(), request.getObjectName(), request.getUploadId());
        return result(entity);
    }

    @Operation(summary = "预下载代理地址", description = "预下载代理地址，避免前端直接访问OSS，同时导致微服务寻址错误",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "操作结果", content = @Content(mediaType = "application/json")),
            })
    @PutMapping(value = DefaultConstants.MINIO_PRESIGNED_OBJECT_PROXY)
    public ResponseEntity<String> presignedObjectProxy(HttpServletRequest request) {
        return presignedObjectUrlDelegate.delegate(request);
    }
}
