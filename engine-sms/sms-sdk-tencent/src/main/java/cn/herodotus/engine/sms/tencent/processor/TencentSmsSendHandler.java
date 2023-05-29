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
package cn.herodotus.engine.sms.tencent.processor;

import cn.herodotus.engine.assistant.core.utils.ListUtils;
import cn.herodotus.engine.sms.core.definition.AbstractSmsSendHandler;
import cn.herodotus.engine.sms.core.domain.Template;
import cn.herodotus.engine.sms.core.enums.SmsSupplier;
import cn.herodotus.engine.sms.core.exception.ParameterOrdersInvalidException;
import cn.herodotus.engine.sms.core.exception.TemplateIdInvalidException;
import cn.herodotus.engine.sms.tencent.properties.TencentSmsProperties;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;
import org.apache.commons.collections4.CollectionUtils;
import org.dromara.hutool.core.array.ArrayUtil;
import org.dromara.hutool.core.collection.CollUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Description: 腾讯云短信发送处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/25 15:59
 */
public class TencentSmsSendHandler extends AbstractSmsSendHandler {

    private static final String SUCCESS_CODE = "OK";

    private final SmsClient sender;
    private final TencentSmsProperties properties;

    public TencentSmsSendHandler(TencentSmsProperties properties) {
        super(properties);
        this.properties = properties;

        Credential credential = new Credential(this.properties.getSecretId(), this.properties.getSecretKey());
        sender = new SmsClient(credential, this.properties.getRegion());
    }

    @Override
    protected String getChannel() {
        return SmsSupplier.TENCENT_CLOUD.name();
    }

    @Override
    protected boolean execute(Template template, List<String> phones) throws TemplateIdInvalidException, ParameterOrdersInvalidException {
        List<List<String>> groups = CollUtil.partition(phones, 200);
        String templateId = this.getTemplateId(template);
        List<String> templateParams = this.getOrderedParams(template);
        List<List<String>> errors = groups.parallelStream().map(group -> this.send(templateId, group, templateParams)).collect(Collectors.toList());

        List<String> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(errors)) {
            for (List<String> subErrors : errors) {
                result.addAll(subErrors);
            }
        }

        return CollectionUtils.isEmpty(result);
    }

    private List<String> send(String templateId, List<String> mobileGroup, List<String> templateParams) {

        try {
            SendSmsRequest request = new SendSmsRequest();
            request.setSmsSdkAppid(this.properties.getSmsAppId());
            request.setSign(this.properties.getSmsSign());
            request.setTemplateID(templateId);
            request.setTemplateParamSet(ListUtils.toStringArray(templateParams));
            request.setPhoneNumberSet(ListUtils.toStringArray(mobileGroup));

            SendSmsResponse sendSmsResponse = sender.SendSms(request);
            if (ArrayUtil.isEmpty(sendSmsResponse.getSendStatusSet())) {
                return mobileGroup;
            } else {
                SendStatus[] sendStatuses = sendSmsResponse.getSendStatusSet();
                return Arrays.stream(sendStatuses)
                        .filter(sendStatus -> !sendStatus.getCode().equals(SUCCESS_CODE))
                        .map(SendStatus::getPhoneNumber).collect(Collectors.toList());
            }
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
