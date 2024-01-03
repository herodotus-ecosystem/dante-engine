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

package cn.herodotus.engine.cache.redisson.properties;

import cn.herodotus.engine.assistant.definition.constants.SymbolConstants;
import cn.herodotus.engine.assistant.core.enums.Protocol;
import cn.herodotus.engine.cache.core.constants.CacheConstants;
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
     * 是否开启 Redisson
     */
    private Boolean enabled = false;
    /**
     * Redis 模式
     */
    private Mode mode = Mode.SINGLE;
    /**
     * 是否使用 SSL 连接。false，协议头为 redis://, true 协议头为 rediss://
     */
    private Boolean useSslConnection = false;
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

    public Boolean getUseSslConnection() {
        return useSslConnection;
    }

    public void setUseSslConnection(Boolean useSslConnection) {
        this.useSslConnection = useSslConnection;
    }

    public String getProtocol() {
        return getUseSslConnection() ? Protocol.REDISS.getFormat() : Protocol.REDIS.getFormat();
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
}
