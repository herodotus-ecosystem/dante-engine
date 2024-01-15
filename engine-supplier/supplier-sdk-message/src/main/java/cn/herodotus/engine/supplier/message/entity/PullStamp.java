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

import cn.herodotus.engine.assistant.definition.constants.DefaultConstants;
import cn.herodotus.engine.assistant.definition.domain.base.AbstractEntity;
import cn.herodotus.engine.message.core.constants.MessageConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

/**
 * <p>Description: 信息拉取标记 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/6 21:34
 */
@Schema(name = "拉取标记")
@Entity
@Table(name = "msg_pull_stamp", indexes = {
        @Index(name = "msg_pull_stamp_id_idx", columnList = "stamp_id"),
        @Index(name = "msg_pull_stamp_sid_idx", columnList = "user_id")
})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = MessageConstants.REGION_MESSAGE_PULL_STAMP)
public class PullStamp extends AbstractEntity {

    @Id
    @UuidGenerator
    @Column(name = "stamp_id", length = 64)
    private String stampId;

    @Schema(name = "用户ID")
    @Column(name = "user_id", length = 64)
    private String userId;

    @Schema(name = "来源", title = "预留字段，以备支持不同端的情况")
    @Column(name = "source", length = 50)
    private String source;

    @Schema(title = "上次拉取时间")
    @Column(name = "latest_pull_time", updatable = false)
    @JsonFormat(pattern = DefaultConstants.DATE_TIME_FORMAT)
    private Date latestPullTime = new Date();

    public String getStampId() {
        return stampId;
    }

    public void setStampId(String stampId) {
        this.stampId = stampId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getLatestPullTime() {
        return latestPullTime;
    }

    public void setLatestPullTime(Date latestPullTime) {
        this.latestPullTime = latestPullTime;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("stampId", stampId)
                .add("userId", userId)
                .add("source", source)
                .add("latestPullTime", latestPullTime)
                .toString();
    }
}
