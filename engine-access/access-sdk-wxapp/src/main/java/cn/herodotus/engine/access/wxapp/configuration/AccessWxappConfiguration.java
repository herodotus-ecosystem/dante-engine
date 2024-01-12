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

package cn.herodotus.engine.access.wxapp.configuration;

import cn.herodotus.engine.access.wxapp.annotation.ConditionalOnWxappEnabled;
import cn.herodotus.engine.access.wxapp.processor.WxappAccessHandler;
import cn.herodotus.engine.access.wxapp.processor.WxappLogHandler;
import cn.herodotus.engine.access.wxapp.processor.WxappProcessor;
import cn.herodotus.engine.access.wxapp.properties.WxappProperties;
import cn.herodotus.engine.assistant.core.enums.AccountType;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: 微信小程序后配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/3/29 9:27
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWxappEnabled
@EnableConfigurationProperties(WxappProperties.class)
public class AccessWxappConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AccessWxappConfiguration.class);

    @PostConstruct
    public void init() {
        log.debug("[Herodotus] |- SDK [Access Wxapp] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public WxappProcessor wxappProcessor(WxappProperties wxappProperties) {
        WxappProcessor wxappProcessor = new WxappProcessor();
        wxappProcessor.setWxappProperties(wxappProperties);
        wxappProcessor.setWxappLogHandler(new WxappLogHandler());
        log.trace("[Herodotus] |- Bean [Wxapp Processor] Auto Configure.");
        return wxappProcessor;
    }

    @Bean(AccountType.WECHAT_MINI_APP_HANDLER)
    @ConditionalOnBean(WxappProcessor.class)
    @ConditionalOnMissingBean
    public WxappAccessHandler wxappAccessHandler(WxappProcessor wxappProcessor) {
        WxappAccessHandler wxappAccessHandler = new WxappAccessHandler(wxappProcessor);
        log.debug("[Herodotus] |- Bean [Wxapp Access Handler] Auto Configure.");
        return wxappAccessHandler;
    }
}
