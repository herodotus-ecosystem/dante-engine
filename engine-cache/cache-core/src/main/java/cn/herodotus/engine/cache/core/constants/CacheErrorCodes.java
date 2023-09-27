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

package cn.herodotus.engine.cache.core.constants;

import cn.herodotus.engine.assistant.core.exception.feedback.NotAcceptableFeedback;

/**
 * <p>Description: Cache 相关错误代码 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/5/2 13:25
 */
public interface CacheErrorCodes {

    NotAcceptableFeedback STAMP_DELETE_FAILED = new NotAcceptableFeedback("从缓存中删除签章失败");
    NotAcceptableFeedback STAMP_HAS_EXPIRED = new NotAcceptableFeedback("签章已过期");
    NotAcceptableFeedback STAMP_MISMATCH = new NotAcceptableFeedback("签章信息无法匹配");
    NotAcceptableFeedback STAMP_PARAMETER_ILLEGAL = new NotAcceptableFeedback("缺少签章身份标记参数");
}
