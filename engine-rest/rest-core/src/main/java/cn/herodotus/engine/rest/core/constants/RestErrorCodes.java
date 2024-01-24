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

package cn.herodotus.engine.rest.core.constants;

import cn.herodotus.stirrup.core.definition.feedback.NotAcceptableFeedback;

/**
 * <p>Description: Cache 相关错误代码 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/5/2 13:25
 */
public interface RestErrorCodes {

    NotAcceptableFeedback SESSION_INVALID = new NotAcceptableFeedback("Session已过期，请刷新再试");
    NotAcceptableFeedback REPEAT_SUBMISSION = new NotAcceptableFeedback("提交进行中，请不要重复提交");
    NotAcceptableFeedback FREQUENT_REQUESTS = new NotAcceptableFeedback("请求频繁，请稍后再试");
    NotAcceptableFeedback FEIGN_DECODER_IO_EXCEPTION = new NotAcceptableFeedback("Feign 解析 Fallback 错误信息出错");

}
