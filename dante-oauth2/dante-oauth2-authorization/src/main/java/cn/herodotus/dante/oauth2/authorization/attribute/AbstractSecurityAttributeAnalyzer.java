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

package cn.herodotus.dante.oauth2.authorization.attribute;

import cn.herodotus.dante.messaging.domain.SecurityAttribute;
import cn.herodotus.dante.oauth2.commons.domain.HerodotusSecurityAttribute;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>Description: SecurityAttribute 分析器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2026/2/27 0:41
 */
abstract class AbstractSecurityAttributeAnalyzer {

    /**
     * 直接使用 {@link org.springframework.security.authorization.DefaultAuthorizationManagerFactory} 中的方法
     *
     * @param authority 权限
     * @return 权限表达式
     */
    private String hasAuthority(String authority) {
        return "hasAuthority('" + authority + "')";
    }

    /**
     * 解析并动态组装所需要的权限。
     * <p>
     * 1. 原 spring-security-oauth
     * Spring Security 基础权限规则，来源于org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.AuthorizedUrl
     * OAuth2 权限规则来源于 org.springframework.security.oauth2.provider.expression.OAuth2SecurityExpressionMethods
     * 2. 新 spring-authorization-server
     * Spring Security 基础权限规则，来源于{@link org.springframework.security.authorization.DefaultAuthorizationManagerFactory}
     * OAuth2 权限规则来源于 目前还没有
     * <p>
     * 具体解析采用的是 Security 的 <code> org.springframework.security.access.AccessDecisionVoter} </code> 方式，而不是采用自定义的 <code> org.springframework.security.access.AccessDecisionManager </code>该方式会与默认的 httpsecurity 配置覆盖。
     * · 基本的权限验证采用的是：<code> org.springframework.security.access.vote.RoleVoter</code>
     * · scope权限采用两种方式：
     * 一种是：Spring Security org.springframework.security.oauth2.provider.vote.ScopeVoter 目前已取消
     * 另一种是：OAuth2 'hasScope'和'hasAnyScope'方式  org.springframework.security.oauth2.provider.expression.OAuth2SecurityExpressionMethods#hasAnyScope(String...)
     * <p>
     * 如果实际应用不满足可以，自己扩展AccessDecisionVoter或者AccessDecisionManager
     *
     * @param securityAttribute {@link SecurityAttribute}
     * @return security权限定义集合
     */
    protected List<HerodotusSecurityAttribute> analysis(SecurityAttribute securityAttribute) {

        List<HerodotusSecurityAttribute> attributes = new ArrayList<>();

        if (StringUtils.isNotBlank(securityAttribute.getPermissions())) {
            String[] permissions = org.springframework.util.StringUtils.commaDelimitedListToStringArray(securityAttribute.getPermissions());
            Arrays.stream(permissions).forEach(item -> attributes.add(HerodotusSecurityAttribute.create(hasAuthority(item), securityAttribute.getClassName(), securityAttribute.getMethodName())));
        }

        if (StringUtils.isNotBlank(securityAttribute.getWebExpression())) {
            attributes.add(HerodotusSecurityAttribute.create(securityAttribute.getWebExpression(), securityAttribute.getClassName(), securityAttribute.getMethodName()));
        }

        return attributes;
    }

    public abstract void postAttributeDistributionProcess(List<SecurityAttribute> securityAttributes);
}
