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

package cn.herodotus.engine.message.information.entity;

import cn.herodotus.engine.data.core.entity.BaseEntity;
import cn.herodotus.engine.message.core.constants.MessageConstants;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

/**
 * <p>Description: 通知队列 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/7 18:19
 */
@Schema(name = "通知队列")
@Entity
@Table(name = "info_notification_queue", indexes = {
        @Index(name = "info_notification_queue_id_idx", columnList = "queue_id"),
        @Index(name = "info_notification_queue_sid_idx", columnList = "user_id")
})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = MessageConstants.REGION_INFORMATION_NOTIFICATION_QUEUE)
public class NotificationQueue extends BaseEntity {

    @Schema(name = "队列ID")
    @Id
    @UuidGenerator
    @Column(name = "queue_id", length = 64)
    private String queueId;

    @Schema(name = "是否已经读取", title = "false 未读，true 已读")
    @Column(name = "is_read")
    private Boolean read = false;

    @Schema(name = "用户ID")
    @Column(name = "user_id", length = 64)
    private String userId;

    @Schema(name = "公告内容")
    @Column(name = "content", columnDefinition="TEXT")
    private String content;

    @Schema(name = "发送人ID")
    @Column(name = "sender_id", length = 64)
    private String senderId;

    @Schema(name = "发送人名称", title = "冗余信息，增加该字段减少重复查询")
    @Column(name = "sender_name", length = 50)
    private String senderName;

    @Schema(name = "通知类别", title = "1. 公告，2.私信")
    @Column(name = "category")
    private Integer category;

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("queueId", queueId)
                .add("read", read)
                .add("userId", userId)
                .add("content", content)
                .add("senderId", senderId)
                .add("senderName", senderName)
                .add("category", category)
                .toString();
    }
}
