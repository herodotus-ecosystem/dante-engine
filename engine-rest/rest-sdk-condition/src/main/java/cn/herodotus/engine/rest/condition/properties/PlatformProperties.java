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

import cn.herodotus.engine.assistant.core.enums.Architecture;
import cn.herodotus.engine.assistant.core.enums.Protocol;
import cn.herodotus.engine.assistant.core.enums.Target;
import cn.herodotus.engine.rest.condition.constants.RestConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: 平台服务相关配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/17 15:22
 */
@ConfigurationProperties(prefix = RestConstants.PROPERTY_PREFIX_PLATFORM)
public class PlatformProperties {

    /**
     * 平台架构类型，默认：DISTRIBUTED（分布式架构）
     */
    private Architecture architecture = Architecture.DISTRIBUTED;
    /**
     * 数据访问策略，默认：远程
     */
    private Target dataAccessStrategy = Target.REMOTE;

    /**
     * 接口地址默认采用的Http协议类型
     */
    private Protocol protocol = Protocol.HTTP;

    public Architecture getArchitecture() {
        return architecture;
    }

    public void setArchitecture(Architecture architecture) {
        this.architecture = architecture;
    }

    public Target getDataAccessStrategy() {
        return dataAccessStrategy;
    }

    public void setDataAccessStrategy(Target dataAccessStrategy) {
        this.dataAccessStrategy = dataAccessStrategy;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }
}
