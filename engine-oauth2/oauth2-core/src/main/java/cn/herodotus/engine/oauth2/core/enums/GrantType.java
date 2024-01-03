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

package cn.herodotus.engine.oauth2.core.enums;

import cn.herodotus.engine.assistant.definition.constants.BaseConstants;
import cn.herodotus.engine.assistant.definition.enums.BaseUiEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.ImmutableMap;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: OAuth2 认证模式枚举 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/16 14:39
 */
@Schema(title = "OAuth2 认证模式")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GrantType implements BaseUiEnum<String> {

    /**
     * enum
     */
    AUTHORIZATION_CODE(AuthorizationGrantType.AUTHORIZATION_CODE.getValue(), "Authorization Code 模式"),
    CLIENT_CREDENTIALS(AuthorizationGrantType.CLIENT_CREDENTIALS.getValue(), "Client Credentials 模式"),
    DEVICE_CODE(AuthorizationGrantType.DEVICE_CODE.getValue(), "Device Code 模式"),
    REFRESH_TOKEN(AuthorizationGrantType.REFRESH_TOKEN.getValue(), "Refresh Token 模式"),
    JWT_BEARER(AuthorizationGrantType.JWT_BEARER.getValue(), "JWT Token 模式"),
    PASSWORD(BaseConstants.PASSWORD, "Password 模式"),
    SOCIAL_CREDENTIALS(BaseConstants.SOCIAL_CREDENTIALS, "Social Credentials 模式");

    private static final Map<Integer, GrantType> INDEX_MAP = new HashMap<>();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList<>();

    static {
        for (GrantType grantType : GrantType.values()) {
            INDEX_MAP.put(grantType.ordinal(), grantType);
            JSON_STRUCTURE.add(grantType.ordinal(),
                    ImmutableMap.<String, Object>builder()
                            .put("value", grantType.getValue())
                            .put("key", grantType.name())
                            .put("text", grantType.getDescription())
                            .put("index", grantType.ordinal())
                            .build());
        }
    }

    @Schema(title = "认证模式")
    private final String value;
    @Schema(title = "文字")
    private final String description;

    GrantType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static GrantType get(Integer index) {
        return INDEX_MAP.get(index);
    }

    public static List<Map<String, Object>> getPreprocessedJsonStructure() {
        return JSON_STRUCTURE;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
