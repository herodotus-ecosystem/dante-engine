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

package cn.herodotus.engine.message.mqtt.condition;

import cn.herodotus.engine.assistant.core.context.PropertyResolver;
import cn.herodotus.engine.message.core.constants.MessageConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * <p>Description: Mqtt 开启判断条件 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/11/2 13:44
 */
public class MqttEnabledCondition implements Condition {

    private static final Logger log = LoggerFactory.getLogger(MqttEnabledCondition.class);

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata metadata) {
        String username = PropertyResolver.getProperty(conditionContext, MessageConstants.ITEM_MQTT_USERNAME);
        String password = PropertyResolver.getProperty(conditionContext, MessageConstants.ITEM_MQTT_PASSWORD);
        boolean result = StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password);
        log.debug("[Herodotus] |- Condition [Mqtt Enabled] value is [{}]", result);
        return result;
    }
}
