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

package cn.herodotus.engine.rest.service.configuration;

import cn.herodotus.engine.rest.service.feign.FeignErrorDecoder;
import cn.herodotus.engine.rest.service.feign.FeignInnerContract;
import cn.herodotus.engine.rest.service.feign.FeignRequestInterceptor;
import feign.Contract;
import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.ConversionService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p> Description : 自定义通用的Feign Fallback处理工厂(基于Sentinel) </p>
 * <p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/1 18:35
 * @see <a href="https://blog.csdn.net/ttzommed/article/details/90669320">参考文档</a>
 */
@Configuration(proxyBeanMethods = false)
public class FeignConfiguration {

    private static final Logger log = LoggerFactory.getLogger(FeignConfiguration.class);

    @Autowired(required = false)
    private List<AnnotatedParameterProcessor> parameterProcessors = new ArrayList<>();

    @Autowired(required = false)
    private FeignClientProperties feignClientProperties;

    @Bean
    public Contract feignContract(ConversionService feignConversionService) {
        boolean decodeSlash = feignClientProperties == null || feignClientProperties.isDecodeSlash();
        log.info("[Herodotus] |- Bean [Herodotus FeignInnerContract] Auto Configure.");
        return new FeignInnerContract(parameterProcessors, feignConversionService, decodeSlash);
    }

    @Bean
    @ConditionalOnMissingBean(FeignRequestInterceptor.class)
    public RequestInterceptor feignRequestInterceptor() {
        FeignRequestInterceptor feignRequestInterceptor = new FeignRequestInterceptor();
        log.trace("[Herodotus] |- Bean [Feign Request Interceptor] Auto Configure.");
        return feignRequestInterceptor;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }

    /**
     * Feign Logger 配置
     * <p>
     * 1. NONE（默认） --- 不记录任何日志
     * 2. BASIC ---	仅记录请求方法，URL，响应状态代码以及执行时间（适合生产环境）
     * 3. HEADERS --- 记录BASIC级别的基础上，记录请求和响应的header
     * 4. FULL --- 记录请求和响应header，body和元数据
     *
     * @return feign 日志级别
     */
    @Bean
    public feign.Logger.Level logger() {
        return feign.Logger.Level.BASIC;
    }

    /**
     * FeignClient超时设置
     * feign超时设置有3种方式：配置文件直接配置FeignClient、自定义Request.Options及配置文件配置Ribbon，优先级从高到低如下。
     * 1、配置文件里对特定FeignClient配置属性： feign.client.config.demo.connectTimeout=1000，feign.client.config.demo.readTimeout=2000 ；
     * 2、自定义对特定FeignClient生效的Request.Options类型的Bean；
     * 3、配置文件里对所有FeienClient属性的配置：feign.client.config.default.connectTimeout=1000，feign.client.config.default.readTimeout=5000
     * 4、对全体FeignClient生效的Request.Options类型的Bean；
     * 5、特定服务的ribbon配置：demo.ribbon.ConnectTimeout=1000，demo.ribbon.ReadTimeout=5000
     * 6、全体服务的ribbon配置：ribbon.ConnectTimeout=1000，ribbon.ReadTimeout=5000
     * 7、Ribbon默认配置：默认连接超时和读取超时都是1000，即1秒
     * <p>
     * 总结一下：
     * 1、FeignClient的直接配置高于Ribbon的配置
     * 2、特定服务的配置高于全体服务的配置
     * 3、配置文件的配置高于自定义Request.Options
     * 4、如果有特定服务的Options和全体服务的配置文件配置，遵循第二条规则，以特定服务的Options为准；
     * 5、如果有特性服务的Ribbon配置和全体服务的FeignClient配置，遵循第一条规则，以FeingClient的配置为准
     * <p>
     * 最佳实践：
     * 1、不要采用Ribbon配置而要直接配置FeignClient，即配置feign.client.xx
     * 2、配置文件配置全体FeignClient的超时设置，同时对特定服务有特殊设置的，也在配置文件里配置
     * <p>
     *
     * @see <a href="https://blog.csdn.net/weixin_36244726/article/details/103953852"></a>
     */
    @Bean
    public Request.Options options() {
        return new Request.Options(10, TimeUnit.SECONDS, 60, TimeUnit.SECONDS, true);
    }

    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default();
    }
}
