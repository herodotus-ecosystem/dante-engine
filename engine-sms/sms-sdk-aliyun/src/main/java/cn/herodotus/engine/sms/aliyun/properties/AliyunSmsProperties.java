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
package cn.herodotus.engine.sms.aliyun.properties;

import cn.herodotus.engine.sms.core.constants.SmsConstants;
import cn.herodotus.engine.sms.core.definition.AbstractSmsProperties;
import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: 阿里云短信配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/25 11:23
 */
@ConfigurationProperties(prefix = SmsConstants.PROPERTY_PREFIX_ALIYUN)
public class AliyunSmsProperties extends AbstractSmsProperties {

    /**
     * Endpoint
     */
    private String regionId = "cn-hangzhou";

    /**
     * accessKeyId
     */
    private String accessKeyId;

    /**
     * accessKeySecret
     */
    private String accessKeySecret;

    /**
     * 短信签名
     */
    private String signName;

    /**
     * 短信API产品域名（接口地址固定，无需修改）
     */
    private String domain = "dysmsapi.aliyuncs.com";

    private String version = "2017-05-25";

    private String product = "Dysmsapi";

    private String action = "SendSms";

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("regionId", regionId)
                .add("accessKeyId", accessKeyId)
                .add("accessKeySecret", accessKeySecret)
                .add("signName", signName)
                .add("domain", domain)
                .add("version", version)
                .add("product", product)
                .add("action", action)
                .toString();
    }
}
