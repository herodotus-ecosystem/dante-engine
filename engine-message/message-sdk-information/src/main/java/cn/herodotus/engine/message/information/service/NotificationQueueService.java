/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.message.information.service;

import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.engine.data.core.service.BaseLayeredService;
import cn.herodotus.engine.message.information.entity.MessagePullStamp;
import cn.herodotus.engine.message.information.entity.NotificationQueue;
import cn.herodotus.engine.message.information.entity.SystemAnnouncement;
import cn.herodotus.engine.message.information.repository.NotificationQueueRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Description: NotificationQueueService </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/7 22:08
 */
@Service
public class NotificationQueueService extends BaseLayeredService<NotificationQueue, String> {

    private static final Logger log = LoggerFactory.getLogger(NotificationQueueService.class);

    private final NotificationQueueRepository notificationQueueRepository;
    private final MessagePullStampService messagePullStampService;
    private final SystemAnnouncementService systemAnnouncementService;

    public NotificationQueueService(NotificationQueueRepository notificationQueueRepository, MessagePullStampService messagePullStampService, SystemAnnouncementService systemAnnouncementService) {
        this.notificationQueueRepository = notificationQueueRepository;
        this.messagePullStampService = messagePullStampService;
        this.systemAnnouncementService = systemAnnouncementService;
    }

    @Override
    public BaseRepository<NotificationQueue, String> getRepository() {
        return notificationQueueRepository;
    }

    public List<NotificationQueue> findAllUnReadNotifications(String userId) {
        List<NotificationQueue> notificationQueues = notificationQueueRepository.findAllByUserIdAndRead(userId, false);
        log.debug("[Herodotus] |- NotificationQueue Service findAllUnReadNotifications.");
        return notificationQueues;
    }

    private void pullAnnouncements(String userId) {
        MessagePullStamp messagePullStamp = messagePullStampService.getPullStamp(userId);
        List<SystemAnnouncement> systemAnnouncements = systemAnnouncementService.pullAnnouncements(messagePullStamp.getLatestPullTime());
        if (CollectionUtils.isNotEmpty(systemAnnouncements)) {
            List<NotificationQueue> notificationQueues = convertSystemAnnouncementsToNotificationQueues(userId, systemAnnouncements);
            this.saveAll(notificationQueues);
        }
    }

    @Transactional
    public List<NotificationQueue> findAllNotificationQueues(String userId) {
        pullAnnouncements(userId);
        List<NotificationQueue> unReadNotifications = findAllUnReadNotifications(userId);
        log.debug("[Herodotus] |- NotificationQueue Service findAllNotificationQueues.");
        return unReadNotifications;
    }

    private List<NotificationQueue> convertSystemAnnouncementsToNotificationQueues(String userId, List<SystemAnnouncement> systemAnnouncements) {
        return systemAnnouncements.stream().map(systemAnnouncement -> convertSystemAnnouncementToNotificationQueue(userId, systemAnnouncement)).collect(Collectors.toList());
    }

    private NotificationQueue convertSystemAnnouncementToNotificationQueue(String userId, SystemAnnouncement systemAnnouncement) {
        NotificationQueue notificationQueue = new NotificationQueue();
        notificationQueue.setUserId(userId);
        notificationQueue.setContent(systemAnnouncement.getContent());
        notificationQueue.setSenderId(systemAnnouncement.getSenderId());
        notificationQueue.setSenderName(systemAnnouncement.getSenderName());
        notificationQueue.setCategory(1);
        return notificationQueue;
    }
}
