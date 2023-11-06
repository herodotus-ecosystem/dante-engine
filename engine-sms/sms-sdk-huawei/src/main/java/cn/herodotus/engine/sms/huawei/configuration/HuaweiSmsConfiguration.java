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
package cn.herodotus.engine.sms.huawei.configuration;

import cn.herodotus.engine.sms.core.constants.SmsConstants;
import cn.herodotus.engine.sms.huawei.annotation.ConditionalOnHuaweiSmsEnabled;
import cn.herodotus.engine.sms.huawei.processor.HuaweiSmsSendHandler;
import cn.herodotus.engine.sms.huawei.properties.HuaweiSmsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: 华为云短信发送配置类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/25 14:48
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnHuaweiSmsEnabled
@EnableConfigurationProperties(HuaweiSmsProperties.class)
public class HuaweiSmsConfiguration {

    private static final Logger log = LoggerFactory.getLogger(HuaweiSmsConfiguration.class);

    /**
     * 构造华为云发送处理
     *
     * @param huaweiSmsProperties 配置对象
     * @return 华为云发送处理
     */
    @Bean(name = SmsConstants.CHANNEL_HUAWEI_CLOUD)
    public HuaweiSmsSendHandler huaweiCloudSmsSendHandler(HuaweiSmsProperties huaweiSmsProperties) {
        HuaweiSmsSendHandler huaweiSmsSendHandler = new HuaweiSmsSendHandler(huaweiSmsProperties);
        log.debug("[Herodotus] |- Bean [Huawei Sms Send Handler] Auto Configure.");
        return huaweiSmsSendHandler;
    }
}
