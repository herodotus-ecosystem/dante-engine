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

package cn.herodotus.engine.cache.jetcache.utils;

import cn.herodotus.engine.cache.jetcache.enhance.JetCacheCreateCacheFactory;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import org.apache.commons.lang3.ObjectUtils;

import java.time.Duration;

/**
 * <p>Description: JetCache 单例工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/8/9 15:43
 */
public class JetCacheUtils {

    private static volatile JetCacheUtils instance;
    private JetCacheCreateCacheFactory jetCacheCreateCacheFactory;

    private JetCacheUtils() {

    }

    public static JetCacheUtils getInstance() {
        if (ObjectUtils.isEmpty(instance)) {
            synchronized (JetCacheUtils.class) {
                if (ObjectUtils.isEmpty(instance)) {
                    instance = new JetCacheUtils();
                }
            }
        }
        return instance;
    }

    public static <K, V> Cache<K, V> create(String name, Duration expire) {
        return create(name, expire, true);
    }

    public static <K, V> Cache<K, V> create(String name, Duration expire, Boolean cacheNullValue) {
        return create(name, expire, cacheNullValue, null);
    }

    public static <K, V> Cache<K, V> create(String name, Duration expire, Boolean cacheNullValue, Boolean syncLocal) {
        return create(name, CacheType.BOTH, expire, cacheNullValue, syncLocal);
    }

    public static <K, V> Cache<K, V> create(String name, CacheType cacheType) {
        return create(name, cacheType, null);
    }

    public static <K, V> Cache<K, V> create(String name, CacheType cacheType, Duration expire) {
        return create(name, cacheType, expire, true);
    }

    public static <K, V> Cache<K, V> create(String name, CacheType cacheType, Duration expire, Boolean cacheNullValue) {
        return create(name, cacheType, expire, cacheNullValue, null);
    }

    public static <K, V> Cache<K, V> create(String name, CacheType cacheType, Duration expire, Boolean cacheNullValue, Boolean syncLocal) {
        return getInstance().getJetCacheCreateCacheFactory().create(name, cacheType, expire, cacheNullValue, syncLocal);
    }

    private void init(JetCacheCreateCacheFactory jetCacheCreateCacheFactory) {
        this.jetCacheCreateCacheFactory = jetCacheCreateCacheFactory;
    }

    private JetCacheCreateCacheFactory getJetCacheCreateCacheFactory() {
        return jetCacheCreateCacheFactory;
    }

    public static void setJetCacheCreateCacheFactory(JetCacheCreateCacheFactory jetCacheCreateCacheFactory) {
        getInstance().init(jetCacheCreateCacheFactory);
    }
}
