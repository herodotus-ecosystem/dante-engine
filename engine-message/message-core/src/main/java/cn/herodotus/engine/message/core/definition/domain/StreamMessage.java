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

package cn.herodotus.engine.message.core.definition.domain;

import cn.herodotus.engine.message.core.definition.domain.Message;
import org.springframework.util.MimeType;

/**
 * <p>Description: Spring Cloud Stream 类型消息参数实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/10/26 14:57
 */
public class StreamMessage implements Message {

    private String bindingName;
    private String binderName;
    private Object data;
    private MimeType outputContentType;

    public String getBindingName() {
        return bindingName;
    }

    public void setBindingName(String bindingName) {
        this.bindingName = bindingName;
    }

    public String getBinderName() {
        return binderName;
    }

    public void setBinderName(String binderName) {
        this.binderName = binderName;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public MimeType getOutputContentType() {
        return outputContentType;
    }

    public void setOutputContentType(MimeType outputContentType) {
        this.outputContentType = outputContentType;
    }
}
