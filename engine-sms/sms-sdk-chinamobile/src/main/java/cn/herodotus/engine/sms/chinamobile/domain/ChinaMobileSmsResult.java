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
package cn.herodotus.engine.sms.chinamobile.domain;

import com.google.common.base.MoreObjects;

/**
 * <p>Description: 响应结果 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/25 14:50
 */
public class ChinaMobileSmsResult {

    /**
     * 响应状态
     */
    public static final String SUCCESS_RSPCOD = "success";

    /**
     * 消息批次号，由云MAS平台生成，用于关联短信发送请求与状态报告，注：若数据验证不通过，该参数值为空
     */
    private String msgGroup;

    /**
     * 响应状态
     */
    private String rspcod;

    /**
     * 是否成功
     */
    private boolean success;

    public String getMsgGroup() {
        return msgGroup;
    }

    public void setMsgGroup(String msgGroup) {
        this.msgGroup = msgGroup;
    }

    public String getRspcod() {
        return rspcod;
    }

    public void setRspcod(String rspcod) {
        this.rspcod = rspcod;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("msgGroup", msgGroup)
                .add("rspcod", rspcod)
                .add("success", success)
                .toString();
    }
}
