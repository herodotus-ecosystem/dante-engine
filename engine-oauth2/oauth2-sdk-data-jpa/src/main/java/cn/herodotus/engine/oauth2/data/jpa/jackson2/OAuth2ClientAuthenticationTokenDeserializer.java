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

import cn.herodotus.stirrup.core.foundation.json.jackson2.utils.JsonNodeUtils;
import cn.herodotus.engine.oauth2.core.definition.domain.HerodotusGrantedAuthority;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.dromara.hutool.core.reflect.FieldUtil;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.io.IOException;
import java.util.Set;

/**
 * <p>Description: OAuth2ClientAuthenticationTokenDeserializer </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/10/24 14:43
 */
public class OAuth2ClientAuthenticationTokenDeserializer extends JsonDeserializer<OAuth2ClientAuthenticationToken> {

    private static final TypeReference<Set<HerodotusGrantedAuthority>> HERODOTUS_GRANTED_AUTHORITY_SET = new TypeReference<Set<HerodotusGrantedAuthority>>() {
    };

    @Override
    public OAuth2ClientAuthenticationToken deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JacksonException {

        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode jsonNode = mapper.readTree(jsonParser);
        return deserialize(jsonParser, mapper, jsonNode);
    }

    private OAuth2ClientAuthenticationToken deserialize(JsonParser parser, ObjectMapper mapper, JsonNode root) throws IOException {
        Set<HerodotusGrantedAuthority> authorities = JsonNodeUtils.findValue(root, "authorities", HERODOTUS_GRANTED_AUTHORITY_SET, mapper);
        RegisteredClient registeredClient = JsonNodeUtils.findValue(root, "registeredClient", new TypeReference<RegisteredClient>() {
        }, mapper);
        String credentials = JsonNodeUtils.findStringValue(root, "credentials");
        ClientAuthenticationMethod clientAuthenticationMethod = JsonNodeUtils.findValue(root, "clientAuthenticationMethod", new TypeReference<ClientAuthenticationMethod>() {
        }, mapper);

        OAuth2ClientAuthenticationToken clientAuthenticationToken = new OAuth2ClientAuthenticationToken(registeredClient, clientAuthenticationMethod, credentials);
        if (CollectionUtils.isNotEmpty(authorities)) {
            FieldUtil.setFieldValue(clientAuthenticationToken, "authorities", authorities);
        }
        return clientAuthenticationToken;
    }
}
