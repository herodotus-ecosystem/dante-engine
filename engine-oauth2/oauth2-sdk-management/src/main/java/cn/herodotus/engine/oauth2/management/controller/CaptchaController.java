/*
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君) (herodotus@aliyun.com & www.herodotus.cn)
 *
 * Dante Engine licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.gnu.org/licenses/lgpl.html>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.herodotus.engine.oauth2.management.controller;

import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.captcha.core.dto.Captcha;
import cn.herodotus.engine.captcha.core.dto.Verification;
import cn.herodotus.engine.captcha.core.processor.CaptchaRendererFactory;
import cn.herodotus.engine.rest.core.annotation.AccessLimited;
import cn.herodotus.engine.rest.core.annotation.Crypto;
import cn.herodotus.engine.rest.core.annotation.Idempotent;
import cn.herodotus.engine.rest.core.controller.Controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>Description: 验证码Controller </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/12 10:44
 */
@RestController
@RequestMapping("/open/captcha")
@Validated
@Tags({
        @Tag(name = "OAuth2 认证服务器接口"),
        @Tag(name = "OAuth2 认证服务器开放接口"),
        @Tag(name = "验证码接口")
})
public class CaptchaController implements Controller {

    private final CaptchaRendererFactory captchaRendererFactory;

    public CaptchaController(CaptchaRendererFactory captchaRendererFactory) {
        this.captchaRendererFactory = captchaRendererFactory;
    }

    @AccessLimited
    @Operation(summary = "获取验证码", description = "通过传递身份信息（类似于Session标识）",
            responses = {@ApiResponse(description = "验证码图形信息", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)))})
    @Parameters({
            @Parameter(name = "identity", required = true, in = ParameterIn.PATH, description = "身份信息"),
            @Parameter(name = "category", required = true, in = ParameterIn.PATH, description = "验证码类型")
    })
    @GetMapping
    public Result<Captcha> create(@NotBlank(message = "身份信息不能为空") String identity, @NotBlank(message = "验证码类型不能为空") String category) {
        Captcha captcha = captchaRendererFactory.getCaptcha(identity, category);
        if (ObjectUtils.isNotEmpty(captcha)) {
            return Result.success("验证码创建成功", captcha);
        } else {
            return Result.failure("验证码创建失败");
        }
    }

    @Idempotent
    @Crypto(responseEncrypt = false)
    @Operation(summary = "验证码验证", description = "验证验证码返回数据是否正确。使用加密信息",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "验证结果", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "jigsawVerification", required = true, description = "验证码验证参数", schema = @Schema(implementation = Verification.class))
    })
    @PostMapping
    public Result<Boolean> check(@Valid @RequestBody Verification verification) {
        boolean isSuccess = captchaRendererFactory.verify(verification);
        if (isSuccess) {
            return Result.success("验证码验证成功", true);
        }
        return Result.failure("验证码验证失败", true);
    }
}
