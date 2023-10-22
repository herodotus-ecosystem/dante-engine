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
package cn.herodotus.engine.sms.core.definition;

import cn.herodotus.engine.sms.core.domain.Template;

import java.util.List;

/**
 * <p>Description: 发送处理 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/24 22:00
 */
public interface SmsSendHandler {

    /**
     * 发送短信
     *
     * @param template 短信模版填充相关内容
     * @param phones   手机号码，以“，”分割的多个手机号码字符串
     * @return true 发送成功，false发送失败
     */
    boolean send(Template template, List<String> phones);
}
