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

package cn.herodotus.engine.supplier.message.entity;

import cn.herodotus.engine.message.core.constants.MessageConstants;
import cn.herodotus.engine.supplier.message.domain.BaseSenderEntity;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

/**
 * <p>Description: 站内通知 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/6 21:33
 */
@Schema(name = "系统公告")
@Entity
@Table(name = "msg_announcement", indexes = {@Index(name = "msg_announcement_id_idx", columnList = "announcement_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = MessageConstants.REGION_MESSAGE_ANNOUNCEMENT)
public class Announcement extends BaseSenderEntity {

    @Schema(name = "公告ID")
    @Id
    @UuidGenerator
    @Column(name = "announcement_id", length = 64)
    private String announcementId;

    @Schema(name = "公告标题")
    @Column(name = "title", length = 128)
    private String title;

    @Schema(name = "公告内容")
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    public String getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(String announcementId) {
        this.announcementId = announcementId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("announcementId", announcementId)
                .add("title", title)
                .add("content", content)
                .toString();
    }
}
