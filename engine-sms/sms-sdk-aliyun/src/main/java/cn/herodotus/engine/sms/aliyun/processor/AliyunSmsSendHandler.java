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
package cn.herodotus.engine.sms.aliyun.processor;

import cn.herodotus.engine.assistant.core.json.jackson2.utils.Jackson2Utils;
import cn.herodotus.engine.sms.aliyun.properties.AliyunSmsProperties;
import cn.herodotus.engine.sms.core.definition.AbstractSmsSendHandler;
import cn.herodotus.engine.sms.core.domain.Template;
import cn.herodotus.engine.sms.core.enums.SmsSupplier;
import cn.herodotus.engine.sms.core.exception.ParameterOrdersInvalidException;
import cn.herodotus.engine.sms.core.exception.TemplateIdInvalidException;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Description: 阿里云短信发送处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/26 10:43
 */
public class AliyunSmsSendHandler extends AbstractSmsSendHandler {

    private static final Logger log = LoggerFactory.getLogger(AliyunSmsSendHandler.class);

    private final IAcsClient iAcsClient;
    private final AliyunSmsProperties properties;

    /**
     * 构造阿里云短信发送处理
     *
     * @param properties 阿里云短信配置
     */
    public AliyunSmsSendHandler(AliyunSmsProperties properties) {
        super(properties);
        this.properties = properties;

        IClientProfile profile = DefaultProfile.getProfile(this.properties.getRegionId(), this.properties.getAccessKeyId(), this.properties.getAccessKeySecret());
        DefaultProfile.addEndpoint(this.properties.getRegionId(), this.properties.getProduct(), this.properties.getDomain());

        iAcsClient = new DefaultAcsClient(profile);
    }

    @Override
    protected String getChannel() {
        return SmsSupplier.ALIYUN.name();
    }

    @Override
    public boolean execute(Template template, List<String> phones) throws TemplateIdInvalidException, ParameterOrdersInvalidException {

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(this.properties.getDomain());
        request.setSysVersion(this.properties.getVersion());
        request.setSysAction(this.properties.getAction());
        request.putQueryParameter("PhoneNumbers", join(phones));
        request.putQueryParameter("SignName", this.properties.getSignName());
        request.putQueryParameter("TemplateCode", this.getTemplateId(template));
        request.putQueryParameter("TemplateParam", Jackson2Utils.toJson(template.getParams()));

        try {
            CommonResponse response = iAcsClient.getCommonResponse(request);

            return ObjectUtils.isNotEmpty(response) && response.getHttpResponse().isSuccess();
        } catch (ClientException e) {
            log.error("[Herodotus] |- [{}] Send Sms Catch Exception: {}", this.getChannel(), e.getMessage());
        }

        return false;
    }
}
