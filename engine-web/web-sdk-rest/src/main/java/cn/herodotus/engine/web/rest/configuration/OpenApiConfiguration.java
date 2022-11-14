/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
 * 2.请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.web.rest.configuration;

import cn.herodotus.engine.assistant.core.constants.BaseConstants;
import cn.herodotus.engine.web.core.definition.OpenApiServerResolver;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.*;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> Description : SwaggerConfiguration </p>
 * <p>
 * 原来的@EnableSwagger2去掉
 *
 * @author : gengwei.zheng
 * @date : 2020/3/31 11:54
 */
@Configuration(proxyBeanMethods = false)
@SecuritySchemes({
        @SecurityScheme(name = BaseConstants.OPEN_API_SECURITY_SCHEME_BEARER_NAME, type = SecuritySchemeType.OAUTH2, bearerFormat = "JWT", scheme = "bearer",
                flows = @OAuthFlows(
                        password = @OAuthFlow(authorizationUrl = "${herodotus.endpoint.authorization-uri}", tokenUrl = "${herodotus.endpoint.access-token-uri}", refreshUrl = "${herodotus.endpoint.access-token-uri}", scopes = @OAuthScope(name = "all")),
                        clientCredentials = @OAuthFlow(authorizationUrl = "${herodotus.endpoint.authorization-uri}", tokenUrl = "${herodotus.endpoint.access-token-uri}", refreshUrl = "${herodotus.endpoint.access-token-uri}", scopes = @OAuthScope(name = "all"))
//                        authorizationCode = @OAuthFlow(authorizationUrl = "${herodotus.platform.endpoint.user-authorization-uri}", tokenUrl = "${herodotus.platform.endpoint.access-token-uri}", refreshUrl = "${herodotus.platform.endpoint.access-token-uri}", scopes = @OAuthScope(name = "all"))
                )),
})
public class OpenApiConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OpenApiConfiguration.class);

    private final OpenApiServerResolver openApiServerResolver;

    @Autowired
    public OpenApiConfiguration(OpenApiServerResolver openApiServerResolver) {
        this.openApiServerResolver = openApiServerResolver;
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Engine Web Rest Swagger] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public OpenAPI createOpenApi() {
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
