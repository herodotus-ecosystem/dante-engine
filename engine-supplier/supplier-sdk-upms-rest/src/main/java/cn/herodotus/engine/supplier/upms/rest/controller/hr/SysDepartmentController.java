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

package cn.herodotus.engine.supplier.upms.rest.controller.hr;

import cn.herodotus.engine.assistant.definition.domain.Result;
import cn.herodotus.engine.data.core.service.WriteableService;
import cn.herodotus.engine.rest.core.controller.BaseWriteableRestController;
import cn.herodotus.engine.supplier.upms.logic.converter.SysDepartmentToTreeNodeConverter;
import cn.herodotus.engine.supplier.upms.logic.entity.hr.SysDepartment;
import cn.herodotus.engine.supplier.upms.logic.service.hr.SysDepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.dromara.hutool.core.tree.MapTree;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>Description: SysDepartmentController </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/9/23 23:13
 */
@RestController
@RequestMapping("/hr/department")
@Tag(name = "部门管理接口")
@Validated
public class SysDepartmentController extends BaseWriteableRestController<SysDepartment, String> {

    private final SysDepartmentService sysDepartmentService;

    public SysDepartmentController(SysDepartmentService sysDepartmentService) {
        this.sysDepartmentService = sysDepartmentService;
    }

    @Override
    public WriteableService<SysDepartment, String> getWriteableService() {
        return this.sysDepartmentService;
    }

    private List<SysDepartment> getSysDepartments(String organizationId) {
        return sysDepartmentService.findAll(organizationId);
    }

    @Operation(summary = "条件查询部门分页数据", description = "根据输入的字段条件查询部门信息",
            responses = {@ApiResponse(description = "单位列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SysDepartment.class)))})
    @Parameters({
            @Parameter(name = "pageNumber", required = true, description = "当前页码"),
            @Parameter(name = "pageSize", required = true, description = "每页显示数量"),
            @Parameter(name = "organizationId", description = "单位ID"),
    })
    @GetMapping("/condition")
    public Result<Map<String, Object>> findByCondition(@NotNull @RequestParam("pageNumber") Integer pageNumber,
                                                       @NotNull @RequestParam("pageSize") Integer pageSize,
                                                       @RequestParam(value = "organizationId", required = false) String organizationId) {
        Page<SysDepartment> pages = sysDepartmentService.findByCondition(pageNumber, pageSize, organizationId);
        return result(pages);
    }

    @Operation(summary = "获取部门列表", description = "根据单位ID获取部门信息列表",
            responses = {@ApiResponse(description = "单位列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SysDepartment.class)))})
    @Parameters({
            @Parameter(name = "organizationId", required = true, description = "单位ID"),
    })
    @GetMapping("/list")
    public Result<List<SysDepartment>> findAllByOrganizationId(@RequestParam(value = "organizationId", required = false) String organizationId) {
        List<SysDepartment> sysDepartments = getSysDepartments(organizationId);
        return result(sysDepartments);
    }

    @Operation(summary = "获取部门树", description = "根据单位ID获取部门数据，转换为树形结构",
            responses = {@ApiResponse(description = "单位列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SysDepartment.class)))})
    @Parameters({
            @Parameter(name = "organizationId", required = true, description = "单位ID"),
    })
    @GetMapping("/tree")
    public Result<List<MapTree<String>>> findTree(@RequestParam(value = "organizationId", required = false) String organizationId) {
        List<SysDepartment> sysDepartments = getSysDepartments(organizationId);
        return result(sysDepartments, new SysDepartmentToTreeNodeConverter());
    }
}
