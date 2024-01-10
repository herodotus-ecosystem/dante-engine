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

package cn.herodotus.engine.sms.core.constants;

import cn.herodotus.stirrup.kernel.definition.constants.BaseConstants;

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
