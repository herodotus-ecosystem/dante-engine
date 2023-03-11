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
import cn.herodotus.engine.rest.core.annotation.AccessLimited;
import cn.herodotus.engine.rest.core.controller.BaseWriteableRestController;
import cn.herodotus.engine.rest.core.definition.dto.Sorter;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysPermission;
import cn.herodotus.engine.supplier.upms.logic.service.security.SysPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>Description: SysPermissionController </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/10 13:21
 */
@RestController
@RequestMapping("/security/permission")
@Tags({
        @Tag(name = "用户安全管理接口"),
        @Tag(name = "系统权限管理接口")
})
public class SysPermissionController extends BaseWriteableRestController<SysPermission, String> {

    private final SysPermissionService sysPermissionService;

    @Autowired
    public SysPermissionController(SysPermissionService sysPermissionService) {
        this.sysPermissionService = sysPermissionService;
    }

    @Override
    public WriteableService<SysPermission, String> getWriteableService() {
        return this.sysPermissionService;
    }

    @AccessLimited
    @Operation(summary = "获取全部权限", description = "获取全部权限",
            responses = {@ApiResponse(description = "单位列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class)))})
    @Parameters({
            @Parameter(name = "sorter", required = true, in = ParameterIn.PATH, description = "排序对象", schema = @Schema(implementation = Sorter.class))
    })
    @GetMapping("/list")
    public Result<List<SysPermission>> findAll(@Validated Sorter sorter) {
        Sort.Direction direction = Sort.Direction.valueOf(sorter.getDirection());
        List<SysPermission> sysAuthorities = sysPermissionService.findAll(Sort.by(direction, sorter.getProperties()));
        return result(sysAuthorities);
    }
}
