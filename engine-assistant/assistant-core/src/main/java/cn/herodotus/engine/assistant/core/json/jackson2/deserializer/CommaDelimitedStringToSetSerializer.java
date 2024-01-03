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

import cn.herodotus.engine.assistant.definition.constants.SymbolConstants;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: 逗号分隔字符串序列化为集合 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/22 14:37
 */
public class CommaDelimitedStringToSetSerializer extends StdSerializer<String> {
    public CommaDelimitedStringToSetSerializer() {
        super(String.class);
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        Set<String> collection = new HashSet<>();
        if (StringUtils.hasText(value)) {
            if (org.apache.commons.lang3.StringUtils.contains(value, SymbolConstants.COMMA)) {
                collection = StringUtils.commaDelimitedListToSet(value);
            } else {
                collection.add(value);
            }
        }

        int len = collection.size();

        gen.writeStartArray(collection, len);
        serializeContents(collection, gen, provider);
        gen.writeEndArray();
    }

    private void serializeContents(Set<String> value, JsonGenerator g, SerializerProvider provider) throws IOException {
        int i = 0;

        try {
            for (String str : value) {
                if (str == null) {
                    provider.defaultSerializeNull(g);
                } else {
                    g.writeString(str);
                }
                ++i;
            }
        } catch (Exception e) {
            wrapAndThrow(provider, e, value, i);
        }
    }
}
