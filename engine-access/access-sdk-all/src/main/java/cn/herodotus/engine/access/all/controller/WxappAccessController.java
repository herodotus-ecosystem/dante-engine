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

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.herodotus.engine.access.all.dto.WxappProfile;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: 微信小程序平台认证 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/28 11:40
 */
@RestController
@Tag(name = "微信小程序平台认证接口")
public class WxappAccessController {

    @Autowired
    private AccessHandlerStrategyFactory accessHandlerStrategyFactory;

    @Operation(summary = "微信小程序登录", description = "利用wx.login获取code，进行小程序登录")
    @Parameters({
            @Parameter(name = "socialDetails", required = true, description = "社交登录自定义参数实体"),
    })
    @PostMapping("/open/identity/wxapp")
    public Result<WxMaJscode2SessionResult> login(@Validated @RequestBody WxappProfile wxappProfile) {
        AccessResponse response = accessHandlerStrategyFactory.preProcess(AccountType.WXAPP, wxappProfile.getCode(), wxappProfile.getAppId());
        if (ObjectUtils.isNotEmpty(response)) {
            return Result.success("微信小程序登录成功", response.getSession());
        } else {
            return Result.failure("微信小程序登录失败");
        }
    }
}
