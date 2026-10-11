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

package cn.herodotus.dante.messaging.definition;

import cn.herodotus.dante.core.domain.Scan;
import cn.herodotus.dante.messaging.domain.AttributeCollector;
import cn.herodotus.dante.messaging.domain.MappingAttribute;
import cn.herodotus.dante.messaging.strategy.AttributeCollectionEventManager;
import cn.herodotus.dante.spring.enums.MappingCategory;
import cn.herodotus.dante.spring.initializer.AbstractApplicationReadyScanner;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Description: {@link MappingAttribute} 扫描抽象定义 </p>
 * <p>
 * 提取 {@link MappingAttribute} 扫描公共方法作为基类
 *
 * @author : gengwei_zheng
 * @date : 2026/9/18 23:32
 */
public abstract class AbstractMappingAttributeScanner extends AbstractApplicationReadyScanner<AttributeCollector> {

    private static final Logger log = LoggerFactory.getLogger(AbstractMappingAttributeScanner.class);

    protected AbstractMappingAttributeScanner(Scan scan, AttributeCollectionEventManager attributeCollectionEventManager) {
        super(scan, attributeCollectionEventManager);
    }

    @Override
    protected boolean isNotEmpty(AttributeCollector data) {
        return ObjectUtils.isNotEmpty(data) && CollectionUtils.isNotEmpty(data.getAttributes());
    }

    /**
     * 扫描完成，发送 Event 传递数据。
     *
     * @param attributes 扫描到的属性值
     * @param category   映射类别 {@link MappingCategory}
     */
    protected void complete(List<MappingAttribute> attributes, MappingCategory category) {
        log.info("[Herodotus] |- Scanner for {} resources in service: [{}] COMPLETE!", category.getLabel(), getServiceId());
        complete(new AttributeCollector(attributes, getServiceId(), category), attributes.size());
    }

    /**
     * 扫描完成，发送 Event 传递数据。
     *
     * @param attributes 扫描到的属性值
     */
    protected void complete(List<MappingAttribute> attributes) {
        complete(attributes, MappingCategory.REST);
    }
}
