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

package cn.herodotus.engine.data.tenant.autoconfigure;

import cn.herodotus.engine.data.tenant.configuration.DatabaseApproachConfiguration;
import cn.herodotus.engine.data.tenant.configuration.DiscriminatorApproachConfiguration;
import cn.herodotus.engine.data.tenant.configuration.SchemaApproachConfiguration;
import cn.herodotus.engine.data.tenant.properties.MultiTenantProperties;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 * <p>Description: 多租户模块统一配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/3/28 23:12
 */
@AutoConfiguration
@EnableConfigurationProperties(MultiTenantProperties.class)
@Import({
        DiscriminatorApproachConfiguration.class,
        SchemaApproachConfiguration.class,
        DatabaseApproachConfiguration.class,
})
public class DataTenantAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SchemaApproachConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Module [Data Tenant] Auto Configure.");
    }
}
