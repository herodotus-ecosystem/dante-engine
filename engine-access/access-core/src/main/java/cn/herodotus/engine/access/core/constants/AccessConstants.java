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

package cn.herodotus.engine.access.core.constants;

import cn.herodotus.engine.assistant.definition.constants.BaseConstants;

/**
 * <p>Description: 接入模块常量 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/25 15:27
 */
public interface AccessConstants extends BaseConstants {

    String PROPERTY_ACCESS_JUSTAUTH = PROPERTY_PREFIX_ACCESS + ".justauth";
    String ITEM_JUSTAUTH_ENABLED = PROPERTY_ACCESS_JUSTAUTH + PROPERTY_ENABLED;
    String PROPERTY_ACCESS_WXAPP = PROPERTY_PREFIX_ACCESS + ".wxapp";
    String ITEM_WXAPP_ENABLED = PROPERTY_ACCESS_WXAPP + PROPERTY_ENABLED;
    String PROPERTY_ACCESS_WXMPP = PROPERTY_PREFIX_ACCESS + ".wxmpp";
    String ITEM_WXMPP_ENABLED = PROPERTY_ACCESS_WXMPP + PROPERTY_ENABLED;

    String PROPERTY_PREFIX_SMS = PROPERTY_PREFIX_ACCESS + ".sms";
    String ITEM_SMS_ENABLED = PROPERTY_PREFIX_SMS + PROPERTY_ENABLED;

    String CACHE_NAME_TOKEN_VERIFICATION_CODE = CACHE_TOKEN_BASE_PREFIX + "verification:";

    String CACHE_NAME_TOKEN_JUSTAUTH = CACHE_TOKEN_BASE_PREFIX + "justauth:";
}
