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

package cn.herodotus.engine.message.mqtt.properties;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * <p>Description: Mqtt 主题 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/10/31 11:20
 */
public class Topic implements Serializable {

    public Topic() {
    }

    public Topic(String name, Integer qos) {
        this.name = name;
        this.qos = qos;
    }

    /**
     * 主题名称
     */
    private String name;
    /**
     * QoS 等级
     */
    private Integer qos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQos() {
        return qos;
    }

    public void setQos(Integer qos) {
        this.qos = qos;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("qos", qos)
                .toString();
    }
}
