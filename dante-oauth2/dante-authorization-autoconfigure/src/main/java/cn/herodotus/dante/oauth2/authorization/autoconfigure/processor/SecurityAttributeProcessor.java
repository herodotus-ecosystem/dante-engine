/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 是 Dante Cloud 系统核心组件库，采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dante.oauth2.authorization.autoconfigure.processor;

import cn.herodotus.dante.logic.upms.converter.SysAttributeToAttributeDispatcherConverter;
import cn.herodotus.dante.logic.upms.converter.SysAttributesToAttributeDispatcherConverter;
import cn.herodotus.dante.logic.upms.converter.SysInterfacesToSysAttributesConverter;
import cn.herodotus.dante.logic.upms.entity.security.SysAttribute;
import cn.herodotus.dante.logic.upms.entity.security.SysInterface;
import cn.herodotus.dante.logic.upms.service.security.SysAttributeService;
import cn.herodotus.dante.logic.upms.service.security.SysInterfaceService;
import cn.herodotus.dante.spring.event.StrategyEventManager;
import cn.herodotus.dante.messaging.domain.AttributeCollector;
import cn.herodotus.dante.messaging.domain.AttributeDistributor;
import cn.herodotus.dante.messaging.event.ApplicationReadinessEvent;
import cn.herodotus.dante.oauth2.authorization.attribute.SecurityAttributeManager;
import cn.herodotus.dante.oauth2.authorization.autoconfigure.bus.RemoteAttributeDistributionEvent;
import cn.herodotus.dante.spring.context.ServiceContextHolder;
import cn.herodotus.dante.spring.founction.ListConverter;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>Description: SecurityMetadata数据处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/8 14:00
 */
@Component
public class SecurityAttributeProcessor implements StrategyEventManager<AttributeDistributor> {

    private static final Logger log = LoggerFactory.getLogger(SecurityAttributeProcessor.class);

    private final ListConverter<SysInterface, SysAttribute> toSysAttributes;
    private final SysAttributeService sysAttributeService;
    private final SysInterfaceService sysInterfaceService;
    private final SecurityAttributeManager securityAttributeManager;

    public SecurityAttributeProcessor(SysAttributeService sysAttributeService, SysInterfaceService sysInterfaceService, SecurityAttributeManager securityAttributeManager) {
        this.sysAttributeService = sysAttributeService;
        this.sysInterfaceService = sysInterfaceService;
        this.securityAttributeManager = securityAttributeManager;
        this.toSysAttributes = new SysInterfacesToSysAttributesConverter();
    }

    /**
     * UPMS 服务既要处理各个服务权限数据的分发，也要处理自身服务权限数据
     *
     * @param data 事件携带数据
     */
    @Override
    public void postLocalProcess(AttributeDistributor data) {
        securityAttributeManager.postAttributeDistributorProcess(data);
    }

    @Override
    public void postRemoteProcess(String data, String originService, String destinationService) {
        publishEvent(new RemoteAttributeDistributionEvent(data, originService, destinationService));
    }

    /**
     * 将SysAuthority表中存在，但是SysSecurityAttribute中不存在的数据同步至SysSecurityAttribute，保证两侧数据一致
     */
    @Transactional(rollbackFor = Exception.class)
    public void postAttributeCollectorProcess(AttributeCollector collector) {

        // 将各个服务发送回来的 requestMappings 存储到 SysInterface 中
        List<SysInterface> storedInterfaces = sysInterfaceService.storeMappingAttributes(collector.getAttributes());

        if (CollectionUtils.isNotEmpty(storedInterfaces)) {
            log.debug("[Herodotus] |- [R5] Mapping attribute store success, start to merge security metadata!");

            // 查询将新增的 SysInterface，将其转存到 SysAttribute 中
            List<SysInterface> sysInterfaces = sysInterfaceService.findAllocatable();
            if (CollectionUtils.isNotEmpty(sysInterfaces)) {
                List<SysAttribute> elements = toSysAttributes.convert(sysInterfaces);
                List<SysAttribute> result = sysAttributeService.saveAll(elements);
                if (CollectionUtils.isNotEmpty(result)) {
                    log.debug("[Herodotus] |- Merge security attribute SUCCESS and FINISHED!");
                } else {
                    log.error("[Herodotus] |- Merge Security attribute failed!, Please Check!");
                }
            } else {
                log.debug("[Herodotus] |- No security attribute requires merge, SKIP!");
            }

            // 执行权限数据分发
            distributeAttributes(collector);

            if (!ServiceContextHolder.isDistributedArchitecture()) {
                publishEvent(new ApplicationReadinessEvent("Attribute Transmitter Distribute Success"));
            }
//
//            List<SysAttribute> sysAttributes = sysAttributeService.findAll();
//            this.postGroupProcess(sysAttributes);
        }
    }

    private void distributeAttributes(AttributeCollector collector) {
        // 每次处理都是只针对一个服务，所以该组数据 serviceId 肯定都相同
        String serviceId = collector.getServiceId();
        List<SysAttribute> sysAttributes = sysAttributeService.findAllByServiceId(serviceId, collector.getCategory());
        if (CollectionUtils.isNotEmpty(sysAttributes)) {
            Converter<List<SysAttribute>, AttributeDistributor> toDispatchers = new SysAttributesToAttributeDispatcherConverter(serviceId, collector.getCategory());
            AttributeDistributor dispatcher = toDispatchers.convert(sysAttributes);
            log.debug("[Herodotus] |- [R6] Synchronization permissions to service [{}]", serviceId);
            this.postProcess(serviceId, dispatcher);
        }
    }

    public void distributeChangedSecurityAttribute(SysAttribute sysAttribute) {
        Converter<SysAttribute, AttributeDistributor> toDispatcher = new SysAttributeToAttributeDispatcherConverter();
        AttributeDistributor dispatcher = toDispatcher.convert(sysAttribute);
        postProcess(dispatcher.getServiceId(), dispatcher);
    }
}
