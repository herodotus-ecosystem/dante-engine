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

package cn.herodotus.engine.message.websocket.condition;

import cn.herodotus.engine.assistant.core.context.PropertyResolver;
import cn.herodotus.engine.message.core.constants.MessageConstants;
import cn.herodotus.engine.message.websocket.enums.InstanceMode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * <p>Description: WebSocket 多实例环境判断条件 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/9/14 13:50
 */
public class MultipleWebSocketInstanceCondition implements Condition {

    private static final Logger log = LoggerFactory.getLogger(MultipleWebSocketInstanceCondition.class);

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata metadata) {
        String property = PropertyResolver.getProperty(conditionContext.getEnvironment(), MessageConstants.ITEM_WEBSOCKET_MULTIPLE_INSTANCE);
        boolean result = StringUtils.isNotBlank(property) && StringUtils.equalsIgnoreCase(property, InstanceMode.MULTIPLE.name());
        log.debug("[Herodotus] |- Condition [Multiple Web Socket Instance] value is [{}]", result);
        return result;

    }
}
