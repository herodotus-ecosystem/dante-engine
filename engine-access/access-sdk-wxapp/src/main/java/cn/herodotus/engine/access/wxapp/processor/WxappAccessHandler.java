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

package cn.herodotus.engine.access.wxapp.processor;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.herodotus.engine.access.core.definition.AccessHandler;
import cn.herodotus.engine.access.core.definition.AccessResponse;
import cn.herodotus.engine.access.core.definition.AccessUserDetails;
import cn.herodotus.engine.access.core.exception.AccessIdentityVerificationFailedException;
import cn.herodotus.engine.access.core.exception.AccessPreProcessFailedException;
import cn.herodotus.engine.assistant.definition.constants.SymbolConstants;
import cn.herodotus.engine.assistant.definition.domain.oauth2.AccessPrincipal;
import cn.herodotus.engine.assistant.core.enums.AccountType;
import org.apache.commons.lang3.ObjectUtils;

/**
 * <p>Description: 微信小程序接入处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/26 10:56
 */
public class WxappAccessHandler implements AccessHandler {

    private final WxappProcessor wxappProcessor;

    public WxappAccessHandler(WxappProcessor wxappProcessor) {
        this.wxappProcessor = wxappProcessor;
    }

    @Override
    public AccessResponse preProcess(String core, String... params) {
        WxMaJscode2SessionResult wxMaSession = wxappProcessor.login(core, params[0]);
        if (ObjectUtils.isNotEmpty(wxMaSession)) {
            AccessResponse accessResponse = new AccessResponse();
            accessResponse.setSession(wxMaSession);
            return accessResponse;
        }

        throw new AccessPreProcessFailedException("Wxapp login failed");
    }

    @Override
    public AccessUserDetails loadUserDetails(String source, AccessPrincipal accessPrincipal) {
        WxMaUserInfo wxMaUserInfo = wxappProcessor.getUserInfo(accessPrincipal.getAppId(), accessPrincipal.getSessionKey(), accessPrincipal.getEncryptedData(), accessPrincipal.getIv());
        if (ObjectUtils.isNotEmpty(wxMaUserInfo)) {
            return convertWxMaUserInfoToAccessUserDetails(wxMaUserInfo, accessPrincipal);
        }

        throw new AccessIdentityVerificationFailedException("Can not find the userinfo from Wechat!");
    }

    private AccessUserDetails convertWxMaUserInfoToAccessUserDetails(WxMaUserInfo wxMaUserInfo, AccessPrincipal accessPrincipal) {
        AccessUserDetails accessUserDetails = new AccessUserDetails();
        accessUserDetails.setUuid(accessPrincipal.getOpenId());
        accessUserDetails.setUsername(wxMaUserInfo.getNickName());
        accessUserDetails.setNickname(wxMaUserInfo.getNickName());
        accessUserDetails.setAvatar(wxMaUserInfo.getAvatarUrl());
        accessUserDetails.setLocation(wxMaUserInfo.getCountry() + SymbolConstants.FORWARD_SLASH + wxMaUserInfo.getProvince() + SymbolConstants.FORWARD_SLASH + wxMaUserInfo.getCity());
        accessUserDetails.setSource(AccountType.WXAPP.name());
        accessUserDetails.setOpenId(accessPrincipal.getOpenId());
        accessUserDetails.setUnionId(accessPrincipal.getUnionId());
        accessUserDetails.setAppId(wxMaUserInfo.getWatermark().getAppid());
        accessUserDetails.setPhoneNumber(wxMaUserInfo.getWatermark().getAppid());
        return accessUserDetails;
    }
}
