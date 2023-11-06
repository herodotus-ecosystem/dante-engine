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

import cn.herodotus.engine.data.core.entity.BaseEntity;
import cn.herodotus.engine.message.core.constants.MessageConstants;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

/**
 * <p>Description: 私信对话 </p>
 * <p>
 * 本质是一张冗余表，作为中间桥梁连接私信联系和私信对话详情。同时保存对话的最新一条信息，方便展示。
 *
 * @author : gengwei.zheng
 * @date : 2022/12/7 11:01
 */
@Schema(name = "私信对话")
@Entity
@Table(name = "msg_dialogue", indexes = {@Index(name = "msg_dialogue_id_idx", columnList = "dialogue_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = MessageConstants.REGION_MESSAGE_DIALOGUE)
public class Dialogue extends BaseEntity {

    @Schema(name = "对话ID")
    @Id
    @UuidGenerator
    @Column(name = "dialogue_id", length = 64)
    private String dialogueId;

    @Schema(name = "最新内容")
    @Column(name = "latest_news", columnDefinition = "TEXT")
    private String latestNews;

    public String getDialogueId() {
        return dialogueId;
    }

    public void setDialogueId(String dialogueId) {
        this.dialogueId = dialogueId;
    }

    public String getLatestNews() {
        return latestNews;
    }

    public void setLatestNews(String latestNews) {
        this.latestNews = latestNews;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("dialogueId", dialogueId)
                .add("latestNews", latestNews)
                .toString();
    }
}
