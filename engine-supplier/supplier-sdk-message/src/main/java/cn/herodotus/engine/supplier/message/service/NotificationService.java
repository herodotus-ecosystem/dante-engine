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
import cn.herodotus.engine.supplier.message.enums.NotificationCategory;
import cn.herodotus.engine.supplier.message.entity.Announcement;
import cn.herodotus.engine.supplier.message.entity.Notification;
import cn.herodotus.engine.supplier.message.entity.PullStamp;
import cn.herodotus.engine.supplier.message.repository.NotificationRepository;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Description: NotificationQueueService </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/7 22:08
 */
@Service
public class NotificationService extends BaseService<Notification, String> {

    private final NotificationRepository notificationRepository;
    private final PullStampService pullStampService;
    private final AnnouncementService announcementService;

    public NotificationService(NotificationRepository notificationRepository, PullStampService pullStampService, AnnouncementService announcementService) {
        this.notificationRepository = notificationRepository;
        this.pullStampService = pullStampService;
        this.announcementService = announcementService;
    }

    @Override
    public BaseRepository<Notification, String> getRepository() {
        return notificationRepository;
    }

    public void pullAnnouncements(String userId) {
        PullStamp pullStamp = pullStampService.getPullStamp(userId);
        List<Announcement> systemAnnouncements = announcementService.pullAnnouncements(pullStamp.getLatestPullTime());
        if (CollectionUtils.isNotEmpty(systemAnnouncements)) {
            List<Notification> notificationQueues = convertAnnouncementsToNotifications(userId, systemAnnouncements);
            this.saveAll(notificationQueues);
        }
    }

    public Page<Notification> findByCondition(int pageNumber, int pageSize, String userId, NotificationCategory category, Boolean read) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Specification<Notification> specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("userId"), userId));

            if (ObjectUtils.isNotEmpty(category)) {
                predicates.add(criteriaBuilder.equal(root.get("category"), category));
            }

            if (ObjectUtils.isNotEmpty(read)) {
                predicates.add(criteriaBuilder.equal(root.get("read"), read));
            }

            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createTime")));
            return criteriaQuery.getRestriction();
        };

        return this.findByPage(specification, pageable);
    }

    private List<Notification> convertAnnouncementsToNotifications(String userId, List<Announcement> announcements) {
        return announcements.stream().map(announcement -> convertAnnouncementToNotification(userId, announcement)).collect(Collectors.toList());
    }

    private Notification convertAnnouncementToNotification(String userId, Announcement announcement) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setContent(announcement.getContent());
        notification.setSenderId(announcement.getSenderId());
        notification.setSenderName(announcement.getSenderName());
        notification.setSenderAvatar(announcement.getSenderAvatar());
        notification.setCategory(NotificationCategory.ANNOUNCEMENT);
        return notification;
    }

    public int setAllRead(String userId) {
        return notificationRepository.updateAllRead(userId);
    }
}
