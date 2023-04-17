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

package cn.herodotus.engine.oss.minio.processor;

import cn.herodotus.engine.oss.minio.domain.MultipartUploadCreate;
import cn.herodotus.engine.oss.minio.service.MultipartUploadAsyncService;
import cn.herodotus.engine.oss.minio.service.ObjectService;
import cn.herodotus.engine.oss.minio.service.PresignedService;
import io.minio.CreateMultipartUploadResponse;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListPartsResponse;
import io.minio.ObjectWriteResponse;
import io.minio.http.Method;
import io.minio.messages.Part;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/3 22:39
 */
@Component
public class MultipartUploadProcessor {

    private final MultipartUploadAsyncService multipartUploadAsyncService;
    private final PresignedService presignedService;

    @Autowired
    public MultipartUploadProcessor(MultipartUploadAsyncService multipartUploadAsyncService, PresignedService presignedService) {
        this.multipartUploadAsyncService = multipartUploadAsyncService;
        this.presignedService = presignedService;
    }

    private String createUploadId(String bucketName, String objectName) {
        CreateMultipartUploadResponse response = multipartUploadAsyncService.createMultipartUpload(bucketName, objectName);
        return response.result().uploadId();
    }

    private String createPresignedObjectUrl(String bucketName, String objectName, String uploadId, int partNumber) {
        Map<String, String> extraQueryParams = new HashMap<>();
        extraQueryParams.put("partNumber", String.valueOf(partNumber));
        extraQueryParams.put("uploadId", uploadId);

        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .method(Method.PUT)
                .extraQueryParams(extraQueryParams)
                .expiry(1, TimeUnit.HOURS)
                .build();
        return presignedService.getPresignedObjectUrl(args);
    }

    private Part[] listParts(String bucketName, String objectName, String uploadId) {
        ListPartsResponse response = multipartUploadAsyncService.listParts(bucketName, objectName, uploadId);
        List<Part> partList = response.result().partList();
        Part[] parts = new Part[partList.size()];
        return partList.toArray(parts);
    }

    public MultipartUploadCreate createMultipartUpload(String bucketName, String objectName, int chunkSize) {
        String uploadId = createUploadId(bucketName, objectName);
        MultipartUploadCreate entity = new MultipartUploadCreate(uploadId);

        for (int i = 0; i < chunkSize; i++) {
            String uploadUrl = createPresignedObjectUrl(bucketName, objectName, uploadId, i);
            entity.appendChunk(uploadUrl);
        }
        return entity;
    }

    public ObjectWriteResponse completeMultipartUpload(String bucketName, String objectName, String uploadId) {
        Part[] parts = listParts(bucketName, objectName, uploadId);
        if (ArrayUtils.isNotEmpty(parts)) {
            return multipartUploadAsyncService.completeMultipartUpload(bucketName, objectName, uploadId, parts);
        }

        return null;
    }
}
