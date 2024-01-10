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

package cn.herodotus.engine.data.tenant.condition;

import cn.herodotus.stirrup.kernel.engine.context.PropertyResolver;
import cn.herodotus.stirrup.data.kernel.constants.DataConstants;
import cn.herodotus.engine.data.core.enums.MultiTenantApproach;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * <p>Description: 标准算法策略条件 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/5/3 23:00
 */
public class SchemaApproachCondition implements Condition {

    private static final Logger log = LoggerFactory.getLogger(SchemaApproachCondition.class);

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String property = PropertyResolver.getProperty(conditionContext, DataConstants.ITEM_MULTI_TENANT_APPROACH);
        boolean result = StringUtils.isNotBlank(property) && StringUtils.equalsIgnoreCase(property, MultiTenantApproach.SCHEMA.name());
        log.debug("[Herodotus] |- Condition [Schema Approach] value is [{}]", result);
        return result;
    }
}
