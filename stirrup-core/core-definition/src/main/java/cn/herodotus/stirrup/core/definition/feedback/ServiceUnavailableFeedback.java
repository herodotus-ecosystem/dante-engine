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

package cn.herodotus.stirrup.core.definition.feedback;

import cn.herodotus.stirrup.core.definition.domain.Feedback;
import org.apache.hc.core5.http.HttpStatus;

/**
 * <p>Description: 503 类型错误反馈 </p>
 * <p>
 * 503	Service Unavailable	由于超载或系统维护，服务器暂时的无法处理客户端的请求。延时的长度可包含在服务器的Retry-After头信息中
 *
 * @author : gengwei.zheng
 * @date : 2023/9/26 8:54
 */
public class ServiceUnavailableFeedback extends Feedback {
    public ServiceUnavailableFeedback(String value) {
        super(value, HttpStatus.SC_SERVICE_UNAVAILABLE);
    }
}