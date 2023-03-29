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

package cn.herodotus.engine.data.tenant.hibernate;

import cn.herodotus.engine.assistant.core.context.TenantContextHolder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>Description: 租户选择器 </p>
 * <p>
 * 数据库请求发生时，应该使用哪个租户的连接信息。使用 CurrentTenantIdentifierResolver （租户ID解析器）接口获取这一信息
 *
 * @author : gengwei.zheng
 * @date : 2022/9/8 18:14
 */
@Component
public class HerodotusTenantIdentifierResolver implements CurrentTenantIdentifierResolver, HibernatePropertiesCustomizer {

    private static final Logger log = LoggerFactory.getLogger(HerodotusTenantIdentifierResolver.class);

    @Override
    public String resolveCurrentTenantIdentifier() {
        String currentTenantId = TenantContextHolder.getTenantId();
        log.trace("[Herodotus] |- Resolve Current Tenant Identifier is : [{}]", currentTenantId);
        return currentTenantId;
    }

    /**
     * Additionally, if the CurrentTenantIdentifierResolver implementation returns true for its validateExistingCurrentSessions method,
     * Hibernate will make sure any existing sessions that are found in scope have a matching tenant identifier.
     * This capability is only pertinent when the CurrentTenantIdentifierResolver is used in current-session settings.
     *
     * @return 确保已经存在的 Session 都有一个对应的 Tenant ID
     */
    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        log.debug("[Herodotus] |- Apply hibernate properties [MULTI_TENANT_IDENTIFIER_RESOLVER]");
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
    }
}
