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

import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * <p>Description: 分页参数Bo对象 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/18 12:24
 */
@Schema(title = "分页参数BO对象")
public class Pager extends Sorter {

    @NotNull(message = "页码不能为空")
    @Min(value = 0, message = "页码不能为负")
    @Schema(name = "页码", type = "integer", minimum = "0", defaultValue = "0")
    private Integer pageNumber = 0;

    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数至少为1条")
    @Max(value = 1000, message = "每页条数不能超过1000")
    @Schema(name = "每页数据条数", type = "integer", minimum = "0", maximum = "1000", defaultValue = "10")
    private Integer pageSize = 10;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("pageNumber", pageNumber)
                .add("pageSize", pageSize)
                .toString();
    }
}
