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

package cn.herodotus.engine.oauth2.data.jpa.jackson2;

import cn.herodotus.engine.assistant.core.json.jackson2.utils.JsonNodeUtils;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.io.IOException;
import java.util.Map;

/**
 * <p>Description: TokenSettingsDeserializer </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/10/24 23:29
 */
public class TokenSettingsDeserializer extends JsonDeserializer<TokenSettings> {

    @Override
    public TokenSettings deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode jsonNode = mapper.readTree(jsonParser);

        Map<String, Object> settings = JsonNodeUtils.findValue(jsonNode, "settings", JsonNodeUtils.STRING_OBJECT_MAP, mapper);

        return TokenSettings.withSettings(settings).build();
    }
}
