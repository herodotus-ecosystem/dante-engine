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

package cn.herodotus.engine.oauth2.core.definition.service;

import cn.herodotus.engine.assistant.definition.domain.oauth2.AccessPrincipal;
import cn.herodotus.engine.oauth2.core.definition.domain.HerodotusUser;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * <p>Description: 自定义UserDetailsService接口，方便以后扩展 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/1/17 12:49
 */
public interface EnhanceUserDetailsService extends UserDetailsService {

    /**
     * 通过社交集成的唯一id，获取用户信息
     * <p>
     * 如果是短信验证码，openId就是手机号码
     *
     * @param accessPrincipal 社交登录提供的相关信息
     * @param source          社交集成提供商类型
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException 用户不存在
     */
    UserDetails loadUserBySocial(String source, AccessPrincipal accessPrincipal) throws AuthenticationException;

    /**
     * 系统用户名
     *
     * @param username 用户账号
     * @return {@link HerodotusUser}
     * @throws UsernameNotFoundException 用户不存在
     */
    HerodotusUser loadHerodotusUserByUsername(String username) throws UsernameNotFoundException;
}
