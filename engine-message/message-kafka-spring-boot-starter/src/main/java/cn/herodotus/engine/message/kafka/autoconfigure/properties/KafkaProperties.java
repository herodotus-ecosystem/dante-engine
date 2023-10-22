/*
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君) (herodotus@aliyun.com & www.herodotus.cn)
 *
 * Dante Engine licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.gnu.org/licenses/lgpl.html>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.herodotus.engine.message.kafka.autoconfigure.properties;

import cn.herodotus.engine.message.core.constants.MessageConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: 消息队列配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/7 23:55
 */
@ConfigurationProperties(prefix = MessageConstants.PROPERTY_PREFIX_KAFKA)
public class KafkaProperties {

    /**
     * Kakfa监听是否自动启动
     */
    private Boolean enabled = false;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
