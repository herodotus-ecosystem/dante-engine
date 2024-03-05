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

package cn.herodotus.engine.assistant.core.domain;

import com.google.common.base.Objects;
import org.dromara.hutool.core.lang.Assert;

import java.io.Serializable;

/**
 * <p>Description: 错误反馈信息实体 </p>
 * <p>
 * 错误体系代码与 HttpStatus 类型绑定以方便辨识错误类型，例如：实际的错误可以归属于 HttpStatus 404 类型的错误，那么错误代码就可以定义为 404XX。
 * 这样做主要考虑：一方面大家对 HttpStatus 错误码熟悉，方便定位错误类型；另一方面，从 HttpStatus 错误码的含义中，可以对常见的大多数问题进行分类了。比如：412 Precondition Failed 客户端请求信息的先决条件错误，那么和前置条件相关的错误例，如接口请求参数错误即可以归在这一类错误中。
 * <p>
 * 当然，实际开发中会有很多错误，是无法找到合适的与之匹配的、对应的 HttpStatus 错误码的，需要自定义。HttpStatus 错误码的开头数字只有从 1~5，自定义错误码就可以指定 6~9。比如当前数据库相关的错误，就指定为 600XX。
 *
 * @author : gengwei.zheng
 * @date : 2023/9/26 8:37
 */
public class Feedback implements Serializable {

    private static final int IS_NOT_CUSTOMIZED = 0;

    private final String message;
    private final int status;
    /**
     * 实际错误码如果与 HttpStatus 错误码对应，即开头数字为 1~5；自定义错误码，开头数字为 6~9。
     * 为了方便区分错误码是与 HttpStatus 错误码对应的还是自定义的，增加了 custom 属性。如果 custom 为 0，即为与 HttpStatus 错误码对应；如果为 6~9 那么就代表是自定义错误码
     */
    private final int custom;

    public Feedback(String message, int status) {
        this(message, status, IS_NOT_CUSTOMIZED);
    }

    public Feedback(String message, int status, int custom) {
        Assert.checkBetween(custom, IS_NOT_CUSTOMIZED, 9);
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
        return custom != IS_NOT_CUSTOMIZED;
    }

    public int getCustom() {
        return custom;
    }

    public int getSequence() {
        if (isCustom()) {
            return custom * 10000;
        } else {
            return status * 100;
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
