/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
 * 2.请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.cache.caffeine.enhance;

import cn.herodotus.engine.assistant.core.definition.constants.SymbolConstants;
import cn.herodotus.engine.cache.core.properties.CacheProperties;
import cn.herodotus.engine.cache.core.properties.Expire;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import java.util.Map;

/**
 * <p>Description: 扩展的 CaffeineCacheManager </p>
 * <p>
 * 用于支持 Caffeine 缓存可以针对实体进行单独的过期时间设定
 *
 * @author : gengwei.zheng
 * @date : 2021/10/25 18:12
 */
public class HerodotusCaffeineCacheManager extends CaffeineCacheManager {

    private static final Logger log = LoggerFactory.getLogger(HerodotusCaffeineCacheManager.class);

    private final CacheProperties cacheProperties;

    public HerodotusCaffeineCacheManager(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
        this.setAllowNullValues(cacheProperties.getAllowNullValues());
    }

    public HerodotusCaffeineCacheManager(CacheProperties cacheProperties, String... cacheNames) {
        super(cacheNames);
        this.cacheProperties = cacheProperties;
        this.setAllowNullValues(cacheProperties.getAllowNullValues());
    }

    @Override
    protected Cache<Object, Object> createNativeCaffeineCache(String name) {
        Map<String, Expire> expires = cacheProperties.getExpires();
        if (MapUtils.isNotEmpty(expires)) {
            String key = StringUtils.replace(name, SymbolConstants.COLON, cacheProperties.getSeparator());
            if(expires.containsKey(key)) {
                Expire expire = expires.get(key);
                log.debug("[Herodotus] |- CACHE - Caffeine cache [{}] is setted to use CUSTEM exprie.", name);
                return Caffeine.newBuilder().expireAfterWrite(expire.getDuration(), expire.getUnit()).build();
            }
        }

        return super.createNativeCaffeineCache(name);
    }
}
