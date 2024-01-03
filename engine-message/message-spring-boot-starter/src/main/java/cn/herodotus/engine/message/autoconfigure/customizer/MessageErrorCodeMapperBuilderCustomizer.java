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

package cn.herodotus.engine.message.autoconfigure.customizer;

import cn.herodotus.engine.assistant.definition.constants.ErrorCodeMapperBuilderOrdered;
import cn.herodotus.engine.assistant.definition.function.ErrorCodeMapperBuilderCustomizer;
import cn.herodotus.engine.assistant.definition.support.ErrorCodeMapperBuilder;
import cn.herodotus.engine.message.core.constants.MessageErrorCodes;
import org.springframework.core.Ordered;

/**
 * <p>Description: Message 错误代码映射定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/9/26 23:27
 */
public class MessageErrorCodeMapperBuilderCustomizer implements ErrorCodeMapperBuilderCustomizer, Ordered {
    @Override
    public void customize(ErrorCodeMapperBuilder builder) {
        builder.notAcceptable(MessageErrorCodes.ILLEGAL_CHANNEL, MessageErrorCodes.PRINCIPAL_NOT_FOUND);
    }

    @Override
    public int getOrder() {
        return ErrorCodeMapperBuilderOrdered.MESSAGE;
    }
}
