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

package com.tencent.cloud.polaris.loadbalancer;

import com.tencent.cloud.polaris.context.ConditionalOnPolarisEnabled;
import com.tencent.cloud.rpc.enhancement.resttemplate.EnhancedRestTemplateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor;
import org.springframework.cloud.client.loadbalancer.RestTemplateCustomizer;
import org.springframework.cloud.client.loadbalancer.RetryLoadBalancerInterceptor;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.loadbalancer.config.LoadBalancerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/2/11 13:25
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties
@ConditionalOnDiscoveryEnabled
@ConditionalOnPolarisEnabled
@ConditionalOnProperty(value = "spring.cloud.polaris.loadbalancer.enabled", matchIfMissing = true)
@AutoConfigureAfter(LoadBalancerAutoConfiguration.class)
@LoadBalancerClients(defaultConfiguration = PolarisLoadBalancerClientConfiguration.class)
public class PolarisLoadBalancerAutoConfiguration {

    @Bean
    public RestTemplateCustomizer polarisRestTemplateCustomizer(
            @Autowired(required = false) RetryLoadBalancerInterceptor retryLoadBalancerInterceptor,
            @Autowired(required = false) LoadBalancerInterceptor loadBalancerInterceptor) {
        return restTemplate -> {
            List<ClientHttpRequestInterceptor> list = new ArrayList<>(restTemplate.getInterceptors());
            // LoadBalancerInterceptor must invoke before EnhancedRestTemplateInterceptor
            int addIndex = list.size();
            if (CollectionUtils.containsInstance(list, retryLoadBalancerInterceptor) || CollectionUtils.containsInstance(list, loadBalancerInterceptor)) {
                ClientHttpRequestInterceptor enhancedRestTemplateInterceptor = null;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) instanceof EnhancedRestTemplateInterceptor) {
                        enhancedRestTemplateInterceptor = list.get(i);
                        addIndex = i;
                    }
                }
                if (enhancedRestTemplateInterceptor != null) {
                    list.remove(addIndex);
                    list.add(enhancedRestTemplateInterceptor);
                }
            }
            else {
                if (retryLoadBalancerInterceptor != null || loadBalancerInterceptor != null) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i) instanceof EnhancedRestTemplateInterceptor) {
                            addIndex = i;
                        }
                    }
                    list.add(addIndex,
                            retryLoadBalancerInterceptor != null
                                    ? retryLoadBalancerInterceptor
                                    : loadBalancerInterceptor);
                }
            }
            restTemplate.setInterceptors(list);
        };
    }

}