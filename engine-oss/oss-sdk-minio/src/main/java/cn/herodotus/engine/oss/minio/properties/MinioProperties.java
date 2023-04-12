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

package cn.herodotus.engine.oss.minio.properties;

import cn.herodotus.engine.oss.core.constants.OssConstants;
import com.google.common.base.MoreObjects;
import org.apache.commons.pool2.impl.BaseObjectPoolConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * <p>Description: Minio 配置参数 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/11/8 10:31
 */
@ConfigurationProperties(prefix = OssConstants.PROPERTY_OSS_MINIO)
public class MinioProperties {

    /**
     * Minio Server URL
     */
    private String endpoint;

    /**
     * Minio Server accessKey
     */
    private String accessKey;

    /**
     * Minio Server secretKey
     */
    private String secretKey;

    private String bucketNamePrefix;

    /**
     * 文件名中时间标识内容格式。
     */
    private String timestampFormat = "yyyy-MM-dd";

    private Pool pool = new Pool();

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketNamePrefix() {
        return bucketNamePrefix;
    }

    public void setBucketNamePrefix(String bucketNamePrefix) {
        this.bucketNamePrefix = bucketNamePrefix;
    }

    public String getTimestampFormat() {
        return timestampFormat;
    }

    public void setTimestampFormat(String timestampFormat) {
        this.timestampFormat = timestampFormat;
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public static class Pool {

        /**
         * 池中的最大对象数
         */
        private Integer maxTotal = GenericObjectPoolConfig.DEFAULT_MAX_TOTAL;

        /**
         * 最多的空闲对象数
         */
        private Integer maxIdle = GenericObjectPoolConfig.DEFAULT_MAX_IDLE;

        /**
         * 最多的空闲对象数
         */
        private Integer minIdle = GenericObjectPoolConfig.DEFAULT_MIN_IDLE;

        /**
         * 对象池存放池化对象方式,true放在空闲队列最前面,false放在空闲队列最后
         */
        private Boolean lifo = true;

        /**
         * 当连接池资源耗尽时,调用者最大阻塞的时间,超时时抛出异常
         */
        private Duration maxWait = BaseObjectPoolConfig.DEFAULT_MAX_WAIT;

        /**
         * 对象池满了，是否阻塞获取（false则借不到直接抛异常）, 默认 true
         */
        private Boolean blockWhenExhausted = BaseObjectPoolConfig.DEFAULT_BLOCK_WHEN_EXHAUSTED;

        /**
         * 空闲的最小时间,达到此值后空闲连接可能会被移除, 默认30分钟
         */
        private Duration minEvictableIdleTime = BaseObjectPoolConfig.DEFAULT_MIN_EVICTABLE_IDLE_DURATION;

        private Duration softMinEvictableIdleTime = BaseObjectPoolConfig.DEFAULT_SOFT_MIN_EVICTABLE_IDLE_DURATION;

        public Integer getMaxTotal() {
            return maxTotal;
        }

        public void setMaxTotal(Integer maxTotal) {
            this.maxTotal = maxTotal;
        }

        public Integer getMaxIdle() {
            return maxIdle;
        }

        public void setMaxIdle(Integer maxIdle) {
            this.maxIdle = maxIdle;
        }

        public Integer getMinIdle() {
            return minIdle;
        }

        public void setMinIdle(Integer minIdle) {
            this.minIdle = minIdle;
        }

        public Boolean getLifo() {
            return lifo;
        }

        public void setLifo(Boolean lifo) {
            this.lifo = lifo;
        }

        public Duration getMaxWait() {
            return maxWait;
        }

        public void setMaxWait(Duration maxWait) {
            this.maxWait = maxWait;
        }

        public Boolean getBlockWhenExhausted() {
            return blockWhenExhausted;
        }

        public void setBlockWhenExhausted(Boolean blockWhenExhausted) {
            this.blockWhenExhausted = blockWhenExhausted;
        }

        public Duration getMinEvictableIdleTime() {
            return minEvictableIdleTime;
        }

        public void setMinEvictableIdleTime(Duration minEvictableIdleTime) {
            this.minEvictableIdleTime = minEvictableIdleTime;
        }

        public Duration getSoftMinEvictableIdleTime() {
            return softMinEvictableIdleTime;
        }

        public void setSoftMinEvictableIdleTime(Duration softMinEvictableIdleTime) {
            this.softMinEvictableIdleTime = softMinEvictableIdleTime;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("maxTotal", maxTotal)
                    .add("maxIdle", maxIdle)
                    .add("minIdle", minIdle)
                    .add("lifo", lifo)
                    .add("maxWait", maxWait)
                    .add("blockWhenExhausted", blockWhenExhausted)
                    .add("minEvictableIdleTime", minEvictableIdleTime)
                    .add("softMinEvictableIdleTime", softMinEvictableIdleTime)
                    .toString();
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("endpoint", endpoint)
                .add("accessKey", accessKey)
                .add("secretKey", secretKey)
                .add("bucketNamePrefix", bucketNamePrefix)
                .add("timestampFormat", timestampFormat)
                .add("pool", pool)
                .toString();
    }
}
