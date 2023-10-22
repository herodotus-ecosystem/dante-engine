/*
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君) (herodotus@aliyun.com & www.herodotus.cn)
 *
 * Dante Engine licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.gnu.org/licenses/lgpl.html>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.herodotus.engine.rest.core.annotation;

import java.lang.annotation.*;
import java.time.Duration;

/**
 * <p>Description: 接口防刷注解 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/25 21:45
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface AccessLimited {

    /**
     * 单位时间内同一个接口可以访问的次数
     *
     * @return int
     */
    int maxTimes() default 0;

    /**
     * 持续时间，即在多长时间内，限制访问多少次。具体单位根据TimeUnit的设置而定。
     * <p>
     * 使用Duration格式{@link Duration}
     * <p>
     * 默认为：0，即不设置该属性。那么就使用StampProperies中的配置进行设置。
     * 如果设置了该值，就以该值进行设置。
     */
    String duration() default "";
}
