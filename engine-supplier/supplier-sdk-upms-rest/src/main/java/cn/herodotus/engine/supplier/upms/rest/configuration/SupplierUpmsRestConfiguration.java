/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.engine.supplier.upms.rest.configuration;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * <p>Description: UpmsRest配置类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/1/5 11:58
 */
@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = {
        "cn.herodotus.engine.supplier.upms.rest.controller.hr",
        "cn.herodotus.engine.supplier.upms.rest.controller.security",
        "cn.herodotus.engine.supplier.upms.rest.controller.assistant",
        "cn.herodotus.engine.supplier.upms.rest.controller.social",
})
public class SupplierUpmsRestConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SupplierUpmsRestConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- SDK [Supplier Upms Rest] Auto Configure.");
    }


}
