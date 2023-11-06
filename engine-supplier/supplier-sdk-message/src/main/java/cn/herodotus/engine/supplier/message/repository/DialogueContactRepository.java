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

import cn.herodotus.engine.assistant.core.exception.transaction.TransactionalRollbackException;
import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.engine.supplier.message.entity.DialogueContact;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * <p>Description: PersonalContactRepository </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/7 22:05
 */
public interface DialogueContactRepository extends BaseRepository<DialogueContact, String> {

    @Transactional(rollbackFor = TransactionalRollbackException.class)
    @Modifying
    @Query("delete from DialogueContact c where c.dialogue.dialogueId = :id")
    void deleteAllByDialogueId(@Param("id") String dialogueId);

    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    Optional<DialogueContact> findBySenderIdAndReceiverId(String senderId, String receiverId);
}
