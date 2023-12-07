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

package cn.herodotus.engine.oauth2.authorization.customizer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.session.FindByIndexNameSessionRepository;

/**
 * <p>Description: 自定义扩展 SessionAuthenticationStrategy </p>
 * <p>
 * 扩展 SessionAuthenticationStrategy 以支持 {@link FindByIndexNameSessionRepository#PRINCIPAL_NAME_INDEX_NAME} 的设置。
 *
 * @author : gengwei.zheng
 * @date : 2023/9/5 14:01
 */
public class HerodotusSessionAuthenticationStrategy extends RegisterSessionAuthenticationStrategy {

    public HerodotusSessionAuthenticationStrategy(SessionRegistry sessionRegistry) {
        super(sessionRegistry);
    }

    @Override
    public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        if (ObjectUtils.isNotEmpty(authentication) && authentication.isAuthenticated()) {
            if (authentication instanceof BearerTokenAuthentication) {
                request.getSession().setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, authentication.getName());
            }
        }
        super.onAuthentication(authentication, request, response);
    }
}
