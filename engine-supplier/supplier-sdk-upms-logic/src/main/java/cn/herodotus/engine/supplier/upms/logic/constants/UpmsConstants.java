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

package cn.herodotus.engine.supplier.upms.logic.constants;

import cn.herodotus.engine.assistant.definition.constants.BaseConstants;

/**
 * <p>Description: Upms 模块常量 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/16 18:19
 */
public interface UpmsConstants extends BaseConstants {

    String UPMS_AREA_PREFIX = AREA_PREFIX + "upms:";

    String REGION_SYS_USER = UPMS_AREA_PREFIX + "sys:user";
    String REGION_SYS_ROLE = UPMS_AREA_PREFIX + "sys:role";
    String REGION_SYS_DEFAULT_ROLE = UPMS_AREA_PREFIX + "sys:defaults:role";
    String REGION_SYS_PERMISSION = UPMS_AREA_PREFIX + "sys:permission";
    String REGION_SYS_OWNERSHIP = UPMS_AREA_PREFIX + "sys:ownership";
    String REGION_SYS_ELEMENT = UPMS_AREA_PREFIX + "sys:element";
    String REGION_SYS_SOCIAL_USER = UPMS_AREA_PREFIX + "sys:social:user";
    String REGION_SYS_DEPARTMENT = UPMS_AREA_PREFIX + "sys:department";
    String REGION_SYS_EMPLOYEE = UPMS_AREA_PREFIX + "sys:employee";
    String REGION_SYS_ORGANIZATION = UPMS_AREA_PREFIX + "sys:organization";
}
