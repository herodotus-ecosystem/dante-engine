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

package cn.herodotus.engine.sms.core.constants;

import cn.herodotus.engine.assistant.core.definition.constants.BaseConstants;

/**
 * <p>Description: 短信相关常量 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/25 11:38
 */
public interface SmsConstants extends BaseConstants {
    String ITEM_SMS_ENABLED = PROPERTY_PREFIX_SMS + PROPERTY_ENABLED;

    String CACHE_NAME_TOKEN_VERIFICATION_CODE = CACHE_TOKEN_BASE_PREFIX + "verification:";

    String REGION_SMS_TEMPLATE = AREA_PREFIX + "sms:template";

    String CHANNEL_ALIYUN = "ALIYUN";
    String CHANNEL_BAIDU_CLOUD = "BAIDU";
    String CHANNEL_CHINA_MOBILE = "CHINA_MOBILE";
    String CHANNEL_HUAWEI_CLOUD = "HUAWEI";
    String CHANNEL_JD_CLOUD = "JD";
    String CHANNEL_JPUSH = "JPUSH";
    String CHANNEL_NETEASE_CLOUD = "NETEASE";
    String CHANNEL_QINIU = "QINIU";
    String CHANNEL_TENCENT_CLOUD = "TENCENT";
    String CHANNEL_UPYUN = "UPYUN";
    String CHANNEL_RECLUSE = "RECLUSE";

    String PROPERTY_PREFIX_ALIYUN = PROPERTY_PREFIX_SMS + ".aliyun";
    String ITEM_ALIYUN_ENABLED = PROPERTY_PREFIX_ALIYUN + PROPERTY_ENABLED;

    String PROPERTY_PREFIX_BAIDU = PROPERTY_PREFIX_SMS + ".baidu";
    String ITEM_BAIDU_ENABLED = PROPERTY_PREFIX_BAIDU + PROPERTY_ENABLED;

    String PROPERTY_PREFIX_CHINA_MOBILE = PROPERTY_PREFIX_SMS + ".chinamobile";
    String ITEM_CHINA_MOBILE_ENABLED = PROPERTY_PREFIX_CHINA_MOBILE + PROPERTY_ENABLED;

    String PROPERTY_PREFIX_HUAWEI = PROPERTY_PREFIX_SMS + ".huawei";
    String ITEM_HUAWEI_ENABLED = PROPERTY_PREFIX_HUAWEI + PROPERTY_ENABLED;

    String PROPERTY_PREFIX_JD = PROPERTY_PREFIX_SMS + ".jd";
    String ITEM_JD_ENABLED = PROPERTY_PREFIX_JD + PROPERTY_ENABLED;

    String PROPERTY_PREFIX_JPUSH = PROPERTY_PREFIX_SMS + ".jpush";
    String ITEM_JPUSH_ENABLED = PROPERTY_PREFIX_JPUSH + PROPERTY_ENABLED;

    String PROPERTY_PREFIX_NETEASE = PROPERTY_PREFIX_SMS + ".netease";
    String ITEM_NETEASE_ENABLED = PROPERTY_PREFIX_NETEASE + PROPERTY_ENABLED;

    String PROPERTY_PREFIX_TENCENT = PROPERTY_PREFIX_SMS + ".tencent";
    String ITEM_TENCENT_ENABLED = PROPERTY_PREFIX_TENCENT + PROPERTY_ENABLED;

    String PROPERTY_PREFIX_QINIU = PROPERTY_PREFIX_SMS + ".qiniu";
    String ITEM_QINIU_ENABLED = PROPERTY_PREFIX_QINIU + PROPERTY_ENABLED;

    String PROPERTY_PREFIX_UPYUN = PROPERTY_PREFIX_SMS + ".upyun";
    String ITEM_UPYUN_ENABLED = PROPERTY_PREFIX_UPYUN + PROPERTY_ENABLED;

    String PROPERTY_PREFIX_RECLUSE = PROPERTY_PREFIX_SMS + ".recluse";
    String ITEM_RECLUSE_ENABLED = PROPERTY_PREFIX_RECLUSE + PROPERTY_ENABLED;
}
