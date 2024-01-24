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

package cn.herodotus.stirrup.core.definition.constants;

/**
 * <p>Description: 配置类顺序控制 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/1/24 23:06
 */
public interface ConfigureOrdered {

    int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;

    int LOWEST_PRECEDENCE = Integer.MAX_VALUE;

    /**
     * Spring Boot Web 启动顺序 {@code WebMvcAutoConfiguration}
     */
    int SPRING_BOOT_WEB = HIGHEST_PRECEDENCE + 10;
    /**
     * core-spring-boot-starter 启动顺序
     */
    int CORE_STARTER = HIGHEST_PRECEDENCE;
    /**
     * web-spring-boot-starter 启动顺序
     */
    int WEB_STARTER = SPRING_BOOT_WEB + 10;
    /**
     * webflux-spring-boot-starter 启动顺序
     */
    int WEBFLUX_STARTER = SPRING_BOOT_WEB + 10;

}
