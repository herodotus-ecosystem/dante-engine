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

import cn.herodotus.engine.assistant.definition.enums.BaseUiEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: OAuth2 TokenJwsAlgorithm </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/12/12 19:48
 */
@Schema(name = "全部Jws加密算法")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AllJwsAlgorithm implements BaseUiEnum<Integer> {

    /**
     * RSASSA-PKCS1-v1_5 using SHA-256 (Recommended)
     */
    RS256(0, "签名算法 RS256"),
    /**
     * RSASSA-PKCS1-v1_5 using SHA-384 (Optional)
     */
    RS384(1, "签名算法 RS384"),
    /**
     * RSASSA-PKCS1-v1_5 using SHA-512 (Optional)
     */
    RS512(2, "签名算法 RS512"),
    /**
     * ECDSA using P-256 and SHA-256 (Recommended+)
     */
    ES256(3, "签名算法 ES256"),
    /**
     * ECDSA using P-384 and SHA-384 (Optional)
     */
    ES384(4, "签名算法 ES384"),
    /**
     * ECDSA using P-521 and SHA-512 (Optional)
     */
    ES512(5, "签名算法 ES512"),
    /**
     * RSASSA-PSS using SHA-256 and MGF1 with SHA-256 (Optional)
     */
    PS256(6, "签名算法 PS256"),
    /**
     * RSASSA-PSS using SHA-384 and MGF1 with SHA-384 (Optional)
     */
    PS384(7, "签名算法 PS384"),
    /**
     * RSASSA-PSS using SHA-512 and MGF1 with SHA-512 (Optional)
     */
    PS512(8, "签名算法 PS512"),
    /**
     * HMAC using SHA-256 (Required)
     */
    HS256(9, "Mac算法 HS256"),

    /**
     * HMAC using SHA-384 (Optional)
     */
    HS384(10, "Mac算法 HS384"),

    /**
     * HMAC using SHA-512 (Optional)
     */
    HS512(11, "Mac算法 HS512");

    private static final Map<Integer, AllJwsAlgorithm> INDEX_MAP = new HashMap<>();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList<>();

    static {
        for (AllJwsAlgorithm allJwsAlgorithm : AllJwsAlgorithm.values()) {
            INDEX_MAP.put(allJwsAlgorithm.getValue(), allJwsAlgorithm);
            JSON_STRUCTURE.add(allJwsAlgorithm.getValue(),
                    ImmutableMap.<String, Object>builder()
                            .put("value", allJwsAlgorithm.getValue())
                            .put("key", allJwsAlgorithm.name())
                            .put("text", allJwsAlgorithm.getDescription())
                            .put("index", allJwsAlgorithm.getValue())
                            .build());
        }
    }

    @Schema(title = "枚举值")
    private final Integer value;
    @Schema(name = "文字")
    private final String description;

    AllJwsAlgorithm(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public static AllJwsAlgorithm get(Integer index) {
        return INDEX_MAP.get(index);
    }

    public static List<Map<String, Object>> getPreprocessedJsonStructure() {
        return JSON_STRUCTURE;
    }

    /**
     * 不加@JsonValue，转换的时候转换出完整的对象。
     * 加了@JsonValue，只会显示相应的属性的值
     * <p>
     * 不使用@JsonValue @JsonDeserializer类里面要做相应的处理
     *
     * @return Enum枚举值
     */
    @JsonValue
    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
