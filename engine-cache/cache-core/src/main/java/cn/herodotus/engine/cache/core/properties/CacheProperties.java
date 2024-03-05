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

package cn.herodotus.engine.cache.core.properties;

import cn.herodotus.engine.cache.core.constants.CacheConstants;
import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: 缓存配置属性 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/13 10:16
 */
@ConfigurationProperties(prefix = CacheConstants.PROPERTY_PREFIX_CACHE)
public class CacheProperties extends CacheSetting {

    /**
     * 是否允许存储空值
     */
    private Boolean allowNullValues = true;

    /**
     * 缓存名称转换分割符。默认值，"-"
     * <p>
     * 默认缓存名称采用 Redis Key 格式（使用 ":" 分割），使用 ":" 分割的字符串作为Map的Key，":"会丢失。
     * 指定一个分隔符，用于 ":" 分割符的转换
     */
    private String separator = "-";

    /**
     * 针对不同实体单独设置的过期时间，如果不设置，则统一使用默认时间。
     */
    private Map<String, CacheSetting> instances = new HashMap<>();

    public Boolean getAllowNullValues() {
        return allowNullValues;
    }

    public void setAllowNullValues(Boolean allowNullValues) {
        this.allowNullValues = allowNullValues;
    }

    public Map<String, CacheSetting> getInstances() {
        return instances;
    }

    public void setInstances(Map<String, CacheSetting> instances) {
        this.instances = instances;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("allowNullValues", allowNullValues)
                .add("separator", separator)
                .toString();
    }
}
