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

package cn.herodotus.stirrup.cache.caffeine.configuration;

import cn.herodotus.stirrup.cache.caffeine.enhance.HerodotusCaffeineCacheManager;
import cn.herodotus.stirrup.cache.core.properties.CacheProperties;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: Caffeine 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/5/23 17:56
 */
@Configuration(proxyBeanMethods = false)
public class CacheCaffeineConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CacheCaffeineConfiguration.class);

    private final CacheProperties cacheProperties;

    public CacheCaffeineConfiguration(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Cache Caffeine] Auto Configure.");
    }

    @Bean
    public Caffeine<Object, Object> caffeine() {
        Caffeine<Object, Object> caffeine = Caffeine
                .newBuilder()
                .expireAfterWrite(ObjectUtils.isNotEmpty(cacheProperties.getLocalExpire()) ? cacheProperties.getLocalExpire() : cacheProperties.getExpire());

        log.trace("[Herodotus] |- Bean [Caffeine] Auto Configure.");

        return caffeine;
    }

    @Bean
    @ConditionalOnMissingBean(CaffeineCacheManager.class)
    public CaffeineCacheManager caffeineCacheManager(Caffeine<Object, Object> caffeine) {
        HerodotusCaffeineCacheManager herodotusCaffeineCacheManager = new HerodotusCaffeineCacheManager(cacheProperties);
        herodotusCaffeineCacheManager.setCaffeine(caffeine);
        log.trace("[Herodotus] |- Bean [Caffeine Cache Manager] Auto Configure.");
        return herodotusCaffeineCacheManager;
    }
}
