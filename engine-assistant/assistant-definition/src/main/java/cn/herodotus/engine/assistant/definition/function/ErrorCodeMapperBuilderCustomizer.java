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

package cn.herodotus.engine.assistant.definition.function;

import cn.herodotus.engine.assistant.definition.support.ErrorCodeMapperBuilder;

/**
 * <p>Description: ErrorCodeMapperBuilder 回调接口</p>
 * <p>
 * 实现了该接口的Bean，可以在自动配置阶段，通过ErrorCodeMapperBuilder进一步扩展错误码
 *
 * @author : gengwei.zheng
 * @date : 2023/9/24 23:06
 */
@FunctionalInterface
public interface ErrorCodeMapperBuilderCustomizer {

    /**
     * 自定义 ErrorCodeMapperBuilder
     *
     * @param builder 被扩展的 {@link ErrorCodeMapperBuilder}
     */
    void customize(ErrorCodeMapperBuilder builder);
}
