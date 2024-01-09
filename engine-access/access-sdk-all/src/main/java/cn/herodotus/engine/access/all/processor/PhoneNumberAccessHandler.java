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

package cn.herodotus.engine.access.all.processor;

import cn.herodotus.engine.access.core.definition.AccessHandler;
import cn.herodotus.engine.access.core.definition.AccessResponse;
import cn.herodotus.engine.access.core.definition.AccessUserDetails;
import cn.herodotus.engine.access.core.exception.AccessIdentityVerificationFailedException;
import cn.herodotus.stirrup.kernel.definition.constants.BaseConstants;
import cn.herodotus.engine.assistant.core.domain.AccessPrincipal;
import cn.herodotus.engine.sms.autoconfigure.processor.SmsSendStrategyFactory;
import cn.herodotus.engine.sms.autoconfigure.stamp.VerificationCodeStampManager;
import cn.herodotus.engine.sms.core.domain.Template;
import com.google.common.collect.ImmutableMap;

/**
 * <p>Description: 手机短信接入处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/26 11:46
 */
public class PhoneNumberAccessHandler implements AccessHandler {

    private final VerificationCodeStampManager verificationCodeStampManager;
    private final SmsSendStrategyFactory smsSendStrategyFactory;

    public PhoneNumberAccessHandler(VerificationCodeStampManager verificationCodeStampManager, SmsSendStrategyFactory smsSendStrategyFactory) {
        this.verificationCodeStampManager = verificationCodeStampManager;
        this.smsSendStrategyFactory = smsSendStrategyFactory;
    }

    @Override
    public AccessResponse preProcess(String core, String... params) {
        String code = verificationCodeStampManager.create(core);
        boolean result;
        if (verificationCodeStampManager.getSandbox()) {
            result = true;
        } else {
            Template template = new Template();
            template.setType(verificationCodeStampManager.getVerificationCodeTemplateId());
            template.setParams(ImmutableMap.of(BaseConstants.CODE, code));
            result = smsSendStrategyFactory.send(template, core);
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
