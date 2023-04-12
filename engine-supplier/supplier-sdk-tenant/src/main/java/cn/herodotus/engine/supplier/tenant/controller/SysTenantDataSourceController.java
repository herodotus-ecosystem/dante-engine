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

package cn.herodotus.engine.supplier.tenant.controller;

import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.data.core.service.WriteableService;
import cn.herodotus.engine.data.tenant.entity.SysTenantDataSource;
import cn.herodotus.engine.rest.core.annotation.AccessLimited;
import cn.herodotus.engine.rest.core.controller.BaseWriteableRestController;
import cn.herodotus.engine.supplier.tenant.service.SysTenantDataSourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: 多租户数据源接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/3/29 21:23
 */
@RestController
@RequestMapping("/security/tenant/datasource")
@Tags({
        @Tag(name = "系统安全管理接口"),
        @Tag(name = "多租户数据源接口")
})
public class SysTenantDataSourceController extends BaseWriteableRestController<SysTenantDataSource, String> {

    private final SysTenantDataSourceService sysTenantDataSourceService;

    public SysTenantDataSourceController(SysTenantDataSourceService sysTenantDataSourceService) {
        this.sysTenantDataSourceService = sysTenantDataSourceService;
    }

    @Override
    public WriteableService<SysTenantDataSource, String> getWriteableService() {
        return sysTenantDataSourceService;
    }

    @AccessLimited
    @Operation(summary = "根据租户代码查询数据源", description = "根据输入的租户代码，查询对应的数据源",
            responses = {
                    @ApiResponse(description = "查询到的数据源", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SysTenantDataSource.class))),
                    @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
                    @ApiResponse(responseCode = "500", description = "查询失败")
            }
    )
    @Parameters({
            @Parameter(name = "tenantId", in = ParameterIn.PATH, required = true, description = "租户代码"),
    })
    @GetMapping("/{tenantId}")
    public Result<SysTenantDataSource> findByRoleCode(@PathVariable("tenantId") String tenantId) {
        SysTenantDataSource sysTenantDataSource = sysTenantDataSourceService.findByTenantId(tenantId);
        return result(sysTenantDataSource);
    }
}
