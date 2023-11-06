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

package cn.herodotus.engine.access.justauth.properties;

import cn.herodotus.engine.access.core.constants.AccessConstants;
import me.zhyd.oauth.config.AuthConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.Map;

/**
 * <p>Description: 用于支持JustAuth第三方登录的配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/16 10:24
 */
@ConfigurationProperties(prefix = AccessConstants.PROPERTY_ACCESS_JUSTAUTH)
public class JustAuthProperties {

    /**
     * 是否开启
     */
    private Boolean enabled;
    /**
     * State 缓存时长，默认5分钟
     */
    private Duration timeout = Duration.ofMinutes(5);
    /**
     * 第三方系统登录配置信息
     */
    private Map<String, AuthConfig> configs;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public Map<String, AuthConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<String, AuthConfig> configs) {
        this.configs = configs;
    }
}
