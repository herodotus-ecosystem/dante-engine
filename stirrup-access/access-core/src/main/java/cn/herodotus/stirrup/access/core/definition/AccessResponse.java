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

package cn.herodotus.stirrup.access.core.definition;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;

/**
 * <p>Description: 外部接入预操作统一返回实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/25 16:35
 */
public class AccessResponse {

    /**
     * JustAuth 认证URL
     */
    private String authorizeUrl;
    /**
     * 手机短信验证码是否发送成功
     */
    private Boolean success;
    /**
     * 微信小程序返回Session信息
     */
    private WxMaJscode2SessionResult session;

    public String getAuthorizeUrl() {
        return authorizeUrl;
    }

    public void setAuthorizeUrl(String authorizeUrl) {
        this.authorizeUrl = authorizeUrl;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public WxMaJscode2SessionResult getSession() {
        return session;
    }

    public void setSession(WxMaJscode2SessionResult session) {
        this.session = session;
    }
}
