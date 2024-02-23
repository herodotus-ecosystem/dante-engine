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

package cn.herodotus.engine.web.server.autoconfigure;

import cn.herodotus.engine.web.server.autoconfigure.configuration.UndertowWebServerFactoryCustomizer;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * <p>Description: Web 自动配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/14 15:43
 */
@AutoConfiguration
@Import({
        UndertowWebServerFactoryCustomizer.class,
})
public class WebServerAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(WebServerAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Module [Web Server Starter] Auto Configure.");
    }
}