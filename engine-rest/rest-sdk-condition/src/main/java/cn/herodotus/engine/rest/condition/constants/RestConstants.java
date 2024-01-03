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

package cn.herodotus.engine.rest.condition.constants;

import cn.herodotus.engine.assistant.definition.constants.BaseConstants;

/**
 * <p>Description: Rest 模块常量 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/19 23:13
 */
public interface RestConstants extends BaseConstants {


    String PROPERTY_OPENFEIGN_HTTP2CLIENT = PROPERTY_SPRING_CLOUD_OPENFEIGN + ".http2client";
    String PROPERTY_OPENFEIGN_HTTPCLIENT5 = PROPERTY_SPRING_CLOUD_OPENFEIGN + ".httpclient";
    String PROPERTY_REST_SCAN = PROPERTY_PREFIX_REST + ".scan";
    String ITEM_PLATFORM_DATA_ACCESS_STRATEGY = PROPERTY_PREFIX_PLATFORM + ".data-access-strategy";
    String ITEM_PLATFORM_ARCHITECTURE = PROPERTY_PREFIX_PLATFORM + ".architecture";

    String ITEM_SCAN_ENABLED = PROPERTY_REST_SCAN + PROPERTY_ENABLED;
    String ITEM_OPENFEIGN_HTTP2CLIENT_ENABLED = PROPERTY_OPENFEIGN_HTTP2CLIENT + PROPERTY_ENABLED;
    String ITEM_OPENFEIGN_HTTPCLIENT5_ENABLED = PROPERTY_OPENFEIGN_HTTPCLIENT5 + ".hc5" + PROPERTY_ENABLED;
    String ITEM_PROTECT_CRYPTO_STRATEGY = PROPERTY_PREFIX_CRYPTO + ".crypto-strategy";

    String CACHE_NAME_TOKEN_IDEMPOTENT = CACHE_TOKEN_BASE_PREFIX + "idempotent:";
    String CACHE_NAME_TOKEN_ACCESS_LIMITED = CACHE_TOKEN_BASE_PREFIX + "access_limited:";
    String CACHE_NAME_TOKEN_SECURE_KEY = CACHE_TOKEN_BASE_PREFIX + "secure_key:";
}
