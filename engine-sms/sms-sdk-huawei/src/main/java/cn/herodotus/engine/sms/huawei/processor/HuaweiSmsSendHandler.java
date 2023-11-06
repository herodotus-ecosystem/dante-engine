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
package cn.herodotus.engine.sms.huawei.processor;

import cn.herodotus.engine.sms.core.definition.AbstractSmsSendHandler;
import cn.herodotus.engine.sms.core.domain.Template;
import cn.herodotus.engine.sms.core.enums.SmsSupplier;
import cn.herodotus.engine.sms.core.exception.ParameterOrdersInvalidException;
import cn.herodotus.engine.sms.core.exception.TemplateIdInvalidException;
import cn.herodotus.engine.sms.huawei.domain.HuaweiSmsRequest;
import cn.herodotus.engine.sms.huawei.domain.HuaweiSmsResponse;
import cn.herodotus.engine.sms.huawei.properties.HuaweiSmsProperties;
import cn.zhxu.okhttps.HttpResult;
import cn.zhxu.okhttps.OkHttps;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.hc.core5.http.HttpHeaders;
import org.dromara.hutool.core.util.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>Description: 华为云发送处理 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/25 14:47
 */
public class HuaweiSmsSendHandler extends AbstractSmsSendHandler<HuaweiSmsProperties> {

    private static final Logger log = LoggerFactory.getLogger(HuaweiSmsSendHandler.class);

    /**
     * 无需修改,用于格式化鉴权头域,给"X-WSSE"参数赋值
     */
    private static final String WSSE_HEADER_FORMAT = "UsernameToken Username=\"%s\",PasswordDigest=\"%s\",Nonce=\"%s\",Created=\"%s\"";

    /**
     * 无需修改,用于格式化鉴权头域,给"Authorization"参数赋值
     */
    private static final String AUTH_HEADER_VALUE = "WSSE realm=\"SDP\",profile=\"UsernameToken\",type=\"Appkey\"";

    public HuaweiSmsSendHandler(HuaweiSmsProperties properties) {
        super(properties);

    }

    /**
     * 构造X-WSSE参数值
     *
     * @return X-WSSE参数值
     */
    private String buildWsseHeader() {
        String appKey = this.getSmsProperties().getAppKey();
        String appSecret = this.getSmsProperties().getAppSecret();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String time = sdf.format(new Date());
        String nonce = UUID.randomUUID().toString().replace("-", "");

        byte[] passwordDigest = DigestUtils.sha256(nonce + time + appSecret);
        String hexDigest = Hex.encodeHexString(passwordDigest);

        String passwordDigestBase64Str = Base64.getEncoder().encodeToString(ByteUtil.toUtf8Bytes(hexDigest));

        return String.format(WSSE_HEADER_FORMAT, appKey, passwordDigestBase64Str, nonce, time);
    }

    @Override
    protected String getChannel() {
        return SmsSupplier.HUAWEI_CLOUD.name();
    }

    @Override
    protected boolean execute(Template template, List<String> phones) throws TemplateIdInvalidException, ParameterOrdersInvalidException {

        String templateId = this.getTemplateId(template);
        String mobiles = this.wellFormed(phones);
        String templateParams = this.getOrderedParamsString(template);

        String wsseHeader = buildWsseHeader();

        HuaweiSmsRequest request = new HuaweiSmsRequest();
        request.setFrom(this.getSmsProperties().getSender());
        request.setTo(mobiles);
        request.setTemplateId(templateId);
        request.setTemplateParas(templateParams);
        request.setSignature(this.getSmsProperties().getSignature());


        HttpResult result = this.http().sync(this.getSmsProperties().getUri())
                .bodyType(OkHttps.JSON)
                .addHeader(HttpHeaders.AUTHORIZATION, AUTH_HEADER_VALUE)
                .addHeader("X-WSSE", wsseHeader)
                .setBodyPara(request)
                .nothrow()
                .post();

        if (result.isSuccessful()) {
            HuaweiSmsResponse huaweiSmsResponse = result.getBody().toBean(HuaweiSmsResponse.class);
            return ObjectUtils.isNotEmpty(huaweiSmsResponse) && HuaweiSmsResponse.SUCCESS_CODE.equals(huaweiSmsResponse.getCode());
        }

        return false;
    }
}
