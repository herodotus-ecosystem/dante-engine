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
import cn.herodotus.engine.message.information.repository.MessagePullStampRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * <p>Description: MessagePullStampService </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/7 22:10
 */
@Service
public class MessagePullStampService extends BaseLayeredService<MessagePullStamp, String> {

    private final MessagePullStampRepository messagePullStampRepository;

    public MessagePullStampService(MessagePullStampRepository messagePullStampRepository) {
        this.messagePullStampRepository = messagePullStampRepository;
    }

    @Override
    public BaseRepository<MessagePullStamp, String> getRepository() {
        return messagePullStampRepository;
    }

    public MessagePullStamp findByUserId(String userId) {
        return messagePullStampRepository.findByUserId(userId).orElse(null);
    }

    public MessagePullStamp getPullStamp(String userId) {

        MessagePullStamp stamp = findByUserId(userId);
        if (ObjectUtils.isEmpty(stamp)) {
            stamp = new MessagePullStamp();
            stamp.setUserId(userId);
        }
        stamp.setLatestPullTime(new Date());

        return this.save(stamp);
    }
}
