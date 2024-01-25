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

package cn.herodotus.stirrup.facility.core.configuration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import cn.herodotus.stirrup.facility.core.annotation.ConditionalOnLogEnabled;
import cn.herodotus.stirrup.facility.core.constants.FacilityConstants;
import cn.herodotus.stirrup.facility.core.properties.LogProperties;
import cn.herodotus.stirrup.core.foundation.json.jackson2.utils.Jackson2Utils;
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
