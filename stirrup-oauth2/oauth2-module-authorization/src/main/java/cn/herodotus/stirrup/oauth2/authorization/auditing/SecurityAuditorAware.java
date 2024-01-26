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

package cn.herodotus.stirrup.oauth2.authorization.auditing;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;

import java.util.Optional;

/**
 * <p>Description: 基于 Security 的数据库审计用户信息获取 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/4/7 15:56
 */
public class SecurityAuditorAware implements AuditorAware<String> {

    private static final Logger log = LoggerFactory.getLogger(SecurityAuditorAware.class);

    @Override
    public Optional<String> getCurrentAuditor() {

        SecurityContext context = SecurityContextHolder.getContext();

        if (ObjectUtils.isNotEmpty(context)) {
            Authentication authentication = context.getAuthentication();
            if (ObjectUtils.isNotEmpty(authentication)) {
                if (authentication.isAuthenticated()) {
                    if (authentication instanceof BearerTokenAuthentication bearerTokenAuthentication) {
                        Object object = bearerTokenAuthentication.getPrincipal();
                        if (object instanceof OAuth2IntrospectionAuthenticatedPrincipal principal) {
                            String username = principal.getName();
                            log.trace("[Herodotus] |- Current auditor is : [{}]", username);
                            return Optional.of(username);
                        }
                    }
                }
            }
        }

        return Optional.empty();
    }
}
