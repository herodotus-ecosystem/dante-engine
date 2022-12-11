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
import cn.herodotus.engine.message.information.entity.PersonalDialogue;
import cn.herodotus.engine.message.information.entity.PersonalDialogueDetail;
import cn.herodotus.engine.message.information.repository.PersonalDialogueDetailRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>Description: PersonalDialogueDetailService </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/7 22:10
 */
@Service
public class PersonalDialogueDetailService extends BaseLayeredService<PersonalDialogueDetail, String> {

    private final PersonalDialogueDetailRepository personalDialogueDetailRepository;
    private final PersonalContactService personalContactService;
    private final PersonalDialogueService personalDialogueService;

    public PersonalDialogueDetailService(PersonalDialogueDetailRepository personalDialogueDetailRepository, PersonalContactService personalContactService, PersonalDialogueService personalDialogueService) {
        this.personalDialogueDetailRepository = personalDialogueDetailRepository;
        this.personalContactService = personalContactService;
        this.personalDialogueService = personalDialogueService;
    }

    @Override
    public BaseRepository<PersonalDialogueDetail, String> getRepository() {
        return personalDialogueDetailRepository;
    }

    /**
     * 借鉴 Gitee 的私信设计
     * 1. 每个人都可以查看与自己有过私信往来的用户列表。自己可以查看与自己有过联系的人，对方也可以查看与自己有过联系的人
     * 2. 私信往来用户列表中，显示最新一条对话的内容
     * 3. 点开某一个用户，可以查看具体的对话详情。自己和私信对话用户看到的内容一致。
     * <p>
     * PersonalContact 存储私信双方的关系，存储两条。以及和对话的关联
     * PersonalDialogue 是一个桥梁连接 PersonalContact 和 PersonalDialogueDetail，同时存储一份最新对话副本
     * <p>
     * 本处的逻辑：
     * 发送私信时，首先要判断是否已经创建了 Dialogue
     * 1. 如果没有创建 Dialogue，就是私信双方第一对话，那么要先创建 Dialogue，同时要建立私信双方的联系 Contact。保存的私信与将生成好的 DialogueId进行关联。
     * 2. 如果已经有Dialogue，那么就直接保存私信对话，同时更新 Dialogue 中的最新信息。
     *
     * @param domain 数据对应实体
     * @return
     */
    @Transactional
    @Override
    public PersonalDialogueDetail save(PersonalDialogueDetail domain) {
        if (StringUtils.isBlank(domain.getDialogueId())) {
            PersonalDialogue dialogue = personalDialogueService.createDialog(domain.getContent());
            domain.setDialogueId(dialogue.getDialogueId());
            personalContactService.createContact(dialogue, domain);
        } else {
            personalDialogueService.updateDialogue(domain.getDialogueId(), domain.getContent());
        }

        return super.save(domain);
    }
}
