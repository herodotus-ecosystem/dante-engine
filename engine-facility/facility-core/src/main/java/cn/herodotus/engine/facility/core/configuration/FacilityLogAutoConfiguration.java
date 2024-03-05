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

package cn.herodotus.engine.facility.core.configuration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import cn.herodotus.engine.assistant.core.json.jackson2.utils.Jackson2Utils;
import cn.herodotus.engine.facility.core.constants.FacilityConstants;
import cn.herodotus.engine.facility.core.annotation.ConditionalOnLogEnabled;
import cn.herodotus.engine.facility.core.properties.LogProperties;
import com.google.common.base.MoreObjects;
import jakarta.annotation.PostConstruct;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import org.apache.skywalking.apm.toolkit.log.logback.v1.x.logstash.TraceIdJsonProvider;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.logging.LogLevel;

import java.util.Map;

/**
 * <p>Description: 基础设置日志配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/5 19:01
 */
@AutoConfiguration
@ConditionalOnLogEnabled
@EnableConfigurationProperties({LogProperties.class})
public class FacilityLogAutoConfiguration {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(FacilityLogAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Facility Log] Auto Configure.");
    }


    @Value(FacilityConstants.ANNOTATION_APPLICATION_NAME)
    private String serviceName;

    @Autowired
    private LogProperties logProperties;

    @PostConstruct
    public void init() {

        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        LoggerContext loggerContext = rootLogger.getLoggerContext();

        LogstashTcpSocketAppender logstashTcpSocketAppender = new LogstashTcpSocketAppender();
        logstashTcpSocketAppender.setName("LOGSTASH");
        logstashTcpSocketAppender.addDestination(logProperties.getServerAddr());
        logstashTcpSocketAppender.setKeepAliveDuration(logProperties.getKeepAliveDuration());
        logstashTcpSocketAppender.setReconnectionDelay(logProperties.getReconnectionDelay());
        logstashTcpSocketAppender.setWriteTimeout(logProperties.getWriteTimeout());
        logstashTcpSocketAppender.setContext(loggerContext);

        TraceIdJsonProvider traceIdJsonProvider = new TraceIdJsonProvider();
        traceIdJsonProvider.setContext(loggerContext);

        CustomFields customFields = new CustomFields();
        customFields.setService(serviceName);

        LogstashEncoder logstashEncoder = new LogstashEncoder();
        logstashEncoder.setCustomFields(Jackson2Utils.toJson(customFields));
        logstashEncoder.addProvider(traceIdJsonProvider);

        logstashTcpSocketAppender.setEncoder(logstashEncoder);
        logstashTcpSocketAppender.start();

        rootLogger.addAppender(logstashTcpSocketAppender);
        rootLogger.setLevel(Level.toLevel(logProperties.getLogLevel().name(), Level.INFO));

        Map<String, LogLevel> loggers = logProperties.getLoggers();
        loggers.forEach((key, value) -> {
            Logger logger = (Logger) LoggerFactory.getLogger(key);
            logger.setLevel(Level.toLevel(value.name()));
        });
    }

    private static class CustomFields {

        private String service;

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("service", service)
                    .toString();
        }
    }
}
