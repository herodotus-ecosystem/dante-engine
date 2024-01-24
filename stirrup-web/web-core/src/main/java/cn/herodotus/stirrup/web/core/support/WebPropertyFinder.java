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

package cn.herodotus.stirrup.web.core.support;

import cn.herodotus.stirrup.core.foundation.context.PropertyResolver;
import cn.herodotus.stirrup.web.core.constants.WebConstants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ConditionContext;

/**
 * <p>Description: Web模块配置获取器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/1/24 17:29
 */
public class WebPropertyFinder {

    public static String getApplicationName(ApplicationContext applicationContext) {
        return PropertyResolver.getProperty(applicationContext.getEnvironment(), WebConstants.ITEM_SPRING_APPLICATION_NAME);
    }

    public static String getDataAccessStrategy(ConditionContext conditionContext, String defaultValue) {
        return PropertyResolver.getProperty(conditionContext, WebConstants.ITEM_PLATFORM_DATA_ACCESS_STRATEGY, defaultValue);
    }

    public static String getDataAccessStrategy(ConditionContext conditionContext) {
        return PropertyResolver.getProperty(conditionContext, WebConstants.ITEM_PLATFORM_DATA_ACCESS_STRATEGY);
    }

    public static String getArchitecture(ConditionContext conditionContext, String defaultValue) {
        return PropertyResolver.getProperty(conditionContext, WebConstants.ITEM_PLATFORM_ARCHITECTURE, defaultValue);
    }

    public static String getArchitecture(ConditionContext conditionContext) {
        return PropertyResolver.getProperty(conditionContext, WebConstants.ITEM_PLATFORM_ARCHITECTURE);
    }

    public static boolean isScanEnabled(ConditionContext conditionContext) {
        return PropertyResolver.getBoolean(conditionContext, WebConstants.ITEM_SCAN_ENABLED);
    }
}
