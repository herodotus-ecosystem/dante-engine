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

package cn.herodotus.engine.data.jpa.configuration;

import cn.herodotus.engine.data.jpa.annotation.ConditionalOnMultiTenantEnabled;
import cn.herodotus.engine.data.jpa.properties.MultiTenantProperties;
import cn.herodotus.engine.data.jpa.tenant.HerodotusMultiTenantConnectionProvider;
import cn.herodotus.engine.data.jpa.tenant.HerodotusMapDataSourceLookup;
import cn.herodotus.engine.data.jpa.tenant.HerodotusCurrentTenantIdentifierResolver;
import jakarta.annotation.PostConstruct;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Map;
import java.util.function.Supplier;

/**
 * <p>Description: Data JPA 模块 多租户配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/9/8 22:15
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnMultiTenantEnabled
@EnableConfigurationProperties(MultiTenantProperties.class)
public class MultiTenantConfiguration {

    private static final Logger log = LoggerFactory.getLogger(DataJpaConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Data Multi Tenant] Auto Configure.");
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public DataSourceLookup dataSourceLookup(DataSource dataSource, MultiTenantProperties multiTenantProperties) {
        HerodotusMapDataSourceLookup herodotusMapDataSourceLookup = new HerodotusMapDataSourceLookup(dataSource, multiTenantProperties);
        log.debug("[Herodotus] |- Bean [Multi Tenant DataSource Lookup] Auto Configure.");
        return herodotusMapDataSourceLookup;
    }

    @Bean
    public MultiTenantConnectionProvider multiTenantConnectionProvider(DataSource dataSource, DataSourceLookup dataSourceLookup) {
        HerodotusMultiTenantConnectionProvider herodotusMultiTenantConnectionProvider = new HerodotusMultiTenantConnectionProvider(dataSource, dataSourceLookup);
        log.debug("[Herodotus] |- Bean [Multi Tenant Connection Provider] Auto Configure.");
        return herodotusMultiTenantConnectionProvider;
    }

    @Bean
    public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
        HerodotusCurrentTenantIdentifierResolver herodotusCurrentTenantIdentifierResolver = new HerodotusCurrentTenantIdentifierResolver();
        log.debug("[Herodotus] |- Bean [Multi Tenant Connection Provider] Auto Configure.");
        return herodotusCurrentTenantIdentifierResolver;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, HibernateProperties hibernateProperties, JpaVendorAdapter jpaVendorAdapter, JpaProperties jpaProperties, MultiTenantProperties multiTenantProperties, MultiTenantConnectionProvider multiTenantConnectionProvider, CurrentTenantIdentifierResolver currentTenantIdentifierResolver) {

        Supplier<String> defaultDdlMode = hibernateProperties::getDdlAuto;
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings().ddlAuto(defaultDdlMode));

        // TODO: 多租户需要继续验证。目前发现参数错误
//        properties.put(Environment.MULTI_TENANT, multiTenancyProperties.getTenancyStrategy());
        properties.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
        properties.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        //此处不能省略，哪怕你使用了 @EntityScan，实际上 @EntityScan 会失效
        emf.setPackagesToScan(multiTenantProperties.getPackageToScan());
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setJpaPropertyMap(properties);
        return emf;
    }
}
