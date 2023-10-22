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

package cn.herodotus.engine.supplier.message.service;

import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.engine.data.core.service.BaseService;
import cn.herodotus.engine.supplier.message.entity.Announcement;
import cn.herodotus.engine.supplier.message.repository.AnnouncementRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>Description: SystemAnnouncementService </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/7 22:11
 */
@Service
public class AnnouncementService extends BaseService<Announcement, String> {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @Override
    public BaseRepository<Announcement, String> getRepository() {
        return announcementRepository;
    }

    public List<Announcement> pullAnnouncements(Date stamp) {
        return announcementRepository.findAllByCreateTimeAfter(stamp);
    }
}
