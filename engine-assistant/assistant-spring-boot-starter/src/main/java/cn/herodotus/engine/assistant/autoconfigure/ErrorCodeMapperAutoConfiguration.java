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

package cn.herodotus.engine.assistant.autoconfigure;

import cn.herodotus.engine.assistant.autoconfigure.customizer.StandardErrorCodeMapperBuilderCustomizer;
import cn.herodotus.engine.assistant.autoconfigure.initializer.AssistantPreparedInitializer;
import cn.herodotus.engine.assistant.core.definition.exception.ErrorCodeMapperBuilderCustomizer;
import cn.herodotus.engine.assistant.core.exception.ErrorCodeMapperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * <p>Description: 错误代码映射自动配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/9/25 15:11
 */
@AutoConfiguration
public class ErrorCodeMapperAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ErrorCodeMapperAutoConfiguration.class);

    @Bean
    public ErrorCodeMapperBuilderCustomizer standardErrorCodeMapperBuilderCustomizer() {
        StandardErrorCodeMapperBuilderCustomizer customizer = new StandardErrorCodeMapperBuilderCustomizer();
        log.trace("[Herodotus] |- Bean [Standard Error Code Mapper Builder Customizer] Auto Configure.");
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
    public AssistantPreparedInitializer assistantPreparedInitializer(ErrorCodeMapperBuilder builder) {
        AssistantPreparedInitializer initializer = new AssistantPreparedInitializer(builder);
        log.trace("[Herodotus] |- Bean [Assistant Prepared Initializer] Auto Configure.");
        return initializer;
    }
}
