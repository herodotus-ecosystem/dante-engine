/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.engine.supplier.upms.rest.controller.hr;

import cn.herodotus.engine.data.core.service.WriteableService;
import cn.herodotus.engine.rest.core.controller.BaseWriteableRestController;
import cn.herodotus.engine.supplier.upms.logic.entity.hr.SysOwnership;
import cn.herodotus.engine.supplier.upms.logic.service.hr.SysOwnershipService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: 人事归属Controller </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/15 16:36
 */
@RestController
@RequestMapping("/hr/ownership")
@Tag(name = "人事归属管理接口")
public class SysOwnershipController extends BaseWriteableRestController<SysOwnership, String> {

    private final SysOwnershipService sysOwnershipService;

    @Autowired
    public SysOwnershipController(SysOwnershipService sysOwnershipService) {
        this.sysOwnershipService = sysOwnershipService;
    }

    @Override
    public WriteableService<SysOwnership, String> getWriteableService() {
        return this.sysOwnershipService;
    }
}
