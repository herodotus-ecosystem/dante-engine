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
import cn.herodotus.engine.oss.minio.response.DeleteErrorResponse;
import cn.herodotus.engine.oss.minio.response.ItemResponse;
import cn.herodotus.engine.oss.minio.request.object.ListObjectsRequest;
import cn.herodotus.engine.oss.minio.request.object.RemoveObjectRequest;
import cn.herodotus.engine.oss.minio.request.object.RemoveObjectsRequest;
import cn.herodotus.engine.oss.minio.service.ObjectService;
import cn.herodotus.engine.rest.core.annotation.AccessLimited;
import cn.herodotus.engine.rest.core.annotation.Idempotent;
import cn.herodotus.engine.rest.core.controller.Controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Description: 对象存储对象管理接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/4/16 21:29
 */
@RestController
@RequestMapping("/oss/minio/object")
@Tags({
        @Tag(name = "对象存储管理接口"),
        @Tag(name = "Minio 对象存储管理接口"),
        @Tag(name = "Minio 对象存储Object管理接口")
})
public class ObjectController implements Controller {

    private final ObjectService objectService;

    public ObjectController(ObjectService objectService) {
        this.objectService = objectService;
    }

    @AccessLimited
    @Operation(summary = "获取全部Bucket接口", description = "获取全部Bucket接口")
    @Parameters({
            @Parameter(name = "request", required = true, in = ParameterIn.PATH, description = "对象列表请求参数对象", schema = @Schema(implementation = ListObjectsRequest.class))
    })
    @GetMapping("/list")
    public Result<List<ItemResponse>> list(@Validated ListObjectsRequest request) {
        List<ItemResponse> items = objectService.listObjects(request.build());
        return result(items);
    }

    @Idempotent
    @Operation(summary = "删除一个对象", description = "根据传入的Object名称删除对应的对象",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "已保存数据", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "request", required = true, description = "删除对象请求参数实体", schema = @Schema(implementation = RemoveObjectRequest.class))
    })
    @DeleteMapping
    public Result<Boolean> removeObject(@Validated @RequestBody RemoveObjectRequest request) {
        objectService.removeObject(request.build());
        return result(true);
    }

    @Idempotent
    @Operation(summary = "删除多个对象", description = "根据传入的Object名称删除对应的对象",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "已保存数据", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "request", required = true, description = "删除对象请求参数实体", schema = @Schema(implementation = RemoveObjectsRequest.class))
    })
    @DeleteMapping("/multi")
    public Result<List<DeleteErrorResponse>> removeObjects(@Validated @RequestBody RemoveObjectsRequest request) {
        List<DeleteErrorResponse> items = objectService.removeObjects(request.build());
        return result(items);
    }
}
