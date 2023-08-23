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

package cn.herodotus.engine.assistant.autoconfigure.jackson2;

import cn.herodotus.engine.assistant.core.json.jackson2.Jackson2CustomizerOrder;
import cn.herodotus.engine.assistant.core.json.jackson2.modules.EncapsulationClassJackson2Module;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: 默认 Jackson Custom 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/4/29 11:04
 */
public class Jackson2DefaultObjectMapperBuilderCustomizer implements BaseObjectMapperBuilderCustomizer {

    private static final Logger log = LoggerFactory.getLogger(Jackson2DefaultObjectMapperBuilderCustomizer.class);

    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {

        builder.featuresToEnable(
                SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS,
                JsonParser.Feature.ALLOW_SINGLE_QUOTES,
                JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature()
        );

        builder.featuresToDisable(
                SerializationFeature.FAIL_ON_EMPTY_BEANS,
                SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
        );

        builder.modulesToInstall(
                modules -> {
                    List<Module> install = new ArrayList<>(modules);
                    install.add(new EncapsulationClassJackson2Module());
                    install.add(new Jdk8Module());
                    install.add(new JavaTimeModule());
                    builder.modulesToInstall(toArray(install));
                }
        );

        builder.findModulesViaServiceLoader(true);

        log.debug("[Herodotus] |- DEFAULT ObjectMapper custom configuration execution is completed.");
    }

    @Override
    public int getOrder() {
        return Jackson2CustomizerOrder.CUSTOMIZER_DEFAULT;
    }
}
