/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.engine.supplier.upms.rest.controller.security;

import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.data.core.service.WriteableService;
import cn.herodotus.engine.rest.core.annotation.Crypto;
import cn.herodotus.engine.rest.core.controller.BaseWriteableRestController;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysUser;
import cn.herodotus.engine.supplier.upms.logic.service.security.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>Description: 系统用户接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/25 10:55
 */
@RestController
@RequestMapping("/security/user")
@Tags({
        @Tag(name = "用户安全管理接口"),
        @Tag(name = "系统用户管理接口")
})
public class SysUserController extends BaseWriteableRestController<SysUser, String> {

    private final SysUserService sysUserService;

    @Autowired
    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    public WriteableService<SysUser, String> getWriteableService() {
        return this.sysUserService;
    }

    /**
     * 给用户分配角色
     *
     * @param userId 用户Id
     * @param roles  角色Id数组
     * @return {@link Result}
     */
    @Operation(summary = "给用户分配角色", description = "给用户分配角色",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/x-www-form-urlencoded")),
            responses = {@ApiResponse(description = "已保存数据", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "userId", required = true, description = "userId"),
            @Parameter(name = "roles[]", required = true, description = "角色对象组成的数组")
    })
    @PutMapping
    public Result<SysUser> assign(@RequestParam(name = "userId") String userId, @RequestParam(name = "roles[]") String[] roles) {
        SysUser sysUser = sysUserService.assign(userId, roles);
        return result(sysUser);
    }

    @Operation(summary = "修改密码", description = "修改用户使用密码，默认使用加密请求传输",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/x-www-form-urlencoded")),
            responses = {@ApiResponse(description = "修改密码后的用户信息", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "userId", required = true, description = "userId"),
            @Parameter(name = "password", required = true, description = "角色对象组成的数组")
    })
    @Crypto(responseEncrypt = false)
    @PutMapping("/change-password")
    public Result<SysUser> changePassword(@RequestParam(name = "userId") String userId, @RequestParam(name = "password") String password) {
        SysUser sysUser = sysUserService.changePassword(userId, password);
        return result(sysUser);
    }

    @Operation(summary = "根据用户名查询系统用户", description = "通过username查询系统用户数据",
            responses = {
                    @ApiResponse(description = "查询到的用户", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SysUser.class))),
                    @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
                    @ApiResponse(responseCode = "500", description = "查询失败")
            }
    )
    @GetMapping("/sign-in/{userName}")
    public Result<SysUser> findByUserName(@PathVariable("userName") String userName) {
        SysUser sysUser = sysUserService.findByUserName(userName);
        return result(sysUser);
    }
}
