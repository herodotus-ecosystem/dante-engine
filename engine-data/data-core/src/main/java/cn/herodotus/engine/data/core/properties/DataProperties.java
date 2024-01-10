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

package cn.herodotus.engine.data.core.properties;

import cn.herodotus.stirrup.data.kernel.constants.DataConstants;
import cn.herodotus.engine.data.core.enums.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: Data 相关模块配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/10/4 12:02
 */
@ConfigurationProperties(DataConstants.PROPERTY_PREFIX_DATA)
public class DataProperties {

    /**
     * 基础数据源切换。用于某些基础核心应用底层存储切换的配置。默认，JPA
     */
    private DataSource dataSource = DataSource.JPA;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
