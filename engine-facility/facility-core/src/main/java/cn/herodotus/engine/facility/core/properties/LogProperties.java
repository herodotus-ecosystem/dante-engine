/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.apache.org/licenses/LICENSE-2.0>
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
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.facility.core.properties;

import ch.qos.logback.core.util.Duration;
import cn.herodotus.engine.facility.core.constants.FacilityConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.logging.LogLevel;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: 日志中心配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/5 17:48
 */
@ConfigurationProperties(prefix = FacilityConstants.PROPERTY_PREFIX_LOG_CENTER)
public class LogProperties {

    /**
     * 日志中心的logstash地址。
     */
    private String serverAddr = "127.0.0.1:5044";
    /**
     * 日志级别，默认为INFO
     */
    private LogLevel logLevel = LogLevel.INFO;
    /**
     * 保持活动持续时间，默认5分钟，单位：分钟
     */
    private Duration keepAliveDuration = Duration.buildByMinutes(5);

    /**
     * 尝试连接到目标间隔时间，默认30秒， 单位：秒
     */
    private Duration reconnectionDelay = Duration.buildBySeconds(30);
    /**
     * 日志写入超时时间，默认1分钟，单位：分钟
     */
    private Duration writeTimeout = Duration.buildByMinutes(1);
    /**
     * 日志级别配置
     */
    private Map<String, LogLevel> loggers = new HashMap<>();

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public Map<String, LogLevel> getLoggers() {
        return loggers;
    }

    public void setLoggers(Map<String, LogLevel> loggers) {
        this.loggers = loggers;
    }

    public Duration getKeepAliveDuration() {
        return keepAliveDuration;
    }

    public void setKeepAliveDuration(Duration keepAliveDuration) {
        this.keepAliveDuration = keepAliveDuration;
    }

    public Duration getReconnectionDelay() {
        return reconnectionDelay;
    }

    public void setReconnectionDelay(Duration reconnectionDelay) {
        this.reconnectionDelay = reconnectionDelay;
    }

    public Duration getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(Duration writeTimeout) {
        this.writeTimeout = writeTimeout;
    }
}
