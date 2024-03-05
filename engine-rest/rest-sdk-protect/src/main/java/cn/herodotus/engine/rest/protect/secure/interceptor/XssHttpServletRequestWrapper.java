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

package cn.herodotus.engine.rest.protect.secure.interceptor;

import cn.herodotus.engine.assistant.core.utils.protect.XssUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Description: Xss 请求参数包装器  </p>
 * <p>
 * Content-Type	传参方式	接收方式
 * application/x-www-form-urlencoded	表单key-value	HttpServletRequest Parameters 获取
 * multipart/form-data	表单key-value	HttpServletRequest Parameters 获取
 * application/json	json格式文本	HttpServletRequest IO流获取
 * <p>
 * 本过滤器主要针对表单提交的参数过滤
 *
 * @author : gengwei.zheng
 * @date : 2021/8/29 21:30
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private static final Logger log = LoggerFactory.getLogger(XssHttpServletRequestWrapper.class);

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * 使用AntiSamy清洗数据
     *
     * @param value 需要清洗的数据
     * @return 清洗后的数据
     */
    private String cleaning(String value) {
        return XssUtils.cleaning(value);
    }

    private String[] cleaning(String[] parameters) {
        List<String> cleanParameters = Arrays.stream(parameters).map(XssUtils::cleaning).collect(Collectors.toList());
        String[] results = new String[cleanParameters.size()];
        return cleanParameters.toArray(results);
    }

    /**
     * 过滤请求头
     *
     * @param name 参数名
     * @return 参数值
     */
    @Override
    public String getHeader(String name) {
        String header = super.getHeader(name);
        // 如果Header为空，则直接返回，否则进行清洗
        return StringUtils.isBlank(header) ? header : cleaning(header);
    }

    @Override
    public String getParameter(String name) {
        String parameter = super.getParameter(name);
        // 如果parameter为空，则直接返回，否则进行清洗
        return StringUtils.isBlank(parameter) ? parameter : cleaning(parameter);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameterValues = super.getParameterValues(name);
        if (ArrayUtils.isNotEmpty(parameterValues)) {
            return cleaning(parameterValues);
        }
        return super.getParameterValues(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> parameterMap = super.getParameterMap();
        return parameterMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> cleaning(entry.getValue())));
    }
}
