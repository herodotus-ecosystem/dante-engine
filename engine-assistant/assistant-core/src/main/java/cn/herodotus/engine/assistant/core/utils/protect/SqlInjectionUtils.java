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

package cn.herodotus.engine.assistant.core.utils.protect;

import cn.herodotus.engine.assistant.core.json.gson.GsonUtils;
import cn.herodotus.engine.assistant.definition.constants.SymbolConstants;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.dromara.hutool.core.net.url.UrlDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * <p>Description: SQL注入处理工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/9/1 10:17
 */
public class SqlInjectionUtils {

    private static final Logger log = LoggerFactory.getLogger(SqlInjectionUtils.class);

    private static final String SQL_REGEX = "\\b(and|or)\\b.{1,6}?(=|>|<|\\bin\\b|\\blike\\b)|\\/\\*.+?\\*\\/|<\\s*script\\b|\\bEXEC\\b|UNION.+?SELECT|UPDATE.+?SET|INSERT\\s+INTO.+?VALUES|(SELECT|DELETE).+?FROM|(CREATE|ALTER|DROP|TRUNCATE)\\s+(TABLE|DATABASE)";

    private static final Pattern SQL_PATTERN = Pattern.compile(SQL_REGEX, Pattern.CASE_INSENSITIVE);

    private static boolean matching(String lowerValue, String param) {
        if (SQL_PATTERN.matcher(param).find()) {
            log.error("[Herodotus] |- The parameter contains keywords {} that do not allow SQL!", lowerValue);
            return true;
        }
        return false;
    }

    private static String toLowerCase(Object obj) {
        //这里需要将参数转换为小写来处理
        return Optional.ofNullable(obj)
                .map(Object::toString)
                .map(String::toLowerCase)
                .orElse("");
    }

    private static boolean checking(Object value) {
        //这里需要将参数转换为小写来处理
        String lowerValue = toLowerCase(value);
        return matching(lowerValue, lowerValue);
    }

    /**
     * get请求sql注入校验
     *
     * @param value 具体的检验
     * @return 是否存在不合规内容
     */
    public static boolean checkForGet(String value) {

        //参数需要url编码
        //这里需要将参数转换为小写来处理
        //不改变原值
        //value示例 order=asc&pageNum=1&pageSize=100&parentId=0
        String lowerValue = UrlDecoder.decode(value, StandardCharsets.UTF_8).toLowerCase();

        //获取到请求中所有参数值-取每个key=value组合第一个等号后面的值
        return Stream.of(lowerValue.split("\\&"))
                .map(kp -> kp.substring(kp.indexOf(SymbolConstants.EQUAL) + 1))
                .parallel()
                .anyMatch(param -> matching(lowerValue, param));
    }

    /**
     * post请求sql注入校验
     *
     * @param value 具体的检验
     * @return 是否存在不合规内容
     */
    public static boolean checkForPost(String value) {

        List<JsonElement> result = new ArrayList<>();

        JsonElement jsonElement = GsonUtils.toJsonElement(value);
        iterator(jsonElement, result);

        return CollectionUtils.isNotEmpty(result);
    }

    private static void iterator(JsonElement jsonElement, List<JsonElement> result) {
        if (jsonElement.isJsonNull()) {
            return;
        }

        if (jsonElement.isJsonPrimitive()) {
            boolean hasInjection = checking(jsonElement.toString());
            if (hasInjection) {
                result.add(jsonElement);
            }
            return;
        }

        if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            if (ObjectUtils.isNotEmpty(jsonArray)) {
                for (JsonElement je : jsonArray) {
                    iterator(je, result);
                }
            }
            return;
        }

        if (jsonElement.isJsonObject()) {
            Set<Map.Entry<String, JsonElement>> es = jsonElement.getAsJsonObject().entrySet();
            for (Map.Entry<String, JsonElement> en : es) {
                iterator(en.getValue(), result);
            }
        }
    }

}