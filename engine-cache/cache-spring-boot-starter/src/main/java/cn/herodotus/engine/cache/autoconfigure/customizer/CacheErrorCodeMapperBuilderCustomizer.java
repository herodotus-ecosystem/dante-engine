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

package cn.herodotus.engine.cache.autoconfigure.customizer;

import cn.herodotus.engine.assistant.definition.constants.ErrorCodeMapperBuilderOrdered;
import cn.herodotus.engine.assistant.definition.function.ErrorCodeMapperBuilderCustomizer;
import cn.herodotus.engine.assistant.definition.support.ErrorCodeMapperBuilder;
import cn.herodotus.engine.cache.core.constants.CacheErrorCodes;
import org.springframework.core.Ordered;

/**
 * <p>Description: Cache 模块内置错误代码生成器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/9/26 22:11
 */
public class CacheErrorCodeMapperBuilderCustomizer implements ErrorCodeMapperBuilderCustomizer, Ordered {
    @Override
    public void customize(ErrorCodeMapperBuilder builder) {
        builder.notAcceptable(
                CacheErrorCodes.STAMP_DELETE_FAILED,
                CacheErrorCodes.STAMP_HAS_EXPIRED,
                CacheErrorCodes.STAMP_MISMATCH,
                CacheErrorCodes.STAMP_PARAMETER_ILLEGAL
        );
    }

    @Override
    public int getOrder() {
        return ErrorCodeMapperBuilderOrdered.CACHE;
    }
}
