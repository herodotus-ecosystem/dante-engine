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

package cn.herodotus.engine.supplier.upms.rest.controller.security;

import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.data.core.service.WriteableService;
import cn.herodotus.engine.rest.core.annotation.AccessLimited;
import cn.herodotus.engine.rest.core.controller.BaseWriteableRestController;
import cn.herodotus.engine.supplier.upms.logic.converter.SysElementToTreeNodeConverter;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysElement;
import cn.herodotus.engine.supplier.upms.logic.service.security.SysElementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.constraints.NotBlank;
import org.dromara.hutool.core.tree.MapTree;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>Description: SysMenuController </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/14 16:05
 */
@RestController
@RequestMapping("/security/element")
@Tags({
        @Tag(name = "用户安全管理接口"),
        @Tag(name = "系统菜单管理接口")
})
public class SysElementController extends BaseWriteableRestController<SysElement, String> {

    private final SysElementService sysElementService;

    public SysElementController(SysElementService sysElementService) {
        this.sysElementService = sysElementService;
    }

    @Override
    public WriteableService<SysElement, String> getWriteableService() {
        return sysElementService;
    }

    @Operation(summary = "获取菜单树", description = "获取系统菜单树",
            responses = {@ApiResponse(description = "单位列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))})
    @GetMapping("/tree")
    public Result<List<MapTree<String>>> findTree() {
        List<SysElement> sysMenus = sysElementService.findAll();
        return result(sysMenus, new SysElementToTreeNodeConverter());
    }

    @Operation(summary = "模糊条件查询前端元素", description = "根据动态输入的字段模糊查询前端元素",
            responses = {@ApiResponse(description = "前端元素列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)))})
    @Parameters({
            @Parameter(name = "pageNumber", required = true, description = "当前页码"),
            @Parameter(name = "pageSize", required = true, description = "每页显示数量"),
            @Parameter(name = "path", description = "组件路径"),
            @Parameter(name = "title", description = "组件标题"),
    })
    @GetMapping("/condition")
    public Result<Map<String, Object>> findByCondition(@NotBlank @RequestParam("pageNumber") Integer pageNumber,
                                                       @NotBlank @RequestParam("pageSize") Integer pageSize,
                                                       @RequestParam(value = "path", required = false) String path,
                                                       @RequestParam(value = "title", required = false) String title) {
        Page<SysElement> pages = sysElementService.findByCondition(pageNumber, pageSize, path, title);
        return result(pages);
    }

    @Operation(summary = "给前端元素分配角色", description = "给前端元素分配角色")
    @Parameters({
            @Parameter(name = "elementId", required = true, description = "元素ID"),
            @Parameter(name = "roles[]", required = true, description = "角色对象组成的数组")
    })
    @PutMapping
    public Result<SysElement> assign(@RequestParam(name = "elementId") String elementId, @RequestParam(name = "roles[]") String[] roles) {
        SysElement sysElement = sysElementService.assign(elementId, roles);
        return result(sysElement);
    }

    @AccessLimited
    @Operation(summary = "获取全部前端元素接口", description = "获取全部前端元素接口",
            responses = {@ApiResponse(description = "元素列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class)))})
    @GetMapping("/list")
    public Result<List<SysElement>> findAll() {
        List<SysElement> sysElements = sysElementService.findAll();
        return result(sysElements);
    }

    @AccessLimited
    @Operation(summary = "根据ID查询元素", description = "根据ID查询元素",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "操作消息", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "id", required = true, in = ParameterIn.PATH, description = "实体ID，@Id注解对应的实体属性")
    })
    @GetMapping("{id}")
    public Result<SysElement> findById(@PathVariable String id) {
        return super.findById(id);
    }
}
