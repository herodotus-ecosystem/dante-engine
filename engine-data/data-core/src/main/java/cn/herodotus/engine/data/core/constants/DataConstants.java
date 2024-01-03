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

package cn.herodotus.engine.data.core.constants;

import cn.herodotus.engine.assistant.definition.constants.BaseConstants;

/**
 * <p>Description: 数据常量 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/19 18:10
 */
public interface DataConstants extends BaseConstants {

    String ITEM_SPRING_SQL_INIT_PLATFORM = "spring.sql.init.platform";
    String PROPERTY_PREFIX_MULTI_TENANT = PROPERTY_PREFIX_DATA + ".multi-tenant";
    String ITEM_DATA_DATA_SOURCE = PROPERTY_PREFIX_DATA + ".data-source";
    String ITEM_MULTI_TENANT_APPROACH = PROPERTY_PREFIX_MULTI_TENANT + ".approach";

    String ANNOTATION_SQL_INIT_PLATFORM = ANNOTATION_PREFIX + ITEM_SPRING_SQL_INIT_PLATFORM + ANNOTATION_SUFFIX;

    String CORE_AREA_PREFIX = AREA_PREFIX + "core:";
    String REGION_SYS_TENANT_DATASOURCE = CORE_AREA_PREFIX + "sys:tenant:datasource";
}
