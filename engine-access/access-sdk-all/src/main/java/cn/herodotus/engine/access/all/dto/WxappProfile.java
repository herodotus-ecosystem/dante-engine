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

package cn.herodotus.engine.access.all.dto;

import cn.herodotus.engine.assistant.definition.domain.base.AbstractDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * <p>Description: 微信小程序登录请求实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/26 14:44
 */
@Schema(name = "微信小程序登录请求实体", title = "根据code和appid返回微信小程序session信息")
public class WxappProfile extends AbstractDto {

    @Schema(name = "code", title = "前端调用小程序自己的方法返回的code")
    @NotBlank(message = "微信小程序code参数不能为空")
    private String code;

    @Schema(name = "appId", title = "需要前端返回给后端appId，以支持多个小程序")
    @NotBlank(message = "微信小程序appId参数不能为空")
    private String appId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
