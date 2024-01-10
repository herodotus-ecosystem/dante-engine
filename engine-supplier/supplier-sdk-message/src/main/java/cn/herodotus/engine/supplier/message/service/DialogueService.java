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

package cn.herodotus.engine.supplier.message.service;

import cn.herodotus.stirrup.data.kernel.repository.BaseRepository;
import cn.herodotus.stirrup.data.kernel.service.BaseService;
import cn.herodotus.engine.supplier.message.entity.Dialogue;
import cn.herodotus.engine.supplier.message.repository.DialogueRepository;
import org.springframework.stereotype.Service;

/**
 * <p>Description: PersonalDialogueService </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/7 22:09
 */
@Service
public class DialogueService extends BaseService<Dialogue, String> {

    private final DialogueRepository dialogueRepository;

    public DialogueService(DialogueRepository dialogueRepository) {
        this.dialogueRepository = dialogueRepository;
    }

    @Override
    public BaseRepository<Dialogue, String> getRepository() {
        return dialogueRepository;
    }

    public Dialogue createDialogue(String content) {
        Dialogue dialogue = new Dialogue();
        dialogue.setLatestNews(content);
        return this.save(dialogue);
    }

    public Dialogue updateDialogue(String dialogueId, String content) {
        Dialogue dialogue = this.findById(dialogueId);
        dialogue.setLatestNews(content);
        return this.save(dialogue);
    }
}
