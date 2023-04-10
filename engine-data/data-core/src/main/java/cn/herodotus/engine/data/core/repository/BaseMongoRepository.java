/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.engine.data.core.repository;

import cn.herodotus.engine.data.core.entity.BaseMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.Serializable;

/**
 * <p>Description: Spring Data Mongo 基础 Repository </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/2/26 17:07
 */
public interface BaseMongoRepository<E extends BaseMongoEntity, ID extends Serializable> extends MongoRepository<E, ID> {
}
