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

import cn.herodotus.stirrup.core.foundation.enums.Target;
import cn.herodotus.engine.rest.condition.constants.RestPropertyFinder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * <p>Description: 远程Feign数据访问策略条件定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/22 16:19
 */
public class RemoteDataAccessCondition implements Condition {

    private static final Logger log = LoggerFactory.getLogger(RemoteDataAccessCondition.class);

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String property = RestPropertyFinder.getDataAccessStrategy(conditionContext, Target.REMOTE.name());
        boolean result = StringUtils.isNotBlank(property) && StringUtils.equalsIgnoreCase(property, Target.REMOTE.name());
        log.debug("[Herodotus] |- Condition [Remote Data Access] value is [{}]", result);
        return result;
    }
}
