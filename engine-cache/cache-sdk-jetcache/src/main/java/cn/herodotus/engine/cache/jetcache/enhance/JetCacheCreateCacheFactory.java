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

package cn.herodotus.engine.cache.jetcache.enhance;

import cn.herodotus.engine.cache.core.enums.CacheMethod;
import cn.herodotus.engine.cache.core.properties.CacheProperties;
import cn.herodotus.engine.cache.core.properties.CacheSetting;
import com.alibaba.fastjson2.JSON;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.template.QuickConfig;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.crypto.SecureUtil;

import java.time.Duration;

/**
 * <p>Description: JetCache 手动创建Cache 工厂 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/23 10:49
 */
public class JetCacheCreateCacheFactory {

    private final CacheManager cacheManager;
    private final CacheProperties cacheProperties;

    public JetCacheCreateCacheFactory(CacheManager cacheManager, CacheProperties cacheProperties) {
        this.cacheManager = cacheManager;
        this.cacheProperties = cacheProperties;
    }

    public <K, V> Cache<K, V> create(String name, CacheSetting cacheSetting) {
        return create(name, false, cacheSetting);
    }

    public <K, V> Cache<K, V> create(String name, Boolean cacheNullValue, CacheSetting cacheSetting) {
        return create(cacheSetting.getArea(), name, getCacheType(cacheSetting.getMethod()), cacheSetting.getExpire(), cacheNullValue, cacheSetting.getSync(), cacheSetting.getLocalExpire(), cacheSetting.getLocalLimit(), false, cacheSetting.getPenetrationProtect(), cacheSetting.getPenetrationProtectTimeout());
    }

    public <K, V> Cache<K, V> create(String name) {
        return create(name, cacheProperties.getExpire());
    }

    public <K, V> Cache<K, V> create(String name, Duration expire) {
        return create(name, expire, cacheProperties.getAllowNullValues());
    }

    public <K, V> Cache<K, V> create(String name, Duration expire, Boolean cacheNullValue) {
        return create(name, expire, cacheNullValue, cacheProperties.getSync());
    }

    public <K, V> Cache<K, V> create(String name, Duration expire, Boolean cacheNullValue, Boolean syncLocal) {
        return create(name, getCacheType(cacheProperties.getMethod()), expire, cacheNullValue, syncLocal);
    }

    public <K, V> Cache<K, V> create(String name, CacheType cacheType, Duration expire, Boolean cacheNullValue, Boolean syncLocal) {
        return create(null, name, cacheType, expire, cacheNullValue, syncLocal);
    }

    public <K, V> Cache<K, V> create(String area, String name, CacheType cacheType, Duration expire, Boolean cacheNullValue, Boolean syncLocal) {
        return create(area, name, cacheType, expire, cacheNullValue, syncLocal, cacheProperties.getLocalExpire());
    }

    public <K, V> Cache<K, V> create(String area, String name, CacheType cacheType, Duration expire, Boolean cacheNullValue, Boolean syncLocal, Duration localExpire) {
        return create(area, name, cacheType, expire, cacheNullValue, syncLocal, localExpire, cacheProperties.getLocalLimit());
    }

    public <K, V> Cache<K, V> create(String area, String name, CacheType cacheType, Duration expire, Boolean cacheNullValue, Boolean syncLocal, Duration localExpire, Integer localLimit) {
        return create(area, name, cacheType, expire, cacheNullValue, syncLocal, localExpire, localLimit, false);
    }

    public <K, V> Cache<K, V> create(String area, String name, CacheType cacheType, Duration expire, Boolean cacheNullValue, Boolean syncLocal, Duration localExpire, Integer localLimit, Boolean useAreaInPrefix) {
        return create(area, name, cacheType, expire, cacheNullValue, syncLocal, localExpire, localLimit, useAreaInPrefix, cacheProperties.getPenetrationProtect(), cacheProperties.getPenetrationProtectTimeout());
    }

    public <K, V> Cache<K, V> create(String area, String name, CacheType cacheType, Duration expire, Boolean cacheNullValue, Boolean syncLocal, Duration localExpire, Integer localLimit, Boolean useAreaInPrefix, Boolean penetrationProtect, Duration penetrationProtectTimeout) {
        QuickConfig.Builder builder = StringUtils.isEmpty(area) ? QuickConfig.newBuilder(name) : QuickConfig.newBuilder(area, name);
        builder.cacheType(cacheType);
        builder.expire(expire);
        if (cacheType == CacheType.BOTH) {
            builder.syncLocal(syncLocal);
        }
        builder.localExpire(localExpire);
        builder.localLimit(localLimit);
        builder.cacheNullValue(cacheNullValue);
        builder.useAreaInPrefix(useAreaInPrefix);
        if (ObjectUtils.isNotEmpty(penetrationProtect)) {
            builder.penetrationProtect(penetrationProtect);
            if (BooleanUtils.isTrue(penetrationProtect) && ObjectUtils.isNotEmpty(penetrationProtectTimeout)) {
                builder.penetrationProtectTimeout(penetrationProtectTimeout);
            }
        }

        builder.keyConvertor(key -> {
            if (key instanceof String) {
                return key;
            } else {
                return SecureUtil.md5(JSON.toJSONString(key));
            }
        });

        QuickConfig quickConfig = builder.build();
        return create(quickConfig);
    }


    @SuppressWarnings("unchecked")
    private <K, V> Cache<K, V> create(QuickConfig quickConfig) {
        return cacheManager.getOrCreateCache(quickConfig);
    }

    private CacheType getCacheType(CacheMethod cacheMethod) {
        if (ObjectUtils.isNotEmpty(cacheMethod)) {
            return CacheType.valueOf(cacheMethod.name());
        } else {
            return CacheType.BOTH;
        }
    }
}
