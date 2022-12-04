/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
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
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.message.socketio.properties;

import cn.herodotus.engine.message.core.constants.MessageConstants;
import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/4 17:48
 */
@ConfigurationProperties(prefix = MessageConstants.PROPERTY_PREFIX_SOCKET_IO)
public class SocketIOProperties {

    /**
     * host在本地测试可以设置为localhost或者本机IP，在Linux服务器跑可换成服务器IP
     */
    private String hostname;
    /**
     * 端口
     */
    private Integer port = 9099;

    /**
     * 设置最大每帧处理数据的长度，防止他人利用大数据来攻击服务器
     */
    private Integer maxFramePayloadLength = 1048576;

    /**
     * 设置http交互最大内容长度
     */
    private Integer maxHttpContentLength = 1048576;

    /**
     * socket连接数大小（如只监听一个端口boss线程组为1即可）
     */
    private Integer bossCount = 1;

    private Integer workCount = 100;

    private Boolean allowCustomRequests = true;

    /**
     * 协议升级超时时间（毫秒），默认10秒。HTTP握手升级为ws协议超时时间
     */
    private Integer upgradeTimeout = 1000000;

    /**
     * Ping消息超时时间（毫秒），默认60秒，这个时间间隔内没有接收到心跳消息就会发送超时事件
     */
    private Integer pingTimeout = 6000000;
    /**
     * Ping消息间隔（毫秒），默认25秒。客户端向服务器发送一条心跳消息间隔
     */
    private Integer pingInterval = 25000;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getMaxFramePayloadLength() {
        return maxFramePayloadLength;
    }

    public void setMaxFramePayloadLength(Integer maxFramePayloadLength) {
        this.maxFramePayloadLength = maxFramePayloadLength;
    }

    public Integer getMaxHttpContentLength() {
        return maxHttpContentLength;
    }

    public void setMaxHttpContentLength(Integer maxHttpContentLength) {
        this.maxHttpContentLength = maxHttpContentLength;
    }

    public Integer getBossCount() {
        return bossCount;
    }

    public void setBossCount(Integer bossCount) {
        this.bossCount = bossCount;
    }

    public Integer getWorkCount() {
        return workCount;
    }

    public void setWorkCount(Integer workCount) {
        this.workCount = workCount;
    }

    public Boolean getAllowCustomRequests() {
        return allowCustomRequests;
    }

    public void setAllowCustomRequests(Boolean allowCustomRequests) {
        this.allowCustomRequests = allowCustomRequests;
    }

    public Integer getUpgradeTimeout() {
        return upgradeTimeout;
    }

    public void setUpgradeTimeout(Integer upgradeTimeout) {
        this.upgradeTimeout = upgradeTimeout;
    }

    public Integer getPingTimeout() {
        return pingTimeout;
    }

    public void setPingTimeout(Integer pingTimeout) {
        this.pingTimeout = pingTimeout;
    }

    public Integer getPingInterval() {
        return pingInterval;
    }

    public void setPingInterval(Integer pingInterval) {
        this.pingInterval = pingInterval;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("hostname", hostname)
                .add("port", port)
                .add("maxFramePayloadLength", maxFramePayloadLength)
                .add("maxHttpContentLength", maxHttpContentLength)
                .add("bossCount", bossCount)
                .add("workCount", workCount)
                .add("allowCustomRequests", allowCustomRequests)
                .add("upgradeTimeout", upgradeTimeout)
                .add("pingTimeout", pingTimeout)
                .add("pingInterval", pingInterval)
                .toString();
    }
}
