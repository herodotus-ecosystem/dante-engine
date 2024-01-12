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

package cn.herodotus.stirrup.access.wxapp.properties;

import cn.herodotus.stirrup.access.core.constants.AccessConstants;
import cn.herodotus.stirrup.access.wxapp.enums.MiniProgramState;
import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Description: 微信小程序配置属性 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/3/26 17:27
 */
@ConfigurationProperties(prefix = AccessConstants.PROPERTY_ACCESS_WXAPP)
public class WxappProperties implements Serializable {

    /**
     * 是否开启
     */
    private Boolean enabled;
    /**
     * 默认App Id
     */
    private String defaultAppId;

    /**
     * 小程序配置列表
     */
    private List<Config> configs;

    /**
     * 小程序订阅消息配置列表
     */
    private List<Subscribe> subscribes;

    public String getDefaultAppId() {
        return defaultAppId;
    }

    public void setDefaultAppId(String defaultAppId) {
        this.defaultAppId = defaultAppId;
    }

    public List<Config> getConfigs() {
        return configs;
    }

    public void setConfigs(List<Config> configs) {
        this.configs = configs;
    }

    public List<Subscribe> getSubscribes() {
        return subscribes;
    }

    public void setSubscribes(List<Subscribe> subscribes) {
        this.subscribes = subscribes;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public static class Config {
        /**
         * 设置微信小程序的appid
         */
        private String appId;

        /**
         * 设置微信小程序的Secret
         */
        private String secret;

        /**
         * 设置微信小程序消息服务器配置的token
         */
        private String token;

        /**
         * 设置微信小程序消息服务器配置的EncodingAESKey
         */
        private String aesKey;

        /**
         * 消息格式，XML或者JSON
         */
        private String messageDataFormat;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAesKey() {
            return aesKey;
        }

        public void setAesKey(String aesKey) {
            this.aesKey = aesKey;
        }

        public String getMessageDataFormat() {
            return messageDataFormat;
        }

        public void setMessageDataFormat(String messageDataFormat) {
            this.messageDataFormat = messageDataFormat;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("appid", appId)
                    .add("secret", secret)
                    .add("token", token)
                    .add("aesKey", aesKey)
                    .add("messageDataFormat", messageDataFormat)
                    .toString();
        }
    }

    public static class Subscribe {

        /**
         * 订阅消息指定的小程序跳转页面地址
         */
        private String redirectPage;
        /**
         * 订阅消息模版ID
         */
        private String templateId;

        /**
         * 自定义Message区分ID，用于获取不同的SubscribeMessageHandler
         */
        private String subscribeId;

        private MiniProgramState miniProgramState = MiniProgramState.formal;

        public String getRedirectPage() {
            return redirectPage;
        }

        public void setRedirectPage(String redirectPage) {
            this.redirectPage = redirectPage;
        }

        public String getTemplateId() {
            return templateId;
        }

        public void setTemplateId(String templateId) {
            this.templateId = templateId;
        }

        public MiniProgramState getMiniProgramState() {
            return miniProgramState;
        }

        public void setMiniProgramState(MiniProgramState miniProgramState) {
            this.miniProgramState = miniProgramState;
        }

        public String getSubscribeId() {
            return subscribeId;
        }

        public void setSubscribeId(String subscribeId) {
            this.subscribeId = subscribeId;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("redirectPage", redirectPage)
                    .add("templateId", templateId)
                    .add("subscribeId", subscribeId)
                    .add("miniProgramState", miniProgramState)
                    .toString();
        }
    }
}
