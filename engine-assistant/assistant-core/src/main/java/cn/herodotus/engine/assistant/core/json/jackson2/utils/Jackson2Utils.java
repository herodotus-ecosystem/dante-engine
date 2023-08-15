/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.assistant.core.json.jackson2.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * @author gengwei.zheng
 */
@Component
public class Jackson2Utils {

    private static final Logger logger = LoggerFactory.getLogger(Jackson2Utils.class);

    private static ObjectMapper OBJECT_MAPPER;

    @Autowired
    private ObjectMapper objectMapper;

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    public static ObjectMapper registerModule(Module module) {
        return getObjectMapper().registerModules(module);
    }

    public static <T> String toJson(T domain) {
        try {
            return getObjectMapper().writeValueAsString(domain);
        } catch (JsonProcessingException e) {
            logger.error("[Herodotus] |- Jackson2 json processing error, when to json! {}", e.getMessage());
            return null;
        }
    }

    public static TypeFactory getTypeFactory() {
        return getObjectMapper().getTypeFactory();
    }

    public static <T> T toObject(String content, Class<T> valueType) {
        try {
            return getObjectMapper().readValue(content, valueType);
        } catch (JsonProcessingException e) {
            logger.error("[Herodotus] |- Jackson2 json processing error, when to object with value type! {}", e.getMessage());
            return null;
        }
    }

    public static <T> T toObject(String content, TypeReference<T> typeReference) {
        try {
            return getObjectMapper().readValue(content, typeReference);
        } catch (JsonProcessingException e) {
            logger.error("[Herodotus] |- Jackson2 json processing error, when to object with type reference! {}", e.getMessage());
            return null;
        }
    }

    public static <T> T toObject(String content, JavaType javaType) {
        try {
            return getObjectMapper().readValue(content, javaType);
        } catch (JsonProcessingException e) {
            logger.error("[Herodotus] |- Jackson2 json processing error, when to object with java type! {}", e.getMessage());
            return null;
        }
    }

    public static <T> List<T> toList(String content, Class<T> clazz) {
        JavaType javaType = getObjectMapper().getTypeFactory().constructParametricType(List.class, clazz);
        return toObject(content, javaType);
    }

    public static <K, V> Map<K, V> toMap(String content, Class<K> keyClass, Class<V> valueClass) {
        JavaType javaType = getObjectMapper().getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
        return toObject(content, javaType);
    }

    public static Map<String, Object> toMap(String content) {
        return toMap(content, String.class, Object.class);
    }

    public static <T> Set<T> toSet(String content, Class<T> clazz) {
        JavaType javaType = getTypeFactory().constructCollectionLikeType(Set.class, clazz);
        return toObject(content, javaType);
    }

    public static <T> T[] toArray(String content, Class<T> clazz) {
        JavaType javaType = getTypeFactory().constructArrayType(clazz);
        return toObject(content, javaType);
    }

    public static <T> T[] toArray(String content) {
        return toObject(content, new TypeReference<T[]>() {
        });
    }

    public static JsonNode toNode(String content) {
        try {
            return getObjectMapper().readTree(content);
        } catch (JsonProcessingException e) {
            logger.error("[Herodotus] |- Jackson2 json processing error, when to node with string! {}", e.getMessage());
            return null;
        }
    }

    public static JsonNode toNode(JsonParser jsonParser) {
        try {
            return getObjectMapper().readTree(jsonParser);
        } catch (IOException e) {
            logger.error("[Herodotus] |- Jackson2 io error, when to node with json parser! {}", e.getMessage());
            return null;
        }
    }

    public static JsonParser createParser(String content) {
        try {
            return getObjectMapper().createParser(content);
        } catch (IOException e) {
            logger.error("[Herodotus] |- Jackson2 io error, when create parser! {}", e.getMessage());
            return null;
        }
    }

    public static <R> R loop(JsonNode jsonNode, Function<JsonNode, R> function) {
        if (jsonNode.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> it = jsonNode.fields();
            while (it.hasNext()) {
                Map.Entry<String, JsonNode> entry = it.next();
                loop(entry.getValue(), function);
            }
        }

        if (jsonNode.isArray()) {
            for (JsonNode node : jsonNode) {
                loop(node, function);
            }
        }

        if (jsonNode.isValueNode()) {
            return function.apply(jsonNode);
        } else {
            return null;
        }
    }

    @PostConstruct
    public void init() {
        if (ObjectUtils.isNotEmpty(this.objectMapper)) {
            OBJECT_MAPPER = this.objectMapper;
        } else {
            OBJECT_MAPPER = new ObjectMapper();
        }
    }
}
