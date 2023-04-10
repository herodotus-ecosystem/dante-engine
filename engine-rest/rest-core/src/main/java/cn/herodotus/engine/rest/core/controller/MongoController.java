/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.engine.rest.core.controller;

import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.data.core.entity.BaseMongoEntity;
import cn.herodotus.engine.data.core.service.BaseMongoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: Spring Data Mongo 基础 controller </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/2/26 20:21
 */
public interface MongoController<E extends BaseMongoEntity, ID extends Serializable> extends Controller {

    BaseMongoService<E, ID> getBaseMongoService();

    /**
     * 查询分页数据
     *
     * @param pageNumber 当前页码，起始页码 0
     * @param pageSize   每页显示数据条数
     * @return {@link Result}
     */
    default Result<Map<String, Object>> findByPage(Integer pageNumber, Integer pageSize) {
        Page<E> pages = getBaseMongoService().findByPage(pageNumber, pageSize);
        return result(pages);
    }

    /**
     * 查询分页数据
     *
     * @param pageNumber 当前页码, 起始页码 0
     * @param pageSize   每页显示的数据条数
     * @param direction  {@link Sort.Direction}
     * @param properties 排序的属性名称
     * @return 分页数据
     */
    default Result<Map<String, Object>> findByPage(Integer pageNumber, Integer pageSize, Sort.Direction direction, String... properties) {
        Page<E> pages = getBaseMongoService().findByPage(pageNumber, pageSize, direction, properties);
        return result(pages);
    }

    default Result<List<E>> findAll() {
        List<E> domains = getBaseMongoService().findAll();
        return result(domains);
    }

    default Result<E> findById(ID id) {
        E domain = getBaseMongoService().findById(id);
        return result(domain);
    }

    default Result<E> saveOrUpdate(E domain) {
        E savedDomain = getBaseMongoService().save(domain);
        return result(savedDomain);
    }

    /**
     * 删除数据
     *
     * @param id 实体ID
     * @return 用Result包装的信息
     */
    default Result<String> delete(ID id) {
        Result<String> result = result(String.valueOf(id));
        getBaseMongoService().deleteById(id);
        return result;
    }
}
