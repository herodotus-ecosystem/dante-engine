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
package cn.herodotus.engine.sms.huawei.domain;

import com.google.common.base.MoreObjects;

import java.util.Collection;

/**
 * <p>Description: 华为云响应结果 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/25 14:47
 */
public class HuaweiSmsResponse {

    /**
     * 成功代码
     */
    public static final String SUCCESS_CODE = "000000";

    /**
     * 请求返回的结果码。
     */
    private String code;

    /**
     * 请求返回的结果码描述。
     */
    private String description;

    /**
     * 短信ID列表，当目的号码存在多个时，每个号码都会返回一个SmsID。
     * <p>
     * 当返回异常响应时不携带此字段。
     */
    private Collection<SmsID> result;

    /**
     * 短信ID
     */

    public static class SmsID {

        /**
         * 短信的唯一标识。
         */
        private String smsMsgId;

        /**
         * 短信发送方的号码。
         */
        private String from;

        /**
         * 短信接收方的号码。
         */
        private String originTo;

        /**
         * 短信状态码
         */
        private String status;

        /**
         * 短信资源的创建时间，即短信平台接收到客户发送短信请求的时间，为UTC时间。
         * <p>
         * 格式为：yyyy-MM-dd'T'HH:mm:ss'Z'。
         */
        private String createTime;

        public String getSmsMsgId() {
            return smsMsgId;
        }

        public void setSmsMsgId(String smsMsgId) {
            this.smsMsgId = smsMsgId;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getOriginTo() {
            return originTo;
        }

        public void setOriginTo(String originTo) {
            this.originTo = originTo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("smsMsgId", smsMsgId)
                    .add("from", from)
                    .add("originTo", originTo)
                    .add("status", status)
                    .add("createTime", createTime)
                    .toString();
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<SmsID> getResult() {
        return result;
    }

    public void setResult(Collection<SmsID> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("code", code)
                .add("description", description)
                .add("result", result)
                .toString();
    }
}
