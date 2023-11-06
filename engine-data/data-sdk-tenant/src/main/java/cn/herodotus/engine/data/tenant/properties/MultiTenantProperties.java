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

package cn.herodotus.engine.data.tenant.properties;

import cn.herodotus.engine.data.core.constants.DataConstants;
import cn.herodotus.engine.data.core.enums.MultiTenantApproach;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: 自定义 JPA 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/9/8 18:22
 */
@ConfigurationProperties(prefix = DataConstants.PROPERTY_PREFIX_MULTI_TENANT)
public class MultiTenantProperties {

    /**
     * 多租户数据源扫描包
     */
    private String[] packageToScan = new String[]{"cn.herodotus.engine", "cn.herodotus.professional"};

    /**
     * 多租户模式，默认：discriminator
     */
    private MultiTenantApproach approach = MultiTenantApproach.DISCRIMINATOR;

    public String[] getPackageToScan() {
        return packageToScan;
    }

    public void setPackageToScan(String[] packageToScan) {
        this.packageToScan = packageToScan;
    }

    public MultiTenantApproach getApproach() {
        return approach;
    }

    public void setApproach(MultiTenantApproach approach) {
        this.approach = approach;
    }
}
