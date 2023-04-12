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

import org.apache.commons.lang3.ObjectUtils;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * <p>Description: 自定义二级缓存过期时间通用属性 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/25 17:41
 */
public class Expire {

    /**
     * 统一缓存时长，默认：1
     */
    private Long duration = 1L;

    /**
     * 统一缓存时长单位，默认：小时。
     */
    private TimeUnit unit = TimeUnit.HOURS;

    /**
     * Redis缓存TTL设置，默认：1小时，单位小时
     * <p>
     * 使用Duration类型，配置参数形式如下：
     * "?ns" //纳秒
     * "?us" //微秒
     * "?ms" //毫秒
     * "?s" //秒
     * "?m" //分
     * "?h" //小时
     * "?d" //天
     */
    private Duration ttl;

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }

    public Duration getTtl() {
        if (ObjectUtils.isEmpty(this.ttl)) {
            this.ttl = convertToDuration(this.duration, this.unit);
        }
        return ttl;
    }

    private Duration convertToDuration(Long duration, TimeUnit timeUnit) {
        switch (timeUnit) {
            case DAYS:
                return Duration.ofDays(duration);
            case HOURS:
                return Duration.ofHours(duration);
            case SECONDS:
                return Duration.ofSeconds(duration);
            default:
                return Duration.ofMinutes(duration);
        }
    }
}
