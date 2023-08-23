/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 6.若您的项目无法满足以上几点，可申请商业授权
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
