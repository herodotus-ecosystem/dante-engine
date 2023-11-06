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

package cn.herodotus.engine.sms.huawei.domain;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * <p>Description: 华为云短信发送请求实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/26 11:21
 */
public class HuaweiSmsRequest implements Serializable {

    private String from;
    private String to;
    private String templateId;
    private String templateParas;
    private String signature;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateParas() {
        return templateParas;
    }

    public void setTemplateParas(String templateParas) {
        this.templateParas = templateParas;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("from", from)
                .add("to", to)
                .add("templateId", templateId)
                .add("templateParas", templateParas)
                .add("signature", signature)
                .toString();
    }
}
