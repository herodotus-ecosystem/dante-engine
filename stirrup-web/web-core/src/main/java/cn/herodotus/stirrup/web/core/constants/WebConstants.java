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

package cn.herodotus.stirrup.web.core.constants;

import cn.herodotus.stirrup.core.definition.constants.BaseConstants;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/1/24 17:19
 */
public interface WebConstants extends BaseConstants {

    String PROPERTY_REST_SCAN = PROPERTY_PREFIX_REST + ".scan";

    String ITEM_PLATFORM_DATA_ACCESS_STRATEGY = PROPERTY_PREFIX_PLATFORM + ".data-access-strategy";
    String ITEM_PLATFORM_ARCHITECTURE = PROPERTY_PREFIX_PLATFORM + ".architecture";
    String ITEM_SCAN_ENABLED = PROPERTY_REST_SCAN + PROPERTY_ENABLED;
}
