/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2019-2022 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.engine.rest.condition.definition;

import cn.herodotus.engine.assistant.core.enums.Architecture;
import cn.herodotus.engine.rest.condition.constants.RestPropertyFinder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * <p>Description: 单体架构条件 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/9 10:47
 */
public class MonocoqueArchitectureCondition implements Condition {

    private static final Logger log = LoggerFactory.getLogger(MonocoqueArchitectureCondition.class);

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String property = RestPropertyFinder.getArchitecture(conditionContext.getEnvironment());
        boolean result = StringUtils.isNotBlank(property) && StringUtils.equalsIgnoreCase(property, Architecture.MONOCOQUE.name());
        log.debug("[Herodotus] |- Condition [Monocoque Architecture] value is [{}]", result);
        return result;
    }
}
