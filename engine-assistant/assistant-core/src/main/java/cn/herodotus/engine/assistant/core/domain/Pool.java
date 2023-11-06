/*
 * Copyright (c) 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
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
 * Dante OSS 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/herodotus/dante-oss>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/herodotus/dante-oss>
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.assistant.core.domain;

import com.google.common.base.MoreObjects;
import org.apache.commons.pool2.impl.BaseObjectPoolConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.time.Duration;

/**
 * <p>Description: 对象池通用配置参数实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/14 16:23
 */
public class Pool {

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
