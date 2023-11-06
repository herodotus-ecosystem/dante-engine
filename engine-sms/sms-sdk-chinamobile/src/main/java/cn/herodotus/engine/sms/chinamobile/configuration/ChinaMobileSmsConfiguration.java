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
package cn.herodotus.engine.sms.chinamobile.configuration;

import cn.herodotus.engine.sms.chinamobile.annotation.ConditionalOnChinaMobileSmsEnabled;
import cn.herodotus.engine.sms.chinamobile.processor.ChinaMobileSmsSendHandler;
import cn.herodotus.engine.sms.chinamobile.properties.ChinaMobileSmsProperties;
import cn.herodotus.engine.sms.core.constants.SmsConstants;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: 移动云短信发送配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/26 16:13
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnChinaMobileSmsEnabled
@EnableConfigurationProperties(ChinaMobileSmsProperties.class)
public class ChinaMobileSmsConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ChinaMobileSmsConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Sms China Mobile] Auto Configure.");
    }

    /**
     * 构造移动云发送处理
     *
     * @param chinaMobileSmsProperties 配置对象
     * @return 移动云发送处理
     */
    @Bean(name = SmsConstants.CHANNEL_CHINA_MOBILE)
    public ChinaMobileSmsSendHandler chinaMobileSmsSendHandler(ChinaMobileSmsProperties chinaMobileSmsProperties) {
        ChinaMobileSmsSendHandler chinaMobileSmsSendHandler = new ChinaMobileSmsSendHandler(chinaMobileSmsProperties);
        log.debug("[Herodotus] |- Bean [China Mobile Sms Send Handler] Auto Configure.");
        return chinaMobileSmsSendHandler;
    }
}
