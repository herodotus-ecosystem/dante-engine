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

package cn.herodotus.engine.oauth2.authentication.configurer;

import cn.herodotus.engine.oauth2.authentication.properties.OAuth2AuthenticationProperties;
import cn.herodotus.engine.oauth2.core.definition.details.FormLoginWebAuthenticationDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationDetailsSource;

/**
 * <p>Description: 表单登录 Details 定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/4/12 10:41
 */
public class OAuth2FormLoginWebAuthenticationDetailSource implements AuthenticationDetailsSource<HttpServletRequest, FormLoginWebAuthenticationDetails> {

    private final OAuth2AuthenticationProperties authenticationProperties;

    public OAuth2FormLoginWebAuthenticationDetailSource(OAuth2AuthenticationProperties authenticationProperties) {
        this.authenticationProperties = authenticationProperties;
    }

    @Override
    public FormLoginWebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new FormLoginWebAuthenticationDetails(context, authenticationProperties.getFormLogin().getCloseCaptcha(), authenticationProperties.getFormLogin().getCaptchaParameter(), authenticationProperties.getFormLogin().getCategory());
    }
}
