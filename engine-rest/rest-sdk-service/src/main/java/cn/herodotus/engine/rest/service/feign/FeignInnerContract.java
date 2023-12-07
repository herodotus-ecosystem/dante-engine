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

package cn.herodotus.engine.rest.service.feign;

import cn.herodotus.engine.assistant.core.utils.http.HeaderUtils;
import cn.herodotus.engine.rest.core.annotation.Inner;
import feign.MethodMetadata;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.core.convert.ConversionService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

/**
 * <p>Description: 自定义 Inner 处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/5/31 11:28
 */
public class FeignInnerContract extends SpringMvcContract {

    private static final Logger log = LoggerFactory.getLogger(FeignInnerContract.class);

    public FeignInnerContract() {
    }

    public FeignInnerContract(List<AnnotatedParameterProcessor> annotatedParameterProcessors) {
        super(annotatedParameterProcessors);
    }

    public FeignInnerContract(List<AnnotatedParameterProcessor> annotatedParameterProcessors, ConversionService conversionService) {
        super(annotatedParameterProcessors, conversionService);
    }

    public FeignInnerContract(List<AnnotatedParameterProcessor> annotatedParameterProcessors, ConversionService conversionService, boolean decodeSlash) {
        super(annotatedParameterProcessors, conversionService, decodeSlash);
    }

    @Override
    protected void processAnnotationOnMethod(MethodMetadata data, Annotation methodAnnotation, Method method) {

        if (methodAnnotation instanceof Inner) {
            Inner inner = findMergedAnnotation(method, Inner.class);
            if (ObjectUtils.isNotEmpty(inner)) {
                log.debug("[Herodotus] |- Found inner annotation on Feign interface, add header!");
                data.template().header(HeaderUtils.X_HERODOTUS_FROM_IN, "true");
            }
        }

        super.processAnnotationOnMethod(data, methodAnnotation, method);
    }
}
