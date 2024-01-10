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

import cn.herodotus.stirrup.data.kernel.repository.BaseRepository;
import cn.herodotus.engine.supplier.message.entity.Announcement;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.Date;
import java.util.List;

/**
 * <p>Description: SystemAnnouncementRepository </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/7 22:07
 */
public interface AnnouncementRepository extends BaseRepository<Announcement, String> {

    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    List<Announcement> findAllByCreateTimeAfter(Date stamp);
}
