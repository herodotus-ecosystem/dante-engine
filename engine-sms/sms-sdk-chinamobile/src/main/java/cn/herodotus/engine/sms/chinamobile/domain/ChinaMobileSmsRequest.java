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

package cn.herodotus.engine.sms.chinamobile.domain;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * <p>Description: 移动短信发送请求实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/25 22:51
 */
public class ChinaMobileSmsRequest {

    private final String ecName;
    private final String apId;
    private final String templateId;
    private final String mobiles;
    private final String params;
    private final String sign;
    private final String addSerial;
    private final String mac;

    public ChinaMobileSmsRequest(String ecName, String apId, String secretKey, String templateId, String mobiles, String params, String sign) {
        this.ecName = ecName;
        this.apId = apId;
        this.templateId = templateId;
        this.mobiles = mobiles;
        this.params = params;
        this.sign = sign;
        this.addSerial = "";
        this.mac = this.generateMac(ecName, apId, secretKey, templateId, mobiles, params, sign);
    }


    private String generateMac(String ecName, String apId, String secretKey, String templateId, String mobiles, String params, String sign) {
        String origin = ecName + apId + secretKey + templateId + mobiles + params + sign;
        return DigestUtils.md5Hex(origin);
    }

    public String getEcName() {
        return ecName;
    }

    public String getApId() {
        return apId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public String getMobiles() {
        return mobiles;
    }

    public String getParams() {
        return params;
    }

    public String getSign() {
        return sign;
    }

    public String getAddSerial() {
        return addSerial;
    }

    public String getMac() {
        return mac;
    }
}
