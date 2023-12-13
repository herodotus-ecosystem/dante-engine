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

package cn.herodotus.engine.oauth2.management.service;

import cn.herodotus.engine.assistant.core.enums.Database;
import cn.herodotus.engine.assistant.core.enums.ServerDevice;
import cn.herodotus.engine.oauth2.core.enums.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: OAuth2 常量服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/3/17 14:36
 */
@Service
public class OAuth2ConstantService {

    private static final List<Map<String, Object>> APPLICATION_TYPE_ENUM = ApplicationType.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> GRANT_TYPE_ENUM = GrantType.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> SIGNATURE_JWS_ALGORITHM_ENUM = SignatureJwsAlgorithm.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> ALL_JWS_ALGORITHM_ENUM = AllJwsAlgorithm.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> AUTHENTICATION_METHOD_ENUM = AuthenticationMethod.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> PERMISSION_EXPRESSION_ENUM = PermissionExpression.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> DATABASE_ENUM = Database.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> SERVER_DEVICE_ENUM = ServerDevice.getPreprocessedJsonStructure();

    public Map<String, Object> getAllEnums() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("applicationType", APPLICATION_TYPE_ENUM);
        map.put("grantType", GRANT_TYPE_ENUM);
        map.put("signatureJwsAlgorithm", SIGNATURE_JWS_ALGORITHM_ENUM);
        map.put("allJwsAlgorithm", ALL_JWS_ALGORITHM_ENUM);
        map.put("permissionExpression", PERMISSION_EXPRESSION_ENUM);
        map.put("authenticationMethod", AUTHENTICATION_METHOD_ENUM);
        map.put("database", DATABASE_ENUM);
        map.put("serverDevice", SERVER_DEVICE_ENUM);
        return map;
    }
}
