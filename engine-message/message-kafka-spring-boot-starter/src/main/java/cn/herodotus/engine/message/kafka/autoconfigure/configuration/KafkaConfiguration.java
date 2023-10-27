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

package cn.herodotus.engine.message.kafka.autoconfigure.configuration;

import cn.herodotus.engine.message.kafka.autoconfigure.annotation.ConditionalOnKafkaEnabled;
import cn.herodotus.engine.message.kafka.autoconfigure.properties.KafkaProperties;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

/**
 * <p>Description: Kafka 配置 </p>
 * <p>
 * Spring Cloud Bus 默认配置参数 {@link org.springframework.cloud.bus.BusEnvironmentPostProcessor}
 *
 * @author : gengwei.zheng
 * @date : 2021/10/23 17:34
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnKafkaEnabled
@EnableConfigurationProperties({
        KafkaProperties.class
})
public class KafkaConfiguration {

    private static final Logger log = LoggerFactory.getLogger(KafkaConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Event Message Kafka] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean(ConcurrentKafkaListenerContainerFactory.class)
    public ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory(KafkaProperties kafkaProperties, ConsumerFactory<String, String> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory);
        concurrentKafkaListenerContainerFactory.setAutoStartup(kafkaProperties.getEnabled());
        log.trace("[Herodotus] |- Bean [Concurrent Kafka Listener ContainerFactory] Auto Configure.");
        return concurrentKafkaListenerContainerFactory;
    }
}
