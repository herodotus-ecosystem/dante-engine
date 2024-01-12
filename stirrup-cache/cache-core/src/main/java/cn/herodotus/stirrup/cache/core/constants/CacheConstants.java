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

package cn.herodotus.stirrup.cache.core.constants;

import cn.herodotus.stirrup.kernel.definition.constants.BaseConstants;

/**
 * <p>Description: Cache Property值常量 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/13 21:22
 */
public interface CacheConstants extends BaseConstants {

    String PROPERTY_REDIS_REDISSON = PROPERTY_SPRING_DATA + ".redisson";
    String ITEM_REDISSON_ENABLED = PROPERTY_REDIS_REDISSON + PROPERTY_ENABLED;
}
