/*
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君) (herodotus@aliyun.com & www.herodotus.cn)
 *
 * Dante Engine licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.gnu.org/licenses/lgpl-3.0.html>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.herodotus.engine.rest.core.definition.dto;

import cn.herodotus.engine.assistant.definition.domain.base.AbstractDto;
import cn.herodotus.engine.rest.core.annotation.EnumeratedValue;
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
