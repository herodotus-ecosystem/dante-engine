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

package cn.herodotus.engine.oauth2.management.controller;

import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.data.core.service.WriteableService;
import cn.herodotus.engine.oauth2.management.entity.OAuth2Compliance;
import cn.herodotus.engine.oauth2.management.service.OAuth2ComplianceService;
import cn.herodotus.engine.rest.core.controller.BaseWriteableRestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>Description: OAuth2ComplianceController </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/7 22:56
 */
@RestController
@RequestMapping("/authorize/compliance")
@Tags({
        @Tag(name = "OAuth2 认证服务接口"),
        @Tag(name = "OAuth2 应用安全合规接口"),
        @Tag(name = "OAuth2 审计管理接口")
})
public class OAuth2ComplianceController extends BaseWriteableRestController<OAuth2Compliance, String> {

    private final OAuth2ComplianceService complianceService;

    @Autowired
    public OAuth2ComplianceController(OAuth2ComplianceService complianceService) {
        this.complianceService = complianceService;
    }

    @Override
    public WriteableService<OAuth2Compliance, String> getWriteableService() {
        return complianceService;
    }

    @Operation(summary = "模糊条件查询合规信息", description = "根据动态输入的字段模糊查询合规信息",
            responses = {@ApiResponse(description = "人员分页列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)))})
    @Parameters({
            @Parameter(name = "pageNumber", required = true, description = "当前页码"),
            @Parameter(name = "pageSize", required = true, description = "每页显示数量"),
            @Parameter(name = "principalName", description = "用户账号"),
            @Parameter(name = "clientId", description = "客户端ID"),
            @Parameter(name = "ip", description = "IP地址"),
    })
    @GetMapping("/condition")
    public Result<Map<String, Object>> findByCondition(@NotBlank @RequestParam("pageNumber") Integer pageNumber,
                                                       @NotBlank @RequestParam("pageSize") Integer pageSize,
                                                       @RequestParam(value = "principalName", required = false) String principalName,
                                                       @RequestParam(value = "clientId", required = false) String clientId,
                                                       @RequestParam(value = "ip", required = false) String ip) {
        Page<OAuth2Compliance> pages = complianceService.findByCondition(pageNumber, pageSize, principalName, clientId, ip);
        return result(pages);
    }
}
