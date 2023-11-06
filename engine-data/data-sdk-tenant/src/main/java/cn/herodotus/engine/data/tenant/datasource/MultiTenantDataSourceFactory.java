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
 * <p>Description: 多租户数据源工厂 </p>
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
