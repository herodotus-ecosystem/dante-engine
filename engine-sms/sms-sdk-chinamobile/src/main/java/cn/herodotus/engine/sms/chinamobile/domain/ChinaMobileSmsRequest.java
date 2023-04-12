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
