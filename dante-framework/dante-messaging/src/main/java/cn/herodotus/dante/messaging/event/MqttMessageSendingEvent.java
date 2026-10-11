/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 是 Dante Cloud 系统核心组件库，采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dante.messaging.event;

import cn.herodotus.dante.messaging.domain.MqttMessage;
import cn.herodotus.dante.spring.context.AbstractApplicationEvent;
import cn.hutool.v7.core.util.ByteUtil;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * <p>Description: Mqtt 类型消息 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/11/2 16:05
 */
public class MqttMessageSendingEvent extends AbstractApplicationEvent<MqttMessage> {

    /**
     * 构建 Mqtt 消息发布事件
     *
     * @param topic           主题
     * @param payload         内容
     * @param qos             Qos
     * @param responseTopic   响应主题
     * @param correlationData 关联数据
     */
    public MqttMessageSendingEvent(String topic, String payload, Integer qos, String responseTopic, String correlationData) {
        byte[] correlationDataBytes = StringUtils.isNotBlank(correlationData) ? ByteUtil.toBytes(correlationData, StandardCharsets.UTF_8) : null;
        this(topic, payload, qos, responseTopic, correlationDataBytes);
    }

    /**
     * 构建 Mqtt 消息发布事件
     *
     * @param topic           主题
     * @param payload         内容
     * @param qos             Qos
     * @param responseTopic   响应主题
     * @param correlationData 关联数据
     */
    public MqttMessageSendingEvent(String topic, String payload, Integer qos, String responseTopic, byte[] correlationData) {
        MqttMessage message = MqttMessage.with(topic, payload)
                .qos(qos)
                .responseTopic(responseTopic)
                .correlationData(correlationData)
                .build();

        this(message);
    }

    public MqttMessageSendingEvent(MqttMessage message) {
        super(message);
    }
}
