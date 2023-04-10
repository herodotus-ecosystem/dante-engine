/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.engine.data.core.service;

import cn.herodotus.engine.data.core.entity.BaseMongoEntity;
import cn.herodotus.engine.data.core.repository.BaseMongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Description: Spring Data Mongo 基础 Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/2/26 17:10
 */
public interface BaseMongoService<E extends BaseMongoEntity, ID extends Serializable> {

    /**
     * 获取对应的 Mongo Repository
     *
     * @return {@link BaseMongoRepository}
     */
    BaseMongoRepository<E, ID> getBaseMongoRepository();

    /**
     * 保存或更新数据
     *
     * @param domain 对应的实体
     * @return 保存后的实体
     */
    default E save(E domain) {
        return getBaseMongoRepository().save(domain);
    }

    /**
     * 查询全部
     *
     * @return 全部数据列表
     */
    default List<E> findAll() {
        return getBaseMongoRepository().findAll();
    }

    /**
     * 分页查询
     *
     * @param pageable {@link Pageable}
     * @return 分页数据
     */
    default Page<E> findByPage(Pageable pageable) {
        return getBaseMongoRepository().findAll(pageable);
    }

    /**
     * 查询分页数据
     *
     * @param pageNumber 当前页码, 起始页码 0
     * @param pageSize   每页显示的数据条数
     * @return 分页数据
     */
    default Page<E> findByPage(int pageNumber, int pageSize) {
        return findByPage(PageRequest.of(pageNumber, pageSize));
    }

    /**
     * 查询分页数据
     *
     * @param pageNumber 当前页码, 起始页码 0
     * @param pageSize   每页显示的数据条数
     * @param sort       {@link Sort}
     * @return 分页数据
     */
    default Page<E> findByPage(int pageNumber, int pageSize, Sort sort) {
        return findByPage(PageRequest.of(pageNumber, pageSize, sort));
    }

    /**
     * 查询分页数据
     *
     * @param pageNumber 当前页码, 起始页码 0
     * @param pageSize   每页显示的数据条数
     * @param direction  {@link Sort.Direction}
     * @param properties 排序的属性名称, 可以多个
     * @return 分页数据
     */
    default Page<E> findByPage(int pageNumber, int pageSize, Sort.Direction direction, String... properties) {
        return findByPage(PageRequest.of(pageNumber, pageSize, direction, properties));
    }

    /**
     * 排序查询全部
     *
     * @param sort {@link Sort}
     * @return 全部数据列表
     */
    default List<E> findAll(Sort sort) {
        return getBaseMongoRepository().findAll(sort);
    }

    /**
     * 根据 ID 查询
     *
     * @param id ID
     * @return 数据对象
     */
    default E findById(ID id) {
        return getBaseMongoRepository().findById(id).orElse(null);
    }

    /**
     * 根据 ID 删除
     *
     * @param id ID
     */
    default void deleteById(ID id) {
        getBaseMongoRepository().deleteById(id);
    }

    /**
     * 删除实体对应的数据
     *
     * @param domain 数据对象实体
     */
    default void delete(E domain) {
        getBaseMongoRepository().delete(domain);
    }
}
