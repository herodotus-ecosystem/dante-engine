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

package cn.herodotus.engine.assistant.core.json.jackson2.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

/**
 * <p>Description: This class is a straight copy from Spring Authorization Server.</p>
 *
 * @author : gengwei.zheng
 * @date : 2022/10/24 15:31
 */
public class JsonNodeUtils {

    public static final TypeReference<Instant> INSTANT = new TypeReference<Instant>() {
    };

    public static final TypeReference<Set<String>> STRING_SET = new TypeReference<Set<String>>() {
    };

    public static final TypeReference<Map<String, Object>> STRING_OBJECT_MAP = new TypeReference<Map<String, Object>>() {
    };

    public static String findStringValue(JsonNode jsonNode, String fieldName) {
        if (jsonNode == null) {
            return null;
        }
        JsonNode value = jsonNode.findValue(fieldName);
        return (value != null && value.isTextual()) ? value.asText() : null;
    }

    public static boolean findBooleanValue(JsonNode jsonNode, String fieldName) {
        if (jsonNode == null) {
            return false;
        }
        JsonNode value = jsonNode.findValue(fieldName);
        return value != null && value.isBoolean() && value.asBoolean();
    }

    public static <T> T findValue(JsonNode jsonNode, String fieldName, TypeReference<T> valueTypeReference, ObjectMapper mapper) {
        if (jsonNode == null) {
            return null;
        }
        JsonNode value = jsonNode.findValue(fieldName);
        return (value != null && value.isContainerNode()) ? mapper.convertValue(value, valueTypeReference) : null;
    }

    public static JsonNode findObjectNode(JsonNode jsonNode, String fieldName) {
        if (jsonNode == null) {
            return null;
        }
        JsonNode value = jsonNode.findValue(fieldName);
        return (value != null && value.isObject()) ? value : null;
    }

    public static JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }
}
