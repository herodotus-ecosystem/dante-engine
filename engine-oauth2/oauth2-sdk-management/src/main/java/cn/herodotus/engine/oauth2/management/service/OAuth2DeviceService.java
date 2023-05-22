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

package cn.herodotus.engine.oauth2.management.service;

import cn.herodotus.engine.assistant.core.exception.transaction.TransactionalRollbackException;
import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.engine.data.core.service.BaseService;
import cn.herodotus.engine.oauth2.data.jpa.repository.HerodotusRegisteredClientRepository;
import cn.herodotus.engine.oauth2.management.converter.OAuth2DeviceToRegisteredClientConverter;
import cn.herodotus.engine.oauth2.management.converter.RegisteredClientToOAuth2DeviceConverter;
import cn.herodotus.engine.oauth2.management.entity.OAuth2Device;
import cn.herodotus.engine.oauth2.management.entity.OAuth2Scope;
import cn.herodotus.engine.oauth2.management.repository.OAuth2DeviceRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.oidc.OidcClientRegistration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: OAuth2DeviceService </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/15 16:36
 */
@Service
public class OAuth2DeviceService extends BaseService<OAuth2Device, String> {

    private static final Logger log = LoggerFactory.getLogger(OAuth2ApplicationService.class);

    private final RegisteredClientRepository registeredClientRepository;
    private final HerodotusRegisteredClientRepository herodotusRegisteredClientRepository;
    private final OAuth2DeviceRepository deviceRepository;
    private final Converter<OAuth2Device, RegisteredClient> oauth2DeviceToRegisteredClientConverter;
    private final Converter<RegisteredClient, OAuth2Device> registeredClientToOAuth2DeviceConverter;


    public OAuth2DeviceService(RegisteredClientRepository registeredClientRepository, HerodotusRegisteredClientRepository herodotusRegisteredClientRepository, OAuth2DeviceRepository deviceRepository, OAuth2ScopeService scopeService) {
        this.registeredClientRepository = registeredClientRepository;
        this.herodotusRegisteredClientRepository = herodotusRegisteredClientRepository;
        this.deviceRepository = deviceRepository;
        this.oauth2DeviceToRegisteredClientConverter = new OAuth2DeviceToRegisteredClientConverter();
        this.registeredClientToOAuth2DeviceConverter = new RegisteredClientToOAuth2DeviceConverter(scopeService);
    }

    @Override
    public BaseRepository<OAuth2Device, String> getRepository() {
        return deviceRepository;
    }

    @Override
    public OAuth2Device saveOrUpdate(OAuth2Device entity) {
        OAuth2Device device = super.saveOrUpdate(entity);
        if (ObjectUtils.isNotEmpty(device)) {
            registeredClientRepository.save(oauth2DeviceToRegisteredClientConverter.convert(device));
            log.trace("[Herodotus] |- OAuth2DeviceService saveOrUpdate.");
            return device;
        } else {
            log.error("[Herodotus] |- OAuth2DeviceService saveOrUpdate error, rollback data!");
            throw new NullPointerException("save or update OAuth2DeviceService failed");
        }
    }

    public boolean sync(OidcClientRegistration oidcClientRegistration) {
        RegisteredClient registeredClient = registeredClientRepository.findByClientId(oidcClientRegistration.getClientId());

        if (ObjectUtils.isNotEmpty(registeredClient)) {
            OAuth2Device oauth2Device = registeredClientToOAuth2DeviceConverter.convert(registeredClient);
            if (ObjectUtils.isNotEmpty(oauth2Device)) {
                OAuth2Device result = deviceRepository.save(oauth2Device);
                return ObjectUtils.isNotEmpty(result);
            }
        }
        return false;
    }

    @Transactional(rollbackFor = TransactionalRollbackException.class)
    @Override
    public void deleteById(String id) {
        super.deleteById(id);
        herodotusRegisteredClientRepository.deleteById(id);
        log.debug("[Herodotus] |- OAuth2DeviceService deleteById.");
    }

    @Transactional(rollbackFor = TransactionalRollbackException.class)
    public OAuth2Device authorize(String deviceId, String[] scopeIds) {

        Set<OAuth2Scope> scopes = new HashSet<>();
        for (String scopeId : scopeIds) {
            OAuth2Scope scope = new OAuth2Scope();
            scope.setScopeId(scopeId);
            scopes.add(scope);
        }

        OAuth2Device oldDevice = findById(deviceId);
        oldDevice.setScopes(scopes);

        OAuth2Device newApplication = saveOrUpdate(oldDevice);
        log.debug("[Herodotus] |- OAuth2DeviceService assign.");
        return newApplication;
    }

    public OAuth2Device findByClientId(String clientId) {
        OAuth2Device device = deviceRepository.findByClientId(clientId);
        log.debug("[Herodotus] |- OAuth2DeviceService findByClientId.");
        return device;
    }
}
