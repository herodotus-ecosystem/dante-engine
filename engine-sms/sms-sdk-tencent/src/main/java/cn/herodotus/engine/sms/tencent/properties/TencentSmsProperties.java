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
package cn.herodotus.engine.sms.tencent.properties;

import cn.herodotus.engine.sms.core.constants.SmsConstants;
import cn.herodotus.engine.sms.core.definition.AbstractSmsProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: 腾讯云短信配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/26 16:22
 */
@ConfigurationProperties(prefix = SmsConstants.PROPERTY_PREFIX_TENCENT)
public class TencentSmsProperties extends AbstractSmsProperties {

    /**
     * 腾讯云 SecretID
     */
    private String secretId;

    /**
     * 腾讯云 SecretKey
     */
    private String secretKey;

    /**
     * 短信签名
     */
    private String region;

    /**
     * 短信应用ID
     */
    private String smsAppId;

    /**
     * 短信签名
     */
    private String smsSign;

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSmsAppId() {
        return smsAppId;
    }

    public void setSmsAppId(String smsAppId) {
        this.smsAppId = smsAppId;
    }

    public String getSmsSign() {
        return smsSign;
    }

    public void setSmsSign(String smsSign) {
        this.smsSign = smsSign;
    }
}
