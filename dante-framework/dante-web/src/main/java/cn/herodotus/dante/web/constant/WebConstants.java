/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 是 Dante Cloud 系统核心组件库，采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dante.web.constant;

import cn.herodotus.dante.core.constant.BaseConstants;

/**
 * <p>Description: Rest 模块常量 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/19 23:13
 */
public interface WebConstants extends BaseConstants {

    String[] SESSION_IDS = new String[]{"JSESSIONID, SESSION"};

    String PROPERTY_PREFIX_SWAGGER = PROPERTY_PREFIX_PLATFORM + ".swagger";
    String PROPERTY_SERVICE_SCANNER = PROPERTY_PREFIX_SERVICE + ".scanner";
    String PROPERTY_SECURE_REST_AUDIT = PROPERTY_PREFIX_SECURE + ".audit";

    String PROPERTY_SCANNER_REST = PROPERTY_SERVICE_SCANNER + ".rest";
    String PROPERTY_SCANNER_GRPC = PROPERTY_SERVICE_SCANNER + ".grpc";
    String PROPERTY_SCANNER_TASK = PROPERTY_SERVICE_SCANNER + ".task";
    String PROPERTY_SCANNER_MCP = PROPERTY_SERVICE_SCANNER + ".mcp";

    String CACHE_NAME_TOKEN_IDEMPOTENT = CACHE_TOKEN_BASE_PREFIX + "idempotent:";
    String CACHE_NAME_TOKEN_ACCESS_LIMITED = CACHE_TOKEN_BASE_PREFIX + "access_limited:";
    String CACHE_NAME_TOKEN_SECURE_KEY = CACHE_TOKEN_BASE_PREFIX + "secure_key:";
    String CACHE_NAME_TOKEN_SIGNATURE = CACHE_TOKEN_BASE_PREFIX + "signature:";
}
