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

package cn.herodotus.engine.data.jpa.hibernate.identifier;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.factory.spi.StandardGenerator;
import org.hibernate.id.uuid.StandardRandomStrategy;
import org.hibernate.type.descriptor.java.UUIDJavaType;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * <p>Description: 基于 Hibernate 6 的自定义 Uuid 生成器 </p>
 * <p>
 * 主要解决手动设置实体 ID 不生效问题。
 *
 * @author : gengwei.zheng
 * @date : 2022/11/7 17:15
 */
public abstract class AbstractUuidGenerator implements StandardGenerator {

    private final StandardRandomStrategy generator;
    private final UUIDJavaType.ValueTransformer valueTransformer;

    public AbstractUuidGenerator(Member idMember) {
        generator = StandardRandomStrategy.INSTANCE;

        final Class<?> propertyType;
        if (idMember instanceof Method) {
            propertyType = ((Method) idMember).getReturnType();
        } else {
            propertyType = ((Field) idMember).getType();
        }

        if (UUID.class.isAssignableFrom(propertyType)) {
            valueTransformer = UUIDJavaType.PassThroughTransformer.INSTANCE;
        } else if (String.class.isAssignableFrom(propertyType)) {
            valueTransformer = UUIDJavaType.ToStringTransformer.INSTANCE;
        } else if (byte[].class.isAssignableFrom(propertyType)) {
            valueTransformer = UUIDJavaType.ToBytesTransformer.INSTANCE;
        } else {
            throw new HibernateException("Unanticipated return type [" + propertyType.getName() + "] for UUID conversion");
        }
    }

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return valueTransformer.transform(generator.generateUuid(session));
    }
}
