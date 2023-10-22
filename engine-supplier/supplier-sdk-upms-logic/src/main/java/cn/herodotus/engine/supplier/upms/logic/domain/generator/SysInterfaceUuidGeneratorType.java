/*
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君) (herodotus@aliyun.com & www.herodotus.cn)
 *
 * Dante Engine licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.gnu.org/licenses/lgpl.html>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.herodotus.engine.supplier.upms.logic.domain.generator;

import cn.herodotus.engine.data.core.identifier.AbstractUuidGenerator;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysInterface;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.factory.spi.CustomIdGeneratorCreationContext;

import java.lang.reflect.Member;

/**
 * <p>Description: 自定义UUID生成器，使得保存实体类时可以在保留主键生成策略的情况下自定义表的主键 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/3/7 11:03
 */
public class SysInterfaceUuidGeneratorType extends AbstractUuidGenerator {

    public SysInterfaceUuidGeneratorType(SysInterfaceUuidGenerator config, Member idMember, CustomIdGeneratorCreationContext creationContext) {
        super(idMember);
    }

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        if (ObjectUtils.isEmpty(object)) {
            throw new HibernateException(new NullPointerException());
        }

        SysInterface sysInterface = (SysInterface) object;

        if (StringUtils.isEmpty(sysInterface.getInterfaceId())) {
            return super.generate(session, object);
        } else {
            return sysInterface.getInterfaceId();
        }
    }
}
