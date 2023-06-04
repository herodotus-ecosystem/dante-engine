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

import cn.herodotus.engine.oss.minio.converter.ObjectWriteResponseToEntityConverter;
import cn.herodotus.engine.oss.minio.entity.ObjectWriteEntity;
import cn.herodotus.engine.oss.minio.entity.MultipartCreateEntity;
import cn.herodotus.engine.oss.minio.service.MultipartUploadService;
import cn.herodotus.engine.oss.minio.service.PresignedService;
import io.minio.CreateMultipartUploadResponse;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListPartsResponse;
import io.minio.ObjectWriteResponse;
import io.minio.http.Method;
import io.minio.messages.Part;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>Description: 大文件分片处理逻辑 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/3 22:39
 */
@Component
public class MultipartUploadProcessor {

    private final MultipartUploadService multipartUploadService;
    private final PresignedService presignedService;
    private final MinioProxyAddressConverter converter;

    @Autowired
    public MultipartUploadProcessor(MultipartUploadService multipartUploadService, PresignedService presignedService, MinioProxyAddressConverter converter) {
        this.multipartUploadService = multipartUploadService;
        this.presignedService = presignedService;
        this.converter = converter;
    }

    /**
     * 第一步：创建分片上传请求, 返回 UploadId
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @return 大文件分片上传 UploadId
     */
    private String createUploadId(String bucketName, String region, String objectName) {
        CreateMultipartUploadResponse response = multipartUploadService.createMultipartUpload(bucketName, region, objectName, null, null);
        return response.result().uploadId();
    }

    /**
     * 第二步：创建文件预上传地址
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param uploadId   第一步中创建的 UploadId
     * @param partNumber 分片号
     * @return 预上传地址
     */
    private String createPresignedObjectUrl(String bucketName, String region, String objectName, String uploadId, int partNumber) {
        Map<String, String> extraQueryParams = new HashMap<>();
        extraQueryParams.put("partNumber", String.valueOf(partNumber));
        extraQueryParams.put("uploadId", uploadId);

        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .region(region)
                .object(objectName)
                .method(Method.PUT)
                .extraQueryParams(extraQueryParams)
                .expiry(1, TimeUnit.HOURS)
                .build();
        return presignedService.getPresignedObjectUrl(args);
    }

    /**
     * 第三步：获取指定 uploadId 下所有的分片文件
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param uploadId   第一步中创建的 UploadId
     * @return uploadId 对应的所有分片
     */
    private Part[] listParts(String bucketName, String region, String objectName, String uploadId) {
        ListPartsResponse response = multipartUploadService.listParts(bucketName, region, objectName, uploadId);
        List<Part> partList = response.result().partList();
        Part[] parts = new Part[partList.size()];
        return partList.toArray(parts);
    }


    /**
     * 创建大文件分片上传
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param totalParts 分片总数
     * @return {@link MultipartCreateEntity}
     */
    private MultipartCreateEntity createMultipartUpload(String bucketName, String region, String objectName, int totalParts) {
        String uploadId = createUploadId(bucketName, region, objectName);
        MultipartCreateEntity entity = new MultipartCreateEntity(uploadId);

        for (int i = 0; i < totalParts; i++) {
            String uploadUrl = createPresignedObjectUrl(bucketName, region, objectName, uploadId, i);
            entity.appendChunk(converter.toServiceUrl(uploadUrl));
        }
        return entity;
    }

    /**
     * 创建大文件分片上传
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param totalParts 分片总数
     * @return {@link MultipartCreateEntity}
     */
    public MultipartCreateEntity createMultipartUpload(String bucketName, String objectName, int totalParts) {
        return createMultipartUpload(bucketName, null, objectName, totalParts);
    }

    /**
     * 合并已经上传完成的分片
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param uploadId   第一步中创建的 UploadId
     * @return {@link ObjectWriteEntity}
     */
    private ObjectWriteEntity completeMultipartUpload(String bucketName, String region, String objectName, String uploadId) {
        Part[] parts = listParts(bucketName, region, objectName, uploadId);
        if (ArrayUtils.isNotEmpty(parts)) {
            ObjectWriteResponse response = multipartUploadService.completeMultipartUpload(bucketName, region, objectName, uploadId, parts);
            Converter<ObjectWriteResponse, ObjectWriteEntity> toEntity = new ObjectWriteResponseToEntityConverter();
            if (ObjectUtils.isNotEmpty(response)) {
                return toEntity.convert(response);
            }
        }

        return null;
    }

    /**
     * 合并已经上传完成的分片
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param uploadId   第一步中创建的 UploadId
     * @return {@link ObjectWriteEntity}
     */
    public ObjectWriteEntity completeMultipartUpload(String bucketName, String objectName, String uploadId) {
        return completeMultipartUpload(bucketName, null, objectName, uploadId);
    }
}
