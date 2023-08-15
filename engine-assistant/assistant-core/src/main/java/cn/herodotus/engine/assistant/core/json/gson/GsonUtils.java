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

package cn.herodotus.engine.assistant.core.json.gson;

import cn.herodotus.engine.assistant.core.definition.constants.DefaultConstants;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: Gson 工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/5/25 12:49
 */
public class GsonUtils {

    private static final GsonBuilder GSON_BUILDER = new GsonBuilder();
    private static volatile Gson instance;

    static {
        GSON_BUILDER.enableComplexMapKeySerialization();
        GSON_BUILDER.serializeNulls();
        GSON_BUILDER.setDateFormat(DefaultConstants.DATE_TIME_FORMAT);
        GSON_BUILDER.disableHtmlEscaping();
    }

    private GsonUtils() {

    }

    public static Gson getInstance() {

        if (ObjectUtils.isEmpty(instance)) {
            synchronized (GSON_BUILDER) {
                if (ObjectUtils.isEmpty(instance)) {
                    instance = GSON_BUILDER.create();
                }

            }
        }

        return instance;
    }

    public static JsonElement toJsonElement(String content) {
        return JsonParser.parseString(content);
    }

    public static JsonArray toJsonArray(String content) {
        return toJsonElement(content).getAsJsonArray();
    }

    public static JsonObject toJsonObject(String content) {
        return toJsonElement(content).getAsJsonObject();
    }

    public static <T> String toJson(T domain) {
        return getInstance().toJson(domain);
    }

    /**
     * 将 json 转化为 对象
     *
     * @param content   json 字符串
     * @param valueType 目标对象类型
     * @param <T>       对象类型
     * @return 转换后的对象
     */
    public static <T> T toObject(String content, Class<T> valueType) {
        return getInstance().fromJson(content, valueType);
    }

    /**
     * 将 json 转化为 对象
     * <p>
     * new TypeToken<List<T>>() {}.getType()
     * new TypeToken<Map<String, T>>() {}.getType()
     * new TypeToken<List<Map<String, T>>>() {}.getType()
     *
     * @param content json 字符串
     * @param typeOfT 目标对象类型
     * @param <T>     对象类型
     * @return 转换后的对象
     */
    public static <T> T toObject(String content, Type typeOfT) {
        return getInstance().fromJson(content, typeOfT);
    }

    public static <T> T toList(String content, Class<T> valueType) {
        return getInstance().fromJson(content, new TypeToken<List<T>>() {
        }.getType());
    }

    public static <T> List<Map<String, T>> toListMap(String content) {
        return getInstance().fromJson(content, new TypeToken<List<Map<String, String>>>() {
        }.getType());
    }

    public static <T> Map<String, T> toMaps(String gsonString) {
        return getInstance().fromJson(gsonString, new TypeToken<Map<String, T>>() {
        }.getType());
    }

}
