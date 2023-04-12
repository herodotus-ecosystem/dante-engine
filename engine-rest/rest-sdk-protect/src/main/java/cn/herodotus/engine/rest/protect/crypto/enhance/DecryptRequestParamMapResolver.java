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

package cn.herodotus.engine.rest.protect.crypto.enhance;

import cn.herodotus.engine.assistant.core.definition.constants.BaseConstants;
import cn.herodotus.engine.assistant.core.definition.constants.HttpHeaders;
import cn.herodotus.engine.rest.core.annotation.Crypto;
import cn.herodotus.engine.rest.protect.crypto.processor.HttpCryptoProcessor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.RequestParamMapMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>Description: @RequestParam 解密处理器</p>
 * <p>
 * 主要针对具备以下几种条件情况，用该处理器进行处理
 * 1. 使用未指定名称的 @RequestParam 注解，同时参数类型为Map的。
 * 2. 使用了@Crypto注解，且requestDecrypt参数为 true。或者 请求路径是 '/oauth/token'
 *
 * @author : gengwei.zheng
 * @date : 2021/10/5 16:40
 */
public class DecryptRequestParamMapResolver implements HandlerMethodArgumentResolver {

    private static final Logger log = LoggerFactory.getLogger(DecryptRequestParamMapResolver.class);

    private HttpCryptoProcessor httpCryptoProcessor;
    private RequestParamMapMethodArgumentResolver requestParamMapMethodArgumentResolver;

    public void setInterfaceCryptoProcessor(HttpCryptoProcessor httpCryptoProcessor) {
        this.httpCryptoProcessor = httpCryptoProcessor;
    }

    public void setRequestParamMapMethodArgumentResolver(RequestParamMapMethodArgumentResolver requestParamMapMethodArgumentResolver) {
        this.requestParamMapMethodArgumentResolver = requestParamMapMethodArgumentResolver;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {

        String methodName = methodParameter.getMethod().getName();
        boolean isSupports = requestParamMapMethodArgumentResolver.supportsParameter(methodParameter);

        log.trace("[Herodotus] |- Is DecryptRequestParamMapResolver supports method [{}] ? Status is [{}].", methodName, isSupports);
        return isSupports;
    }

    /**
     * 判断该接口方法是否用@Crypto注解标记，同时requestDecrypt的值是true
     *
     * @param methodParameter {@link MethodParameter}
     * @return 是否开启了自定义@Crypto
     */
    private boolean isConfigCrypto(MethodParameter methodParameter) {
        Crypto crypto = methodParameter.getMethodAnnotation(Crypto.class);
        return ObjectUtils.isNotEmpty(crypto) && crypto.requestDecrypt();
    }

    /**
     * 是否 Post /oauth/token 请求
     *
     * @param uri    请求uri
     * @param method 请求类型
     * @return 是否 Post /oauth/token 请求
     */
    private boolean isOauthTokenRequest(String uri, String method) {
        return StringUtils.equals(uri, BaseConstants.DEFAULT_TOKEN_ENDPOINT) && StringUtils.equalsIgnoreCase(method, HttpMethod.POST.name());
    }

    /**
     * 是否是常规Map
     *
     * @param methodParameter {@link MethodParameter}
     * @return boolean
     */
    private boolean isRegularMap(MethodParameter methodParameter) {
        if (!MultiValueMap.class.isAssignableFrom(methodParameter.getParameterType())) {
            ResolvableType resolvableType = ResolvableType.forMethodParameter(methodParameter);
            Class<?> valueType = resolvableType.asMap().getGeneric(1).resolve();
            if (!(valueType == MultipartFile.class || valueType == Part.class)) {
                return true;
            }
        }

        return false;
    }

    @Override
    @Nullable
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();
        String sessionId = request.getHeader(HttpHeaders.X_HERODOTUS_SESSION);

        if (isConfigCrypto(methodParameter) || isOauthTokenRequest(requestURI, requestMethod)) {

            if (StringUtils.isNotBlank(sessionId)) {

                if (isRegularMap(methodParameter)) {
                    Map<String, String[]> parameterMap = webRequest.getParameterMap();
                    Map<String, String> result = CollectionUtils.newLinkedHashMap(parameterMap.size());

                    for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                        String key = entry.getKey();
                        String[] values = entry.getValue();

                        if (values.length > 0) {
                            String value = httpCryptoProcessor.decrypt(sessionId, values[0]);
                            ;
                            result.put(key, value);
                        }
                    }

                    return result;
                }
            } else {
                log.warn("[Herodotus] |- Cannot find Herodotus Cloud custom session header. Use interface crypto founction need add X_HERODOTUS_SESSION to request header.");
            }
        }

        log.debug("[Herodotus] |- The decryption conditions are not met DecryptRequestParamMapResolver, skip! to next!");
        return requestParamMapMethodArgumentResolver.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
    }
}
