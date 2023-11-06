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
package cn.herodotus.engine.sms.upyun.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

/**
 * <p>Description: 发送响应 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/26 16:15
 */
public class UpyunSmsResponse {

    /**
     * 所有手机号发送短信的结果
     */
    @JsonProperty("message_ids")
    @JSONField(name = "message_ids")
    private Collection<MessageId> messageIds;

    public Collection<MessageId> getMessageIds() {
        return messageIds;
    }

    public void setMessageIds(Collection<MessageId> messageIds) {
        this.messageIds = messageIds;
    }
}
