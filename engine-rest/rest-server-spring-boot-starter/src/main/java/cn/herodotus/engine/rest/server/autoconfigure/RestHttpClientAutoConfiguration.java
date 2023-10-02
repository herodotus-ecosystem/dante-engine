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

package cn.herodotus.engine.rest.server.autoconfigure;

import cn.herodotus.engine.assistant.core.annotation.ConditionalOnSwaggerEnabled;
import cn.herodotus.engine.assistant.core.context.ServiceContextHolder;
import cn.herodotus.engine.assistant.core.definition.constants.BaseConstants;
import cn.herodotus.engine.rest.client.definition.OpenApiServerResolver;
import cn.herodotus.engine.rest.server.autoconfigure.properties.SwaggerProperties;
import com.google.common.collect.ImmutableList;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.*;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: 服务信息配置类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/6/13 13:40
 */
@AutoConfiguration
public class RestHttpClientAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RestHttpClientAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Rest HttpClient] Auto Configure.");
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnSwaggerEnabled
    @EnableConfigurationProperties(SwaggerProperties.class)
    @SecuritySchemes({
            @SecurityScheme(name = BaseConstants.OPEN_API_SECURITY_SCHEME_BEARER_NAME, type = SecuritySchemeType.OAUTH2, bearerFormat = "JWT", scheme = "bearer",
                    flows = @OAuthFlows(
                            password = @OAuthFlow(authorizationUrl = "${herodotus.endpoint.authorization-uri}", tokenUrl = "${herodotus.endpoint.access-token-uri}", refreshUrl = "${herodotus.endpoint.access-token-uri}", scopes = @OAuthScope(name = "all")),
                            clientCredentials = @OAuthFlow(authorizationUrl = "${herodotus.endpoint.authorization-uri}", tokenUrl = "${herodotus.endpoint.access-token-uri}", refreshUrl = "${herodotus.endpoint.access-token-uri}", scopes = @OAuthScope(name = "all"))
//                        authorizationCode = @OAuthFlow(authorizationUrl = "${herodotus.platform.endpoint.user-authorization-uri}", tokenUrl = "${herodotus.platform.endpoint.access-token-uri}", refreshUrl = "${herodotus.platform.endpoint.access-token-uri}", scopes = @OAuthScope(name = "all"))
                    )),
    })
    static class OpenApiAutoConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public OpenApiServerResolver openApiServerResolver() {
            OpenApiServerResolver resolver = () -> {
                Server server = new Server();
                server.setUrl(ServiceContextHolder.getInstance().getUrl());
                return ImmutableList.of(server);
            };
            log.trace("[Herodotus] |- Bean [Open Api Server Resolver] Auto Configure.");
            return resolver;
        }

        @Bean
        @ConditionalOnMissingBean
        public OpenAPI createOpenApi(OpenApiServerResolver openApiServerResolver) {
            return new OpenAPI()
                    .servers(openApiServerResolver.getServers())
                    .info(new Info().title("Herodotus Cloud")
                            .description("Herodotus Cloud Microservices Architecture")
                            .version("Swagger V3")
                            .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/")))
                    .externalDocs(new ExternalDocumentation()
                            .description("Herodotus Cloud Documentation")
                            .url(" https://www.herodotus.cn"));
        }
    }
}
