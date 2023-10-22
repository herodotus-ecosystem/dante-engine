/*
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君) (herodotus@aliyun.com & www.herodotus.cn)
 *
 * Dante Engine licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.gnu.org/licenses/lgpl.html>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
public interface MongoService<E extends BaseMongoEntity, ID extends Serializable> {

    /**
     * 获取对应的 Mongo Repository
     *
     * @return {@link BaseMongoRepository}
     */
    BaseMongoRepository<E, ID> getRepository();

    /**
     * 保存或更新数据
     *
     * @param domain 对应的实体
     * @return 保存后的实体
     */
    default E save(E domain) {
        return getRepository().save(domain);
    }

    /**
     * 查询全部
     *
     * @return 全部数据列表
     */
    default List<E> findAll() {
        return getRepository().findAll();
    }

    /**
     * 分页查询
     *
     * @param pageable {@link Pageable}
     * @return 分页数据
     */
    default Page<E> findByPage(Pageable pageable) {
        return getRepository().findAll(pageable);
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
        return getRepository().findAll(sort);
    }

    /**
     * 根据 ID 查询
     *
     * @param id ID
     * @return 数据对象
     */
    default E findById(ID id) {
        return getRepository().findById(id).orElse(null);
    }

    /**
     * 根据 ID 删除
     *
     * @param id ID
     */
    default void deleteById(ID id) {
        getRepository().deleteById(id);
    }

    /**
     * 删除实体对应的数据
     *
     * @param domain 数据对象实体
     */
    default void delete(E domain) {
        getRepository().delete(domain);
    }
}
