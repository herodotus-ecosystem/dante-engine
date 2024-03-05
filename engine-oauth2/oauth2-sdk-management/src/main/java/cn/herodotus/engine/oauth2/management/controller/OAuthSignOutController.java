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
import cn.herodotus.engine.message.core.logic.event.AccountReleaseFromCacheEvent;
import cn.herodotus.engine.oauth2.management.service.OAuth2ComplianceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: OAuth2 扩展 接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/7 17:05
 */
@RestController
@RequestMapping("/oauth2")
@Tags({
        @Tag(name = "OAuth2 认证服务接口"),
        @Tag(name = "OAuth2 扩展接口")
})
public class OAuthSignOutController {

    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2ComplianceService complianceService;
    private final ApplicationContext applicationContext;

    public OAuthSignOutController(OAuth2AuthorizationService authorizationService, OAuth2ComplianceService complianceService, ApplicationContext applicationContext) {
        this.authorizationService = authorizationService;
        this.complianceService = complianceService;
        this.applicationContext = applicationContext;
    }

    @Operation(summary = "注销OAuth2应用", description = "根据接收到的AccessToken,删除后端存储的Token信息,起到注销效果",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/x-www-form-urlencoded")),
            responses = {@ApiResponse(description = "是否成功", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "accessToken", required = true, description = "Access Token"),
            @Parameter(name = "Authorization", in = ParameterIn.HEADER, required = true, description = "Basic Token"),
    })
    @PutMapping("/sign-out")
    public Result<String> signOut(@RequestParam(name = "accessToken") @NotBlank String accessToken, HttpServletRequest request) {
        OAuth2Authorization authorization = authorizationService.findByToken(accessToken, OAuth2TokenType.ACCESS_TOKEN);
        if (ObjectUtils.isNotEmpty(authorization)) {
            authorizationService.remove(authorization);
            complianceService.save(authorization.getPrincipalName(), authorization.getRegisteredClientId(), "退出系统", request);
            applicationContext.publishEvent(new AccountReleaseFromCacheEvent(authorization.getPrincipalName()));
        }
        return Result.success("注销成功");
    }
}
