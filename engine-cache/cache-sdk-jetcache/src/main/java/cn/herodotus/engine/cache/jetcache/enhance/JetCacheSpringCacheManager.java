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

import cn.herodotus.engine.assistant.definition.constants.SymbolConstants;
import cn.herodotus.engine.cache.core.properties.CacheSetting;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Description: 基于 JetCache 的 Spring Cache Manager 扩展 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/23 14:06
 */
public class JetCacheSpringCacheManager implements CacheManager {

    private static final Logger log = LoggerFactory.getLogger(JetCacheSpringCacheManager.class);
    private final Map<String, Cache> cacheMap = new ConcurrentHashMap<>(16);
    private final JetCacheCreateCacheFactory jetCacheCreateCacheFactory;
    private boolean dynamic = true;
    private boolean allowNullValues = true;

    public JetCacheSpringCacheManager(JetCacheCreateCacheFactory jetCacheCreateCacheFactory) {
        this.jetCacheCreateCacheFactory = jetCacheCreateCacheFactory;
    }

    public JetCacheSpringCacheManager(JetCacheCreateCacheFactory jetCacheCreateCacheFactory, String... cacheNames) {
        this.jetCacheCreateCacheFactory = jetCacheCreateCacheFactory;
        setCacheNames(Arrays.asList(cacheNames));
    }

    public boolean isAllowNullValues() {
        return allowNullValues;
    }

    public void setAllowNullValues(boolean allowNullValues) {
        this.allowNullValues = allowNullValues;
    }

    protected Cache createJetCache(String name) {
        com.alicp.jetcache.Cache<Object, Object> cache = jetCacheCreateCacheFactory.create(name);
        log.debug("[Herodotus] |- CACHE - Herodotus cache [{}] is CREATED.", name);
        return new JetCacheSpringCache(name, cache, allowNullValues);
    }

    protected Cache createJetCache(String name, CacheSetting cacheSetting) {
        com.alicp.jetcache.Cache<Object, Object> cache = jetCacheCreateCacheFactory.create(name, allowNullValues, cacheSetting);
        log.debug("[Herodotus] |- CACHE - Herodotus cache [{}] use entity cache is CREATED.", name);
        return new JetCacheSpringCache(name, cache, allowNullValues);
    }

    private String availableCacheName(String name) {
        if (StringUtils.endsWith(name, SymbolConstants.COLON)) {
            return name;
        } else {
            return name + SymbolConstants.COLON;
        }
    }

    @Override
    @Nullable
    public Cache getCache(String name) {
        String usedName = availableCacheName(name);
        return this.cacheMap.computeIfAbsent(usedName, cacheName ->
                this.dynamic ? createJetCache(cacheName) : null);
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(this.cacheMap.keySet());
    }

    private void setCacheNames(@Nullable Collection<String> cacheNames) {
        if (cacheNames != null) {
            for (String name : cacheNames) {
                this.cacheMap.put(name, createJetCache(name));
            }
            this.dynamic = false;
        } else {
            this.dynamic = true;
        }
    }
}
