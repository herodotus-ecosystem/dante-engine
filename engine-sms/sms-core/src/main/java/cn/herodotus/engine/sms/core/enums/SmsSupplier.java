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

package cn.herodotus.engine.sms.core.enums;

import cn.herodotus.engine.sms.core.constants.SmsConstants;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 短信提供商列表 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/26 10:00
 */
public enum SmsSupplier {
    /**
     * 短信供应商
     */
    ALIYUN(SmsConstants.CHANNEL_ALIYUN, "阿里云短信"),
    BAIDU_CLOUD(SmsConstants.CHANNEL_BAIDU_CLOUD, "百度云短信"),
    CHINA_MOBILE(SmsConstants.CHANNEL_CHINA_MOBILE, "中国移动短信"),
    HUAWEI_CLOUD(SmsConstants.CHANNEL_HUAWEI_CLOUD, "华为云短信"),
    JD_CLOUD(SmsConstants.CHANNEL_JD_CLOUD, "京东云短信"),
    JPUSH(SmsConstants.CHANNEL_JPUSH, "极光短信"),
    NETEASE_CLOUD(SmsConstants.CHANNEL_NETEASE_CLOUD, "网易云短信"),
    QINIU(SmsConstants.CHANNEL_QINIU, "七牛短信"),
    TENCENT_CLOUD(SmsConstants.CHANNEL_TENCENT_CLOUD, "腾讯云短信"),
    UPYUN(SmsConstants.CHANNEL_UPYUN, "又拍短信"),
    RECLUSE(SmsConstants.CHANNEL_RECLUSE, "内部短信");


    private final String channel;
    private final String description;

    private static final Map<Integer, SmsSupplier> indexMap = new HashMap<>();
    private static final List<Map<String, Object>> toJsonStruct = new ArrayList<>();

    static {
        for (SmsSupplier smsSupplier : SmsSupplier.values()) {
            indexMap.put(smsSupplier.ordinal(), smsSupplier);
            toJsonStruct.add(smsSupplier.ordinal(),
                    ImmutableMap.<String, Object>builder()
                            .put("value", smsSupplier.getChannel())
                            .put("key", smsSupplier.name())
                            .put("text", smsSupplier.getDescription())
                            .build());
        }
    }

    SmsSupplier(String channel, String description) {
        this.channel = channel;
        this.description = description;
    }

    /**
     * 不加@JsonValue，转换的时候转换出完整的对象。
     * 加了@JsonValue，只会显示相应的属性的值
     * <p>
     * 不使用@JsonValue @JsonDeserializer类里面要做相应的处理
     *
     * @return Enum索引
     */
    @JsonValue
    public String getChannel() {
        return channel;
    }

    public String getDescription() {
        return this.description;
    }

    public static SmsSupplier getSmsSupplier(Integer index) {
        return indexMap.get(index);
    }

    public static List<Map<String, Object>> getToJsonStruct() {
        return toJsonStruct;
    }
}
