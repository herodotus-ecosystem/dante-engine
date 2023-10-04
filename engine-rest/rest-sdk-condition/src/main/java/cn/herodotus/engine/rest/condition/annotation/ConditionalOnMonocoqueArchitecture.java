/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2019-2022 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.engine.rest.condition.annotation;

import cn.herodotus.engine.rest.condition.definition.MonocoqueArchitectureCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * <p>Description: 单体架构模式条件注解 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/9 10:42
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(MonocoqueArchitectureCondition.class)
public @interface ConditionalOnMonocoqueArchitecture {
}
