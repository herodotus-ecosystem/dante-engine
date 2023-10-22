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
package cn.herodotus.engine.sms.aliyun.configuration;

import cn.herodotus.engine.sms.aliyun.annotation.ConditionalOnAliyunSmsEnabled;
import cn.herodotus.engine.sms.aliyun.processor.AliyunSmsSendHandler;
import cn.herodotus.engine.sms.aliyun.properties.AliyunSmsProperties;
import cn.herodotus.engine.sms.core.constants.SmsConstants;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: 阿里云短信发送配置类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/25 12:08
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnAliyunSmsEnabled
@EnableConfigurationProperties(AliyunSmsProperties.class)
public class AliyunSmsConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AliyunSmsConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Sms Aliyun] Auto Configure.");
    }

    /**
     * 构造阿里云发送处理
     *
     * @param aliyunSmsProperties 配置对象
     * @return 阿里云发送处理
     */
    @Bean(name = SmsConstants.CHANNEL_ALIYUN)
    public AliyunSmsSendHandler aliyunSmsSendHandler(AliyunSmsProperties aliyunSmsProperties) {
        AliyunSmsSendHandler aliyunSmsSendHandler = new AliyunSmsSendHandler(aliyunSmsProperties);
        log.debug("[Herodotus] |- Bean [Aliyun Sms Send Handler] Auto Configure.");
        return aliyunSmsSendHandler;
    }
}
