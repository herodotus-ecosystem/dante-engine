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

package cn.herodotus.engine.supplier.message.entity;

import cn.herodotus.engine.message.core.constants.MessageConstants;
import cn.herodotus.engine.supplier.message.domain.BaseSenderEntity;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

/**
 * <p>Description: 私信对话详情 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/7 11:03
 */
@Schema(name = "私信对话详情")
@Entity
@Table(name = "msg_dialogue_detail", indexes = {
        @Index(name = "msg_dialogue_detail_id_idx", columnList = "detail_id"),
        @Index(name = "msg_dialogue_detail_sid_idx", columnList = "sender_id"),
        @Index(name = "msg_dialogue_detail_rid_idx", columnList = "receiver_id"),
        @Index(name = "msg_dialogue_detail_did_idx", columnList = "dialogue_id")
})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = MessageConstants.REGION_MESSAGE_DIALOGUE_DETAIL)
public class DialogueDetail extends BaseSenderEntity {

    @Schema(name = "对话详情ID")
    @Id
    @UuidGenerator
    @Column(name = "detail_id", length = 64)
    private String detailId;

    @Schema(name = "接收人ID")
    @Column(name = "receiver_id", length = 64)
    private String receiverId;

    @Schema(name = "接收人名称", title = "冗余信息，增加该字段减少重复查询")
    @Column(name = "receiver_name", length = 50)
    private String receiverName;

    @Schema(name = "发送人头像")
    @Column(name = "receiver_avatar", length = 1000)
    private String receiverAvatar;

    @Schema(name = "公告内容")
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Schema(name = "对话ID")
    @Column(name = "dialogue_id", length = 64)
    private String dialogueId;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverAvatar() {
        return receiverAvatar;
    }

    public void setReceiverAvatar(String receiverAvatar) {
        this.receiverAvatar = receiverAvatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDialogueId() {
        return dialogueId;
    }

    public void setDialogueId(String dialogueId) {
        this.dialogueId = dialogueId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("detailId", detailId)
                .add("receiverId", receiverId)
                .add("receiverName", receiverName)
                .add("receiverAvatar", receiverAvatar)
                .add("content", content)
                .add("dialogueId", dialogueId)
                .toString();
    }
}
