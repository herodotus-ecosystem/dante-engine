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

package cn.herodotus.engine.access.sms.configuration;

import cn.herodotus.engine.access.sms.annotation.ConditionalOnSmsEnabled;
import cn.herodotus.engine.access.sms.processor.PhoneNumberAccessHandler;
import cn.herodotus.engine.access.sms.properties.SmsProperties;
import cn.herodotus.engine.access.sms.stamp.VerificationCodeStampManager;
import cn.herodotus.engine.assistant.core.enums.AccountType;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: 发送短信统一配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/25 12:03
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnSmsEnabled
@EnableConfigurationProperties({SmsProperties.class})
public class AccessSmsConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AccessSmsConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Access SMS] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public VerificationCodeStampManager verificationCodeStampManager(SmsProperties smsProperties) {
        VerificationCodeStampManager verificationCodeStampManager = new VerificationCodeStampManager();
        verificationCodeStampManager.setSmsProperties(smsProperties);
        log.trace("[Herodotus] |- Bean [Verification Code Stamp Manager] Auto Configure.");
        return verificationCodeStampManager;
    }

    @Bean(AccountType.PHONE_NUMBER_HANDLER)
    public PhoneNumberAccessHandler phoneNumberAccessHandler(VerificationCodeStampManager verificationCodeStampManager) {
        PhoneNumberAccessHandler phoneNumberAuthenticationHandler = new PhoneNumberAccessHandler(verificationCodeStampManager);
        log.trace("[Herodotus] |- Bean [Phone Number SignIn Handler] Auto Configure.");
        return phoneNumberAuthenticationHandler;
    }
}
