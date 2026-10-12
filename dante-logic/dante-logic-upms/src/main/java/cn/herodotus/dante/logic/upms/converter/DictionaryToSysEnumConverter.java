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

import cn.herodotus.dante.core.constant.SymbolConstants;
import cn.herodotus.dante.core.constant.SystemConstants;
import cn.herodotus.dante.core.domain.Dictionary;
import cn.herodotus.dante.logic.upms.entity.security.SysEnum;
import cn.herodotus.dante.spring.founction.ListConverter;
import cn.hutool.v7.crypto.SecureUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>Description: Dictionary 转 SysEnum 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/8/23 22:09
 */
public class DictionaryToSysEnumConverter implements ListConverter<Dictionary, SysEnum> {

    @Override
    public SysEnum from(Dictionary source) {

        SysEnum target = new SysEnum();
        target.setCategory(source.getCategory());
        target.setClassName(source.getClazz());
        target.setEnumId(createId(source));
        target.setLabel(source.getLabel());
        target.setName(source.getName());
        target.setOrdinal(source.getOrdinal());
        target.setValue(source.getValue());
        target.setValueType(getValueType(source));

        return target;
    }

    private String createId(Dictionary source) {
        String key = source.getClazz() + SymbolConstants.DASH + source.getCategory() + SymbolConstants.DASH + source.getLabel() + SymbolConstants.DASH + source.getName() + SymbolConstants.DASH + source.getValue() + SymbolConstants.DASH + source.getOrdinal();
        return SecureUtil.md5(key);
    }

    private String getValueType(Dictionary source) {
        if (StringUtils.isNotBlank(source.getValue())) {
            if (StringUtils.isNumeric(source.getValue())) {
                return SystemConstants.DATA_TYPE__NUMBER;
            }
        }

        return SystemConstants.DATA_TYPE__STRING;
    }
}
