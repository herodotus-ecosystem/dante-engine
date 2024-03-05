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

package cn.herodotus.engine.rest.autoconfigure;

import cn.herodotus.engine.rest.condition.annotation.ConditionalOnUseHttpClient5RestClient;
import cn.herodotus.engine.rest.condition.annotation.ConditionalOnUseOkHttp3RestClient;
import cn.herodotus.engine.rest.condition.annotation.ConditionalOnUseSimpleRestClient;
import feign.hc5.ApacheHttp5Client;
import jakarta.annotation.PostConstruct;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.*;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * <p>Description: Rest Template Configuration </p>
 * <p>
 * 准备去除Okhttp3支持
 * <a herf="https://github.com/spring-projects/spring-framework/issues/30919">去除 OkHttp3 支持。</a>
 * <p>
 * {@link ClientHttpRequestFactory} 具体用途参见： {@link org.springframework.boot.web.client.ClientHttpRequestFactories}
 *
 * @author : gengwei.zheng
 * @date : 2020/5/29 17:32
 */
@AutoConfiguration(after = {FeignAutoConfiguration.class})
public class RestTemplateAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RestTemplateAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Module [Rest Template] Auto Configure.");
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

    /**
     * 使用 @LoadBalanced 注解表示使用 loadbalancer 实现客户端负载均衡
     *
     * @return RestTemplate
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {

        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

        /**
         * 默认的 RestTemplate 有个机制是请求状态码非200 就抛出异常，会中断接下来的操作。
         * 如果不想中断对结果数据得解析，可以通过覆盖默认的 ResponseErrorHandler ，
         * 对hasError修改下，让他一直返回true，即是不检查状态码及抛异常了
         */
        ResponseErrorHandler responseErrorHandler = new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return true;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {

            }
        };

        restTemplate.setErrorHandler(responseErrorHandler);

        log.trace("[Herodotus] |- Bean [Rest Template] Auto Configure.");
        return restTemplate;
    }
}
