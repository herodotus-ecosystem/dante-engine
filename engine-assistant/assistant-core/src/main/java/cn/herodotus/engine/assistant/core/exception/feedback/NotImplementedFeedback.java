/*
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君) (herodotus@aliyun.com & www.herodotus.cn)
 *
 * Dante Engine licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.gnu.org/licenses/lgpl.html>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.herodotus.engine.assistant.core.exception.feedback;

import cn.herodotus.engine.assistant.core.domain.Feedback;
import org.apache.hc.core5.http.HttpStatus;

/**
 * <p>Description: 501 类型错误反馈 </p>
 * <p>
 * 501	Not Implemented	服务器不支持请求的功能，无法完成请求
 *
 * @author : gengwei.zheng
 * @date : 2023/9/26 8:54
 */
public class NotImplementedFeedback extends Feedback {
    public NotImplementedFeedback(String value) {
        super(value, HttpStatus.SC_NOT_IMPLEMENTED);
    }

    public NotImplementedFeedback(String value, int custom) {
        super(value, HttpStatus.SC_NOT_IMPLEMENTED, custom);
    }
}
