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

package cn.herodotus.stirrup.data.kernel.service;

import cn.herodotus.stirrup.kernel.definition.constants.SymbolConstants;
import cn.herodotus.stirrup.kernel.definition.domain.base.Entity;

import java.io.Serializable;

/**
 * <p>Description: 通用核心 Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/14 14:32
 */
public abstract class BaseService<E extends Entity, ID extends Serializable> implements WriteableService<E, ID> {

    protected String like(String property) {
        return SymbolConstants.PERCENT + property + SymbolConstants.PERCENT;
    }
}
