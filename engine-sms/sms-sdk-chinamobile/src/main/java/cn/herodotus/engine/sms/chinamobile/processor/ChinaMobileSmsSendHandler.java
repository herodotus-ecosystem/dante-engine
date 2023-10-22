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
package cn.herodotus.engine.sms.chinamobile.processor;

import cn.herodotus.engine.sms.chinamobile.domain.ChinaMobileSmsRequest;
import cn.herodotus.engine.sms.chinamobile.properties.ChinaMobileSmsProperties;
import cn.herodotus.engine.sms.core.definition.AbstractSmsSendHandler;
import cn.herodotus.engine.sms.core.domain.Template;
import cn.herodotus.engine.sms.core.enums.SmsSupplier;
import cn.herodotus.engine.sms.core.exception.ParameterOrdersInvalidException;
import cn.herodotus.engine.sms.core.exception.TemplateIdInvalidException;
import cn.zhxu.okhttps.HttpResult;
import cn.zhxu.okhttps.OkHttps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Description: 移动云发送处理 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/25 14:57
 */
public class ChinaMobileSmsSendHandler extends AbstractSmsSendHandler {

    private static final Logger log = LoggerFactory.getLogger(ChinaMobileSmsSendHandler.class);

    private final ChinaMobileSmsProperties properties;

    public ChinaMobileSmsSendHandler(ChinaMobileSmsProperties properties) {
        super(properties);
        this.properties = properties;
    }

    @Override
    protected String getChannel() {
        return SmsSupplier.CHINA_MOBILE.name();
    }

    @Override
    protected boolean execute(Template template, List<String> phones) throws TemplateIdInvalidException, ParameterOrdersInvalidException {
        String templateId = this.getTemplateId(template);
        String templateParams = this.getOrderedParamsString(template);

        ChinaMobileSmsRequest request = new ChinaMobileSmsRequest(
                this.properties.getEcName(),
                this.properties.getApId(),
                this.properties.getSecretKey(),
                templateId,
                join(phones),
                templateParams,
                this.properties.getSign());

        HttpResult result = this.http().sync(this.properties.getUri())
                .bodyType(OkHttps.FORM)
                .setBodyPara(request)
                .nothrow()
                .post();

        return result.isSuccessful();
    }
}
