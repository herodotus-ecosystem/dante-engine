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

package cn.herodotus.stirrup.oauth2.data.jpa.generator;

import cn.herodotus.engine.data.core.identifier.AbstractUuidGenerator;
import cn.herodotus.stirrup.oauth2.data.jpa.entity.HerodotusRegisteredClient;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.factory.spi.CustomIdGeneratorCreationContext;

import java.lang.reflect.Member;

/**
 * <p>Description: OAuth2RegisteredClient Id 生成器 </p>
 * <p>
 * 指定ID生成器，解决实体ID无法手动设置问题。
 *
 * @author : gengwei.zheng
 * @date : 2022/1/22 17:50
 */
public class HerodotusRegisteredClientUuidGeneratorType extends AbstractUuidGenerator {

    public HerodotusRegisteredClientUuidGeneratorType(HerodotusRegisteredClientUuidGenerator config, Member idMember, CustomIdGeneratorCreationContext creationContext) {
        super(idMember);
    }

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        if (ObjectUtils.isEmpty(object)) {
            throw new HibernateException(new NullPointerException());
        }

        HerodotusRegisteredClient herodotusRegisteredClient = (HerodotusRegisteredClient) object;

        if (StringUtils.isEmpty(herodotusRegisteredClient.getId())) {
            return super.generate(session, object);
        } else {
            return herodotusRegisteredClient.getId();
        }
    }
}
