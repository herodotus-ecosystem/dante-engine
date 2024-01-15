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

package cn.herodotus.engine.access.justauth.processor;

import cn.herodotus.engine.access.core.definition.AccessHandler;
import cn.herodotus.engine.access.core.definition.AccessResponse;
import cn.herodotus.engine.access.core.definition.AccessUserDetails;
import cn.herodotus.engine.access.core.exception.AccessIdentityVerificationFailedException;
import cn.herodotus.engine.assistant.definition.domain.oauth2.AccessPrincipal;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.apache.commons.lang3.ObjectUtils;

/**
 * <p>Description: JustAuth 接入处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/25 17:45
 */
public class JustAuthAccessHandler implements AccessHandler {

    private final JustAuthProcessor justAuthProcessor;

    public JustAuthAccessHandler(JustAuthProcessor justAuthProcessor) {
        this.justAuthProcessor = justAuthProcessor;
    }

    @Override
    public AccessResponse preProcess(String core, String... params) {
        String url = justAuthProcessor.getAuthorizeUrl(core);

        AccessResponse accessResponse = new AccessResponse();
        accessResponse.setAuthorizeUrl(url);
        return accessResponse;
    }

    @Override
    public AccessUserDetails loadUserDetails(String source, AccessPrincipal accessPrincipal) {
        AuthRequest authRequest = justAuthProcessor.getAuthRequest(source);

        AuthCallback authCallback = AuthCallback.builder()
                .code(accessPrincipal.getCode())
                .auth_code(accessPrincipal.getAuth_code())
                .state(accessPrincipal.getState())
                .authorization_code(accessPrincipal.getAuthorization_code())
                .oauth_token(accessPrincipal.getOauth_token())
                .oauth_verifier(accessPrincipal.getOauth_verifier())
                .build();

        AuthResponse<AuthUser> response = authRequest.login(authCallback);
        if (response.ok()) {
            AuthUser authUser = response.getData();
            return convertAuthUserToAccessUserDetails(authUser);
        }

        throw new AccessIdentityVerificationFailedException(response.getMsg());
    }

    private AccessUserDetails convertAuthUserToAccessUserDetails(AuthUser authUser) {
        AccessUserDetails sysSocialUser = new AccessUserDetails();
        sysSocialUser.setUuid(authUser.getUuid());
        sysSocialUser.setUsername(authUser.getUsername());
        sysSocialUser.setNickname(authUser.getNickname());
        sysSocialUser.setAvatar(authUser.getAvatar());
        sysSocialUser.setBlog(authUser.getBlog());
        sysSocialUser.setCompany(authUser.getCompany());
        sysSocialUser.setLocation(authUser.getLocation());
        sysSocialUser.setEmail(authUser.getEmail());
        sysSocialUser.setRemark(authUser.getRemark());
        sysSocialUser.setGender(authUser.getGender());
        sysSocialUser.setSource(authUser.getSource());
        AuthToken authToken = authUser.getToken();
        if (ObjectUtils.isNotEmpty(authToken)) {
            setAccessUserInfo(sysSocialUser, authToken.getAccessToken(), authToken.getExpireIn(), authToken.getRefreshToken(), authToken.getRefreshTokenExpireIn(), authToken.getScope(), authToken.getTokenType(), authToken.getUid(), authToken.getOpenId(), authToken.getAccessCode(), authToken.getUnionId());
        }

        return sysSocialUser;
    }

    private void setAccessUserInfo(AccessUserDetails accessUserDetails, String accessToken, Integer expireIn, String refreshToken, Integer refreshTokenExpireIn, String scope, String tokenType, String uid, String openId, String accessCode, String unionId) {
        accessUserDetails.setAccessToken(accessToken);
        accessUserDetails.setExpireIn(expireIn);
        accessUserDetails.setRefreshToken(refreshToken);
        accessUserDetails.setRefreshTokenExpireIn(refreshTokenExpireIn);
        accessUserDetails.setScope(scope);
        accessUserDetails.setTokenType(tokenType);
        accessUserDetails.setUid(uid);
        accessUserDetails.setOpenId(openId);
        accessUserDetails.setAccessCode(accessCode);
        accessUserDetails.setUnionId(unionId);
    }
}
