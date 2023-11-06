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
import org.apache.commons.lang3.StringUtils;

/**
 * <p>Description: 手机号发送短信的结果 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/26 14:20
 */
public class MessageId {

    /**
     * 错误情况
     */
    @JsonProperty("error_code")
    @JSONField(name = "error_code")
    private String errorCode;

    /**
     * 旧版本国内短信的 message 编号
     */
    @JsonProperty("message_id")
    @JSONField(name = "message_id")
    private Integer messageId;

    /**
     * message 编号
     */
    @JsonProperty("msg_id")
    @JSONField(name = "msg_id")
    private String msgId;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 判断是否成功
     *
     * @return 是否成功
     */
    public boolean succeed() {
        return StringUtils.isBlank(errorCode) && StringUtils.isNotBlank(msgId);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
