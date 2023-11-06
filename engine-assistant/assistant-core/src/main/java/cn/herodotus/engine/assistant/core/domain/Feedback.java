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

package cn.herodotus.engine.assistant.core.domain;

import com.google.common.base.Objects;
import org.dromara.hutool.core.lang.Assert;

import java.io.Serializable;

/**
 * <p>Description: 错误反馈信息实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/9/26 8:37
 */
public class Feedback implements Serializable {

    private final String message;
    private final int status;
    private final int custom;

    public Feedback(String message, int status) {
        this(message, status, 0);
    }

    public Feedback(String message, int status, int custom) {
        Assert.checkBetween(custom, 0, 9);
        this.message = message;
        this.status = status;
        this.custom = custom;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public boolean isCustom() {
        return custom == 0;
    }

    public int getCustom() {
        return custom;
    }

    public int getSequence() {
        if (isCustom()) {
            return status * 100;
        } else {
            return custom + 10000;
        }
    }

    public int getSequence(int index) {
        return getSequence() + index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Feedback feedback = (Feedback) o;
        return Objects.equal(message, feedback.message);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(message);
    }
}
