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

package cn.herodotus.engine.oauth2.authorization.autoconfigure;

import cn.herodotus.engine.data.tenant.annotation.ConditionalOnDatabaseApproach;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>Description: 多租户 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/3/29 21:18
 */
@AutoConfiguration
@ConditionalOnDatabaseApproach
@ComponentScan(basePackages = {
        "cn.herodotus.engine.oauth2.authorization.autoconfigure.tenant",
})
public class TenantDataSourceAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(TenantDataSourceAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Module [Tenant Data Source] Auto Configure.");
    }
}
