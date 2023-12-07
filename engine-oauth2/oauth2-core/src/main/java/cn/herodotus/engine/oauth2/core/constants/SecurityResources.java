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

package cn.herodotus.engine.oauth2.core.constants;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * <p>Description: Web配置常量 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/3 23:03
 */
public class SecurityResources {

    public static final List<String> DEFAULT_IGNORED_STATIC_RESOURCES = Lists.newArrayList(
            "/error/**",
            "/plugins/**",
            "/herodotus/**",
            "/static/**",
            "/webjars/**",
            "/assets/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/openapi.json",
            "/favicon.ico");
    public static final List<String> DEFAULT_PERMIT_ALL_RESOURCES = Lists.newArrayList("/open/**", "/stomp/ws", "/oauth2/sign-out", "/login*");

    public static final List<String> DEFAULT_HAS_AUTHENTICATED_RESOURCES = Lists.newArrayList("/engine-rest/**");
}
