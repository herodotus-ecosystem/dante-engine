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

package cn.herodotus.engine.message.websocket.controller;

import cn.herodotus.engine.assistant.definition.domain.Result;
import cn.herodotus.engine.message.websocket.definition.WebSocketMessageSender;
import cn.herodotus.engine.message.websocket.utils.WebSocketUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: WebSocket 消息接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/11/18 14:06
 */
@RestController
@RequestMapping("/message/websocket")
@Tags({
        @Tag(name = "消息接口"),
        @Tag(name = "WebSocket消息接口")
})
public class WebSocketMessageController {

    private final WebSocketMessageSender webSocketMessageSender;

    public WebSocketMessageController(WebSocketMessageSender webSocketMessageSender) {
        this.webSocketMessageSender = webSocketMessageSender;
    }

    @Operation(summary = "后端发送通知", description = "后端发送 WebSocket 广播通知接口",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "是否成功", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "message", required = true, description = "消息实体")
    })
    @PostMapping("/send/notice")
    public Result<String> sendNotice(@RequestBody String message) {

        if (StringUtils.isNotBlank(message)) {
            webSocketMessageSender.announcement(message);
        }

        return Result.success(message);
    }

    @Operation(summary = "获取统计信息", description = "获取WebSocket相关的统计信息")
    @GetMapping(value = "/stat")
    public Result<Map<String, Object>> findAllStat() {
        Map<String, Object> stat = new HashMap<>();
        stat.put("onlineCount", WebSocketUtils.getOnlineCount());
        return Result.success("获取统计信息成功", stat);
    }
}
