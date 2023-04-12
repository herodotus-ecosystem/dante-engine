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

import cn.herodotus.engine.rest.client.annotation.ConditionalOnFeignUseOkHttp;
import feign.Client;
import feign.okhttp.OkHttpClient;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import okhttp3.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.loadbalancer.FeignLoadBalancerAutoConfiguration;
import org.springframework.cloud.openfeign.support.FeignHttpClientProperties;
import org.springframework.context.annotation.Bean;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * <p>Description: OkHttp 自动配置 </p>
 * <p>
 * 1. 默认让 Feign 使用 OkHttp 作为 HttpClient。所以直接使用 Feign 的配置来对 OkHttp 进行配置。
 * 2. 如果存在 `feign.okhttp.enabled` 配置， 同时其值为 `true`，就会自动配置 OkHttp。
 * 3. 在此处配置 OkHttp，也是为了共用 OkHttp 的配置，让其可以同时支持 RestTemplate
 * <p>
 * {@link org.springframework.cloud.openfeign.FeignAutoConfiguration}
 *
 * @author : gengwei.zheng
 * @date : 2022/5/29 17:54
 * @see <a href='http://leejoker.github.io/post/feign%E4%BD%BF%E7%94%A8okhttp3%E7%9A%84%E6%AD%A3%E7%A1%AE%E5%A7%BF%E5%8A%BF/'> 参考资料</a>
 */
@AutoConfiguration(before = {FeignLoadBalancerAutoConfiguration.class})
@ConditionalOnFeignUseOkHttp
public class OkHttpFeignConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OkHttpFeignConfiguration.class);

    private okhttp3.OkHttpClient okHttpClient;

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Web OkHttp] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public okhttp3.OkHttpClient.Builder okHttpClientBuilder() {
        return new okhttp3.OkHttpClient.Builder();
    }

    @Bean
    @ConditionalOnMissingBean(ConnectionPool.class)
    public ConnectionPool httpClientConnectionPool(FeignHttpClientProperties httpClientProperties) {
        int maxTotalConnections = httpClientProperties.getMaxConnections();
        long timeToLive = httpClientProperties.getTimeToLive();
        TimeUnit ttlUnit = httpClientProperties.getTimeToLiveUnit();
        return new ConnectionPool(maxTotalConnections, timeToLive, ttlUnit);
    }

    @Bean
    public okhttp3.OkHttpClient okHttpClient(okhttp3.OkHttpClient.Builder builder, ConnectionPool connectionPool, FeignHttpClientProperties httpClientProperties) {
        boolean followRedirects = httpClientProperties.isFollowRedirects();
        int connectTimeout = httpClientProperties.getConnectionTimeout();
        boolean disableSslValidation = httpClientProperties.isDisableSslValidation();
        Duration readTimeout = httpClientProperties.getOkHttp().getReadTimeout();
        if (disableSslValidation) {
            disableSsl(builder);
        }
        this.okHttpClient = builder.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .followRedirects(followRedirects).readTimeout(readTimeout).connectionPool(connectionPool).build();
        return this.okHttpClient;
    }

    private void disableSsl(okhttp3.OkHttpClient.Builder builder) {
        try {
            X509TrustManager disabledTrustManager = new OkHttpFeignConfiguration.DisableValidationTrustManager();
            TrustManager[] trustManagers = new TrustManager[1];
            trustManagers[0] = disabledTrustManager;
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustManagers, new java.security.SecureRandom());
            SSLSocketFactory disabledSSLSocketFactory = sslContext.getSocketFactory();
            builder.sslSocketFactory(disabledSSLSocketFactory, disabledTrustManager);
            builder.hostnameVerifier(new OkHttpFeignConfiguration.TrustAllHostnames());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            log.warn("Error setting SSLSocketFactory in OKHttpClient", e);
        }
    }

    @PreDestroy
    public void destroy() {
        if (this.okHttpClient != null) {
            this.okHttpClient.dispatcher().executorService().shutdown();
            this.okHttpClient.connectionPool().evictAll();
        }
    }

    @Bean
    @ConditionalOnMissingBean(Client.class)
    public Client feignClient(okhttp3.OkHttpClient client) {
        return new OkHttpClient(client);
    }

    /**
     * A {@link X509TrustManager} that does not validate SSL certificates.
     */
    class DisableValidationTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

    }

    class TrustAllHostnames implements HostnameVerifier {

        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }

    }
}
