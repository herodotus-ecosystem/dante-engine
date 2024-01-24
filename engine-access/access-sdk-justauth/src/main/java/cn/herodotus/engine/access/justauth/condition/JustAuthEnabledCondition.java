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

package cn.herodotus.engine.access.justauth.condition;

import cn.herodotus.engine.access.core.constants.AccessConstants;
import cn.herodotus.stirrup.core.foundation.context.PropertyResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * <p>Description: JusAuth注入条件 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/27 22:08
 */
public class JustAuthEnabledCondition implements Condition {

    private static final Logger log = LoggerFactory.getLogger(JustAuthEnabledCondition.class);

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata metadata) {
        boolean result = PropertyResolver.getBoolean(conditionContext, AccessConstants.ITEM_JUSTAUTH_ENABLED, false);
        log.debug("[Herodotus] |- Condition [JustAuth Enabled] value is [{}]", result);
        return result;
    }
}
