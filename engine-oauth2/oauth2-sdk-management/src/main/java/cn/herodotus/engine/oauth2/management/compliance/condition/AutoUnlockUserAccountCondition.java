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

package cn.herodotus.engine.oauth2.management.compliance.condition;

import cn.herodotus.stirrup.core.foundation.context.PropertyResolver;
import cn.herodotus.engine.oauth2.core.constants.OAuth2Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * <p>Description: 阿里云短信开启条件 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/27 16:23
 */
public class AutoUnlockUserAccountCondition implements Condition {

    private static final Logger log = LoggerFactory.getLogger(AutoUnlockUserAccountCondition.class);

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata metadata) {
        boolean result = PropertyResolver.getBoolean(conditionContext, OAuth2Constants.ITEM_COMPLIANCE_AUTO_UNLOCK, true);
        log.debug("[Herodotus] |- Condition [Auto Unlock User Account] value is [{}]", result);
        return result;
    }
}
