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
