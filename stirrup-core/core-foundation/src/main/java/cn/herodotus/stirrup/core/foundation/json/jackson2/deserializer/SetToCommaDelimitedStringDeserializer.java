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

package cn.herodotus.stirrup.core.foundation.json.jackson2.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Set;

/**
 * <p>Description: Set集合反序列化为逗号分隔字符串 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/22 13:55
 */
public class SetToCommaDelimitedStringDeserializer extends StdDeserializer<String> {

    protected SetToCommaDelimitedStringDeserializer() {
        super(String.class);
    }

    public JavaType getValueType() {
        return TypeFactory.defaultInstance().constructType(Set.class);
    }

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        Set<String> collection = jsonParser.readValueAs(new TypeReference<Set<String>>() {
        });

        if (CollectionUtils.isNotEmpty(collection)) {
            return StringUtils.collectionToCommaDelimitedString(collection);
        }

        return null;
    }
}
