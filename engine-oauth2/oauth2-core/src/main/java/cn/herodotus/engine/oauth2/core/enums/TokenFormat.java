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
 * <p>Description: 令牌格式 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/3/25 0:02
 */
@Schema(name = "令牌格式")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TokenFormat implements BaseUiEnum<Integer> {

    /**
     * enum
     */
    SELF_CONTAINED(0, "self-contained", "自包含格式令牌"),
    REFERENCE(1, "reference", "引用（不透明）令牌");

    private static final Map<String, TokenFormat> INDEX_MAP = new HashMap<>();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList<>();

    static {
        for (TokenFormat tokenFormat : TokenFormat.values()) {
            INDEX_MAP.put(tokenFormat.getFormat(), tokenFormat);
            JSON_STRUCTURE.add(tokenFormat.getValue(),
                    ImmutableMap.<String, Object>builder()
                            // 使用数字作为 value, 适用于单选，同时数据库只存 value值即可
                            // 使用具体的字符串值作为value, 适用于多选，同时数据库存储以逗号分隔拼接的字符串
                            .put("value", tokenFormat.getValue())
                            .put("key", tokenFormat.name())
                            .put("text", tokenFormat.getDescription())
                            .put("format", tokenFormat.getFormat())
                            .put("index", tokenFormat.ordinal())
                            .build());
        }
    }

    @Schema(title = "枚举值")
    private final Integer value;
    @Schema(title = "格式")
    private final String format;
    @Schema(title = "文字")
    private final String description;

    TokenFormat(Integer value, String method, String description) {
        this.value = value;
        this.format = method;
        this.description = description;
    }

    public static TokenFormat get(String format) {
        return INDEX_MAP.get(format);
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

    public String getFormat() {
        return format;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
