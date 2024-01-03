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

package cn.herodotus.engine.data.tenant.hibernate;

import cn.herodotus.engine.assistant.definition.constants.DefaultConstants;
import cn.herodotus.engine.data.tenant.datasource.MultiTenantDataSourceFactory;
import org.apache.commons.lang3.ObjectUtils;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: 数据库连接提供者 </p>
 * <p>
 * 通过该类明确，在租户系统中具体使用的是哪个 Database 或 Schema
 *
 * @author : gengwei.zheng
 * @date : 2022/9/8 18:14
 */
public class DatabaseMultiTenantConnectionProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl<String> implements HibernatePropertiesCustomizer {

    private static final Logger log = LoggerFactory.getLogger(DatabaseMultiTenantConnectionProvider.class);
    private final Map<String, DataSource> dataSources = new HashMap<>();
    private final DataSource defaultDataSource;
    private boolean isDataSourceInit = false;

    public DatabaseMultiTenantConnectionProvider(DataSource dataSource) {
        this.defaultDataSource = dataSource;
        dataSources.put(DefaultConstants.TENANT_ID, dataSource);
    }

    private void initialize() {
        isDataSourceInit = true;
        MultiTenantDataSourceFactory factory = SpringUtil.getBean(MultiTenantDataSourceFactory.class);
        dataSources.putAll(factory.getAll(defaultDataSource));
    }

    /**
     * 在没有指定 tenantId 的情况下选择的数据源（例如启动处理）
     *
     * @return {@link DataSource}
     */
    @Override
    protected DataSource selectAnyDataSource() {
        log.debug("[Herodotus] |- Select any dataSource: " + defaultDataSource);
        return defaultDataSource;
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        if (!isDataSourceInit) {
            initialize();
        }

        DataSource currentDataSource = dataSources.get(tenantIdentifier);
        if (ObjectUtils.isNotEmpty(currentDataSource)) {
            log.debug("[Herodotus] |- Found the multi tenant dataSource for id : [{}]", tenantIdentifier);
            return currentDataSource;
        } else {
            log.warn("[Herodotus] |- Cannot found the dataSource for tenant [{}], change to use default.", tenantIdentifier);
            return defaultDataSource;
        }
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, this);
    }
}
