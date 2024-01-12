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

package cn.herodotus.engine.cache.redis.definition;

import cn.herodotus.stirrup.cache.core.constants.CacheConstants;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * <p>Description: SimpleRedisCache的基础实现 </p>
 * <p>
 * 增加这一层是为了方便扩展，比如说支持JustAuth
 *
 * @author : gengwei.zheng
 * @date : 2021/5/21 23:45
 */
public abstract class AbstractStringRedisCache implements StringRedisCache {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofMinutes(10);
    private static final String DEFAULT_PREFIX = CacheConstants.CACHE_SIMPLE_BASE_PREFIX + "default:";

    private StringRedisTemplate stringRedisTemplate;
    private String prefix = DEFAULT_PREFIX;
    private long defaultTimeout = DEFAULT_TIMEOUT.toMillis();

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setDefaultTimeout(long defaultTimeout) {
        this.defaultTimeout = defaultTimeout;
    }

    @Override
    public void cache(String key, String value) {
        this.cache(key, value, this.defaultTimeout);
    }

    /**
     * 存入缓存
     *
     * @param key     缓存key
     * @param value   缓存内容
     * @param timeout 指定缓存过期时间（毫秒）
     */
    @Override
    public void cache(String key, String value, long timeout) {
        this.stringRedisTemplate.opsForValue().set(this.prefix + key, value, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取缓存内容
     *
     * @param key 缓存key
     * @return 缓存内容
     */
    @Override
    public String get(String key) {
        return this.stringRedisTemplate.opsForValue().get(this.prefix + key);
    }

    /**
     * 是否存在key，如果对应key的value值已过期，也返回false
     *
     * @param key 缓存key
     * @return true：存在key，并且value没过期；false：key不存在或者已过期
     */
    @Override
    public boolean containsKey(String key) {
        Long expire = this.stringRedisTemplate.getExpire(this.prefix + key, TimeUnit.MILLISECONDS);
        if (expire == null) {
            expire = 0L;
        }
        return expire > 0;
    }

    @Override
    public boolean delete(String key) {
        if (containsKey(key)) {
            return this.stringRedisTemplate.delete(key);
        }
        return true;
    }
}
