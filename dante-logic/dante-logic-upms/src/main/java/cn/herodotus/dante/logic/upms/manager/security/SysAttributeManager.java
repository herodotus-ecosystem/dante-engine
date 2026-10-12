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

package cn.herodotus.dante.logic.upms.manager.security;

import cn.herodotus.dante.core.jackson.JacksonUtils;
import cn.herodotus.dante.logic.upms.converter.SysAttributeToAttributeDispatcherConverter;
import cn.herodotus.dante.logic.upms.converter.SysAttributesToAttributeDispatcherConverter;
import cn.herodotus.dante.logic.upms.converter.SysInterfacesToSysAttributesConverter;
import cn.herodotus.dante.logic.upms.entity.security.SysAttribute;
import cn.herodotus.dante.logic.upms.entity.security.SysInterface;
import cn.herodotus.dante.logic.upms.service.security.SysAttributeService;
import cn.herodotus.dante.logic.upms.service.security.SysInterfaceService;
import cn.herodotus.dante.messaging.domain.AttributeCollector;
import cn.herodotus.dante.messaging.domain.AttributeDistributor;
import cn.herodotus.dante.messaging.processor.AttributeTransferProcessor;
import cn.herodotus.dante.spring.founction.ListConverter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * <p>Description: {@link SysAttribute} Manager </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/10/12 18:14
 */
public class SysAttributeManager implements AttributeTransferProcessor {

    private static final Logger log = LoggerFactory.getLogger(SysAttributeManager.class);

    private final SysAttributeService sysAttributeService;
    private final SysInterfaceService sysInterfaceService;
    private final ListConverter<SysInterface, SysAttribute> toSysAttributes;

    public SysAttributeManager(SysAttributeService sysAttributeService, SysInterfaceService sysInterfaceService) {
        this.sysAttributeService = sysAttributeService;
        this.sysInterfaceService = sysInterfaceService;
        this.toSysAttributes = new SysInterfacesToSysAttributesConverter();
    }

    /**
     * 将服务发送的 {@link AttributeCollector} 信息，聚合存储至 {@link SysInterface}。然后将 {@link SysInterface} 表中存在但 {@link SysAttribute} 表中不存在的数据同步至 {@link SysAttribute} ，保证两侧数据一致
     *
     * @param collector    权限元数据收集器 {@link AttributeCollector}
     * @param publishEvent 数据发送操作
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void storeAndMergeAttributeCollector(AttributeCollector collector, BiConsumer<String, AttributeDistributor> publishEvent) {

        // 将各个服务发送回来的 requestMappings 存储到 SysInterface 中
        List<SysInterface> storedInterfaces = sysInterfaceService.storeMappingAttributes(collector.getAttributes());

        if (CollectionUtils.isNotEmpty(storedInterfaces)) {
            log.debug("[Herodotus] |- Mapping attribute store success, start to merge security metadata!");

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
            distributeAttributes(collector, publishEvent);
        }
    }

    private void distributeAttributes(AttributeCollector collector, BiConsumer<String, AttributeDistributor> publishEvent) {
        // 每次处理都是只针对一个服务，所以该组数据 serviceId 肯定都相同
        String serviceId = collector.getServiceId();
        List<SysAttribute> sysAttributes = sysAttributeService.findAllByServiceId(serviceId, collector.getCategory());
        if (CollectionUtils.isNotEmpty(sysAttributes)) {
            Converter<List<SysAttribute>, AttributeDistributor> toDispatchers = new SysAttributesToAttributeDispatcherConverter(serviceId, collector.getCategory());
            AttributeDistributor dispatcher = toDispatchers.convert(sysAttributes);
            log.debug("[Herodotus] |- [R6] Synchronization permissions to service [{}]", serviceId);
            publishEvent.accept(serviceId, dispatcher);
        }
    }

    @Override
    public void distributeChangedSecurityAttribute(String attribute, BiConsumer<String, AttributeDistributor> publishEvent) {
        if (StringUtils.isNotEmpty(attribute)) {
            SysAttribute sysAttribute = JacksonUtils.toObject(attribute, SysAttribute.class);
            if (ObjectUtils.isNotEmpty(sysAttribute)) {
                Converter<SysAttribute, AttributeDistributor> toDispatcher = new SysAttributeToAttributeDispatcherConverter();
                AttributeDistributor dispatcher = toDispatcher.convert(sysAttribute);
                publishEvent.accept(dispatcher.getServiceId(), dispatcher);
            } else {
                log.error("[Herodotus] |- Changed sysAttribute deserialization to entity failed.");
            }
        } else {
            log.error("[Herodotus] |- Changed sysAttribute data is null.");
        }
    }
}
