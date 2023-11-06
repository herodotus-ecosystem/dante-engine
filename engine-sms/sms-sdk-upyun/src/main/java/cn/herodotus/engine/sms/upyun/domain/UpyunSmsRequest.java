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
import com.google.common.base.MoreObjects;

/**
 * <p>Description: 又拍网发送短信请求实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/26 14:17
 */
public class UpyunSmsRequest {

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 模板编号
     */
    @JsonProperty("template_id")
    @JSONField(name = "template_id")
    private String templateId;

    /**
     * 短信参数
     */
    private String vars;

    /**
     * 拓展码
     */
    @JsonProperty("extend_code")
    @JSONField(name = "extend_code")
    private String extendCode;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getVars() {
        return vars;
    }

    public void setVars(String vars) {
        this.vars = vars;
    }

    public String getExtendCode() {
        return extendCode;
    }

    public void setExtendCode(String extendCode) {
        this.extendCode = extendCode;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mobile", mobile)
                .add("templateId", templateId)
                .add("vars", vars)
                .add("extendCode", extendCode)
                .toString();
    }
}
