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

package cn.herodotus.engine.access.core.constants;

import cn.herodotus.engine.assistant.definition.feedback.PreconditionFailedFeedback;

/**
 * <p>Description: Access 模块错误代码 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/9/2 17:50
 */
public interface AccessErrorCodes {

    PreconditionFailedFeedback ACCESS_CONFIG_ERROR = new PreconditionFailedFeedback("Access 模块配置错误");
    PreconditionFailedFeedback ACCESS_HANDLER_NOT_FOUND = new PreconditionFailedFeedback("Access 模块接入处理器未找到错误");
    PreconditionFailedFeedback ACCESS_IDENTITY_VERIFICATION_FAILED = new PreconditionFailedFeedback("接入身份认证错误");
    PreconditionFailedFeedback ACCESS_PRE_PROCESS_FAILED_EXCEPTION = new PreconditionFailedFeedback("接入预操作失败错误");

    PreconditionFailedFeedback ILLEGAL_ACCESS_ARGUMENT = new PreconditionFailedFeedback("社交登录参数错误");
    PreconditionFailedFeedback ILLEGAL_ACCESS_SOURCE = new PreconditionFailedFeedback("社交登录Source参数错误");
}
