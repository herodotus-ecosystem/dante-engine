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

package cn.herodotus.engine.oauth2.management.dto;

import cn.herodotus.engine.assistant.definition.domain.base.AbstractDto;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * <p>Description: 机要传递实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/2 16:29
 */
@Schema(title = "机要传递实体")
public class SessionExchange extends AbstractDto {

    @NotBlank(message = "confidential参数不能为空")
    @Schema(title = "用后端RSA/SM2 PublicKey加密的前端RSA/SM2 PublicKey")
    private String publicKey;

    @NotBlank(message = "Session Key不能为空")
    @Schema(title = "未登录前端身份标识")
    private String sessionId;

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
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
                .add("publicKey", publicKey)
                .add("sessionId", sessionId)
                .toString();
    }
}
