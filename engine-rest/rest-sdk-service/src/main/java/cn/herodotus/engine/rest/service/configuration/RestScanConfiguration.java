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

package cn.herodotus.engine.rest.service.configuration;

import cn.herodotus.engine.message.core.logic.strategy.RequestMappingScanEventManager;
import cn.herodotus.stirrup.web.core.annotation.ConditionalOnScanEnabled;
import cn.herodotus.engine.rest.service.processor.RequestMappingScanner;
import cn.herodotus.engine.rest.condition.properties.ScanProperties;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: 接口扫描配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/16 18:40
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RequestMappingScanEventManager.class)
@ConditionalOnScanEnabled
@EnableConfigurationProperties(ScanProperties.class)
public class RestScanConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RestScanConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Rest Scan] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestMappingScanner requestMappingScanner(ScanProperties scanProperties, RequestMappingScanEventManager requestMappingScanManager) {
        RequestMappingScanner requestMappingScanner = new RequestMappingScanner(scanProperties, requestMappingScanManager);
        log.trace("[Herodotus] |- Bean [Request Mapping Scanner] Auto Configure.");
        return requestMappingScanner;
    }
}
