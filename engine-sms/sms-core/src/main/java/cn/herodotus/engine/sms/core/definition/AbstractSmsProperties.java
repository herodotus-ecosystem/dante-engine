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

import org.apache.commons.collections4.MapUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>Description: 抽象配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/24 22:07
 */
public abstract class AbstractSmsProperties implements SmsProperties {

    /**
     * 是否开启
     */
    private Boolean enabled;
    /**
     * 短信模板
     */
    private Map<String, String> templates;

    /**
     * 参数顺序
     */
    private Map<String, List<String>> orders;

    @Override
    public Map<String, String> getTemplates() {
        return templates;
    }

    public void setTemplates(Map<String, String> templates) {
        this.templates = templates;
    }

    public Map<String, List<String>> getOrders() {
        return orders;
    }

    public void setOrders(Map<String, List<String>> orders) {
        this.orders = orders;
    }

    /**
     * 获取短信模板
     *
     * @param type 类型
     * @return 短信模板
     */
    @Override
    public String getTemplates(String type) {
        if (MapUtils.isNotEmpty(this.templates) && this.templates.containsKey(type)) {
            return this.templates.get(type);
        }
        return null;
    }

    /**
     * 返回参数顺序
     *
     * @param type 类型
     * @return 参数顺序
     */
    @Override
    public List<String> getOrders(String type) {
        if (MapUtils.isNotEmpty(this.orders) && this.orders.containsKey(type)) {
            return this.orders.get(type);
        }
        return null;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
