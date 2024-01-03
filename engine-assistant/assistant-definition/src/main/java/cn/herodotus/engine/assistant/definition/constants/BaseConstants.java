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

package cn.herodotus.engine.assistant.definition.constants;

/**
 * <p>Description: 基础共用常量值常量 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/13 21:18
 */
public interface BaseConstants {

    String NONE = "none";
    String CODE = "code";

    /* ---------- 配置属性通用常量 ---------- */

    String PROPERTY_ENABLED = ".enabled";
    String PROPERTY_PREFIX_SPRING = "spring";
    String PROPERTY_PREFIX_HERODOTUS = "herodotus";

    String PROPERTY_SPRING_CLOUD = PROPERTY_PREFIX_SPRING + ".cloud";
    String PROPERTY_SPRING_CLOUD_OPENFEIGN = PROPERTY_SPRING_CLOUD + ".openfeign";
    String PROPERTY_SPRING_DATA = PROPERTY_PREFIX_SPRING + ".data";
    String PROPERTY_SPRING_DATA_REDIS = PROPERTY_SPRING_DATA + ".redis";
    String PROPERTY_SPRING_JPA = PROPERTY_PREFIX_SPRING + ".jpa";
    String ANNOTATION_PREFIX = "${";
    String ANNOTATION_SUFFIX = "}";

    /* ---------- Herodotus 自定义配置属性 ---------- */
    String PROPERTY_PREFIX_ACCESS = PROPERTY_PREFIX_HERODOTUS + ".access";
    String PROPERTY_PREFIX_API = PROPERTY_PREFIX_HERODOTUS + ".api";
    String PROPERTY_PREFIX_CACHE = PROPERTY_PREFIX_HERODOTUS + ".cache";
    String PROPERTY_PREFIX_CAPTCHA = PROPERTY_PREFIX_HERODOTUS + ".captcha";
    String PROPERTY_PREFIX_CLIENT = PROPERTY_PREFIX_HERODOTUS + ".client";
    String PROPERTY_PREFIX_CRYPTO = PROPERTY_PREFIX_HERODOTUS + ".crypto";
    String PROPERTY_PREFIX_DATA = PROPERTY_PREFIX_HERODOTUS + ".data";
    String PROPERTY_PREFIX_ENDPOINT = PROPERTY_PREFIX_HERODOTUS + ".endpoint";
    String PROPERTY_PREFIX_EVENT = PROPERTY_PREFIX_HERODOTUS + ".event";
    String PROPERTY_PREFIX_IP2REGION = PROPERTY_PREFIX_HERODOTUS + ".ip2region";
    String PROPERTY_PREFIX_LOG_CENTER = PROPERTY_PREFIX_HERODOTUS + ".log-center";
    String PROPERTY_PREFIX_MESSAGE = PROPERTY_PREFIX_HERODOTUS + ".message";
    String PROPERTY_PREFIX_OAUTH2 = PROPERTY_PREFIX_HERODOTUS + ".oauth2";
    String PROPERTY_PREFIX_OSS = PROPERTY_PREFIX_HERODOTUS + ".oss";
    String PROPERTY_PREFIX_PAY = PROPERTY_PREFIX_HERODOTUS + ".pay";
    String PROPERTY_PREFIX_PLATFORM = PROPERTY_PREFIX_HERODOTUS + ".platform";
    String PROPERTY_PREFIX_REST = PROPERTY_PREFIX_HERODOTUS + ".rest";
    String PROPERTY_PREFIX_SECURE = PROPERTY_PREFIX_HERODOTUS + ".secure";
    String PROPERTY_PREFIX_SMS = PROPERTY_PREFIX_HERODOTUS + ".sms";
    String PROPERTY_PREFIX_SWAGGER = PROPERTY_PREFIX_HERODOTUS + ".swagger";
    String PROPERTY_PREFIX_TSDB = PROPERTY_PREFIX_HERODOTUS + ".tsdb";
    String PROPERTY_PREFIX_IOT = PROPERTY_PREFIX_HERODOTUS + ".iot";

    /* ---------- Spring 家族配置属性 ---------- */

    String ITEM_SWAGGER_ENABLED = PROPERTY_PREFIX_SWAGGER + PROPERTY_ENABLED;
    String ITEM_SPRING_APPLICATION_NAME = PROPERTY_PREFIX_SPRING + ".application.name";
    String ITEM_SPRING_SESSION_REDIS = PROPERTY_PREFIX_SPRING + ".session.redis.repository-type";

    String ANNOTATION_APPLICATION_NAME = ANNOTATION_PREFIX + ITEM_SPRING_APPLICATION_NAME + ANNOTATION_SUFFIX;


    /* ---------- 通用缓存常量 ---------- */

    String CACHE_PREFIX = "cache:";
    String CACHE_SIMPLE_BASE_PREFIX = CACHE_PREFIX + "simple:";
    String CACHE_TOKEN_BASE_PREFIX = CACHE_PREFIX + "token:";

    String AREA_PREFIX = "data:";


    /* ---------- Oauth2 和 Security 通用缓存常量 ---------- */

    /**
     * Oauth2 模式类型
     */
    String PASSWORD = "password";
    String SOCIAL_CREDENTIALS = "social_credentials";

    String OPEN_API_SECURITY_SCHEME_BEARER_NAME = "HERODOTUS_AUTH";

    String BEARER_TYPE = "Bearer";
    String BEARER_TOKEN = BEARER_TYPE + SymbolConstants.SPACE;
    String BASIC_TYPE = "Basic";
    String BASIC_TOKEN = BASIC_TYPE + SymbolConstants.SPACE;
    String AUTHORITIES = "authorities";
    String AVATAR = "avatar";
    String EMPLOYEE_ID = "employeeId";
    String LICENSE = "license";
    String OPEN_ID = "openid";
    String PRINCIPAL = "principal";
    String ROLES = "roles";
    String SOURCE = "source";
    String USERNAME = "username";
}
