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
import org.dromara.hutool.core.data.id.IdUtil;

/**
 * <p>Description: 幂等Stamp管理 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/22 16:05
 */
public class IdempotentStampManager extends AbstractStampManager<String, String> {

    private final SecureProperties secureProperties;

    public IdempotentStampManager(SecureProperties secureProperties) {
        super(RestConstants.CACHE_NAME_TOKEN_IDEMPOTENT);
        this.secureProperties = secureProperties;
    }

    public SecureProperties getSecureProperties() {
        return secureProperties;
    }

    @Override
    public String nextStamp(String key) {
        return IdUtil.fastSimpleUUID();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.setExpire(secureProperties.getIdempotent().getExpire());
    }
}
