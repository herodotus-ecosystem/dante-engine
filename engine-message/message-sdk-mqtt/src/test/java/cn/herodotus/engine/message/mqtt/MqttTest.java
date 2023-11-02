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

package cn.herodotus.engine.message.mqtt;

import cn.herodotus.engine.message.mqtt.configuration.MessageMqttConfiguration;
import cn.herodotus.engine.message.mqtt.properties.MqttProperties;
import cn.herodotus.engine.message.mqtt.messaging.MqttMessagingTemplate;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/10/31 12:13
 */
@ExtendWith(MockitoExtension.class)
public class MqttTest {

    @InjectMocks
    private MqttMessagingTemplate mqttMessagingTemplate;



    @BeforeEach
    void setup() {
        MessageMqttConfiguration configuration = new MessageMqttConfiguration();
        MqttProperties properties = new MqttProperties();
        properties.setClientId("IotApp-V5MyuncRk");
        properties.setUsername("IotApp-V5MyuncRk");
        properties.setPassword("GNxU20VYTZ");
        properties.setServerUrls(ImmutableList.of("tcp://192.168.101.10:18083"));
        configuration.clientManager(properties);
    }

    @Test
    @DisplayName("Exception Test: send Mqtt Message Error")
    void givenNullUserEmail_whenSaveUser_thenThrowsEmailMustNotNullEx() {
        // when
        mqttMessagingTemplate.publish("troilabox/234", "troilabox/123", "响应报文", 0, "请求响应");
    }
}
