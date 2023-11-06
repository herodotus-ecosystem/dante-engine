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

package cn.herodotus.engine.sms.core.definition;

import java.util.List;
import java.util.Map;

/**
 * <p>Description: 短信属性定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/25 18:52
 */
public interface SmsProperties {

    /**
     * 获取短信模版
     *
     * @return 短信模版
     */
    Map<String, String> getTemplates();

    /**
     * 根据类别获取短信模版
     *
     * @param type 模版类别
     * @return 与模版类别对应的短信模版
     */
    String getTemplates(String type);

    /**
     * 短信模版顺序定义
     *
     * @param type 模版类别
     * @return 与模版类别对应的顺序定义
     */
    List<String> getOrders(String type);
}
