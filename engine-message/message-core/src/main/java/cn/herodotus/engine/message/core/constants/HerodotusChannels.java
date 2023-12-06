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

package cn.herodotus.engine.message.core.constants;

/**
 * <p>Description: 消息通道定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/11/22 18:49
 */
public interface HerodotusChannels {

    /**
     * Mqtt 默认入站通道
     */
    String MQTT_DEFAULT_INBOUND_CHANNEL = "mqttDefaultInboundChannel";
    /**
     * Mqtt 默认出站通道
     */
    String MQTT_DEFAULT_OUTBOUND_CHANNEL = "mqttDefaultOutboundChannel";
    /**
     * Emqx 默认的监控指标数据数据 Mqtt 类型入站通道
     */
    String EMQX_DEFAULT_MONITOR_MQTT_INBOUND_CHANNEL = "emqxDefaultMonitorMqttInboundChannel";
    /**
     * Emqx 默认的 Webhook 数据 HTTP 类型入站通道
     */
    String EMQX_DEFAULT_WEBHOOK_HTTP_INBOUND_CHANNEL = "emqxDefaultWebhookHttpInboundChannel";
    /**
     * Emqx 默认的系统时间数据 EVENT 类型出站通道
     */
    String EMQX_DEFAULT_EVENT_OUTBOUND_CHANNEL = "emqxDefaultEventOutboundChannel";
}
