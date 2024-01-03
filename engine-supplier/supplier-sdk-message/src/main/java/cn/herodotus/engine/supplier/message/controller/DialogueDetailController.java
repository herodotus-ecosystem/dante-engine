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

package cn.herodotus.engine.supplier.message.controller;

import cn.herodotus.engine.assistant.definition.domain.Result;
import cn.herodotus.engine.data.core.service.WriteableService;
import cn.herodotus.engine.rest.core.annotation.Idempotent;
import cn.herodotus.engine.rest.core.controller.BaseWriteableRestController;
import cn.herodotus.engine.supplier.message.entity.DialogueDetail;
import cn.herodotus.engine.supplier.message.service.DialogueDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>Description: DialogueDetailController </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/17 12:49
 */
@RestController
@RequestMapping("/message/dialogue/detail")
@Tags({
        @Tag(name = "消息管理接口"),
        @Tag(name = "私信管理接口"),
        @Tag(name = "私信详情管理接口")
})
public class DialogueDetailController extends BaseWriteableRestController<DialogueDetail, String> {

    private final DialogueDetailService dialogueDetailService;

    public DialogueDetailController(DialogueDetailService dialogueDetailService) {
        this.dialogueDetailService = dialogueDetailService;
    }

    @Override
    public WriteableService<DialogueDetail, String> getWriteableService() {
        return dialogueDetailService;
    }

    @Operation(summary = "条件查询私信详情分页数据", description = "根据输入的字段条件查询详情信息",
            responses = {@ApiResponse(description = "详情列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)))})
    @Parameters({
            @Parameter(name = "pageNumber", required = true, description = "当前页码", schema = @Schema(type = "integer")),
            @Parameter(name = "pageSize", required = true, description = "每页显示数量", schema = @Schema(type = "integer")),
            @Parameter(name = "dialogueId", required = true, description = "对话ID"),
    })
    @GetMapping("/condition")
    public Result<Map<String, Object>> findByCondition(@NotNull @RequestParam("pageNumber") Integer pageNumber,
                                                       @NotNull @RequestParam("pageSize") Integer pageSize,
                                                       @NotNull @RequestParam("dialogueId") String dialogueId) {
        Page<DialogueDetail> pages = dialogueDetailService.findByCondition(pageNumber, pageSize, dialogueId);
        return result(pages);
    }

    @Idempotent
    @Operation(summary = "根据dialogueId删除私信整个对话", description = "根据实体dialogueId删除私信整个对话，包括相关联的关联数据",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "操作消息", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "id", required = true, in = ParameterIn.PATH, description = "DialogueId 关联私信联系人和私信详情的ID")
    })
    @DeleteMapping("/dialogue/{id}")
    public Result<String> deleteDialogueById(@PathVariable String id) {
        dialogueDetailService.deleteDialogueById(id);
        return Result.success("删除成功");
    }
}
