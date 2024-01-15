/*
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君) (herodotus@aliyun.com & www.herodotus.cn)
 *
 * Dante Engine licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.gnu.org/licenses/lgpl-3.0.html>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.herodotus.engine.data.core.service;

import cn.herodotus.engine.assistant.definition.domain.base.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Description: 可读、可写的Service基础接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/7 16:47
 */
public interface WriteableService<E extends Entity, ID extends Serializable> extends ReadableService<E, ID> {

    /**
     * 删除数据
     *
     * @param entity 数据对应实体
     */
    default void delete(E entity) {
        getRepository().delete(entity);
    }

    /**
     * 批量全部删除
     */
    default void deleteAllInBatch() {
        getRepository().deleteAllInBatch();
    }

    /**
     * 删除指定多个数据
     *
     * @param entities 数据对应实体集合
     */
    default void deleteAll(Iterable<E> entities) {
        getRepository().deleteAll(entities);
    }

    /**
     * 删除全部数据
     */
    default void deleteAll() {
        getRepository().deleteAll();
    }

    /**
     * 根据ID删除数据
     *
     * @param id 数据对应ID
     */
    default void deleteById(ID id) {
        getRepository().deleteById(id);
    }

    /**
     * 保存或更新数据
     *
     * @param domain 数据对应实体
     * @return 已保存数据
     */
    default E save(E domain) {
        return getRepository().save(domain);
    }

    /**
     * 批量保存或更新数据
     *
     * @param entities 实体集合
     * @return 已经保存的实体集合
     */
    default <S extends E> List<S> saveAll(Iterable<S> entities) {
        return getRepository().saveAll(entities);
    }

    /**
     * 保存或者更新
     *
     * @param entity 实体
     * @return 保存后实体
     */
    default E saveAndFlush(E entity) {
        return getRepository().saveAndFlush(entity);
    }

    /**
     * 批量保存或者更新
     *
     * @param entities 实体列表
     * @return 保存或更新后的实体
     */
    default List<E> saveAllAndFlush(List<E> entities) {
        return getRepository().saveAllAndFlush(entities);
    }

    /**
     * 刷新实体状态
     */
    default void flush() {
        getRepository().flush();
    }
}
