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

package cn.herodotus.engine.rest.condition.definition;

import cn.herodotus.engine.rest.condition.constants.RestPropertyFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * <p>Description: 使用默认客户端作为 RestTemplate 和 OpenFeign 引擎条件 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/15 23:12
 */
public class UseSimpleClientAsRestClientCondition implements Condition {

    private static final Logger log = LoggerFactory.getLogger(UseSimpleClientAsRestClientCondition.class);

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        boolean isHttp2ClientEnabled = RestPropertyFinder.isOpenFeignHttp2ClientEnabled(conditionContext);
        boolean isHttpClient5Enabled = RestPropertyFinder.isOpenFeignHttpClient5Enabled(conditionContext);
        boolean result = !isHttp2ClientEnabled && !isHttpClient5Enabled;
        log.debug("[Herodotus] |- Condition [Use Simple Rest Client] value is [{}]", result);
        return result;
    }
}
