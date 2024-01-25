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

package cn.herodotus.stirrup.oauth2.authentication.autoconfigure;

import cn.herodotus.engine.message.core.logic.strategy.AccountStatusEventManager;
import cn.herodotus.engine.oauth2.management.configuration.OAuth2ManagementConfiguration;
import cn.herodotus.stirrup.oauth2.authentication.autoconfigure.status.DefaultAccountStatusEventManager;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * <p>Description: 授权服务器必要组件自动配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/1/25 13:16
 */
@AutoConfiguration
@Import({
        OAuth2ManagementConfiguration.class
})
public class OAuth2AuthenticationAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OAuth2AuthenticationAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [OAuth2 Authorization] Auto Configure.");
    }

    @Bean
    public AccountStatusEventManager accountStatusEventManager() {
        DefaultAccountStatusEventManager manager = new DefaultAccountStatusEventManager();
        log.trace("[Herodotus] |- Bean [Herodotus Account Status Event Manager] Auto Configure.");
        return manager;
    }
}
