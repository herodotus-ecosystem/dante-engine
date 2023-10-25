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

package cn.herodotus.engine.assistant.ip2region.configuration;

import cn.herodotus.engine.assistant.ip2region.definition.Ip2RegionSearcher;
import cn.herodotus.engine.assistant.ip2region.properties.Ip2RegionProperties;
import cn.herodotus.engine.assistant.ip2region.searcher.DefaultIp2RegionSearcher;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * <p>Description: Ip2Region 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/10/24 11:59
 */
@AutoConfiguration
@EnableConfigurationProperties(Ip2RegionProperties.class)
public class Ip2RegionConfiguration {

    private static final Logger log = LoggerFactory.getLogger(Ip2RegionConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Ip2Region] Auto Configure.");
    }

    @Bean
    public Ip2RegionSearcher defaultIp2RegionSearcher(Ip2RegionProperties ip2RegionProperties) {
        DefaultIp2RegionSearcher searcher = new DefaultIp2RegionSearcher(ip2RegionProperties.getIpV4Resource(), ip2RegionProperties.getIpV6Resource());
        log.trace("[Herodotus] |- Bean [Ip2Region Searcher] Auto Configure.");
        return searcher;
    }

}
