/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2019-2021 Zhenggengwei<码匠君>, herodotus@aliyun.com
 *
 * This file is part of Herodotus Cloud.
 *
 * Herodotus Cloud is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * Herodotus Cloud is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with with Herodotus Cloud;
 * if no see <https://gitee.com/herodotus/herodotus-cloud>
 *
 * - Author: Zhenggengwei<码匠君>
 * - Contact: herodotus@aliyun.com
 * - License: GNU Lesser General Public License (LGPL)
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.engine.security.authorize.controller;

import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.captcha.core.dto.Captcha;
import cn.herodotus.engine.captcha.core.dto.Verification;
import cn.herodotus.engine.captcha.core.processor.CaptchaRendererFactory;
import cn.herodotus.engine.rest.core.annotation.AccessLimited;
import cn.herodotus.engine.rest.core.annotation.Crypto;
import cn.herodotus.engine.rest.core.annotation.Idempotent;
import cn.herodotus.engine.rest.core.controller.Controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * <p>Description: 验证码Controller </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/12 10:44
 */
@RestController
@RequestMapping("/open/captcha")
@Validated
public class CaptchaController implements Controller {

    @Autowired
    private CaptchaRendererFactory captchaRendererFactory;

    @AccessLimited
    @Operation(summary = "获取验证码", description = "通过传递身份信息（类似于Session标识）",
            responses = {@ApiResponse(description = "验证码图形信息", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)))})
    @Parameters({
            @Parameter(name = "identity", required = true, in = ParameterIn.PATH, description = "身份信息"),
            @Parameter(name = "category", required = true, in = ParameterIn.PATH, description = "验证码类型")
    })
    @GetMapping
    public Result<Captcha> create(@NotBlank(message = "身份信息不能为空") String identity, @NotBlank(message = "验证码类型不能为空")String category) {
        Captcha captcha = captchaRendererFactory.getCaptcha(identity, category);
        if (ObjectUtils.isNotEmpty(captcha)) {
            return Result.success("验证码创建成功", captcha);
        } else {
            return Result.failure("验证码创建失败");
        }
    }

    @Idempotent
    @Crypto(responseEncrypt = false)
    @Operation(summary = "验证码验证", description = "验证验证码返回数据是否正确。使用加密信息",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "验证结果", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "jigsawVerification", required = true, description = "验证码验证参数", schema = @Schema(implementation = Verification.class))
    })
    @PostMapping
    public Result<Boolean> check(@Valid @RequestBody Verification verification) {
        boolean isSuccess = captchaRendererFactory.verify(verification);
        if (isSuccess) {
            return Result.success("验证码创建成功", true);
        }
        return Result.failure("验证失败", true);
    }
}