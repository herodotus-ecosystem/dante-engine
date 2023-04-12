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

package cn.herodotus.engine.rest.core.definition.dto;

import cn.herodotus.engine.assistant.core.annotation.EnumeratedValue;
import cn.herodotus.engine.assistant.core.definition.domain.AbstractDto;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>Description: 排序参数 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/9 15:04
 */
@Schema(title = "排序参数BO对象")
public class Sorter extends AbstractDto {

    @EnumeratedValue(names = {"ASC", "DESC"}, message = "排序方式的值只能是大写 ASC 或者 DESC")
    @Schema(name = "排序方向", title = "排序方向的值只能是大写 ASC 或者 DESC, 默认值：DESC", defaultValue = "DESC")
    private String direction = "DESC";

    @Schema(name = "属性值", title = "指定排序的字段名称")
    private String[] properties;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String[] getProperties() {
        return properties;
    }

    public void setProperties(String[] properties) {
        this.properties = properties;
    }
}
