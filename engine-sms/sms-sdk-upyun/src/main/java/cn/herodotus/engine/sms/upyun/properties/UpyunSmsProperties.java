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
package cn.herodotus.engine.sms.upyun.properties;

import cn.herodotus.engine.sms.core.constants.SmsConstants;
import cn.herodotus.engine.sms.core.definition.AbstractSmsProperties;
import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: 又拍云短信配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/26 14:18
 */
@ConfigurationProperties(prefix = SmsConstants.PROPERTY_PREFIX_UPYUN)
public class UpyunSmsProperties extends AbstractSmsProperties {

    /**
     * token
     */
    private String token;

    private String apiUrl = "https://sms-api.upyun.com/api/messages";

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("token", token)
                .add("apiUrl", apiUrl)
                .toString();
    }
}
