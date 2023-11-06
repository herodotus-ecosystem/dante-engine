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

package cn.herodotus.engine.cache.jetcache.stamp;

import cn.herodotus.engine.cache.core.exception.StampDeleteFailedException;
import cn.herodotus.engine.cache.core.exception.StampHasExpiredException;
import cn.herodotus.engine.cache.core.exception.StampMismatchException;
import cn.herodotus.engine.cache.core.exception.StampParameterIllegalException;
import cn.herodotus.engine.cache.jetcache.utils.JetCacheUtils;
import com.alicp.jetcache.AutoReleaseLock;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import org.apache.commons.lang3.ObjectUtils;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * <p>Description: 抽象Stamp管理 </p>
 *
 * @param <K> 签章缓存对应Key值的类型。
 * @param <V> 签章缓存存储数据，对应的具体存储值的类型，
 * @author : gengwei.zheng
 * @date : 2021/8/23 11:51
 */
public abstract class AbstractStampManager<K, V> implements StampManager<K, V> {

    private static final Duration DEFAULT_EXPIRE = Duration.ofMinutes(30);

    private String cacheName;
    private CacheType cacheType;
    private Duration expire;
    private Cache<K, V> cache;

    public AbstractStampManager(String cacheName) {
        this(cacheName, CacheType.BOTH);
    }

    public AbstractStampManager(String cacheName, CacheType cacheType) {
        this(cacheName, cacheType, DEFAULT_EXPIRE);
    }

    public AbstractStampManager(String cacheName, CacheType cacheType, Duration expire) {
        this.cacheName = cacheName;
        this.cacheType = cacheType;
        this.expire = expire;
        this.cache = JetCacheUtils.create(this.cacheName, this.cacheType, this.expire);
    }

    /**
     * 指定数据存储缓存
     *
     * @return {@link Cache}
     */
    protected Cache<K, V> getCache() {
        return this.cache;
    }

    @Override
    public Duration getExpire() {
        return this.expire;
    }

    public void setExpire(Duration expire) {
        this.expire = expire;
    }

    @Override
    public boolean check(K key, V value) {
        if (ObjectUtils.isEmpty(value)) {
            throw new StampParameterIllegalException("Parameter Stamp value is null");
        }

        V storedStamp = this.get(key);
        if (ObjectUtils.isEmpty(storedStamp)) {
            throw new StampHasExpiredException("Stamp is invalid!");
        }

        if (ObjectUtils.notEqual(storedStamp, value)) {
            throw new StampMismatchException("Stamp is mismathch!");
        }

        return true;
    }

    @Override
    public V get(K key) {
        return this.getCache().get(key);
    }

    @Override
    public void delete(K key) throws StampDeleteFailedException {
        boolean result = this.getCache().remove(key);
        if (!result) {
            throw new StampDeleteFailedException("Delete Stamp From Storage Failed");
        }
    }

    @Override
    public void put(K key, V value, long expireAfterWrite, TimeUnit timeUnit) {
        this.getCache().put(key, value, expireAfterWrite, timeUnit);
    }

    @Override
    public AutoReleaseLock lock(K key, long expire, TimeUnit timeUnit) {
        return this.getCache().tryLock(key, expire, timeUnit);
    }

    @Override
    public boolean lockAndRun(K key, long expire, TimeUnit timeUnit, Runnable action) {
        return this.getCache().tryLockAndRun(key, expire, timeUnit, action);
    }
}
