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

package cn.herodotus.engine.message.websocket.properties;

import cn.herodotus.engine.assistant.definition.constants.SymbolConstants;
import cn.herodotus.engine.assistant.core.utils.http.HeaderUtils;
import cn.herodotus.engine.message.core.constants.MessageConstants;
import cn.herodotus.engine.message.websocket.enums.InstanceMode;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;

/**
 * <p>Description: Web Socket 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/24 18:38
 */
@ConfigurationProperties(prefix = MessageConstants.PROPERTY_PREFIX_WEBSOCKET)
public class WebSocketProperties {

    /**
     * WebSocket 实例模式，单实例还是多实例。默认为单实例
     */
    private InstanceMode mode = InstanceMode.SINGLE;
    /**
     * 客户端尝试连接端点
     */
    private String endpoint = "stomp/ws";

    /**
     * 全局使用的消息前缀
     */
    private List<String> applicationDestinationPrefixes = Collections.singletonList("/app");

    /**
     * 点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
     */
    private String userDestinationPrefix = "/user";

    /**
     * 集群模式下，信息同步消息队列Topic
     */
    private String topic = "ws";

    /**
     * 请求中传递的用户身份标识属性名
     */
    private String principalHeader = HeaderUtils.X_HERODOTUS_OPEN_ID;

    public InstanceMode getMode() {
        return mode;
    }

    public void setMode(InstanceMode mode) {
        this.mode = mode;
    }

    private String format(String endpoint) {
        if (StringUtils.isNotBlank(endpoint) && !StringUtils.startsWith(endpoint, SymbolConstants.FORWARD_SLASH)) {
            return SymbolConstants.FORWARD_SLASH + endpoint;
        } else {
            return endpoint;
        }
    }

    public String getEndpoint() {
        return format(endpoint);
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public List<String> getApplicationDestinationPrefixes() {
        return applicationDestinationPrefixes;
    }

    public void setApplicationDestinationPrefixes(List<String> applicationDestinationPrefixes) {
        this.applicationDestinationPrefixes = applicationDestinationPrefixes;
    }

    public String[] getApplicationPrefixes() {
        List<String> prefixes = this.getApplicationDestinationPrefixes();
        if (CollectionUtils.isNotEmpty(prefixes)) {
            List<String> wellFormed = prefixes.stream().map(this::format).toList();
            String[] result = new String[wellFormed.size()];
            return wellFormed.toArray(result);
        } else {
            return new String[]{};
        }
    }

    public String getUserDestinationPrefix() {
        return format(userDestinationPrefix);
    }

    public void setUserDestinationPrefix(String userDestinationPrefix) {
        this.userDestinationPrefix = userDestinationPrefix;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPrincipalHeader() {
        return principalHeader;
    }

    public void setPrincipalHeader(String principalHeader) {
        this.principalHeader = principalHeader;
    }
}
