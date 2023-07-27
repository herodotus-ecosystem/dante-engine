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

package cn.herodotus.engine.facility.alibaba.sentinel.configuration;

import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.assistant.core.json.jackson2.utils.Jackson2Utils;
import cn.herodotus.engine.facility.alibaba.sentinel.enhance.HerodotusSentinelFeign;
import com.alibaba.cloud.sentinel.feign.SentinelFeignAutoConfiguration;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.adapter.spring.webflux.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import feign.Feign;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * <p>Description: 基础设施 Sentinel 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/5 17:57
 */
@AutoConfiguration(before = SentinelFeignAutoConfiguration.class)
@ConditionalOnClass({SphU.class, Feign.class})
public class FacilityAlibabaSentinelConfiguration {

    private static final Logger log = LoggerFactory.getLogger(FacilityAlibabaSentinelConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Facility Alibaba Sentinel] Auto Configure.");
    }

    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "feign.sentinel.enabled")
    public Feign.Builder feignSentinelBuilder() {
        return HerodotusSentinelFeign.builder();
    }

    /**
     * 限流、熔断统一处理类
     */
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(HttpServletRequest.class)
    public static class WebmvcHandler {
        @Bean
        public BlockExceptionHandler webmvcBlockExceptionHandler() {
            return (request, response, e) -> {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                Result<String> result = Result.failure("Too many request, please retry later.");
                response.getWriter().print(Jackson2Utils.toJson(result));
            };
        }

    }

    /**
     * 限流、熔断统一处理类
     */
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(ServerResponse.class)
    public static class WebfluxHandler {
        @Bean
        public BlockRequestHandler webfluxBlockExceptionHandler() {
            return (exchange, t) ->
                    ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromValue(Result.failure(t.getMessage())));
        }
    }
}
