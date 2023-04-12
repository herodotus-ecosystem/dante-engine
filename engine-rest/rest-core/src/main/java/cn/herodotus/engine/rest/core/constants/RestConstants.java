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

package cn.herodotus.engine.rest.core.constants;

import cn.herodotus.engine.assistant.core.definition.constants.BaseConstants;

/**
 * <p>Description: Rest 模块常量 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/19 23:13
 */
public interface RestConstants extends BaseConstants {


    String PROPERTY_FEIGN_OKHTTP = PROPERTY_PREFIX_FEIGN + ".okhttp";
    String PROPERTY_FEIGN_HTTPCLIENT = PROPERTY_PREFIX_FEIGN + ".httpclient";
    String PROPERTY_REST_SCAN = PROPERTY_PREFIX_REST + ".scan";

    String ITEM_SCAN_ENABLED = PROPERTY_REST_SCAN + PROPERTY_ENABLED;
    String ITEM_FEIGN_OKHTTP_ENABLED = PROPERTY_FEIGN_OKHTTP + PROPERTY_ENABLED;
    String ITEM_FEIGN_HTTPCLIENT_ENABLED = PROPERTY_FEIGN_HTTPCLIENT + PROPERTY_ENABLED;
    String ITEM_PROTECT_CRYPTO_STRATEGY = PROPERTY_PREFIX_CRYPTO + ".crypto-strategy";

    String CACHE_NAME_TOKEN_IDEMPOTENT = CACHE_TOKEN_BASE_PREFIX + "idempotent:";
    String CACHE_NAME_TOKEN_ACCESS_LIMITED = CACHE_TOKEN_BASE_PREFIX + "access_limited:";
    String CACHE_NAME_TOKEN_SECURE_KEY = CACHE_TOKEN_BASE_PREFIX + "secure_key:";
}
