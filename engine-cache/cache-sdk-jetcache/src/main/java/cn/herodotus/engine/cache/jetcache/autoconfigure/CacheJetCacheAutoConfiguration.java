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

package cn.herodotus.engine.cache.jetcache.autoconfigure;

import cn.herodotus.engine.cache.caffeine.configuration.CacheCaffeineConfiguration;
import cn.herodotus.engine.cache.core.properties.CacheProperties;
import cn.herodotus.engine.cache.jetcache.enhance.HerodotusCacheManager;
import cn.herodotus.engine.cache.jetcache.enhance.JetCacheCreateCacheFactory;
import cn.herodotus.engine.cache.jetcache.utils.JetCacheUtils;
import cn.herodotus.engine.cache.redis.configuration.CacheRedisConfiguration;
import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.autoconfigure.JetCacheAutoConfiguration;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

/**
 * <p>Description: JetCacheConfiguration </p>
 * <p>
 * 新增JetCache配置，解决JetCache依赖循环问题
 * <p>
 * 注解 @AutoConfiguration 它是 @Configuration、@AutoConfigureBefore、@AutoConfigureAfter三个注解结合体，以一顶三。
 * 标注 @AutoConfiguration 注解的类也必须放进
 * META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
 * 文件里才算自动配置类，否则也只是普通配置类而已
 *
 * @author : gengwei.zheng
 * @date : 2021/12/4 10:44
 */
@AutoConfiguration(after = JetCacheAutoConfiguration.class)
@ConditionalOnClass({CacheManager.class})
@EnableConfigurationProperties(CacheProperties.class)
@Import({CacheCaffeineConfiguration.class, CacheRedisConfiguration.class})
public class CacheJetCacheAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CacheJetCacheAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Cache JetCache] Auto Configure.");
    }

    @Bean
    public JetCacheCreateCacheFactory jetCacheCreateCacheFactory(@Qualifier("jcCacheManager") CacheManager cacheManager, CacheProperties cacheProperties) {
        JetCacheCreateCacheFactory factory = new JetCacheCreateCacheFactory(cacheManager, cacheProperties);
        JetCacheUtils.setJetCacheCreateCacheFactory(factory);
        log.trace("[Herodotus] |- Bean [Jet Cache Create Cache Factory] Auto Configure.");
        return factory;
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean
    public HerodotusCacheManager herodotusCacheManager(JetCacheCreateCacheFactory jetCacheCreateCacheFactory, CacheProperties cacheProperties) {
        HerodotusCacheManager herodotusCacheManager = new HerodotusCacheManager(jetCacheCreateCacheFactory, cacheProperties);
        herodotusCacheManager.setAllowNullValues(cacheProperties.getAllowNullValues());
        log.trace("[Herodotus] |- Bean [Jet Cache Herodotus Cache Manager] Auto Configure.");
        return herodotusCacheManager;
    }
}
