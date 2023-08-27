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

package cn.herodotus.engine.sms.autoconfigure.processor;

import cn.herodotus.engine.sms.autoconfigure.properties.SmsProperties;
import cn.herodotus.engine.sms.core.definition.SmsSendHandler;
import cn.herodotus.engine.sms.core.domain.Template;
import cn.herodotus.engine.sms.core.enums.SmsSupplier;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Description: 短信发送工厂 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/26 16:52
 */
@Component
public class SmsSendStrategyFactory {

    private static final Logger log = LoggerFactory.getLogger(SmsSendStrategyFactory.class);
    @Autowired
    private final Map<String, SmsSendHandler> handlers = new ConcurrentHashMap<>();
    private SmsProperties smsProperties;

    public void setSmsProperties(SmsProperties smsProperties) {
        this.smsProperties = smsProperties;
    }

    public boolean send(Template template, String phone) {
        SmsSupplier smsSupplier = smsProperties.getDefaultChannel();
        if (ObjectUtils.isNotEmpty(smsSupplier)) {
            return this.send(smsSupplier.name(), template, phone);
        } else {
            log.error("[Herodotus] |- Default sms channel is not correct!");
            return false;
        }
    }

    public boolean send(Template template, List<String> phones) {
        SmsSupplier smsSupplier = smsProperties.getDefaultChannel();
        if (ObjectUtils.isNotEmpty(smsSupplier)) {
            return this.send(smsSupplier.name(), template, phones);
        } else {
            log.error("[Herodotus] |- Default sms channel is not correct!");
            return false;
        }
    }

    public boolean send(String channel, Template template, String phone) {
        SmsSendHandler handler = handlers.get(channel);
        if (ObjectUtils.isNotEmpty(handler)) {
            return handler.send(template, ImmutableList.of(phone));
        } else {
            log.error("[Herodotus] |- Sms channel [{}] is not config!", channel);
            return false;
        }
    }

    public boolean send(String channel, Template template, List<String> phones) {
        SmsSendHandler handler = handlers.get(channel);
        if (ObjectUtils.isNotEmpty(handler)) {
            return handler.send(template, phones);
        } else {
            log.error("[Herodotus] |- Sms channel [{}] is not config!", channel);
            return false;
        }
    }


}
