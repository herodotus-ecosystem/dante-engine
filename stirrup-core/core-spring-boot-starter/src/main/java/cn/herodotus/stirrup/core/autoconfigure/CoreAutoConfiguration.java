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

package cn.herodotus.stirrup.core.autoconfigure;

import cn.herodotus.engine.assistant.definition.domain.ErrorCodeMapper;
import cn.herodotus.engine.assistant.definition.function.ErrorCodeMapperBuilderCustomizer;
import cn.herodotus.engine.assistant.definition.support.ErrorCodeMapperBuilder;
import cn.herodotus.stirrup.core.autoconfigure.customizer.StandardErrorCodeMapperBuilderCustomizer;
import jakarta.annotation.PostConstruct;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.List;

/**
 * <p>Description: 核心基础模块统一 Starter </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/1/23 22:10
 */
@AutoConfiguration
@Import({
        SpringUtil.class,
})
public class CoreAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CoreAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Starter [Core] Auto Configure.");
    }

    @Bean
    public ErrorCodeMapperBuilderCustomizer standardErrorCodeMapperBuilderCustomizer() {
        StandardErrorCodeMapperBuilderCustomizer customizer = new StandardErrorCodeMapperBuilderCustomizer();
        log.debug("[Herodotus] |- Strategy [Standard ErrorCodeMapper Builder Customizer] Auto Configure.");
        return customizer;
    }

    @Bean
    public ErrorCodeMapperBuilder errorCodeMapperBuilder(List<ErrorCodeMapperBuilderCustomizer> customizers) {
        ErrorCodeMapperBuilder builder = new ErrorCodeMapperBuilder();
        customize(builder, customizers);
        log.debug("[Herodotus] |- Bean [Error Code Mapper Builder] Auto Configure.");
        return builder;
    }

    private void customize(ErrorCodeMapperBuilder builder, List<ErrorCodeMapperBuilderCustomizer> customizers) {
        for (ErrorCodeMapperBuilderCustomizer customizer : customizers) {
            customizer.customize(builder);
        }
    }

    @Bean
    public ErrorCodeMapper errorCodeMapper(ErrorCodeMapperBuilder builder) {
        ErrorCodeMapper mapper = builder.build();
        log.debug("[Herodotus] |- Bean [Error Code Mapper] Auto Configure.");
        return mapper;
    }
}
