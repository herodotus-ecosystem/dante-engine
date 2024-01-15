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

package cn.herodotus.engine.assistant.definition.domain;

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
    private Duration minEvictableIdleDuration = BaseObjectPoolConfig.DEFAULT_MIN_EVICTABLE_IDLE_DURATION;

    private Duration softMinEvictableIdleDuration = BaseObjectPoolConfig.DEFAULT_SOFT_MIN_EVICTABLE_IDLE_DURATION;

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

    public Duration getMinEvictableIdleDuration() {
        return minEvictableIdleDuration;
    }

    public void setMinEvictableIdleDuration(Duration minEvictableIdleDuration) {
        this.minEvictableIdleDuration = minEvictableIdleDuration;
    }

    public Duration getSoftMinEvictableIdleDuration() {
        return softMinEvictableIdleDuration;
    }

    public void setSoftMinEvictableIdleDuration(Duration softMinEvictableIdleDuration) {
        this.softMinEvictableIdleDuration = softMinEvictableIdleDuration;
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
                .add("minEvictableIdleTime", minEvictableIdleDuration)
                .add("softMinEvictableIdleTime", softMinEvictableIdleDuration)
                .toString();
    }
}
