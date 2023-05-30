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
import cn.herodotus.engine.oss.minio.domain.response.RetentionResponse;
import cn.herodotus.engine.oss.minio.request.object.GetObjectRetentionRequest;
import cn.herodotus.engine.oss.minio.request.object.SetObjectRetentionRequest;
import cn.herodotus.engine.oss.minio.service.ObjectRetentionService;
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

/**
 * <p>Description: 对象存储对象管理接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/4/16 21:29
 */
@RestController
@RequestMapping("/oss/minio/object/retention")
@Tags({
        @Tag(name = "对象存储管理接口"),
        @Tag(name = "Minio 对象存储管理接口"),
        @Tag(name = "Object Retention管理接口")
})
public class ObjectRetentionController implements Controller {

    private final ObjectRetentionService objectRetentionService;

    public ObjectRetentionController(ObjectRetentionService objectRetentionService) {
        this.objectRetentionService = objectRetentionService;
    }

    @AccessLimited
    @Operation(summary = "获取对象的保留配置", description = "获取对象的保留配置")
    @Parameters({
            @Parameter(name = "request", required = true, in = ParameterIn.PATH, description = "获取对象保留配置请求参数实体", schema = @Schema(implementation = GetObjectRetentionRequest.class))
    })
    @GetMapping
    public Result<RetentionResponse> get(@Validated GetObjectRetentionRequest request) {
        RetentionResponse response = objectRetentionService.getObjectRetention(request.build());
        return result(response);
    }

    @Idempotent
    @Operation(summary = "设置对象的保留配置", description = "设置对象的保留配置",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "已保存数据", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "request", required = true, description = "设置对象保留配置请求参数实体", schema = @Schema(implementation = SetObjectRetentionRequest.class))
    })
    @PutMapping
    public Result<Boolean> set(@Validated @RequestBody SetObjectRetentionRequest request) {
        objectRetentionService.setObjectRetention(request.build());
        return result(true);
    }
}
