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

package cn.herodotus.engine.data.tenant.datasource;

import cn.herodotus.engine.data.tenant.entity.SysTenantDataSource;
import cn.herodotus.engine.data.tenant.repository.SysTenantDataSourceRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/3/29 11:43
 */
@Component
public class MultiTenantDataSourceFactory {

    @Autowired
    private SysTenantDataSourceRepository sysTenantDataSourceRepository;

    private DataSource createDataSource(DataSource defaultDataSource, SysTenantDataSource sysTenantDataSource) {
        if (defaultDataSource instanceof HikariDataSource defaultHikariDataSource) {
            Properties defaultDataSourceProperties = defaultHikariDataSource.getDataSourceProperties();
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setDriverClassName(sysTenantDataSource.getDriverClassName());
            hikariConfig.setJdbcUrl(sysTenantDataSource.getUrl());
            hikariConfig.setUsername(sysTenantDataSource.getUsername());
            hikariConfig.setPassword(sysTenantDataSource.getPassword());

            if (ObjectUtils.isNotEmpty(defaultDataSource)) {
                defaultDataSourceProperties.forEach((key, value) -> hikariConfig.addDataSourceProperty(String.valueOf(key), value));
            }

            return new HikariDataSource(hikariConfig);
        } else {
            return DataSourceBuilder.create()
                    .type(HikariDataSource.class)
                    .url(sysTenantDataSource.getUrl())
                    .driverClassName(sysTenantDataSource.getDriverClassName())
                    .username(sysTenantDataSource.getUsername())
                    .password(sysTenantDataSource.getPassword())
                    .build();
        }
    }

    public Map<String, DataSource> getAll(DataSource defaultDataSource) {
        List<SysTenantDataSource> sysTenantDataSources = sysTenantDataSourceRepository.findAll();
        if (CollectionUtils.isNotEmpty(sysTenantDataSources)) {
            return sysTenantDataSources.stream().collect(Collectors.toMap(SysTenantDataSource::getTenantId, value -> createDataSource(defaultDataSource, value)));
        } else {
            return new HashMap<>();
        }
    }
}
