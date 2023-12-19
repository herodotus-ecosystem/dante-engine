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

package cn.herodotus.engine.assistant.core.context;

import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;

/**
 * <p>Description: 配置信息读取工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/7 13:39
 */
public class PropertyResolver {

    /**
     * 从环境信息中获取配置信息
     *
     * @param environment Spring Boot Environment {@link Environment}
     * @param property    配置名称
     * @return 配置属性值
     */
    public static String getProperty(Environment environment, String property) {
        return environment.getProperty(property);
    }

    /**
     * 从环境信息中获取配置信息
     *
     * @param environment  Spring Boot Environment {@link Environment}
     * @param property     配置名称
     * @param defaultValue 默认值
     * @return 配置属性值
     */
    public static String getProperty(Environment environment, String property, String defaultValue) {
        return environment.getProperty(property, defaultValue);
    }

    /**
     * 从条件上下文中获取配置信息
     *
     * @param conditionContext Spring Boot ConditionContext {@link ConditionContext}
     * @param property         配置名称
     * @return 配置属性值
     */
    public static String getProperty(ConditionContext conditionContext, String property) {
        return getProperty(conditionContext.getEnvironment(), property);
    }

    /**
     * 从条件上下文中获取配置信息
     *
     * @param conditionContext Spring Boot ConditionContext {@link ConditionContext}
     * @param property         配置名称
     * @param defaultValue     默认值
     * @return 配置属性值
     */
    public static String getProperty(ConditionContext conditionContext, String property, String defaultValue) {
        return getProperty(conditionContext.getEnvironment(), property, defaultValue);
    }

    public static <T> T getProperty(Environment environment, String property, Class<T> targetType) {
        return environment.getProperty(property, targetType);
    }

    public static <T> T getProperty(Environment environment, String property, Class<T> targetType, T defaultValue) {
        return environment.getProperty(property, targetType, defaultValue);
    }

    public static <T> T getProperty(ConditionContext conditionContext, String property, Class<T> targetType) {
        return getProperty(conditionContext.getEnvironment(), property, targetType);
    }

    public static <T> T getProperty(ConditionContext conditionContext, String property, Class<T> targetType, T defaultValue) {
        return getProperty(conditionContext.getEnvironment(), property, targetType, defaultValue);
    }

    public static boolean contains(Environment environment, String property) {
        return environment.containsProperty(property);
    }

    public static boolean contains(ConditionContext conditionContext, String property) {
        return contains(conditionContext.getEnvironment(), property);
    }

    /**
     * 从条件上下文中获取Boolean类型值配置信息
     *
     * @param environment  Spring Boot ConditionContext {@link ConditionContext}
     * @param property     配置名称
     * @param defaultValue 默认值
     * @return 配置属性值
     */
    public static boolean getBoolean(Environment environment, String property, boolean defaultValue) {
        return getProperty(environment, property, Boolean.class, defaultValue);
    }

    /**
     * 从条件上下文中获取Boolean类型值配置信息
     *
     * @param environment Spring Boot ConditionContext {@link ConditionContext}
     * @param property    配置名称
     * @return 配置属性值
     */
    public static boolean getBoolean(Environment environment, String property) {
        return getProperty(environment, property, Boolean.class, false);
    }

    /**
     * 从条件上下文中获取Boolean类型值配置信息
     *
     * @param conditionContext Spring Boot ConditionContext {@link ConditionContext}
     * @param property         配置名称
     * @return 配置属性值
     */
    public static boolean getBoolean(ConditionContext conditionContext, String property) {
        return getProperty(conditionContext, property, Boolean.class, false);
    }

    /**
     * 从条件上下文中获取Boolean类型值配置信息
     *
     * @param conditionContext Spring Boot ConditionContext {@link ConditionContext}
     * @param property         配置名称
     * @param defaultValue     默认值
     * @return 配置属性值
     */
    public static boolean getBoolean(ConditionContext conditionContext, String property, boolean defaultValue) {
        return getProperty(conditionContext, property, Boolean.class, defaultValue);
    }
}
