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

package cn.herodotus.engine.rest.condition.constants;


import cn.herodotus.engine.assistant.core.context.PropertyResolver;
import cn.herodotus.engine.assistant.core.definition.constants.BaseConstants;
import org.springframework.core.env.Environment;

/**
 * <p>Description: 策略模块配置获取器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/1 19:23
 */
public class RestPropertyFinder {

    public static String getApplicationName(Environment environment) {
        return PropertyResolver.getProperty(environment, BaseConstants.ITEM_SPRING_APPLICATION_NAME);
    }

    public static String getCryptoStrategy(Environment environment, String defaultValue) {
        return PropertyResolver.getProperty(environment, RestConstants.ITEM_PROTECT_CRYPTO_STRATEGY, defaultValue);
    }

    public static String getCryptoStrategy(Environment environment) {
        return PropertyResolver.getProperty(environment, RestConstants.ITEM_PROTECT_CRYPTO_STRATEGY);
    }

    public static boolean isScanEnabled(Environment environment) {
        return PropertyResolver.getBoolean(environment, RestConstants.ITEM_SCAN_ENABLED);
    }

    public static boolean isOpenFeignOkHttpEnabled(Environment environment) {
        return PropertyResolver.getBoolean(environment, RestConstants.ITEM_OPENFEIGN_OKHTTP_ENABLED);
    }

    public static boolean isOpenFeignHttpClientEnabled(Environment environment) {
        return PropertyResolver.getBoolean(environment, RestConstants.ITEM_OPENFEIGN_HTTPCLIENT_ENABLED);
    }

    public static String getDataAccessStrategy(Environment environment, String defaultValue) {
        return PropertyResolver.getProperty(environment, RestConstants.ITEM_PLATFORM_DATA_ACCESS_STRATEGY, defaultValue);
    }

    public static String getDataAccessStrategy(Environment environment) {
        return PropertyResolver.getProperty(environment, RestConstants.ITEM_PLATFORM_DATA_ACCESS_STRATEGY);
    }

    public static String getArchitecture(Environment environment, String defaultValue) {
        return PropertyResolver.getProperty(environment, RestConstants.ITEM_PLATFORM_ARCHITECTURE, defaultValue);
    }

    public static String getArchitecture(Environment environment) {
        return PropertyResolver.getProperty(environment, RestConstants.ITEM_PLATFORM_ARCHITECTURE);
    }
}
