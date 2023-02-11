/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
 * 2.请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.sms.all.configuration;

import cn.herodotus.engine.sms.aliyun.configuration.AliyunSmsConfiguration;
import cn.herodotus.engine.sms.all.annotation.ConditionalOnSmsEnabled;
import cn.herodotus.engine.sms.all.processor.SmsSendStrategyFactory;
import cn.herodotus.engine.sms.all.properties.SmsProperties;
import cn.herodotus.engine.sms.all.stamp.VerificationCodeStampManager;
import cn.herodotus.engine.sms.chinamobile.configuration.ChinaMobileSmsConfiguration;
import cn.herodotus.engine.sms.core.definition.SmsSendHandler;
import cn.herodotus.engine.sms.huawei.configuration.HuaweiSmsConfiguration;
import cn.herodotus.engine.sms.jd.configuration.JdSmsConfiguration;
import cn.herodotus.engine.sms.netease.configuration.NeteaseSmsConfiguration;
import cn.herodotus.engine.sms.qiniu.configuration.QiniuSmsConfiguration;
import cn.herodotus.engine.sms.tencent.configuration.TencentSmsConfiguration;
import cn.herodotus.engine.sms.upyun.configuration.UpyunSmsConfiguration;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <p>Description: 发送短信统一配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/25 12:03
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnSmsEnabled
@EnableConfigurationProperties({SmsProperties.class})
@Import({
        AliyunSmsConfiguration.class,
        ChinaMobileSmsConfiguration.class,
        HuaweiSmsConfiguration.class,
        JdSmsConfiguration.class,
        NeteaseSmsConfiguration.class,
        QiniuSmsConfiguration.class,
        TencentSmsConfiguration.class,
        UpyunSmsConfiguration.class,
})
public class SmsConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SmsConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Sms All] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public VerificationCodeStampManager verificationCodeStampManager(SmsProperties smsProperties) {
        VerificationCodeStampManager verificationCodeStampManager = new VerificationCodeStampManager();
        verificationCodeStampManager.setSmsProperties(smsProperties);
        log.trace("[Herodotus] |- Bean [Verification Code Stamp Manager] Auto Configure.");
        return verificationCodeStampManager;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnSingleCandidate(SmsSendHandler.class)
    public SmsSendStrategyFactory smsSendStrategyFactory(SmsProperties smsProperties) {
        SmsSendStrategyFactory smsSendStrategyFactory = new SmsSendStrategyFactory();
        smsSendStrategyFactory.setSmsProperties(smsProperties);
        log.trace("[Herodotus] |- Bean [Sms Send Strategy Factory] Auto Configure.");
        return smsSendStrategyFactory;
    }
}
