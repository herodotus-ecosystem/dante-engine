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

package cn.herodotus.dante.logic.upms.converter;

import cn.herodotus.dante.logic.upms.entity.security.SysAttribute;
import cn.herodotus.dante.messaging.domain.AttributeDistributor;
import cn.herodotus.dante.messaging.domain.SecurityAttribute;
import cn.herodotus.dante.spring.enums.MappingCategory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

/**
 * <p>Description: {@link SysAttribute} list 转 {@link AttributeDistributor} 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/23 22:59
 */
public class SysAttributesToAttributeDispatcherConverter implements Converter<List<SysAttribute>, AttributeDistributor> {

    private final Converter<SysAttribute, SecurityAttribute> toSecurityAttribute;
    private final String serviceId;
    private final MappingCategory category;

    public SysAttributesToAttributeDispatcherConverter(String serviceId, MappingCategory category) {
        this.toSecurityAttribute = new SysAttributeToSecurityAttributeConverter();
        this.serviceId = serviceId;
        this.category = category;
    }

    @Override
    public AttributeDistributor convert(List<SysAttribute> source) {
        if (CollectionUtils.isNotEmpty(source)) {
            AttributeDistributor target = new AttributeDistributor();

            List<SecurityAttribute> attributes = source.stream().map(toSecurityAttribute::convert).toList();
            target.setAttributes(attributes);
            target.setServiceId(serviceId);
            target.setCategory(category);

            return target;
        }

        return null;
    }
}
