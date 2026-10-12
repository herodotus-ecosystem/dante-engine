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

package cn.herodotus.dante.logic.upms.constant;

import cn.herodotus.dante.core.constant.BaseConstants;

/**
 * <p>Description: Upms 模块常量 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/16 18:19
 */
public interface LogicUpmsConstants extends BaseConstants {

    String UPMS_AREA_PREFIX = AREA_PREFIX + "upms:";

    String REGION__SYS_USER = UPMS_AREA_PREFIX + "sys:user";
    String REGION__SYS_ROLE = UPMS_AREA_PREFIX + "sys:role";
    String REGION__SYS_DEFAULT_ROLE = UPMS_AREA_PREFIX + "sys:defaults:role";
    String REGION__SYS_PERMISSION = UPMS_AREA_PREFIX + "sys:permission";
    String REGION__SYS_OWNERSHIP = UPMS_AREA_PREFIX + "sys:ownership";
    String REGION__SYS_ELEMENT = UPMS_AREA_PREFIX + "sys:element";
    String REGION__SYS_SOCIAL_USER = UPMS_AREA_PREFIX + "sys:social:user";
    String REGION__SYS_DEPARTMENT = UPMS_AREA_PREFIX + "sys:department";
    String REGION__SYS_EMPLOYEE = UPMS_AREA_PREFIX + "sys:employee";
    String REGION__SYS_ORGANIZATION = UPMS_AREA_PREFIX + "sys:organization";
    String REGION__SYS_DICTIONARY = UPMS_AREA_PREFIX + "sys:dictionary";

    String REGION__OAUTH2_SUPPORTED_SCOPE = AREA_PREFIX + "oauth2:scope:supported";
    String REGION__OAUTH2_RESOURCE = AREA_PREFIX + "oauth2:resource";
}
