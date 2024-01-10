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

package cn.herodotus.engine.oauth2.core.jackson2;

import cn.herodotus.stirrup.kernel.engine.json.jackson2.utils.JsonNodeUtils;
import cn.herodotus.engine.oauth2.core.definition.domain.HerodotusGrantedAuthority;
import cn.herodotus.engine.oauth2.core.definition.domain.HerodotusUser;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.IOException;
import java.util.Set;

/**
 * <p>Description: 自定义 UserDetails 序列化 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/17 21:01
 */
public class HerodotusUserDeserializer extends JsonDeserializer<HerodotusUser> {

    private static final TypeReference<Set<HerodotusGrantedAuthority>> HERODOTUS_GRANTED_AUTHORITY_SET = new TypeReference<Set<HerodotusGrantedAuthority>>() {
    };
    private static final TypeReference<Set<String>> HERODOTUS_ROLE_SET = new TypeReference<Set<String>>() {
    };

    /**
     * This method will create {@link User} object. It will ensure successful object
     * creation even if password key is null in serialized json, because credentials may
     * be removed from the {@link User} by invoking {@link User#eraseCredentials()}. In
     * that case there won't be any password key in serialized json.
     *
     * @param jp   the JsonParser
     * @param ctxt the DeserializationContext
     * @return the user
     * @throws IOException             if a exception during IO occurs
     * @throws JsonProcessingException if an error during JSON processing occurs
     */
    @Override
    public HerodotusUser deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);
        Set<? extends GrantedAuthority> authorities = mapper.convertValue(jsonNode.get("authorities"), HERODOTUS_GRANTED_AUTHORITY_SET);
        Set<String> roles = mapper.convertValue(jsonNode.get("roles"), HERODOTUS_ROLE_SET);
        JsonNode passwordNode = JsonNodeUtils.readJsonNode(jsonNode, "password");
        String userId = JsonNodeUtils.findStringValue(jsonNode, "userId");
        String username = JsonNodeUtils.findStringValue(jsonNode, "username");
        String password = passwordNode.asText("");
        boolean enabled = JsonNodeUtils.findBooleanValue(jsonNode, "enabled");
        boolean accountNonExpired = JsonNodeUtils.findBooleanValue(jsonNode, "accountNonExpired");
        boolean credentialsNonExpired = JsonNodeUtils.findBooleanValue(jsonNode, "credentialsNonExpired");
        boolean accountNonLocked = JsonNodeUtils.findBooleanValue(jsonNode, "accountNonLocked");
        String employeeId = JsonNodeUtils.findStringValue(jsonNode, "employeeId");
        String avatar = JsonNodeUtils.findStringValue(jsonNode, "avatar");
        HerodotusUser result = new HerodotusUser(userId, username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities, roles, employeeId, avatar);
        if (passwordNode.asText(null) == null) {
            result.eraseCredentials();
        }
        return result;
    }
}
