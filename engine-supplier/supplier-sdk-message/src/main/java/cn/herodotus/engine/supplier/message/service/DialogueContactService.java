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
import cn.herodotus.engine.supplier.message.entity.Dialogue;
import cn.herodotus.engine.supplier.message.entity.DialogueContact;
import cn.herodotus.engine.supplier.message.entity.DialogueDetail;
import cn.herodotus.engine.supplier.message.repository.DialogueContactRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: PersonalContactService </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/7 22:09
 */
@Service
public class DialogueContactService extends BaseService<DialogueContact, String> {

    private final DialogueContactRepository dialogueContactRepository;

    public DialogueContactService(DialogueContactRepository dialogueContactRepository) {
        this.dialogueContactRepository = dialogueContactRepository;
    }

    @Override
    public BaseRepository<DialogueContact, String> getRepository() {
        return dialogueContactRepository;
    }

    public List<DialogueContact> createContact(Dialogue dialogue, DialogueDetail dialogueDetail) {
        DialogueContact contact = new DialogueContact();
        contact.setDialogue(dialogue);
        contact.setReceiverId(dialogueDetail.getReceiverId());
        contact.setSenderId(dialogueDetail.getSenderId());
        contact.setSenderName(dialogueDetail.getSenderName());
        contact.setSenderAvatar(dialogueDetail.getSenderAvatar());

        DialogueContact reverseContext = new DialogueContact();
        reverseContext.setDialogue(dialogue);
        reverseContext.setReceiverId(dialogueDetail.getSenderId());
        reverseContext.setSenderId(dialogueDetail.getReceiverId());
        reverseContext.setSenderName(dialogueDetail.getReceiverName());
        reverseContext.setSenderAvatar(dialogueDetail.getReceiverAvatar());

        List<DialogueContact> personalContacts = new ArrayList<>();
        personalContacts.add(contact);
        personalContacts.add(reverseContext);

        return this.saveAll(personalContacts);
    }

    public Page<DialogueContact> findByCondition(int pageNumber, int pageSize, String receiverId) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Specification<DialogueContact> specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("receiverId"), receiverId));

            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createTime")));
            return criteriaQuery.getRestriction();
        };

        return this.findByPage(specification, pageable);
    }

    public void deleteByDialogueId(String dialogueId) {
        dialogueContactRepository.deleteAllByDialogueId(dialogueId);
    }

    public DialogueContact findBySenderIdAndReceiverId(String senderId, String receiverId) {
        return dialogueContactRepository.findBySenderIdAndReceiverId(senderId, receiverId).orElse(null);
    }
}
