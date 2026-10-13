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

package cn.herodotus.dante.oauth2.commons.domain;

import cn.herodotus.dante.oauth2.commons.enums.PermissionExpression;
import cn.herodotus.dante.security.exception.SecurityAttributeClassNotFoundException;
import cn.hutool.v7.core.reflect.TypeUtil;
import cn.hutool.v7.core.reflect.method.MethodUtil;
import cn.hutool.v7.extra.spring.SpringUtil;
import com.google.common.base.MoreObjects;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.util.MethodInvocationUtils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * <p>Description: 自定义SecurityConfig </p>
 * <p>
 * 自定义SecurityConfig，主要为了构建无参数构造函数，以解决序列化出错问题
 *
 * @author : gengwei.zheng
 * @date : 2021/9/11 15:57
 */
public class HerodotusSecurityAttribute implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(HerodotusSecurityAttribute.class);

    private String expression;

    private String className;

    private String methodName;

    public HerodotusSecurityAttribute() {
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public static HerodotusSecurityAttribute create(String expression, String className, String methodName) {
        HerodotusSecurityAttribute attribute = new HerodotusSecurityAttribute();
        attribute.setExpression(expression);
        attribute.setClassName(className);
        attribute.setMethodName(methodName);
        return attribute;
    }

    public static HerodotusSecurityAttribute createDefaultPermitAll() {
        HerodotusSecurityAttribute attribute = new HerodotusSecurityAttribute();
        attribute.setExpression(PermissionExpression.PERMIT_ALL.getValue());
        attribute.setClassName(HerodotusSecurityAttribute.class.getName());
        attribute.setMethodName("getExpression");
        return attribute;
    }

    public static MethodInvocation createMethodInvocation(HerodotusSecurityAttribute attribute) {
        try {
            Class<?> clazz = ClassUtils.getClass(attribute.getClassName());
            Object object = SpringUtil.getBean(clazz);

            if (ObjectUtils.isEmpty(object)) {
                return MethodInvocationUtils.createFromClass(new HerodotusSecurityAttribute(), HerodotusSecurityAttribute.class, attribute.getMethodName(), null, null);
            } else {
                Method method = MethodUtil.getMethodByName(clazz, attribute.getMethodName());
                Class<?>[] classArgs = TypeUtil.getParamClasses(method);
                return MethodInvocationUtils.createFromClass(object, clazz, attribute.getMethodName(), classArgs, null);
            }
        } catch (ClassNotFoundException e) {
            log.error("[Herodotus] |- Reactive createMethodInvocation error, can not found the class [{}]", attribute.getClassName());
            throw new SecurityAttributeClassNotFoundException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HerodotusSecurityAttribute that = (HerodotusSecurityAttribute) o;
        return Objects.equals(expression, that.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(expression);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("attrib", expression)
                .toString();
    }
}
