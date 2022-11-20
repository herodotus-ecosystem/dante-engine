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

package cn.herodotus.engine.data.jpa.tenant;

import cn.herodotus.engine.assistant.core.constants.BaseConstants;
import cn.herodotus.engine.data.jpa.properties.MultiTenantDataSource;
import cn.herodotus.engine.data.jpa.properties.MultiTenantProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.lookup.MapDataSourceLookup;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

/**
 * <p>Description: DataSource 查询 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/9/8 18:55
 */
public class HerodotusMapDataSourceLookup extends MapDataSourceLookup {

    private final MultiTenantProperties multiTenantProperties;

    public HerodotusMapDataSourceLookup(DataSource defaultDataSource, MultiTenantProperties multiTenantProperties) {
        this.multiTenantProperties = multiTenantProperties;
        initDefaultDataSource(defaultDataSource);
        initDataSource(defaultDataSource);
    }

    private void initDefaultDataSource(DataSource defaultDataSource) {
        addDataSource(BaseConstants.DEFAULT_TENANT_ID, defaultDataSource);
    }

    private void initDataSource(DataSource defaultDataSource) {
        Map<String, MultiTenantDataSource> dataSources = multiTenantProperties.getDataSources();
        if (MapUtils.isNotEmpty(dataSources)) {
            dataSources.forEach((tenantIdentifier, multiTenantDataSource)-> {
                addDataSource(tenantIdentifier, createDataSource(defaultDataSource, multiTenantDataSource));
            });
        }
    }

    private DataSource createDataSource(DataSource defaultDataSource, MultiTenantDataSource multiTenantDataSource) {
        if (defaultDataSource instanceof HikariDataSource defaultHikariDataSource) {
            Properties defaultDataSourceProperties = defaultHikariDataSource.getDataSourceProperties();
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setDriverClassName(multiTenantDataSource.getDriverClassName());
            hikariConfig.setJdbcUrl(multiTenantDataSource.getUrl());
            hikariConfig.setUsername(multiTenantDataSource.getUsername());
            hikariConfig.setPassword(multiTenantDataSource.getPassword());

            if (ObjectUtils.isNotEmpty(defaultDataSource)) {
                defaultDataSourceProperties.forEach((key, value) -> hikariConfig.addDataSourceProperty(String.valueOf(key), value));
            }

            return new HikariDataSource(hikariConfig);
        } else {
            return DataSourceBuilder.create()
                    .type(HikariDataSource.class)
                    .url(multiTenantDataSource.getUrl())
                    .driverClassName(multiTenantDataSource.getDriverClassName())
                    .username(multiTenantDataSource.getUsername())
                    .password(multiTenantDataSource.getPassword())
                    .build();
        }
    }
}
