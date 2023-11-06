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

package cn.herodotus.engine.assistant.core.json.jackson2.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>Description: 数组转字符串序列化 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/3/18 12:16
 */
public class ArrayOrStringToListDeserializer extends StdDeserializer<List<String>> {

    public ArrayOrStringToListDeserializer() {
        super(List.class);
    }

    public JavaType getValueType() {
        return TypeFactory.defaultInstance().constructType(String.class);
    }

    @Override
    public List<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonToken token = jsonParser.getCurrentToken();
        if (token.isScalarValue()) {
            String value = jsonParser.getText();
            value = value.replaceAll("\\s+", ",");
            return new ArrayList<>(Arrays.asList(StringUtils.commaDelimitedListToStringArray(value)));
        } else {
            return jsonParser.readValueAs(new TypeReference<List<String>>() {
            });
        }
    }


}
