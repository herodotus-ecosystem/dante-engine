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

package cn.herodotus.engine.assistant.definition.feedback;

import cn.herodotus.engine.assistant.definition.domain.Feedback;
import org.apache.hc.core5.http.HttpStatus;

/**
 * <p>Description: 500 类型错误反馈 </p>
 * <p>
 * 500	Internal Server Error	服务器内部错误，无法完成请求
 *
 * @author : gengwei.zheng
 * @date : 2023/9/26 8:54
 */
public class InternalServerErrorFeedback extends Feedback {
    public InternalServerErrorFeedback(String value) {
        super(value, HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
}
