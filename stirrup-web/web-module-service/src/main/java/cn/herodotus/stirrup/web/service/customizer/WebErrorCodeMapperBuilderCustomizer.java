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

package cn.herodotus.stirrup.web.service.customizer;

import cn.herodotus.stirrup.core.definition.constants.ErrorCodeMapperBuilderOrdered;
import cn.herodotus.stirrup.core.definition.function.ErrorCodeMapperBuilderCustomizer;
import cn.herodotus.stirrup.core.definition.support.ErrorCodeMapperBuilder;
import cn.herodotus.stirrup.web.core.constants.WebErrorCodes;
import org.springframework.core.Ordered;

/**
 * <p>Description: Rest 错误代码映射定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/9/26 23:20
 */
public class WebErrorCodeMapperBuilderCustomizer implements ErrorCodeMapperBuilderCustomizer, Ordered {
    @Override
    public void customize(ErrorCodeMapperBuilder builder) {
        builder.notAcceptable(
                WebErrorCodes.SESSION_INVALID,
                WebErrorCodes.REPEAT_SUBMISSION,
                WebErrorCodes.FREQUENT_REQUESTS,
                WebErrorCodes.FEIGN_DECODER_IO_EXCEPTION
        );
    }

    @Override
    public int getOrder() {
        return ErrorCodeMapperBuilderOrdered.REST;
    }
}
