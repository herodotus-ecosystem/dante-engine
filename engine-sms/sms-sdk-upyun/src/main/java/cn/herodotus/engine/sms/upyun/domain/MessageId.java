/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.apache.org/licenses/LICENSE-2.0>
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
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 6.若您的项目无法满足以上几点，可申请商业授权
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
}
