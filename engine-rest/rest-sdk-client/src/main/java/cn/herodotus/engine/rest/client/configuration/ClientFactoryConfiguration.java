/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.rest.client.configuration;

import cn.herodotus.engine.rest.client.annotation.ConditionalOnUseHttpClient5RestClient;
import cn.herodotus.engine.rest.client.annotation.ConditionalOnUseOkHttp3RestClient;
import cn.herodotus.engine.rest.client.annotation.ConditionalOnUseSimpleRestClient;
import feign.hc5.ApacheHttp5Client;
import jakarta.annotation.PostConstruct;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

/**
 * <p>Description: Client Factory 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/15 22:43
 */
@AutoConfiguration(after = {FeignAutoConfiguration.class})
public class ClientFactoryConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ClientFactoryConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Rest Client Factory] Auto Configure.");
    }

    @Bean
    @ConditionalOnClass(okhttp3.OkHttpClient.class)
    @ConditionalOnUseOkHttp3RestClient
    @ConditionalOnMissingBean
    public ClientHttpRequestFactory okHttp3ClientHttpRequestFactory(okhttp3.OkHttpClient okHttpClient) {
        OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory(okHttpClient);
        log.trace("[Herodotus] |- Bean [OkHttp3 Client Http Request Factory] Auto Configure.");
        return factory;
    }

    @Bean
    @ConditionalOnClass(ApacheHttp5Client.class)
    @ConditionalOnUseHttpClient5RestClient
    @ConditionalOnMissingBean
    public ClientHttpRequestFactory httpComponentsClientHttpRequestFactory(CloseableHttpClient okHttpClient) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(okHttpClient);
        log.trace("[Herodotus] |- Bean [Http Components Http Request Factory] Auto Configure.");
        return factory;
    }

    @Bean
    @ConditionalOnUseSimpleRestClient
    @ConditionalOnMissingBean
    public ClientHttpRequestFactory SimpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        log.trace("[Herodotus] |- Bean [Simple Client Http Request Factory] Auto Configure.");
        return factory;
    }
}
