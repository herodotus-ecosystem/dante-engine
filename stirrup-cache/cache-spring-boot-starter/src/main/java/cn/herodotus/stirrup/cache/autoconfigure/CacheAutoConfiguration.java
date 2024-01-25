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

package cn.herodotus.stirrup.cache.autoconfigure;

import cn.herodotus.stirrup.cache.autoconfigure.customizer.CacheErrorCodeMapperBuilderCustomizer;
import cn.herodotus.stirrup.core.definition.function.ErrorCodeMapperBuilderCustomizer;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * <p>Description: 缓存相关模块统一自动配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/1/25 12:02
 */
@AutoConfiguration
public class CacheAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CacheAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Starter [Cache] Auto Configure.");
    }

    @Bean
    public ErrorCodeMapperBuilderCustomizer cacheErrorCodeMapperBuilderCustomizer() {
        CacheErrorCodeMapperBuilderCustomizer customizer = new CacheErrorCodeMapperBuilderCustomizer();
        log.debug("[Herodotus] |- Strategy [Cache ErrorCodeMapper Builder Customizer] Auto Configure.");
        return customizer;
    }
}
