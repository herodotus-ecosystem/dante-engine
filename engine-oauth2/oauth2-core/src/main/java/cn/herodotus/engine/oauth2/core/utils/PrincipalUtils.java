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

package cn.herodotus.engine.oauth2.core.utils;

import cn.herodotus.engine.assistant.definition.constants.BaseConstants;
import cn.herodotus.engine.assistant.definition.domain.oauth2.PrincipalDetails;
import cn.herodotus.engine.oauth2.core.definition.domain.HerodotusUser;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;

import java.util.HashSet;
import java.util.List;

/**
 * <p>Description: 身份信息工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/31 12:07
 */
public class PrincipalUtils {

    public static PrincipalDetails toPrincipalDetails(HerodotusUser herodotusUser) {
        PrincipalDetails details = new PrincipalDetails();
        details.setOpenId(herodotusUser.getUserId());
        details.setUsername(herodotusUser.getUsername());
        details.setRoles(herodotusUser.getRoles());
        details.setAvatar(herodotusUser.getAvatar());
        details.setEmployeeId(herodotusUser.getEmployeeId());
        return details;
    }

    public static PrincipalDetails toPrincipalDetails(OAuth2AuthenticatedPrincipal authenticatedPrincipal) {
        PrincipalDetails details = new PrincipalDetails();
        details.setOpenId(authenticatedPrincipal.getAttribute(BaseConstants.OPEN_ID));
        details.setUsername(authenticatedPrincipal.getName());
        List<String> roles = authenticatedPrincipal.getAttribute(BaseConstants.ROLES);
        if (CollectionUtils.isNotEmpty(roles)) {
            details.setRoles(new HashSet<>(roles));
        }
        details.setAvatar(authenticatedPrincipal.getAttribute(BaseConstants.AVATAR));
        details.setEmployeeId(authenticatedPrincipal.getAttribute(BaseConstants.EMPLOYEE_ID));
        return details;
    }

    public static PrincipalDetails toPrincipalDetails(Jwt jwt) {
        PrincipalDetails details = new PrincipalDetails();
        details.setOpenId(jwt.getClaimAsString(BaseConstants.OPEN_ID));
        details.setUsername(jwt.getClaimAsString(JwtClaimNames.SUB));
        details.setRoles(jwt.getClaim(BaseConstants.ROLES));
        details.setAvatar(jwt.getClaimAsString(BaseConstants.AVATAR));
        details.setEmployeeId(jwt.getClaimAsString(BaseConstants.EMPLOYEE_ID));
        return details;
    }
}
