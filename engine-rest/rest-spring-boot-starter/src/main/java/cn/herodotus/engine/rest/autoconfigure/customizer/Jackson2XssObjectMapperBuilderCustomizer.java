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

package cn.herodotus.engine.rest.autoconfigure.customizer;

import cn.herodotus.engine.assistant.core.support.BaseObjectMapperBuilderCustomizer;
import cn.herodotus.engine.assistant.core.json.jackson2.Jackson2CustomizerOrder;
import cn.herodotus.engine.rest.protect.jackson2.XssStringJsonDeserializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: Jackson Xss Customizer </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/4/29 16:30
 */
public class Jackson2XssObjectMapperBuilderCustomizer implements BaseObjectMapperBuilderCustomizer {

    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(String.class, new XssStringJsonDeserializer());

        builder.modulesToInstall(modules -> {
            List<Module> install = new ArrayList<>(modules);
            install.add(simpleModule);
            builder.modulesToInstall(toArray(install));
        });
    }

    @Override
    public int getOrder() {
        return Jackson2CustomizerOrder.CUSTOMIZER_XSS;
    }
}
