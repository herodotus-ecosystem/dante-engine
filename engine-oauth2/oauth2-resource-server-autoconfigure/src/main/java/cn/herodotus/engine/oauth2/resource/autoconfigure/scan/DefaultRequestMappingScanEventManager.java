/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 *    Author: ZHENGGENGWEI<码匠君>
 *    Contact: <herodotus@aliyun.com>
 *    Blog and source code availability: <https://gitee.com/herodotus/herodotus-cloud>
 */

package cn.herodotus.engine.oauth2.resource.autoconfigure.scan;

import cn.herodotus.engine.assistant.core.context.ServiceContextHolder;
import cn.herodotus.engine.message.core.logic.strategy.RequestMappingScanEventManager;
import cn.herodotus.engine.message.core.logic.domain.RequestMapping;
import cn.herodotus.engine.message.core.logic.event.RequestMappingGatherEvent;
import cn.herodotus.engine.oauth2.authorization.processor.SecurityMetadataSourceAnalyzer;
import cn.herodotus.engine.oauth2.resource.autoconfigure.bus.RemoteRequestMappingGatherEvent;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * <p>Description: 自定义 RequestMappingScanManager</p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/17 0:08
 */
@Component
public class DefaultRequestMappingScanEventManager implements RequestMappingScanEventManager {

    private final SecurityMetadataSourceAnalyzer securityMetadataSourceAnalyzer;

    public DefaultRequestMappingScanEventManager(SecurityMetadataSourceAnalyzer securityMetadataSourceAnalyzer) {
        this.securityMetadataSourceAnalyzer = securityMetadataSourceAnalyzer;
    }

    @Override
    public Class<? extends Annotation> getScanAnnotationClass() {
        return EnableWebSecurity.class;
    }

    @Override
    public String getDestinationServiceName() {
        return ServiceContextHolder.getInstance().getUpmsServiceName();
    }

    @Override
    public void postLocalStorage(List<RequestMapping> requestMappings) {
        securityMetadataSourceAnalyzer.processRequestMatchers();
    }

    @Override
    public void postLocalProcess(List<RequestMapping> data) {
        publishEvent(new RequestMappingGatherEvent(data));
    }

    @Override
    public void postRemoteProcess(String data, String originService, String destinationService) {
        publishEvent(new RemoteRequestMappingGatherEvent(data, originService, destinationService));
    }
}
