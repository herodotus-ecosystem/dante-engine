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

package cn.herodotus.engine.cache.core.constants;

import cn.herodotus.engine.assistant.definition.feedback.NotAcceptableFeedback;

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
