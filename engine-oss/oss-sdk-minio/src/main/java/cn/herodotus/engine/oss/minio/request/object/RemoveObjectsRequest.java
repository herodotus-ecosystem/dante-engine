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

package cn.herodotus.engine.oss.minio.request.object;

import cn.herodotus.engine.oss.minio.definition.request.BucketRequest;
import cn.herodotus.engine.oss.minio.request.domain.DeleteObjectRequest;
import io.minio.RemoveObjectsArgs;
import io.minio.messages.DeleteObject;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * <p>Description: 删除对象 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/4/18 11:32
 */
public class RemoveObjectsRequest extends BucketRequest<RemoveObjectsArgs.Builder, RemoveObjectsArgs> {

    private Boolean bypassGovernanceMode;

    @Size(min = 1, message = "至少传入一项")
    private List<DeleteObjectRequest> objects;

    public Boolean getBypassGovernanceMode() {
        return bypassGovernanceMode;
    }

    public void setBypassGovernanceMode(Boolean bypassGovernanceMode) {
        this.bypassGovernanceMode = bypassGovernanceMode;
    }

    public List<DeleteObjectRequest> getObjects() {
        return objects;
    }

    public void setObjects(List<DeleteObjectRequest> objects) {
        this.objects = objects;
    }

    @Override
    public void prepare(RemoveObjectsArgs.Builder builder) {
        List<DeleteObject> deleteObjects = getObjects().stream().map(item -> new DeleteObject(item.getName(), item.getVersionId())).toList();
        builder.objects(deleteObjects);
        super.prepare(builder);
    }

    @Override
    public RemoveObjectsArgs.Builder getBuilder() {
        return RemoveObjectsArgs.builder();
    }
}
