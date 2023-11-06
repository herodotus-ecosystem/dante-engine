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

package cn.herodotus.engine.data.tenant.configuration;

import cn.herodotus.engine.data.tenant.hibernate.HerodotusTenantIdentifierResolver;
import jakarta.annotation.PostConstruct;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: 共享数据库，独立Schema，共享数据表多租户配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/3/28 22:26
 */
@Configuration(proxyBeanMethods = false)
public class DiscriminatorApproachConfiguration {

    private static final Logger log = LoggerFactory.getLogger(DiscriminatorApproachConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Discriminator Approach] Auto Configure.");
    }

    @Bean
    public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
        HerodotusTenantIdentifierResolver herodotusTenantIdentifierResolver = new HerodotusTenantIdentifierResolver();
        log.debug("[Herodotus] |- Bean [Current Tenant Identifier Resolver] Auto Configure.");
        return herodotusTenantIdentifierResolver;
    }
}
