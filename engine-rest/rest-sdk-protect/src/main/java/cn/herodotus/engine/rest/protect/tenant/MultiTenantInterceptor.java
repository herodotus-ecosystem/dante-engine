/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.rest.protect.tenant;

import cn.herodotus.engine.assistant.core.constants.DefaultConstants;
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
