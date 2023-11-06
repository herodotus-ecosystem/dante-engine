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

package cn.herodotus.engine.rest.condition.properties;

import cn.herodotus.engine.rest.condition.constants.RestConstants;
import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: Swagger 自定义配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/9 18:45
 */
@ConfigurationProperties(prefix = RestConstants.PROPERTY_PREFIX_SWAGGER)
public class SwaggerProperties {

    /**
     * 是否开启Swagger
     */
    private Boolean enabled;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("enabled", enabled)
                .toString();
    }
}
