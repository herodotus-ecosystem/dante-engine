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
 * 2.请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.nosql.influxdb.configuration;

import cn.herodotus.engine.nosql.influxdb.annotation.ConditionalOnInfluxdbEnabled;
import cn.herodotus.engine.nosql.influxdb.properties.InfluxdbProperties;
import cn.herodotus.engine.nosql.influxdb.support.InfluxdbTemplate;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * <p>Description: Influxdb 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/11/17 17:39
 */
@Configuration
@ConditionalOnInfluxdbEnabled
@EnableConfigurationProperties({InfluxdbProperties.class})
public class InfluxdbConfiguration {

    private static final Logger log = LoggerFactory.getLogger(InfluxdbConfiguration.class);

    @PostConstruct
    public void init() {
        log.info("[Herodotus] |- Plugin [Herodotus Influxdb] Auto Configure.");
    }

    @Bean
    public InfluxDB influxdb(InfluxdbProperties influxdbProperties) {

        InfluxDB influxdb;
        if (StringUtils.isNotBlank(influxdbProperties.getUsername()) && StringUtils.isNotBlank(influxdbProperties.getPassword())) {
            influxdb = InfluxDBFactory.connect(influxdbProperties.getEndpoint(), influxdbProperties.getUsername(), influxdbProperties.getPassword());
        } else {
            influxdb = InfluxDBFactory.connect(influxdbProperties.getEndpoint());
        }

        try {
            /**
             * 异步插入：
             * enableBatch这里第一个是point的个数，第二个是时间，单位毫秒
             * point的个数和时间是联合使用的，如果满100条或者2000毫秒
             * 满足任何一个条件就会发送一次写的请求。
             */
            influxdb.setDatabase(influxdbProperties.getDatabase()).enableBatch(100, 2000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("[Herodotus] |- Influxdb set database catch error.", e);
        } finally {
            influxdb.setRetentionPolicy("autogen");
        }
        influxdb.setLogLevel(InfluxDB.LogLevel.BASIC);
        return influxdb;
    }

    @Bean
    @ConditionalOnBean(InfluxDB.class)
    public InfluxdbTemplate influxdbTemplate(InfluxDB influxdb) {
        InfluxdbTemplate influxdbTemplate = new InfluxdbTemplate(influxdb);
        log.trace("[Herodotus] |- Bean [Influxdb Template Auto Configure.");
        return influxdbTemplate;
    }
}
