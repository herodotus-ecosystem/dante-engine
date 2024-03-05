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

package cn.herodotus.engine.access.sms.processor;

import cn.herodotus.engine.access.core.definition.AccessHandler;
import cn.herodotus.engine.access.core.definition.AccessResponse;
import cn.herodotus.engine.access.core.definition.AccessUserDetails;
import cn.herodotus.engine.access.core.exception.AccessIdentityVerificationFailedException;
import cn.herodotus.engine.access.sms.stamp.VerificationCodeStampManager;
import cn.herodotus.engine.assistant.core.constants.BaseConstants;
import cn.herodotus.engine.assistant.core.domain.AccessPrincipal;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.core.factory.SmsFactory;

import java.util.LinkedHashMap;

/**
 * <p>Description: 手机短信接入处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/26 11:46
 */
public class PhoneNumberAccessHandler implements AccessHandler {

    private final VerificationCodeStampManager verificationCodeStampManager;

    public PhoneNumberAccessHandler(VerificationCodeStampManager verificationCodeStampManager) {
        this.verificationCodeStampManager = verificationCodeStampManager;
    }

    @Override
    public AccessResponse preProcess(String phone, String... params) {
        String code = verificationCodeStampManager.create(phone);
        boolean result;
        if (verificationCodeStampManager.getSandbox()) {
            result = true;
        } else {
            SmsBlend smsBlend = SmsFactory.getSmsBlend();
            LinkedHashMap<String, String> message = new LinkedHashMap<>();
            message.put(BaseConstants.CODE, code);
            SmsResponse smsResponse = smsBlend.sendMessage(phone, verificationCodeStampManager.getVerificationCodeTemplateId(), message);
            result = smsResponse.isSuccess();
        }

        AccessResponse accessResponse = new AccessResponse();
        accessResponse.setSuccess(result);
        return accessResponse;
    }

    @Override
    public AccessUserDetails loadUserDetails(String source, AccessPrincipal accessPrincipal) {
        boolean isCodeOk = verificationCodeStampManager.check(accessPrincipal.getMobile(), accessPrincipal.getCode());
        if (isCodeOk) {
            AccessUserDetails accessUserDetails = new AccessUserDetails();
            accessUserDetails.setUuid(accessPrincipal.getMobile());
            accessUserDetails.setPhoneNumber(accessPrincipal.getMobile());
            accessUserDetails.setUsername(accessPrincipal.getMobile());
            accessUserDetails.setSource(source);

            verificationCodeStampManager.delete(accessPrincipal.getMobile());
            return accessUserDetails;
        }

        throw new AccessIdentityVerificationFailedException("Phone Verification Code Error!");
    }
}
