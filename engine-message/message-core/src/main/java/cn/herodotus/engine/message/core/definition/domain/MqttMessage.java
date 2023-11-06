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

package cn.herodotus.engine.message.core.definition.domain;

import com.google.common.base.MoreObjects;

/**
 * <p>Description: Mqtt 类型消息参数实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/11/2 16:05
 */
public class MqttMessage implements Message {

    private String topic;
    private String responseTopic;
    private String correlationData;
    private Integer qos;
    private String payload;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getResponseTopic() {
        return responseTopic;
    }

    public void setResponseTopic(String responseTopic) {
        this.responseTopic = responseTopic;
    }

    public String getCorrelationData() {
        return correlationData;
    }

    public void setCorrelationData(String correlationData) {
        this.correlationData = correlationData;
    }

    public Integer getQos() {
        return qos;
    }

    public void setQos(Integer qos) {
        this.qos = qos;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("topic", topic)
                .add("responseTopic", responseTopic)
                .add("correlationData", correlationData)
                .add("qos", qos)
                .add("payload", payload)
                .toString();
    }
}
