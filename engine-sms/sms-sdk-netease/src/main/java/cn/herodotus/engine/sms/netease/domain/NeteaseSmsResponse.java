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
package cn.herodotus.engine.sms.netease.domain;

import com.google.common.base.MoreObjects;

/**
 * <p>Description: 响应结果 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/26 14:38
 */
public class NeteaseSmsResponse {

    /**
     * 成功代码
     */
    public static final Integer SUCCESS_CODE = 200;

    /**
     * 请求返回的结果码。
     */
    private int code;

    /**
     * 请求返回的结果码描述。
     */
    private String msg;

    private Long obj;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getObj() {
        return obj;
    }

    public void setObj(Long obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("code", code)
                .add("msg", msg)
                .add("obj", obj)
                .toString();
    }
}
