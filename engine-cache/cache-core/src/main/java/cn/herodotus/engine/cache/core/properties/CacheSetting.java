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

import cn.herodotus.engine.cache.core.enums.CacheMethod;
import com.google.common.base.MoreObjects;

import java.time.Duration;

/**
 * <p>Description: 自定义二级缓存过期时间通用属性 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/25 17:41
 */
public class CacheSetting {

    /**
     * 指定缓存区域，与 JetCache Area 配置对应。
     */
    private String area;
    /**
     * 缓存方式。默认：多级缓存
     */
    private CacheMethod method = CacheMethod.BOTH;
    /**
     * 缓存过期时间，默认两小时
     *
     * 使用Duration类型，配置参数形式如下：
     * "?ns" //纳秒
     * "?us" //微秒
     * "?ms" //毫秒
     * "?s" //秒
     * "?m" //分
     * "?h" //小时
     * "?d" //天
     */
    private Duration expire = Duration.ofHours(2);
    /**
     * 是否开启多实例本地缓存同步，默认：不开启
     * 仅在多级缓存模式下生效。
     */
    private Boolean sync = false;

    /**
     * 本地缓存过期时间
     */
    private Duration localExpire;
    /**
     * 本地缓存数量限制。
     */
    private Integer localLimit;
    /**
     * 是否开启缓存穿透保护, 默认：true
     */
    private Boolean penetrationProtect;
    /**
     * 缓存穿透保护有效期，默认：2 小时。
     */
    private Duration penetrationProtectTimeout;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public CacheMethod getMethod() {
        return method;
    }

    public void setMethod(CacheMethod method) {
        this.method = method;
    }

    public Duration getExpire() {
        return expire;
    }

    public void setExpire(Duration expire) {
        this.expire = expire;
    }

    public Boolean getSync() {
        return sync;
    }

    public void setSync(Boolean sync) {
        this.sync = sync;
    }

    public Duration getLocalExpire() {
        return localExpire;
    }

    public void setLocalExpire(Duration localExpire) {
        this.localExpire = localExpire;
    }

    public Integer getLocalLimit() {
        return localLimit;
    }

    public void setLocalLimit(Integer localLimit) {
        this.localLimit = localLimit;
    }

    public Boolean getPenetrationProtect() {
        return penetrationProtect;
    }

    public void setPenetrationProtect(Boolean penetrationProtect) {
        this.penetrationProtect = penetrationProtect;
    }

    public Duration getPenetrationProtectTimeout() {
        return penetrationProtectTimeout;
    }

    public void setPenetrationProtectTimeout(Duration penetrationProtectTimeout) {
        this.penetrationProtectTimeout = penetrationProtectTimeout;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("area", area)
                .add("method", method)
                .add("expire", expire)
                .add("sync", sync)
                .add("localExpire", localExpire)
                .add("localLimit", localLimit)
                .add("penetrationProtect", penetrationProtect)
                .add("penetrationProtectTimeout", penetrationProtectTimeout)
                .toString();
    }
}
