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

package cn.herodotus.engine.assistant.core.context;

import cn.herodotus.engine.assistant.definition.constants.DefaultConstants;
import com.alibaba.ttl.TransmittableThreadLocal;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>Description: 存储/获取当前线程的租户信息 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/9/4 22:34
 */
public class TenantContextHolder {

    private static final ThreadLocal<String> CURRENT_CONTEXT = new TransmittableThreadLocal<>();

    public static String getTenantId() {
        String tenantId = CURRENT_CONTEXT.get();
        if (StringUtils.isBlank(tenantId)) {
            tenantId = DefaultConstants.TENANT_ID;
        }
        return tenantId;
    }

    public static void setTenantId(final String tenantId) {
        if (StringUtils.isBlank(tenantId)) {
            CURRENT_CONTEXT.set(DefaultConstants.TENANT_ID);
        } else {
            CURRENT_CONTEXT.set(tenantId);
        }
    }

    public static void clear() {
        CURRENT_CONTEXT.remove();
    }
}
