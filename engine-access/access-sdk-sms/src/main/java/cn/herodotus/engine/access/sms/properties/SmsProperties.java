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

package cn.herodotus.engine.access.sms.properties;

import cn.herodotus.engine.access.core.constants.AccessConstants;
import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * <p>Description: 短信验证码配置属性 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/26 17:02
 */
@ConfigurationProperties(prefix = AccessConstants.PROPERTY_PREFIX_SMS)
public class SmsProperties {

    /**
     * 是否开启
     */
    private Boolean enabled;

    /**
     * 启用短信沙盒测试模式
     */
    private Boolean sandbox = false;

    /**
     * 短信沙盒测试模式中，创建的默认验证码。
     */
    private String testCode = "123456";

    /**
     * 验证码短信模版名称
     */
    private String verificationCodeTemplateId = "VERIFICATION_CODE";

    /**
     * 超时时长，默认5分钟
     */
    private Duration expire = Duration.ofMinutes(5);

    /**
     * 手机验证码长度，默认为6位数
     */
    private int length = 6;

    public Duration getExpire() {
        return expire;
    }

    public void setExpire(Duration expire) {
        this.expire = expire;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getSandbox() {
        return sandbox;
    }

    public void setSandbox(Boolean sandbox) {
        this.sandbox = sandbox;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getVerificationCodeTemplateId() {
        return verificationCodeTemplateId;
    }

    public void setVerificationCodeTemplateId(String verificationCodeTemplateId) {
        this.verificationCodeTemplateId = verificationCodeTemplateId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("enabled", enabled)
                .add("sandbox", sandbox)
                .add("testCode", testCode)
                .add("verificationCodeTemplateId", verificationCodeTemplateId)
                .add("expire", expire)
                .add("length", length)
                .toString();
    }
}
