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

package cn.herodotus.engine.oauth2.management.dto;

import cn.herodotus.engine.assistant.core.definition.domain.AbstractDto;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * <p>Description: 加密创建请求 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/1 15:19
 */
@Schema(title = "加密创建请求")
public class SessionCreate extends AbstractDto {

    @NotBlank(message = "客户端ID不能为空")
    @Schema(title = "客户端ID")
    private String clientId;

    @NotBlank(message = "客户端秘钥不能为空")
    @Schema(title = "客户端秘钥")
    private String clientSecret;

    @Schema(title = "未登录前端身份标识")
    private String sessionId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("clientId", clientId)
                .add("clientSecret", clientSecret)
                .add("sessionId", sessionId)
                .toString();
    }
}
