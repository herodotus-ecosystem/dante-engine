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
package cn.herodotus.engine.sms.qiniu.processor;

import cn.herodotus.engine.sms.core.definition.AbstractSmsSendHandler;
import cn.herodotus.engine.sms.core.domain.Template;
import cn.herodotus.engine.sms.core.enums.SmsSupplier;
import cn.herodotus.engine.sms.core.exception.ParameterOrdersInvalidException;
import cn.herodotus.engine.sms.core.exception.TemplateIdInvalidException;
import cn.herodotus.engine.sms.qiniu.properties.QiniuSmsProperties;
import com.qiniu.http.Response;
import com.qiniu.sms.SmsManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * <p>Description: 七牛云短信发送处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/25 16:00
 */
public class QiniuSmsSendHandler extends AbstractSmsSendHandler {

    private static final Logger log = LoggerFactory.getLogger(QiniuSmsSendHandler.class);

    private final SmsManager smsManager;
    private final QiniuSmsProperties properties;

    public QiniuSmsSendHandler(QiniuSmsProperties properties) {
        super(properties);
        this.properties = properties;

        Auth auth = Auth.create(this.properties.getAccessKey(), this.properties.getSecretKey());
        smsManager = new SmsManager(auth);
    }

    @Override
    protected String getChannel() {
        return SmsSupplier.QINIU.name();
    }

    @Override
    protected boolean execute(Template template, List<String> phones) throws TemplateIdInvalidException, ParameterOrdersInvalidException {

        String[] mobiles = convertPhonesToArray(phones);
        String templateId = this.getTemplateId(template);
        Map<String, String> templateParams = template.getParams();

        try {
            Response response = smsManager.sendMessage(templateId, mobiles, templateParams);

            return response.isOK();

        } catch (Exception e) {
            log.debug(e.getLocalizedMessage(), e);
        }

        return false;
    }
}
