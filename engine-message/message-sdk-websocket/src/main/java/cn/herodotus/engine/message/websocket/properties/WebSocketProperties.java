/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.message.websocket.properties;

import cn.herodotus.engine.assistant.core.definition.constants.SymbolConstants;
import cn.herodotus.engine.message.core.constants.MessageConstants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Description: Web Socket 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/24 18:38
 */
@ConfigurationProperties(prefix = MessageConstants.PROPERTY_PREFIX_WEBSOCKET)
public class WebSocketProperties {

    /**
     * 客户端尝试连接端点
     */
    private String endpoint = "stomp/websocketjs";
    /**
     * WebSocket 广播消息代理地址
     */
    private String broadcast = "topic";

    /**
     * WebSocket 点对点消息代理地址
     */
    private String peerToPeer = "private";

    /**
     * 全局使用的消息前缀
     */
    private List<String> applicationDestinationPrefixes;

    /**
     * 点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
     */
    private String userDestinationPrefix;

    /**
     * 集群模式下，信息同步消息队列Topic
     */
    private String topic = "ws";

    /**
     * 请求中传递的用户身份标识属性名
     */
    private String principalAttribute = "openid";

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

    public String getBroadcast() {
        return format(broadcast);
    }

    public void setBroadcast(String broadcast) {
        this.broadcast = broadcast;
    }

    public String getPeerToPeer() {
        return format(peerToPeer);
    }

    public void setPeerToPeer(String peerToPeer) {
        this.peerToPeer = peerToPeer;
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
            List<String> wellFormed = prefixes.stream().map(this::format).collect(Collectors.toList());
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

    public String getPrincipalAttribute() {
        return principalAttribute;
    }

    public void setPrincipalAttribute(String principalAttribute) {
        this.principalAttribute = principalAttribute;
    }
}
