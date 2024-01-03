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

import cn.herodotus.engine.assistant.core.annotation.ConditionalOnSwaggerEnabled;
import cn.herodotus.engine.assistant.core.context.ServiceContextHolder;
import cn.herodotus.engine.assistant.definition.constants.BaseConstants;
import cn.herodotus.engine.rest.core.definition.OpenApiServerResolver;
import cn.herodotus.engine.rest.condition.properties.SwaggerProperties;
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
public class SpringdocConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SpringdocConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Open Api] Auto Configure.");
    }

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
