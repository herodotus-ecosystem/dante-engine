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

package cn.herodotus.engine.data.core.identifier;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
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
public abstract class AbstractUuidGenerator implements IdentifierGenerator, StandardGenerator {

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
