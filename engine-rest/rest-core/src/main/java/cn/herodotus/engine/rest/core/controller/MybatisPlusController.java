/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 *    Author: ZHENGGENGWEI<码匠君>
 *    Contact: <herodotus@aliyun.com>
 *    Blog and source code availability: <https://gitee.com/herodotus/herodotus-cloud>
 */

package cn.herodotus.engine.rest.core.controller;

import cn.herodotus.engine.assistant.core.definition.domain.Entity;
import cn.herodotus.engine.assistant.core.domain.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: Mybatis Plus 基础 Controller </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/1/21 16:17
 */
public interface MybatisPlusController extends Controller {

    default <E extends Entity> Result<Map<String, Object>> result(IPage<E> pages) {
        if (ObjectUtils.isNotEmpty(pages)) {
            if (CollectionUtils.isNotEmpty(pages.getRecords())) {
                return Result.success("查询数据成功！", getPageInfoMap(pages));
            } else {
                return Result.empty("未查询到数据！");
            }
        } else {
            return Result.failure("查询数据失败！");
        }
    }

    default <E extends Entity> Map<String, Object> getPageInfoMap(IPage<E> page) {
        Map<String, Object> result = new HashMap<>(8);
        result.put("content", page.getRecords());
        result.put("totalPages", (int) page.getPages());
        result.put("totalElements", (int) page.getTotal());
        return result;
    }
}
