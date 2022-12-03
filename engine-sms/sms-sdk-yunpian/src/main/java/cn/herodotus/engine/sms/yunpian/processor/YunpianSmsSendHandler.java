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
package cn.herodotus.engine.sms.yunpian.processor;

import cn.herodotus.engine.assistant.core.definition.constants.SymbolConstants;
import cn.herodotus.engine.sms.core.definition.AbstractSmsSendHandler;
import cn.herodotus.engine.sms.core.domain.Template;
import cn.herodotus.engine.sms.core.enums.SmsSupplier;
import cn.herodotus.engine.sms.core.exception.ParameterOrdersInvalidException;
import cn.herodotus.engine.sms.core.exception.TemplateIdInvalidException;
import cn.herodotus.engine.sms.yunpian.properties.YunpianSmsProperties;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * <p>Description: 云片网短信发送处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/25 15:58
 */
public class YunpianSmsSendHandler extends AbstractSmsSendHandler {

    private static final Logger log = LoggerFactory.getLogger(YunpianSmsSendHandler.class);

    private final YunpianClient client;
    private final YunpianSmsProperties properties;

    public YunpianSmsSendHandler(YunpianSmsProperties properties) {
        super(properties);
        this.properties = properties;

        client = new YunpianClient(properties.getApikey()).init();
    }

    @Override
    protected String getChannel() {
        return SmsSupplier.YUNPIAN.name();
    }

    @Override
    protected boolean execute(Template template, List<String> phones) throws TemplateIdInvalidException, ParameterOrdersInvalidException {

        Map<String, String> params = template.getParams();
        List<String> list = new ArrayList<>();

        if (MapUtils.isNotEmpty(params)) {
            params.forEach((key, value) -> {
                list.add(encode(key, value));
            });
        }

        String templateParams = this.join(list, SymbolConstants.AMPERSAND);

        String mobile = join(phones);

        Map<String, String> data = new HashMap<>(8);
        data.put("apikey", properties.getApikey());
        data.put("mobile", mobile);
        data.put("tpl_id", this.getTemplateId(template));
        data.put("tpl_value", templateParams);

        Result<?> result;

        if (StringUtils.contains(mobile, SymbolConstants.COMMA)) {
            result = client.sms().batch_send(data);
        } else {
            result = client.sms().single_send(data);
        }

        return Objects.equals(result.getCode(), 0);
    }

    private String encode(String key, String value) {
        String prefix = encode(SymbolConstants.POUND + key + SymbolConstants.POUND);
        String suffix = encode(value);
        return prefix + SymbolConstants.EQUAL + suffix;
    }

    private String encode(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
    }
}
