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

package cn.herodotus.engine.rest.protect.tenant;

import cn.herodotus.engine.assistant.definition.constants.DefaultConstants;
import cn.herodotus.engine.assistant.core.context.TenantContextHolder;
import cn.herodotus.engine.assistant.core.utils.http.HeaderUtils;
import cn.herodotus.engine.assistant.core.utils.http.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * <p>Description: 多租户拦截器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/9/6 11:16
 */
public class MultiTenantInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(MultiTenantInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String tenantId = HeaderUtils.getHerodotusTenantId(request);
        if (StringUtils.isBlank(tenantId)) {
            tenantId = DefaultConstants.TENANT_ID;
        }
        TenantContextHolder.setTenantId(tenantId);
        log.debug("[Herodotus] |- TENANT ID is : [{}].", tenantId);

        String path = request.getRequestURI();
        String sessionId = SessionUtils.getSessionId(request);
        String herodotusSessionId = HeaderUtils.getHerodotusSessionId(request);

        log.debug("[Herodotus] |- SESSION ID for [{}] is : [{}].", path, sessionId);
        log.debug("[Herodotus] |- SESSION ID of HERODOTUS for [{}] is : [{}].", path, herodotusSessionId);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String path = request.getRequestURI();
        TenantContextHolder.clear();
        log.debug("[Herodotus] |- Tenant Interceptor CLEAR tenantId for request [{}].", path);
    }
}
