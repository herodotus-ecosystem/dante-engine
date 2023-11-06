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

package cn.herodotus.engine.rest.core.annotation;

import java.lang.annotation.*;

/**
 * <p>Description: 幂等标识注解 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/22 14:25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Idempotent {

    /**
     * 过期时间，即幂等签章有效时间。使用Duration格式配置。。
     * <p>
     * 默认为：空，即不设置该属性。那么就使用StampProperies中的配置进行设置。
     * 如果设置了该值，就以该值进行设置。
     *
     * @return {@link Long}
     */
    String expire() default "PT5S";
}
