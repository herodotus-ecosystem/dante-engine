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
import cn.herodotus.engine.oss.minio.domain.MinioBucket;
import cn.herodotus.engine.oss.minio.dto.logic.CreateBucketDto;
import cn.herodotus.engine.oss.minio.dto.request.bucket.MakeBucketRequest;
import cn.herodotus.engine.oss.minio.dto.request.bucket.RemoveBucketRequest;
import cn.herodotus.engine.oss.minio.service.BucketService;
import cn.herodotus.engine.rest.core.annotation.AccessLimited;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Description: Minio 对象存储 Bucket 管理接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/2 14:45
 */
@RestController
@RequestMapping("/manage/oss/minio/bucket")
@Tags({
        @Tag(name = "对象存储管理接口"),
        @Tag(name = "Minio 对象存储管理接口"),
        @Tag(name = "Minio 对象存储Bucket管理接口")
})
public class BucketController implements Controller {

    private final BucketService bucketService;

    @Autowired
    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @AccessLimited
    @Operation(summary = "获取全部Bucket接口", description = "获取全部Bucket接口")
    @GetMapping("/list")
    public Result<List<MinioBucket>> findAll() {
        List<MinioBucket> minioBucketRespons = bucketService.listBuckets();
        return result(minioBucketRespons);
    }

    @Idempotent
    @Operation(summary = "创建Bucket接口", description = "创建Bucket接口，该接口仅是创建，不包含是否已存在检查",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "是否成功", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "domain", required = true, description = "Make Bucket 请求实体", schema = @Schema(implementation = MakeBucketRequest.class))
    })
    @PostMapping
    public Result<String> make(@Validated @RequestBody MakeBucketRequest domain) {
        bucketService.makeBucket(domain.build());
        return result(true);
    }

    @Idempotent
    @Operation(summary = "创建Bucket接口", description = "创建Bucket接口，该接口包含Bucket是否已存在检查",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "是否成功", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "domain", required = true, description = "Make Bucket 请求实体", schema = @Schema(implementation = CreateBucketDto.class))
    })
    @PostMapping("/create")
    public Result<String> create(@Validated @RequestBody CreateBucketDto domain) {
        if (StringUtils.isNotBlank(domain.getRegion())) {
            bucketService.makeBucket(domain.getBucketName(), domain.getRegion());
        } else {
            bucketService.makeBucket(domain.getBucketName());
        }
        return result(true);
    }

    @Idempotent
    @Operation(summary = "删除Bucket接口", description = "根据Bucket 名称删除数据，可指定 Region",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "操作消息", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "domain", required = true, description = "Remote Bucket 请求实体", schema = @Schema(implementation = RemoveBucketRequest.class))
    })
    @DeleteMapping
    public Result<String> delete(@Validated @RequestBody RemoveBucketRequest domain) {
        bucketService.removeBucket(domain.build());
        return result(true);
    }
}
