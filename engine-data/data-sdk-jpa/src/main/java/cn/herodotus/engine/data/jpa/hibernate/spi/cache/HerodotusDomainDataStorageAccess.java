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

package cn.herodotus.engine.data.jpa.hibernate.spi.cache;

import cn.herodotus.engine.assistant.core.context.TenantContextHolder;
import cn.herodotus.engine.assistant.definition.constants.SymbolConstants;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.crypto.SecureUtil;
import org.hibernate.cache.spi.QueryKey;
import org.hibernate.cache.spi.support.DomainDataStorageAccess;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;

/**
 * <p>Description: 自定义Hibernate二级缓存DomainDataStorageAccess </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/12 22:06
 */
public class HerodotusDomainDataStorageAccess implements DomainDataStorageAccess {

    private static final Logger log = LoggerFactory.getLogger(HerodotusDomainDataStorageAccess.class);

    private Cache cache;

    public HerodotusDomainDataStorageAccess() {
    }

    public HerodotusDomainDataStorageAccess(Cache cache) {
        this.cache = cache;
    }

    private String secure(Object key) {
        if (key instanceof QueryKey queryKey) {
            int hashCode = queryKey.hashCode();
            String hashCodeString = String.valueOf(hashCode);
            String secureKey = SecureUtil.md5(hashCodeString);
            log.trace("[Herodotus] |- SPI - Convert query key hashcode [{}] to secureKey [{}]", hashCode, secureKey);
            return secureKey;
        }
        return String.valueOf(key);
    }

    private String getTenantId() {
        String tenantId = TenantContextHolder.getTenantId();
        log.trace("[Herodotus] |- SPI - Tenant identifier for jpa second level cache is : [{}]", tenantId);
        return StringUtils.toRootLowerCase(tenantId);
    }

    private String wrapper(Object key) {
        String original = secure(key);
        String tenantId = getTenantId();

        String result = tenantId + SymbolConstants.COLON + original;
        log.trace("[Herodotus] |- SPI - Current cache key is : [{}]", result);
        return result;
    }

    private Object get(Object key) {
        Cache.ValueWrapper value = cache.get(key);

        if (ObjectUtils.isNotEmpty(value)) {
            return value.get();
        }
        return null;
    }

    @Override
    public boolean contains(Object key) {
        String wrapperKey = wrapper(key);
        Object value = this.get(wrapperKey);
        log.trace("[Herodotus] |- SPI - check is key : [{}] exist.", wrapperKey);
        return ObjectUtils.isNotEmpty(value);
    }

    @Override
    public Object getFromCache(Object key, SharedSessionContractImplementor session) {
        String wrapperKey = wrapper(key);
        Object value = this.get(wrapperKey);
        log.trace("[Herodotus] |- SPI - get from cache key is : [{}], value is : [{}]", wrapperKey, value);
        return value;
    }

    @Override
    public void putIntoCache(Object key, Object value, SharedSessionContractImplementor session) {
        String wrapperKey = wrapper(key);
        log.trace("[Herodotus] |- SPI - put into cache key is : [{}], value is : [{}]", wrapperKey, value);
        cache.put(wrapperKey, value);
    }

    @Override
    public void removeFromCache(Object key, SharedSessionContractImplementor session) {
        String wrapperKey = wrapper(key);
        log.trace("[Herodotus] |- SPI - remove from cache key is : [{}]", wrapperKey);
        cache.evict(wrapperKey);
    }

    @Override
    public void evictData(Object key) {
        String wrapperKey = wrapper(key);
        log.trace("[Herodotus] |- SPI - evict key : [{}] from cache.", wrapperKey);
        cache.evict(wrapperKey);
    }

    @Override
    public void clearCache(SharedSessionContractImplementor session) {
        this.evictData();
    }

    @Override
    public void evictData() {
        cache.clear();
    }

    @Override
    public void release() {
        cache.invalidate();
    }
}
