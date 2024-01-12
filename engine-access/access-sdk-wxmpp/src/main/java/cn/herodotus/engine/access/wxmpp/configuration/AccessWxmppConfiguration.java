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

package cn.herodotus.engine.access.wxmpp.configuration;

import cn.herodotus.engine.access.wxmpp.annotation.ConditionalOnWxmppEnabled;
import cn.herodotus.engine.access.wxmpp.processor.WxmppLogHandler;
import cn.herodotus.engine.access.wxmpp.processor.WxmppProcessor;
import cn.herodotus.engine.access.wxmpp.properties.WxmppProperties;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * <p>Description: 微信公众号配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/7 13:25
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWxmppEnabled
@EnableConfigurationProperties(WxmppProperties.class)
public class AccessWxmppConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AccessWxmppConfiguration.class);

    @PostConstruct
    public void init() {
        log.debug("[Herodotus] |- SDK [Access Wxmpp] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public WxmppProcessor wxmppProcessor(WxmppProperties wxmppProperties, StringRedisTemplate stringRedisTemplate) {
        WxmppProcessor wxmppProcessor = new WxmppProcessor();
        wxmppProcessor.setWxmppProperties(wxmppProperties);
        wxmppProcessor.setWxmppLogHandler(new WxmppLogHandler());
        wxmppProcessor.setStringRedisTemplate(stringRedisTemplate);
        log.trace("[Herodotus] |- Bean [Wxmpp Processor] Auto Configure.");
        return wxmppProcessor;
    }
}
