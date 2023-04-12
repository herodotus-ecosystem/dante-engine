/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.cache.redis.definition;

import cn.herodotus.engine.cache.core.constants.CacheConstants;
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
