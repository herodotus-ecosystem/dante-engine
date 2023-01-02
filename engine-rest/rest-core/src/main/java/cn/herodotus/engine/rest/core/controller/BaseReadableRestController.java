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

package cn.herodotus.engine.rest.core.controller;

import cn.herodotus.engine.assistant.core.definition.constants.BaseConstants;
import cn.herodotus.engine.assistant.core.definition.domain.AbstractEntity;
import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.rest.core.annotation.AccessLimited;
import cn.herodotus.engine.rest.core.definition.dto.Pager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>Description: 只读RestController </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/5 17:21
 */
@SecurityRequirement(name = BaseConstants.OPEN_API_SECURITY_SCHEME_BEARER_NAME)
public abstract class BaseReadableRestController<E extends AbstractEntity, ID extends Serializable> implements ReadableController<E, ID> {

    @AccessLimited
    @Operation(summary = "分页查询数据", description = "通过pageNumber和pageSize获取分页数据",
            responses = {@ApiResponse(description = "单位列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)))})
    @Parameters({
            @Parameter(name = "pager", required = true, in = ParameterIn.PATH, description = "分页Bo对象", schema = @Schema(implementation = Pager.class))
    })
    @GetMapping
    public Result<Map<String, Object>> findByPage(@Validated Pager pager) {
        if (ArrayUtils.isNotEmpty(pager.getProperties())) {
            Sort.Direction direction = Sort.Direction.valueOf(pager.getDirection());
            return ReadableController.super.findByPage(pager.getPageNumber(), pager.getPageSize(), direction, pager.getProperties());
        } else {
            return ReadableController.super.findByPage(pager.getPageNumber(), pager.getPageSize());
        }
    }
}
