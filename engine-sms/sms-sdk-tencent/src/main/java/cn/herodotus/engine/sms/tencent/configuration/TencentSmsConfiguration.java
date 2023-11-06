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
package cn.herodotus.engine.sms.tencent.configuration;

import cn.herodotus.engine.sms.core.constants.SmsConstants;
import cn.herodotus.engine.sms.tencent.annotation.ConditionalOnTencentSmsEnabled;
import cn.herodotus.engine.sms.tencent.processor.TencentSmsSendHandler;
import cn.herodotus.engine.sms.tencent.properties.TencentSmsProperties;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: 腾讯云短信发送配置类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/25 15:24
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnTencentSmsEnabled
@EnableConfigurationProperties(TencentSmsProperties.class)
public class TencentSmsConfiguration {

    private static final Logger log = LoggerFactory.getLogger(TencentSmsConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Sms Tencent] Auto Configure.");
    }

    /**
     * 构造腾讯云V3发送处理
     *
     * @param tencentSmsProperties 配置对象
     * @return 腾讯云发送处理
     */
    @Bean(name = SmsConstants.CHANNEL_TENCENT_CLOUD)
    public TencentSmsSendHandler tencentCloudSmsSendHandler(TencentSmsProperties tencentSmsProperties) {
        TencentSmsSendHandler tencentSmsSendHandler = new TencentSmsSendHandler(tencentSmsProperties);
        log.debug("[Herodotus] |- Bean [Tencent Sms Send Handler] Auto Configure.");
        return tencentSmsSendHandler;
    }
}
