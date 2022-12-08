/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
 * 2.请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 6.若您的项目无法满足以上几点，可申请商业授权
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
import org.apache.http.HttpHeaders;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
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
public class HuaweiSmsSendHandler extends AbstractSmsSendHandler {

    private static final Logger log = LoggerFactory.getLogger(HuaweiSmsSendHandler.class);

    /**
     * 无需修改,用于格式化鉴权头域,给"X-WSSE"参数赋值
     */
    private static final String WSSE_HEADER_FORMAT = "UsernameToken Username=\"%s\",PasswordDigest=\"%s\",Nonce=\"%s\",Created=\"%s\"";

    /**
     * 无需修改,用于格式化鉴权头域,给"Authorization"参数赋值
     */
    private static final String AUTH_HEADER_VALUE = "WSSE realm=\"SDP\",profile=\"UsernameToken\",type=\"Appkey\"";

    private final HuaweiSmsProperties properties;

    public HuaweiSmsSendHandler(HuaweiSmsProperties properties) {
        super(properties);
        this.properties = properties;
    }

    /**
     * 构造X-WSSE参数值
     *
     * @return X-WSSE参数值
     */
    private String buildWsseHeader() {
        String appKey = this.properties.getAppKey();
        String appSecret = this.properties.getAppSecret();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String time = sdf.format(new Date());
        String nonce = UUID.randomUUID().toString().replace("-", "");

        byte[] passwordDigest = DigestUtils.sha256(nonce + time + appSecret);
        String hexDigest = Hex.encodeHexString(passwordDigest);

        String passwordDigestBase64Str = Base64.getEncoder().encodeToString(hexDigest.getBytes());

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
        request.setFrom(this.properties.getSender());
        request.setTo(mobiles);
        request.setTemplateId(templateId);
        request.setTemplateParas(templateParams);
        request.setSignature(this.properties.getSignature());


        HttpResult result = this.http().sync(this.properties.getUri())
                .bodyType(OkHttps.JSON)
                .addHeader(HttpHeaders.AUTHORIZATION, AUTH_HEADER_VALUE)
                .addHeader("X-WSSE", wsseHeader)
                .setBodyPara(request)
                .nothrow()
                .post();

        if (result.isSuccessful()) {
            HuaweiSmsResponse huaweiSmsResponse = result.getBody().toBean(HuaweiSmsResponse.class);
            if (ObjectUtils.isNotEmpty(huaweiSmsResponse) && HuaweiSmsResponse.SUCCESS_CODE.equals(huaweiSmsResponse.getCode())) {
                return true;
            }
        }

        return false;
    }
}
