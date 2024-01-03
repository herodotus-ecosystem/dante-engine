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
import cn.herodotus.engine.rest.core.controller.BaseWriteableRestController;
import cn.herodotus.engine.supplier.message.entity.DialogueContact;
import cn.herodotus.engine.supplier.message.service.DialogueContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>Description: DialogueContactController </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/17 0:05
 */
@RestController
@RequestMapping("/message/dialogue/contact")
@Tags({
        @Tag(name = "消息管理接口"),
        @Tag(name = "私信管理接口"),
        @Tag(name = "私信联系人管理接口")
})
public class DialogueContactController extends BaseWriteableRestController<DialogueContact, String> {

    private final DialogueContactService dialogueContactService;

    public DialogueContactController(DialogueContactService dialogueContactService) {
        this.dialogueContactService = dialogueContactService;
    }

    @Override
    public WriteableService<DialogueContact, String> getWriteableService() {
        return dialogueContactService;
    }

    @Operation(summary = "条件查询私信联系人分页数据", description = "根据输入的字段条件查询联系人信息",
            responses = {@ApiResponse(description = "联系人列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)))})
    @Parameters({
            @Parameter(name = "pageNumber", required = true, description = "当前页码", schema = @Schema(type = "integer")),
            @Parameter(name = "pageSize", required = true, description = "每页显示数量", schema = @Schema(type = "integer")),
            @Parameter(name = "receiverId", required = true, description = "收信人ID，即当前用户ID"),
    })
    @GetMapping("/condition")
    public Result<Map<String, Object>> findByCondition(@NotNull @RequestParam("pageNumber") Integer pageNumber,
                                                       @NotNull @RequestParam("pageSize") Integer pageSize,
                                                       @NotNull @RequestParam("receiverId") String receiverId) {
        Page<DialogueContact> pages = dialogueContactService.findByCondition(pageNumber, pageSize, receiverId);
        return result(pages);
    }
}
