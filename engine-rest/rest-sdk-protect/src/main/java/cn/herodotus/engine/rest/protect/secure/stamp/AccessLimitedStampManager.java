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

package cn.herodotus.engine.rest.protect.secure.stamp;

import cn.herodotus.engine.cache.jetcache.stamp.AbstractStampManager;
import cn.herodotus.engine.rest.condition.constants.RestConstants;
import cn.herodotus.engine.rest.condition.properties.SecureProperties;

/**
 * <p>Description: 防刷签章管理器 </p>
 * <p>
 * 这里使用Long类型作为值的存储类型，是为了解决该Cache 同时可以存储Duration相关的数据
 *
 * @author : gengwei.zheng
 * @date : 2021/8/25 21:43
 */
public class AccessLimitedStampManager extends AbstractStampManager<String, Long> {

    private final SecureProperties secureProperties;

    public AccessLimitedStampManager(SecureProperties secureProperties) {
        super(RestConstants.CACHE_NAME_TOKEN_ACCESS_LIMITED);
        this.secureProperties = secureProperties;
    }

    public SecureProperties getSecureProperties() {
        return secureProperties;
    }

    @Override
    public Long nextStamp(String key) {
        return 1L;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.setExpire(secureProperties.getAccessLimited().getExpire());
    }
}
