/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.rest.core.controller;

import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.data.core.entity.BaseMongoEntity;
import cn.herodotus.engine.data.core.service.MongoService;
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

    MongoService<E, ID> getMongoService();

    /**
     * 查询分页数据
     *
     * @param pageNumber 当前页码，起始页码 0
     * @param pageSize   每页显示数据条数
     * @return {@link Result}
     */
    default Result<Map<String, Object>> findByPage(Integer pageNumber, Integer pageSize) {
        Page<E> pages = getMongoService().findByPage(pageNumber, pageSize);
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
        Page<E> pages = getMongoService().findByPage(pageNumber, pageSize, direction, properties);
        return result(pages);
    }

    default Result<List<E>> findAll() {
        List<E> domains = getMongoService().findAll();
        return result(domains);
    }

    default Result<E> findById(ID id) {
        E domain = getMongoService().findById(id);
        return result(domain);
    }

    default Result<E> saveOrUpdate(E domain) {
        E savedDomain = getMongoService().save(domain);
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
        getMongoService().deleteById(id);
        return result;
    }
}
