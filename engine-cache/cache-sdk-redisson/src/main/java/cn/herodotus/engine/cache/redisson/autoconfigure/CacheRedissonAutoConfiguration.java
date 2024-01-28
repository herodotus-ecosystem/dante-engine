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

package cn.herodotus.engine.cache.redisson.autoconfigure;

import cn.herodotus.engine.assistant.definition.constants.SymbolConstants;
import cn.herodotus.engine.assistant.core.utils.ResourceUtils;
import cn.herodotus.engine.cache.redisson.annotation.ConditionalOnRedissonEnabled;
import cn.herodotus.engine.cache.redisson.properties.RedissonProperties;
import jakarta.annotation.PostConstruct;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>Description: Redisson配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/22 13:56
 */
@AutoConfiguration
@ConditionalOnRedissonEnabled
@EnableConfigurationProperties(RedissonProperties.class)
public class CacheRedissonAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CacheRedissonAutoConfiguration.class);

    private final RedissonProperties redissonProperties;
    private final RedisProperties redisProperties;

    public CacheRedissonAutoConfiguration(RedissonProperties redissonProperties, RedisProperties redisProperties) {
        this.redissonProperties = redissonProperties;
        this.redisProperties = redisProperties;
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Cache Redisson] Auto Configure.");
    }

    private File readConfigFile() {
        String configFile = redissonProperties.getConfig();
        if (StringUtils.isNotBlank(configFile)) {
            try {
                return ResourceUtils.getFile(configFile);
            } catch (IOException e) {
                log.error("[Herodotus] |- Can not found the config file [{}], check whether the format is correct.", configFile);
            }
        }

        return null;
    }

    private Config getConfigByFile() {
        try {
            File configFile = readConfigFile();
            if (ObjectUtils.isNotEmpty(configFile)) {
                if (StringUtils.endsWithIgnoreCase(configFile.getName(), SymbolConstants.SUFFIX_YAML)) {
                    return Config.fromYAML(configFile);
                }
            }
        } catch (IOException e) {
            log.error("[Herodotus] |- Redisson loading the config file error!");
        }

        return null;
    }

    private Config getDefaultConfig() {
        Config config = new Config();

        switch (redissonProperties.getMode()) {
            case CLUSTER -> {
                ClusterServersConfig clusterServersConfig = config.useClusterServers();
                // 未配置redisson的cluster配置时，使用 spring.data.redis 的配置
                ClusterServersConfig redissonClusterConfig = redissonProperties.getClusterServersConfig();
                if (ObjectUtils.isNotEmpty(redissonClusterConfig)) {
                    BeanUtils.copyProperties(redissonClusterConfig, clusterServersConfig, ClusterServersConfig.class);
                    clusterServersConfig.setNodeAddresses(redissonClusterConfig.getNodeAddresses());
                }
                if (CollectionUtils.isEmpty(clusterServersConfig.getNodeAddresses())) {
                    // 使用 spring.data.redis 的
                    List<String> nodes = redisProperties.getCluster().getNodes();
                    nodes.stream().map(a -> redissonProperties.getProtocol() + a).forEach(clusterServersConfig::addNodeAddress);
                }
                if (StringUtils.isBlank(clusterServersConfig.getPassword())) {
                    // 使用 spring.data.redis 的
                    clusterServersConfig.setPassword(redisProperties.getPassword());
                }
            }
            case SENTINEL -> {
                SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
                SentinelServersConfig redissonSentinelConfig = redissonProperties.getSentinelServersConfig();
                if (ObjectUtils.isNotEmpty(redissonSentinelConfig)) {
                    BeanUtils.copyProperties(redissonSentinelConfig, sentinelServersConfig, SentinelServersConfig.class);
                    sentinelServersConfig.setSentinelAddresses(redissonSentinelConfig.getSentinelAddresses());
                }
                if (CollectionUtils.isEmpty(sentinelServersConfig.getSentinelAddresses())) {
                    // 使用 spring.data.redis 的配置
                    List<String> nodes = redisProperties.getSentinel().getNodes();
                    nodes.stream().map(a -> redissonProperties.getProtocol() + a).forEach(sentinelServersConfig::addSentinelAddress);
                }
                if (StringUtils.isBlank(sentinelServersConfig.getPassword())) {
                    // 使用 spring.data.redis 的配置
                    sentinelServersConfig.setPassword(redisProperties.getPassword());
                }
                if (StringUtils.isBlank(sentinelServersConfig.getMasterName())) {
                    // 使用 spring.data.redis 的配置
                    sentinelServersConfig.setMasterName(redisProperties.getSentinel().getMaster());
                }
                // database 不做处理，以免不生效
            }
            default -> {
                SingleServerConfig singleServerConfig = config.useSingleServer();
                if (ObjectUtils.isNotEmpty(redissonProperties.getSingleServerConfig())) {
                    BeanUtils.copyProperties(redissonProperties.getSingleServerConfig(), singleServerConfig, SingleServerConfig.class);
                }
                if (StringUtils.isBlank(singleServerConfig.getAddress())) {
                    // 使用 spring.data.redis 的配置
                    singleServerConfig.setAddress(redissonProperties.getProtocol() + redisProperties.getHost() + SymbolConstants.COLON + redisProperties.getPort());
                }
                if (StringUtils.isBlank(singleServerConfig.getPassword())) {
                    // 使用 spring.data.redis 的配置
                    singleServerConfig.setPassword(redisProperties.getPassword());
                }
                // 单机模式下，database使用redis的
                singleServerConfig.setDatabase(redisProperties.getDatabase());
            }
        }

        config.setCodec(new JsonJacksonCodec());
        //默认情况下，看门狗的检查锁的超时时间是30秒钟
        config.setLockWatchdogTimeout(1000 * 30);
        return config;
    }

    @Bean
    public RedissonClient redissonClient() {
        Config config = getConfigByFile();
        if (ObjectUtils.isEmpty(config)) {
            config = getDefaultConfig();
        }

        RedissonClient redissonClient = Redisson.create(config);

        log.trace("[Herodotus] |- Bean [Redisson Client] Auto Configure.");

        return redissonClient;
    }
}
