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

package cn.herodotus.stirrup.web.autoconfigure;

import cn.herodotus.engine.rest.protect.configuration.HttpCryptoConfiguration;
import cn.herodotus.engine.rest.protect.crypto.enhance.DecryptRequestParamMapResolver;
import cn.herodotus.engine.rest.protect.crypto.enhance.DecryptRequestParamResolver;
import com.google.common.collect.Lists;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.annotation.RequestParamMapMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;

/**
 * <p>Description: Rest 请求加解密配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/5/31 9:48
 */
@AutoConfiguration
@Import({
        HttpCryptoConfiguration.class
})
public class WebCryptoAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(WebServletAutoConfiguration.class);
    private final RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    private final DecryptRequestParamResolver decryptRequestParamResolver;
    private final DecryptRequestParamMapResolver decryptRequestParamMapResolver;

    public WebCryptoAutoConfiguration(RequestMappingHandlerAdapter requestMappingHandlerAdapter, DecryptRequestParamResolver decryptRequestParamResolver, DecryptRequestParamMapResolver decryptRequestParamMapResolver) {
        this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
        this.decryptRequestParamResolver = decryptRequestParamResolver;
        this.decryptRequestParamMapResolver = decryptRequestParamMapResolver;
    }

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Module [Rest Crypto] Auto Configure.");

        List<HandlerMethodArgumentResolver> unmodifiableList = requestMappingHandlerAdapter.getArgumentResolvers();
        List<HandlerMethodArgumentResolver> list = Lists.newArrayList();
        for (HandlerMethodArgumentResolver methodArgumentResolver : unmodifiableList) {
            //需要在requestParam之前执行自定义逻辑，然后再执行下一个逻辑（责任链模式）
            if (methodArgumentResolver instanceof RequestParamMapMethodArgumentResolver) {
                decryptRequestParamMapResolver.setRequestParamMapMethodArgumentResolver((RequestParamMapMethodArgumentResolver) methodArgumentResolver);
                list.add(decryptRequestParamMapResolver);
            }
            if (methodArgumentResolver instanceof RequestParamMethodArgumentResolver) {
                decryptRequestParamResolver.setRequestParamMethodArgumentResolver((RequestParamMethodArgumentResolver) methodArgumentResolver);
                list.add(decryptRequestParamResolver);
            }
            list.add(methodArgumentResolver);
        }
        log.debug("[Herodotus] |- Rest Crypto HandlerMethodArgumentResolver Auto Configure.");

        requestMappingHandlerAdapter.setArgumentResolvers(list);
    }
}
