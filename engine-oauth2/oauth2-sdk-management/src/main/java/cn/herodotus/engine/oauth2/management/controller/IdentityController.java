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

package cn.herodotus.engine.oauth2.management.controller;

import cn.herodotus.engine.assistant.definition.domain.Result;
import cn.herodotus.engine.assistant.definition.domain.oauth2.SecretKey;
import cn.herodotus.engine.oauth2.authentication.dto.SignInErrorPrompt;
import cn.herodotus.engine.oauth2.authentication.dto.SignInErrorStatus;
import cn.herodotus.engine.oauth2.authentication.stamp.SignInFailureLimitedStampManager;
import cn.herodotus.engine.oauth2.management.dto.Session;
import cn.herodotus.engine.oauth2.management.dto.SessionCreate;
import cn.herodotus.engine.oauth2.management.dto.SessionExchange;
import cn.herodotus.engine.oauth2.management.service.InterfaceSecurityService;
import cn.herodotus.engine.rest.core.annotation.Crypto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gengwei.zheng
 * @see <a href="https://conkeyn.iteye.com/blog/2296406">参考文档</a>
 */
@RestController
@Tags({
        @Tag(name = "OAuth2 认证服务器接口"),
        @Tag(name = "OAuth2 认证服务器开放接口"),
        @Tag(name = "OAuth2 身份认证辅助接口")
})
public class IdentityController {

    private final InterfaceSecurityService interfaceSecurityService;
    private final SignInFailureLimitedStampManager signInFailureLimitedStampManager;

    public IdentityController(InterfaceSecurityService interfaceSecurityService, SignInFailureLimitedStampManager signInFailureLimitedStampManager) {
        this.interfaceSecurityService = interfaceSecurityService;
        this.signInFailureLimitedStampManager = signInFailureLimitedStampManager;
    }

    @Operation(summary = "获取后台加密公钥", description = "根据未登录时的身份标识，在后台创建RSA/SM2公钥和私钥。身份标识为前端的唯一标识，如果为空，则在后台创建一个",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "自定义Session", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "sessionCreate", required = true, description = "Session创建请求参数", schema = @Schema(implementation = SessionCreate.class)),
    })
    @PostMapping("/open/identity/session")
    public Result<Session> create(@Validated @RequestBody SessionCreate sessionCreate, HttpServletRequest request) {

        String sessionId = sessionCreate.getSessionId();
        if (StringUtils.isEmpty(sessionId)) {
            HttpSession session = request.getSession();
            sessionId = session.getId();
        }

        SecretKey secretKey = interfaceSecurityService.createSecretKey(sessionCreate.getClientId(), sessionCreate.getClientSecret(), sessionId);
        if (ObjectUtils.isNotEmpty(secretKey)) {
            Session session = new Session();
            session.setSessionId(secretKey.getIdentity());
            session.setPublicKey(secretKey.getPublicKey());
            session.setState(secretKey.getState());

            return Result.content(session);
        }

        return Result.failure();
    }

    @Operation(summary = "获取AES秘钥", description = "用后台publicKey，加密前台publicKey，到后台换取AES秘钥",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "加密后的AES", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "sessionExchange", required = true, description = "秘钥交换", schema = @Schema(implementation = SessionExchange.class)),
    })
    @PostMapping("/open/identity/exchange")
    public Result<String> exchange(@Validated @RequestBody SessionExchange sessionExchange) {

        String encryptedAesKey = interfaceSecurityService.exchange(sessionExchange.getSessionId(), sessionExchange.getPublicKey());
        if (StringUtils.isNotEmpty(encryptedAesKey)) {
            return Result.content(encryptedAesKey);
        }

        return Result.failure();
    }

    @Crypto(responseEncrypt = false)
    @Operation(summary = "获取登录出错剩余次数", description = "获取登录出错剩余次数",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "加密后的AES", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "signInErrorPrompt", required = true, description = "提示信息所需参数", schema = @Schema(implementation = SignInErrorPrompt.class)),
    })
    @PostMapping("/open/identity/prompt")
    public Result<SignInErrorStatus> prompt(@Validated @RequestBody SignInErrorPrompt signInErrorPrompt) {
        SignInErrorStatus signInErrorStatus = signInFailureLimitedStampManager.errorStatus(signInErrorPrompt.getUsername());
        return Result.content(signInErrorStatus);
    }
}
