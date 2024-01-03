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

package cn.herodotus.engine.access.all.controller;

import cn.herodotus.engine.access.all.processor.AccessHandlerStrategyFactory;
import cn.herodotus.engine.access.core.definition.AccessResponse;
import cn.herodotus.engine.assistant.definition.domain.Result;
import cn.herodotus.engine.assistant.core.enums.AccountType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: 手机验证码登录 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/28 11:39
 */
@RestController
@Tag(name = "手机验证码登录接口")
public class PhoneNumberAccessController {

    @Autowired
    private AccessHandlerStrategyFactory accessHandlerStrategyFactory;

    @Operation(summary = "手机验证码发送地址", description = "接收手机号码，发送验证码，并缓存至Redis")
    @Parameters({
            @Parameter(name = "mobile", required = true, description = "手机号码"),
    })
    @PostMapping("/open/identity/verification-code")
    public Result<String> sendCode(@RequestParam("mobile") String mobile) {
        AccessResponse response = accessHandlerStrategyFactory.preProcess(AccountType.SMS, mobile);
        if (ObjectUtils.isNotEmpty(response)) {
            if (response.getSuccess()) {
                return Result.success("短信发送成功！");
            } else {
                return Result.failure("短信发送失败！");
            }
        }
        return Result.failure("手机号码接收失败！");
    }
}
