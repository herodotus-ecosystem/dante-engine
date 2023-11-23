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

package cn.herodotus.engine.message.mqtt.properties;

import cn.herodotus.engine.message.core.constants.MessageConstants;
import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.List;

/**
 * <p>Description: MQTT 相关配置参数 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/9/10 17:16
 */
@ConfigurationProperties(prefix = MessageConstants.PROPERTY_PREFIX_MQTT)
public class MqttProperties {

    /**
     * Mqtt 服务器地址，支持多个
     */
    private List<String> serverUrls;
    /**
     * 连接用户名
     */
    private String username;
    /**
     * 连接密码
     */
    private String password;
    /**
     * Mqtt 客户端ID
     */
    private String clientId = "herodotus-client-manager-client-id-v5";
    /**
     * 设置“保持活动”间隔。此值以秒为单位，定义发送或接收消息之间的最大时间间隔。
     * <p>
     * 它使客户端能够检测服务器是否不再可用，而无需等待TCP/IP超时。客户端将确保在每个保活期内至少有一条消息在网络上传播。在这段时间内没有数据相关消息的情况下，客户端会发送一条非常小的“ping”消息，服务器会对此进行确认。
     * 值为0将禁用客户端中的保持活动处理。
     * <p>
     * 默认值为60秒
     */
    private Duration keepAliveInterval = Duration.ofSeconds(60);

    /**
     * 设置如果连接丢失，客户端是否会自动尝试重新连接到服务器。
     * <p>
     * · 如果设置为false，则在连接丢失的情况下，客户端将不会尝试自动重新连接到服务器。
     * · 如果设置为true，则在连接丢失的情况下，客户端将尝试重新连接到服务器。在尝试重新连接之前，它最初将等待1秒，对于每次失败的重新连接尝试，延迟将加倍，直到达到2分钟，此时延迟将保持在2分钟。
     * <p>
     * 您可以使用setAutomaticReconnectDelay（int，int）更改最小和最大延迟。此默认值为true
     */
    private Boolean automaticReconnect = true;

    /**
     * 第一次自动重新连接尝试前等待的时间（秒）。默认值 1 秒
     */
    private Duration automaticReconnectMinDelay = Duration.ofSeconds(1);
    /**
     * 等待自动重新连接尝试的最长时间（秒）。默认值 120 秒
     */
    private Duration automaticReconnectMaxDelay = Duration.ofSeconds(120);

    /**
     * 设置客户端和服务器是否应在重新启动和重新连接时记住状态。
     * <p>
     * 如果设置为false，则服务器将保留会话状态，直到出现以下情况之一：
     * · 将与客户端建立新连接，并将cleanStart标志设置为true。
     * · 网络连接关闭后超过了会话到期间隔，请参阅setSessionExpiryInterval
     * <p>
     * 如果设置为true，服务器将立即放弃给定客户端的任何现有会话状态，并启动新会话。为了实现QoS 1和QoS 2协议流，客户端和服务器需要将状态与客户端标识符相关联，这被称为会话状态。
     * 服务器还将订阅存储为会话状态的一部分。会话可以在一系列网络连接中继续。它的持续时间与最近的网络连接加上会话到期时间间隔一样长。客户端中的会话状态包括：
     * · QoS 1和QoS 2消息，已发送到服务器，但尚未协同发送
     * · 已从服务器接收但尚未完全确认的QoS 2消息。
     * <p>
     * 服务器中的会话状态包括：
     * <p>
     * · 会话的存在，即使会话状态的其余部分为空。
     * · 客户端订阅，包括任何订阅标识符。
     * · 已发送到客户端但尚未完全确认的QoS 1和QoS 2消息。
     * · 待传输到客户端的QoS 1和QoS 2消息以及待传输到客户机的可选QoS 0消息。
     * · 已从客户端接收到但尚未完全确认的QoS 2消息。遗嘱信息和遗嘱延迟间隔
     * · 如果会话当前未连接，则会话结束的时间和会话状态将被丢弃。
     * <p>
     * 保留的消息不构成服务器中会话状态的一部分，它们不会因会话结束而被删除。
     */
    private Boolean cleanStart = true;
    /**
     * 默认的主题，
     */
    private String defaultTopic = "herodotus.mqtt.default";
    /**
     * 默认的 Qos 级别，默认值为：1
     */
    private Integer defaultQos = 1;
    /**
     * 默认订阅的主题
     */
    private List<String> defaultSubscribes;

    public List<String> getServerUrls() {
        return serverUrls;
    }

    public void setServerUrls(List<String> serverUrls) {
        this.serverUrls = serverUrls;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Duration getKeepAliveInterval() {
        return keepAliveInterval;
    }

    public void setKeepAliveInterval(Duration keepAliveInterval) {
        this.keepAliveInterval = keepAliveInterval;
    }

    public Boolean getAutomaticReconnect() {
        return automaticReconnect;
    }

    public void setAutomaticReconnect(Boolean automaticReconnect) {
        this.automaticReconnect = automaticReconnect;
    }

    public Duration getAutomaticReconnectMinDelay() {
        return automaticReconnectMinDelay;
    }

    public void setAutomaticReconnectMinDelay(Duration automaticReconnectMinDelay) {
        this.automaticReconnectMinDelay = automaticReconnectMinDelay;
    }

    public Duration getAutomaticReconnectMaxDelay() {
        return automaticReconnectMaxDelay;
    }

    public void setAutomaticReconnectMaxDelay(Duration automaticReconnectMaxDelay) {
        this.automaticReconnectMaxDelay = automaticReconnectMaxDelay;
    }

    public Boolean getCleanStart() {
        return cleanStart;
    }

    public void setCleanStart(Boolean cleanStart) {
        this.cleanStart = cleanStart;
    }

    public String getDefaultTopic() {
        return defaultTopic;
    }

    public void setDefaultTopic(String defaultTopic) {
        this.defaultTopic = defaultTopic;
    }

    public Integer getDefaultQos() {
        return defaultQos;
    }

    public void setDefaultQos(Integer defaultQos) {
        this.defaultQos = defaultQos;
    }

    public List<String> getDefaultSubscribes() {
        return defaultSubscribes;
    }

    public void setDefaultSubscribes(List<String> defaultSubscribes) {
        this.defaultSubscribes = defaultSubscribes;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("username", username)
                .add("password", password)
                .add("clientId", clientId)
                .add("keepAliveInterval", keepAliveInterval)
                .add("automaticReconnect", automaticReconnect)
                .add("automaticReconnectMinDelay", automaticReconnectMinDelay)
                .add("automaticReconnectMaxDelay", automaticReconnectMaxDelay)
                .add("cleanStart", cleanStart)
                .add("defaultTopic", defaultTopic)
                .add("defaultQos", defaultQos)
                .toString();
    }
}
