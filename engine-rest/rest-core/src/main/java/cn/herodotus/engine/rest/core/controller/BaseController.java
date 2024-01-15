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

package cn.herodotus.engine.rest.core.controller;

import cn.herodotus.engine.assistant.definition.domain.base.AbstractEntity;
import cn.herodotus.engine.assistant.definition.domain.Result;
import cn.herodotus.engine.data.core.service.ReadableService;

import java.io.Serializable;

/**
 * <p> Description : 通用Controller </p>
 * <p>
 * 单独提取出一些公共方法，是为了解决某些支持feign的controller，requestMapping 不方便统一编写的问题。
 *
 * @author : gengwei.zheng
 * @date : 2020/4/30 11:00
 */
public abstract class BaseController<E extends AbstractEntity, ID extends Serializable> implements WriteableController<E, ID> {

    /**
     * 获取Service
     *
     * @return Service
     */
    @Override
    public ReadableService<E, ID> getReadableService() {
        return this.getWriteableService();
    }

    @Override
    public Result<E> saveOrUpdate(E domain) {
        E savedDomain = getWriteableService().saveAndFlush(domain);
        return result(savedDomain);
    }

    @Override
    public Result<String> delete(ID id) {
        Result<String> result = result(String.valueOf(id));
        getWriteableService().deleteById(id);
        return result;
    }
}
