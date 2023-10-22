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

package cn.herodotus.engine.rest.autoconfigure;

import cn.herodotus.engine.rest.service.configuration.FeignConfiguration;
import cn.herodotus.engine.rest.service.configuration.RestScanConfiguration;
import cn.herodotus.engine.rest.service.configuration.SpringdocConfiguration;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * <p>Description: Rest 服务基础内容配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/10/3 22:55
 */
@AutoConfiguration
@Import({
        FeignConfiguration.class,
        RestScanConfiguration.class,
        SpringdocConfiguration.class
})
public class RestServiceAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RestServiceAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Module [Rest Service] Auto Configure.");
    }
}
