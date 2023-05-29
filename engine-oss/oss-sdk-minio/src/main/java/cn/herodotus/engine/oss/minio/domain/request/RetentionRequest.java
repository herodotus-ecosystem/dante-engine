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

package cn.herodotus.engine.oss.minio.domain.request;

import cn.herodotus.engine.oss.minio.definition.request.BaseRequest;
import cn.herodotus.engine.rest.core.annotation.EnumeratedValue;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>Description: 对象保留设置请求参实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/2 22:22
 */
public class RetentionRequest implements BaseRequest {
    @EnumeratedValue(names = {"GOVERNANCE", "COMPLIANCE"}, message = "存储模式的值只能是大写 GOVERNANCE 或者 COMPLIANCE")
    @Schema(name = "对象保留模式", title = "存储模式的值只能是大写 GOVERNANCE 或者 COMPLIANCE")
    private String mode;

    @Schema(name = "保留到日期", title = "对象保留到的日期")
    private String retainUntilDate;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getRetainUntilDate() {
        return retainUntilDate;
    }

    public void setRetainUntilDate(String retainUntilDate) {
        this.retainUntilDate = retainUntilDate;
    }
}
