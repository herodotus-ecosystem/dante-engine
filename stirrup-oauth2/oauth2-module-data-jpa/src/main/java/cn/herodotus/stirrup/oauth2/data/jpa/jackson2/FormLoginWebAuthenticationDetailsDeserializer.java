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

package cn.herodotus.stirrup.oauth2.data.jpa.jackson2;

import cn.herodotus.stirrup.core.foundation.json.jackson2.utils.JsonNodeUtils;
import cn.herodotus.engine.oauth2.core.definition.details.FormLoginWebAuthenticationDetails;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * <p>Description: FormLoginWebAuthenticationDetailsDeserializer </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/4/14 11:48
 */
public class FormLoginWebAuthenticationDetailsDeserializer extends JsonDeserializer<FormLoginWebAuthenticationDetails> {
    @Override
    public FormLoginWebAuthenticationDetails deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);

        String remoteAddress = JsonNodeUtils.findStringValue(jsonNode, "remoteAddress");
        String sessionId = JsonNodeUtils.findStringValue(jsonNode, "sessionId");
        String parameterName = JsonNodeUtils.findStringValue(jsonNode, "parameterName");
        String category = JsonNodeUtils.findStringValue(jsonNode, "category");
        String code = JsonNodeUtils.findStringValue(jsonNode, "code");
        String identity = JsonNodeUtils.findStringValue(jsonNode, "identity");
        boolean closed = JsonNodeUtils.findBooleanValue(jsonNode, "closed");

        return new FormLoginWebAuthenticationDetails(remoteAddress, sessionId, closed, parameterName, category, code, identity);
    }
}
