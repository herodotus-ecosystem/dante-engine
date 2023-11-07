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

package cn.herodotus.engine.sms.autoconfigure;

import cn.herodotus.engine.sms.aliyun.configuration.AliyunSmsConfiguration;
import cn.herodotus.engine.sms.autoconfigure.annotation.ConditionalOnSmsEnabled;
import cn.herodotus.engine.sms.autoconfigure.processor.SmsSendStrategyFactory;
import cn.herodotus.engine.sms.autoconfigure.properties.SmsProperties;
import cn.herodotus.engine.sms.autoconfigure.stamp.VerificationCodeStampManager;
import cn.herodotus.engine.sms.chinamobile.configuration.ChinaMobileSmsConfiguration;
import cn.herodotus.engine.sms.core.definition.SmsSendHandler;
import cn.herodotus.engine.sms.huawei.configuration.HuaweiSmsConfiguration;
import cn.herodotus.engine.sms.netease.configuration.NeteaseSmsConfiguration;
import cn.herodotus.engine.sms.qiniu.configuration.QiniuSmsConfiguration;
import cn.herodotus.engine.sms.tencent.configuration.TencentSmsConfiguration;
import cn.herodotus.engine.sms.upyun.configuration.UpyunSmsConfiguration;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * <p>Description: 发送短信统一配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/25 12:03
 */
@AutoConfiguration
@ConditionalOnSmsEnabled
@EnableConfigurationProperties({SmsProperties.class})
@Import({
        AliyunSmsConfiguration.class,
        ChinaMobileSmsConfiguration.class,
        HuaweiSmsConfiguration.class,
        NeteaseSmsConfiguration.class,
        QiniuSmsConfiguration.class,
        TencentSmsConfiguration.class,
        UpyunSmsConfiguration.class,
})
public class SmsAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SmsAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Module [Sms Starter] Auto Configure.");
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
