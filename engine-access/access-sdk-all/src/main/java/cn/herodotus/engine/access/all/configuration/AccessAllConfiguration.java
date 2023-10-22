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

package cn.herodotus.engine.access.all.configuration;

import cn.herodotus.engine.access.all.controller.JustAuthAccessController;
import cn.herodotus.engine.access.all.controller.PhoneNumberAccessController;
import cn.herodotus.engine.access.all.controller.WxappAccessController;
import cn.herodotus.engine.access.all.processor.AccessHandlerStrategyFactory;
import cn.herodotus.engine.access.all.processor.PhoneNumberAccessHandler;
import cn.herodotus.engine.access.justauth.annotation.ConditionalOnJustAuthEnabled;
import cn.herodotus.engine.access.justauth.configuration.JustAuthConfiguration;
import cn.herodotus.engine.access.wxapp.annotation.ConditionalOnWxappEnabled;
import cn.herodotus.engine.access.wxapp.configuration.WxappConfiguration;
import cn.herodotus.engine.access.wxmpp.configuration.WxmppConfiguration;
import cn.herodotus.engine.assistant.core.enums.AccountType;
import cn.herodotus.engine.sms.autoconfigure.SmsAutoConfiguration;
import cn.herodotus.engine.sms.autoconfigure.annotation.ConditionalOnSmsEnabled;
import cn.herodotus.engine.sms.autoconfigure.processor.SmsSendStrategyFactory;
import cn.herodotus.engine.sms.autoconfigure.stamp.VerificationCodeStampManager;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <p>Description: Access 业务模块配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/26 14:59
 */
@Configuration(proxyBeanMethods = false)
@Import({
        JustAuthConfiguration.class,
        WxappConfiguration.class,
        WxmppConfiguration.class
})
public class AccessAllConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AccessAllConfiguration.class);

    @PostConstruct
    public void init() {
        log.debug("[Herodotus] |- SDK [Access All] Auto Configure.");
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnSmsEnabled
    @Import({SmsAutoConfiguration.class})
    static class PhoneNumberSignInConfiguration {

        @Bean(AccountType.PHONE_NUMBER_HANDLER)
        @ConditionalOnBean({VerificationCodeStampManager.class, SmsSendStrategyFactory.class})
        public PhoneNumberAccessHandler phoneNumberAccessHandler(VerificationCodeStampManager verificationCodeStampManager, SmsSendStrategyFactory smsSendStrategyFactory) {
            PhoneNumberAccessHandler phoneNumberAuthenticationHandler = new PhoneNumberAccessHandler(verificationCodeStampManager, smsSendStrategyFactory);
            log.trace("[Herodotus] |- Bean [Phone Number SignIn Handler] Auto Configure.");
            return phoneNumberAuthenticationHandler;
        }
    }

    @Bean
    @ConditionalOnMissingBean(AccessHandlerStrategyFactory.class)
    public AccessHandlerStrategyFactory accessHandlerStrategyFactory() {
        AccessHandlerStrategyFactory accessHandlerStrategyFactory = new AccessHandlerStrategyFactory();
        log.trace("[Herodotus] |- Bean [Access Handler Strategy Factory] Auto Configure.");
        return accessHandlerStrategyFactory;
    }

    @Configuration(proxyBeanMethods = false)
    static class ControllerConfiguration {

        @PostConstruct
        public void init() {
            log.debug("[Herodotus] |- SDK [Access All Controller] Auto Configure.");
        }

        @Bean
        @ConditionalOnSmsEnabled
        @ConditionalOnMissingBean
        public PhoneNumberAccessController phoneNumberAccessController() {
            PhoneNumberAccessController phoneNumberAuthenticationController = new PhoneNumberAccessController();
            log.trace("[Herodotus] |- Bean [Phone Number Access Controller] Auto Configure.");
            return phoneNumberAuthenticationController;
        }

        @Bean
        @ConditionalOnJustAuthEnabled
        @ConditionalOnMissingBean
        public JustAuthAccessController justAuthSignInController() {
            JustAuthAccessController justAuthAuthenticationController = new JustAuthAccessController();
            log.trace("[Herodotus] |- Bean [Just Auth Access Controller] Auto Configure.");
            return justAuthAuthenticationController;
        }

        @Bean
        @ConditionalOnWxappEnabled
        @ConditionalOnMissingBean
        public WxappAccessController wxappAccessController() {
            WxappAccessController wxappAccessController = new WxappAccessController();
            log.trace("[Herodotus] |- Bean [Wxapp Access Controller] Auto Configure.");
            return wxappAccessController;
        }
    }
}
