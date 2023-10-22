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

package cn.herodotus.engine.cache.jetcache.enhance;

import cn.herodotus.engine.assistant.core.json.jackson2.utils.Jackson2Utils;
import com.alicp.jetcache.Cache;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.lang.Nullable;

import java.util.concurrent.Callable;

/**
 * <p>Description: 基于 JetCache 的 Spring Cache 扩展 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/23 11:11
 */
public class JetCacheSpringCache extends AbstractValueAdaptingCache {

    private static final Logger log = LoggerFactory.getLogger(JetCacheSpringCache.class);

    private final String cacheName;
    private final Cache<Object, Object> cache;

    public JetCacheSpringCache(String cacheName, Cache<Object, Object> cache, boolean allowNullValues) {
        super(allowNullValues);
        this.cacheName = cacheName;
        this.cache = cache;
    }

    @Override
    public String getName() {
        return this.cacheName;
    }

    @Override
    public final Cache<Object, Object> getNativeCache() {
        return this.cache;
    }

    @Override
    @Nullable
    protected Object lookup(Object key) {
        Object value = cache.get(key);
        if (ObjectUtils.isNotEmpty(value)) {
            log.trace("[Herodotus] |- CACHE - Lookup data in herodotus cache, value is : [{}]", Jackson2Utils.toJson(value));
            return value;
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Nullable
    public <T> T get(Object key, Callable<T> valueLoader) {

        log.trace("[Herodotus] |- CACHE - Get data in herodotus cache, key: {}", key);

        return (T) fromStoreValue(cache.computeIfAbsent(key, k -> {
            try {
                return toStoreValue(valueLoader.call());
            } catch (Throwable ex) {
                throw new ValueRetrievalException(key, valueLoader, ex);
            }
        }));
    }

    @Override
    @Nullable
    public void put(Object key, @Nullable Object value) {
        log.trace("[Herodotus] |- CACHE - Put data in herodotus cache, key: {}", key);
        cache.put(key, this.toStoreValue(value));
    }


    @Override
    @Nullable
    public ValueWrapper putIfAbsent(Object key, @Nullable Object value) {
        log.trace("[Herodotus] |- CACHE - PutIfPresent data in herodotus cache, key: {}", key);
        Object existing = cache.putIfAbsent(key, toStoreValue(value));
        return toValueWrapper(existing);
    }

    @Override
    public void evict(Object key) {
        log.trace("[Herodotus] |- CACHE - Evict data in herodotus cache, key: {}", key);
        cache.remove(key);
    }

    @Override
    public boolean evictIfPresent(Object key) {
        log.trace("[Herodotus] |- CACHE - EvictIfPresent data in herodotus cache, key: {}", key);
        return cache.remove(key);
    }

    @Override
    public void clear() {
        log.trace("[Herodotus] |- CACHE - Clear data in herodotus cache.");
        cache.close();
    }
}
