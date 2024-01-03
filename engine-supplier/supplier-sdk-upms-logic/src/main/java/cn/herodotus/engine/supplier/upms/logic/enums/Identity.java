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

package cn.herodotus.engine.supplier.upms.logic.enums;

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
 * <p>Description: 人员身份</p>
 *
 * @author gengwei.zheng
 * @date 2019/2/15
 */
@Schema(title = "人员身份")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Identity implements BaseUiEnum<Integer> {
    /**
     * enum
     */
    STAFF(0, "员工"),
    SECTION_LEADER(1, "部门负责人"),
    LEADERSHIP(2, "领导");

    private static final Map<Integer, Identity> INDEX_MAP = new HashMap<>();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList<>();

    static {
        for (Identity identity : Identity.values()) {
            INDEX_MAP.put(identity.getValue(), identity);
            JSON_STRUCTURE.add(identity.getValue(),
                    ImmutableMap.<String, Object>builder()
                            .put("value", identity.getValue())
                            .put("key", identity.name())
                            .put("text", identity.getDescription())
                            .put("index", identity.getValue())
                            .build());
        }
    }

    @Schema(title = "索引")
    private final Integer value;
    @Schema(title = "文字")
    private String description;

    Identity(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public static Identity get(Integer index) {
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
     * @return Enum索引
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
