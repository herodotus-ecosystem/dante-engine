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
package cn.herodotus.engine.sms.netease.processor;

import cn.herodotus.engine.sms.core.definition.AbstractSmsSendHandler;
import cn.herodotus.engine.sms.core.domain.Template;
import cn.herodotus.engine.sms.core.enums.SmsSupplier;
import cn.herodotus.engine.sms.core.exception.ParameterOrdersInvalidException;
import cn.herodotus.engine.sms.core.exception.TemplateIdInvalidException;
import cn.herodotus.engine.sms.netease.domain.NeteaseSmsResponse;
import cn.herodotus.engine.sms.netease.properties.NeteaseSmsProperties;
import cn.zhxu.okhttps.HttpResult;
import cn.zhxu.okhttps.OkHttps;
import org.apache.commons.lang3.ObjectUtils;
import org.dromara.hutool.core.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * <p>Description: 网易云短信发送处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/25 16:00
 */
public class NeteaseSmsSendHandler extends AbstractSmsSendHandler<NeteaseSmsProperties> {

    private static final Logger log = LoggerFactory.getLogger(NeteaseSmsSendHandler.class);

    public NeteaseSmsSendHandler(NeteaseSmsProperties neteaseSmsProperties) {
        super(neteaseSmsProperties);
    }

    @Override
    protected String getChannel() {
        return SmsSupplier.NETEASE_CLOUD.name();
    }

    @Override
    protected boolean execute(Template template, List<String> phones) throws TemplateIdInvalidException, ParameterOrdersInvalidException {

        String templateId = this.getTemplateId(template);
        String templateParams = this.getOrderedParamsString(template);
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String nonce = RandomUtil.randomString(6);
        String checkSum = CheckSumBuilder.getCheckSum(this.getSmsProperties().getAppSecret(), nonce, curTime);

        HttpResult result = this.http().sync(this.getSmsProperties().getApiUrl())
                .bodyType(OkHttps.FORM)
                .addHeader("AppKey", this.getSmsProperties().getAppKey())
                .addHeader("CurTime", curTime)
                .addHeader("CheckSum", checkSum)
                .addHeader("Nonce", nonce)
                .addBodyPara("templateid", templateId)
                .addBodyPara("mobiles", join(phones))
                .addBodyPara("params", templateParams)
                .nothrow()
                .post();

        if (result.isSuccessful()) {
            NeteaseSmsResponse neteaseSmsResponse = result.getBody().toBean(NeteaseSmsResponse.class);
            return ObjectUtils.isNotEmpty(neteaseSmsResponse) && NeteaseSmsResponse.SUCCESS_CODE.equals(neteaseSmsResponse.getCode());
        }

        return false;
    }
}
