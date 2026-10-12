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

package cn.herodotus.dante.logic.upms.entity.security;

import cn.herodotus.dante.core.constant.SystemConstants;
import cn.herodotus.dante.data.jpa.entity.AbstractEntity;
import cn.herodotus.dante.logic.upms.domain.generator.SysEnumIdGenerator;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

/**
 * <p>Description: 系统枚举信息 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/8/23 17:22
 */
@Schema(name = "系统枚举数据")
@Entity
@Table(name = "sys_enum", indexes = {@Index(name = "sys_enum_id_idx", columnList = "enum_id")})
public class SysEnum extends AbstractEntity {

    @Schema(name = "枚举ID")
    @Id
    @SysEnumIdGenerator
    @Column(name = "enum_id", length = 64)
    private String enumId;

    @Schema(name = "枚举分类")
    @Column(name = "category", length = 100)
    private String category;

    @Schema(name = "枚举标签")
    @Column(name = "enum_value", length = 50)
    private String value;

    @Schema(name = "显示值")
    @Column(name = "enum_label", length = 100)
    private String label;

    @Schema(name = "枚举字面量")
    @Column(name = "enum_name", length = 128)
    private String name;

    @Schema(name = "枚举原始值")
    @Column(name = "enum_ordinal")
    private int ordinal;

    @Schema(name = "接口所在类")
    @Column(name = "class_name", length = 512)
    private String className;

    @Schema(name = "Value 数据类型")
    @Column(name = "value_type", length = 20)
    private String valueType = SystemConstants.DATA_TYPE__STRING;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getEnumId() {
        return enumId;
    }

    public void setEnumId(String id) {
        this.enumId = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("category", category)
                .add("id", enumId)
                .add("value", value)
                .add("label", label)
                .add("name", name)
                .add("ordinal", ordinal)
                .add("className", className)
                .add("valueType", valueType)
                .toString();
    }
}
