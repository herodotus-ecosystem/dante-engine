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
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.io.IOException;
import java.time.Instant;
import java.util.Set;

/**
 * <p>Description: RegisteredClientDeserializer </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/10/24 15:11
 */
public class RegisteredClientDeserializer extends JsonDeserializer<RegisteredClient> {

    private static final TypeReference<Set<ClientAuthenticationMethod>> CLIENT_AUTHENTICATION_METHOD_SET = new TypeReference<Set<ClientAuthenticationMethod>>() {
    };
    private static final TypeReference<Set<AuthorizationGrantType>> AUTHORIZATION_GRANT_TYPE_SET = new TypeReference<Set<AuthorizationGrantType>>() {
    };

    @Override
    public RegisteredClient deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {

        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode root = mapper.readTree(jsonParser);
        return deserialize(jsonParser, mapper, root);
    }

    private RegisteredClient deserialize(JsonParser parser, ObjectMapper mapper, JsonNode root) throws IOException {

        String id = JsonNodeUtils.findStringValue(root, "id");
        String clientId = JsonNodeUtils.findStringValue(root, "clientId");
        Instant clientIdIssuedAt = JsonNodeUtils.findValue(root, "clientIdIssuedAt", JsonNodeUtils.INSTANT, mapper);
        String clientSecret = JsonNodeUtils.findStringValue(root, "clientSecret");
        Instant clientSecretExpiresAt = JsonNodeUtils.findValue(root, "clientSecretExpiresAt", JsonNodeUtils.INSTANT, mapper);
        String clientName = JsonNodeUtils.findStringValue(root, "clientName");

        Set<ClientAuthenticationMethod> clientAuthenticationMethods = JsonNodeUtils.findValue(root, "clientAuthenticationMethods", CLIENT_AUTHENTICATION_METHOD_SET, mapper);
        Set<AuthorizationGrantType> authorizationGrantTypes = JsonNodeUtils.findValue(root, "authorizationGrantTypes", AUTHORIZATION_GRANT_TYPE_SET, mapper);
        Set<String> redirectUris = JsonNodeUtils.findValue(root, "redirectUris", JsonNodeUtils.STRING_SET, mapper);
        Set<String> postLogoutRedirectUris = JsonNodeUtils.findValue(root, "postLogoutRedirectUris", JsonNodeUtils.STRING_SET, mapper);
        Set<String> scopes = JsonNodeUtils.findValue(root, "scopes", JsonNodeUtils.STRING_SET, mapper);
        ClientSettings clientSettings = JsonNodeUtils.findValue(root, "clientSettings", new TypeReference<>() {
        }, mapper);
        TokenSettings tokenSettings = JsonNodeUtils.findValue(root, "tokenSettings", new TypeReference<>() {
        }, mapper);

        return RegisteredClient.withId(id)
                .clientId(clientId)
                .clientIdIssuedAt(clientIdIssuedAt)
                .clientSecret(clientSecret)
                .clientSecretExpiresAt(clientSecretExpiresAt)
                .clientName(clientName)
                .clientAuthenticationMethods(methods -> methods.addAll(clientAuthenticationMethods))
                .authorizationGrantTypes(types -> types.addAll(authorizationGrantTypes))
                .redirectUris(uris -> uris.addAll(redirectUris))
                .postLogoutRedirectUris(uris -> uris.addAll(postLogoutRedirectUris))
                .scopes(s -> s.addAll(scopes))
                .clientSettings(clientSettings)
                .tokenSettings(tokenSettings)
                .build();
    }
}
