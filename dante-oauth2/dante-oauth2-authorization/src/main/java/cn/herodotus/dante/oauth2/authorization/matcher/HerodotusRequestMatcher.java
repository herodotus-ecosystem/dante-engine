/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 是 Dante Cloud 系统核心组件库，采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dante.oauth2.authorization.matcher;

import cn.herodotus.dante.oauth2.authorization.attribute.RestSecurityAttributeStorage;
import cn.herodotus.dante.oauth2.commons.domain.HerodotusRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * <p>Description: 自定义 {@link RequestMatcher} 接口扩展，在没有 {@link HttpServletRequest} 的环境下</p>
 * <p>
 * 概扩展的目的，主要是为了实现 {@link RestSecurityAttributeStorage} 代码中，使用 {@link PathPatternRequestMatcher} 逻辑进行高效请求路径去重分析。
 *
 * @author : gengwei.zheng
 * @date : 2025/8/24 0:27
 */
public interface HerodotusRequestMatcher extends RequestMatcher {

    /**
     * 判断策略所实施的规则是否与提供的请求相匹配
     *
     * @param request 自定义请求对象 {@link HerodotusRequest}
     * @return true 请求是否匹配，false 不匹配
     */
    boolean matches(HerodotusRequest request);

    /**
     * 判断策略所实施的规则是否与提供的请求相匹配
     *
     * @param request 自定义请求对象 {@link HerodotusRequest}
     * @return 匹配结果 {@link MatchResult}
     */
    default MatchResult matcher(HerodotusRequest request) {
        boolean match = matches(request);
        return match ? MatchResult.match() : MatchResult.notMatch();
    }
}
