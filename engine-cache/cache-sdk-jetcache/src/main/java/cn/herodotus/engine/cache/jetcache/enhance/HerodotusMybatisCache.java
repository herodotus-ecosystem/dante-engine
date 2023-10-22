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

import org.apache.ibatis.cache.Cache;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>Description: 扩展的Mybatis二级缓存 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/26 17:02
 */
public class HerodotusMybatisCache implements Cache {

    private static final Logger log = LoggerFactory.getLogger(HerodotusMybatisCache.class);

    private final String id;
    private final com.alicp.jetcache.Cache<Object, Object> cache;
    private final AtomicInteger counter = new AtomicInteger(0);

    public HerodotusMybatisCache(String id) {
        this.id = id;
        JetCacheCreateCacheFactory jetCacheCreateCacheFactory = SpringUtil.getBean("jetCacheCreateCacheFactory");
        this.cache = jetCacheCreateCacheFactory.create(this.id);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        cache.put(key, value);
        counter.incrementAndGet();
        log.debug("[Herodotus] |- CACHE - Put data into Mybatis Cache, with key: [{}]", key);
    }

    @Override
    public Object getObject(Object key) {
        Object obj = cache.get(key);
        log.debug("[Herodotus] |- CACHE - Get data from Mybatis Cache, with key: [{}]", key);
        return obj;
    }

    @Override
    public Object removeObject(Object key) {
        Object obj = cache.remove(key);
        counter.decrementAndGet();
        log.debug("[Herodotus] |- CACHE - Remove data from Mybatis Cache, with key: [{}]", key);
        return obj;
    }

    @Override
    public void clear() {
        cache.close();
        log.debug("[Herodotus] |- CACHE - Clear Mybatis Cache.");
    }

    @Override
    public int getSize() {
        return counter.get();
    }
}
