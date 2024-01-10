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

package cn.herodotus.engine.supplier.message.repository;

import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.engine.supplier.message.entity.Notification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>Description: NotificationQueueRepository </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/7 22:03
 */
public interface NotificationRepository extends BaseRepository<Notification, String> {

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update Notification n set n.read = true where n.userId = :userId")
    int updateAllRead(@Param("userId") String userId);
}
