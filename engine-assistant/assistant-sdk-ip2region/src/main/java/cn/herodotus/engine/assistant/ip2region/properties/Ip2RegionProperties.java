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

package cn.herodotus.engine.assistant.ip2region.properties;

import cn.herodotus.engine.assistant.definition.constants.BaseConstants;
import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: Ip2Region 配置参数 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/10/24 11:39
 */
@ConfigurationProperties(prefix = BaseConstants.PROPERTY_PREFIX_IP2REGION)
public class Ip2RegionProperties {

    /**
     * Ip V4 地址数据库位置，默认值：classpath*:/db/ip2region.db
     */
    private String ipV4Resource = "classpath:/db/ip2region.xdb";
    /**
     * Ip V6 地址数据库位置，默认值：classpath:db/ipv6wry.db
     */
    private String ipV6Resource = "classpath:/db/ipv6wry.db";

    public String getIpV4Resource() {
        return ipV4Resource;
    }

    public void setIpV4Resource(String ipV4Resource) {
        this.ipV4Resource = ipV4Resource;
    }

    public String getIpV6Resource() {
        return ipV6Resource;
    }

    public void setIpV6Resource(String ipV6Resource) {
        this.ipV6Resource = ipV6Resource;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("ipV4Resource", ipV4Resource)
                .add("ipV6Resource", ipV6Resource)
                .toString();
    }
}
