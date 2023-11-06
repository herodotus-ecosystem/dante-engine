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

package cn.herodotus.engine.oauth2.management.repository;

import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.engine.oauth2.management.entity.OAuth2Device;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * <p>Description: OAuth2DeviceRepository </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/15 16:14
 */
public interface OAuth2DeviceRepository extends BaseRepository<OAuth2Device, String> {

    /**
     * 根据 Client ID 查询 OAuth2Device
     *
     * @param clientId OAuth2Device 中的 clientId
     * @return {@link OAuth2Device}
     */
    OAuth2Device findByClientId(String clientId);

    /**
     * 激活设备
     * <p>
     * 更新设备是否激活的状态
     * <p>
     * 1.@Query注解来将自定义sql语句绑定到自定义方法上。
     * 2.@Modifying注解来标注只需要绑定参数的自定义的更新类语句（更新、插入、删除）。
     * 3.@Modifying只与@Query联合使用，派生类的查询方法和自定义的方法不需要此注解。
     * 4.@Modifying注解时，JPA会以更新类语句来执行，而不再是以查询语句执行。
     *
     * @param clientId    可区分客户端身份的ID
     * @param isActivated 是否已激活
     * @return 影响数据条目
     */
    @Transactional
    @Modifying
    @Query("update OAuth2Device d set d.activated = ?2 where d.clientId = ?1")
    int activate(String clientId, boolean isActivated);
}
