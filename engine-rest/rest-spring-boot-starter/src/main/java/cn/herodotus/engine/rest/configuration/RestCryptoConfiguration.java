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

package cn.herodotus.engine.rest.configuration;

import cn.herodotus.engine.rest.protect.crypto.enhance.DecryptRequestParamMapResolver;
import cn.herodotus.engine.rest.protect.crypto.enhance.DecryptRequestParamResolver;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.annotation.RequestParamMapMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import jakarta.annotation.PostConstruct;
import java.util.List;

/**
 * <p>Description: Rest 请求加解密配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/5/31 9:48
 */
@Configuration(proxyBeanMethods = false)
public class RestCryptoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RestWebMvcConfiguration.class);
    private final RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    private final DecryptRequestParamResolver decryptRequestParamResolver;
    private final DecryptRequestParamMapResolver decryptRequestParamMapResolver;

    @Autowired
    public RestCryptoConfiguration(RequestMappingHandlerAdapter requestMappingHandlerAdapter, DecryptRequestParamResolver decryptRequestParamResolver, DecryptRequestParamMapResolver decryptRequestParamMapResolver) {
        this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
        this.decryptRequestParamResolver = decryptRequestParamResolver;
        this.decryptRequestParamMapResolver = decryptRequestParamMapResolver;
    }

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- SDK [Rest Crypto] Auto Configure.");

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
