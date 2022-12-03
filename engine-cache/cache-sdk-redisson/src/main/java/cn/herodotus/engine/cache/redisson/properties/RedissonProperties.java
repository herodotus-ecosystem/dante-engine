/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
 * 2.请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.cache.redisson.properties;

import cn.herodotus.engine.cache.core.constants.CacheConstants;
import cn.herodotus.engine.assistant.core.definition.constants.SymbolConstants;
import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: Redisson 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/22 14:02
 */
@ConfigurationProperties(prefix = CacheConstants.PROPERTY_REDIS_REDISSON)
public class RedissonProperties {

    /**
     * Redisson 使用模式
     */
    public enum Mode {
        /**
         * 单机
         */
        SINGLE,
        /**
         * 哨兵
         */
        SENTINEL,
        /**
         * 集群
         */
        CLUSTER
    }

    /**
     * 是否开启 Redisson
     */
    private Boolean enabled = false;

    /**
     * Redis 模式
     */
    private Mode mode = Mode.SINGLE;

    /**
     * 配置文件路径
     */
    private String config;

    /**
     * 单体配置
     */
    private SingleServerConfig singleServerConfig;
    /**
     * 集群配置
     */
    private ClusterServersConfig clusterServersConfig;

    /**
     * 哨兵配置
     */
    private SentinelServersConfig sentinelServersConfig;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public SingleServerConfig getSingleServerConfig() {
        return singleServerConfig;
    }

    public void setSingleServerConfig(SingleServerConfig singleServerConfig) {
        this.singleServerConfig = singleServerConfig;
    }

    public ClusterServersConfig getClusterServersConfig() {
        return clusterServersConfig;
    }

    public void setClusterServersConfig(ClusterServersConfig clusterServersConfig) {
        this.clusterServersConfig = clusterServersConfig;
    }

    public SentinelServersConfig getSentinelServersConfig() {
        return sentinelServersConfig;
    }

    public void setSentinelServersConfig(SentinelServersConfig sentinelServersConfig) {
        this.sentinelServersConfig = sentinelServersConfig;
    }

    public boolean isExternalConfig() {
        return StringUtils.isNotBlank(this.getConfig());
    }

    public boolean isYamlConfig() {
        if (this.isExternalConfig()) {
            return StringUtils.endsWithIgnoreCase(this.getConfig(), SymbolConstants.SUFFIX_YAML);
        } else {
            return false;
        }
    }

    public boolean isJsonConfig() {
        if (this.isExternalConfig()) {
            return StringUtils.endsWithIgnoreCase(this.getConfig(), SymbolConstants.SUFFIX_JSON);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("enabled", enabled)
                .add("mode", mode)
                .add("config", config)
                .toString();
    }
}
