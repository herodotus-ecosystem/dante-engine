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

import cn.herodotus.engine.data.core.service.WriteableService;
import cn.herodotus.engine.rest.core.controller.BaseWriteableRestController;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysInterface;
import cn.herodotus.engine.supplier.upms.logic.service.security.SysInterfaceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: 系统应用程序接口 Controller </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/3/9 5:05
 */
@RestController
@RequestMapping("/security/interface")
@Tags({
        @Tag(name = "用户安全管理接口"),
        @Tag(name = "系统接口管理接口")
})
public class SysInterfaceController extends BaseWriteableRestController<SysInterface, String> {

    private final SysInterfaceService sysInterfaceService;

    public SysInterfaceController(SysInterfaceService sysInterfaceService) {
        this.sysInterfaceService = sysInterfaceService;
    }

    @Override
    public WriteableService<SysInterface, String> getWriteableService() {
        return sysInterfaceService;
    }
}
