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
