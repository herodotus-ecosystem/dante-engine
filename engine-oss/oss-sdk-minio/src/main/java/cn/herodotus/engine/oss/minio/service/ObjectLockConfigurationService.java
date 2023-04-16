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

package cn.herodotus.engine.oss.minio.service;

import cn.herodotus.engine.oss.core.exception.*;
import cn.herodotus.engine.oss.minio.definition.pool.MinioClientObjectPool;
import cn.herodotus.engine.oss.minio.definition.service.BaseMinioService;
import io.minio.DeleteObjectLockConfigurationArgs;
import io.minio.GetObjectLockConfigurationArgs;
import io.minio.MinioClient;
import io.minio.SetObjectLockConfigurationArgs;
import io.minio.errors.*;
import io.minio.messages.ObjectLockConfiguration;
import io.minio.messages.RetentionDuration;
import io.minio.messages.RetentionMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>Description: Minio 对象锁定配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 16:04
 */
@Service
public class ObjectLockConfigurationService extends BaseMinioService {
    private static final Logger log = LoggerFactory.getLogger(ObjectLockConfigurationService.class);

    public ObjectLockConfigurationService(MinioClientObjectPool minioClientObjectPool) {
        super(minioClientObjectPool);
    }

    /**
     * 设置对象锁定
     *
     * @param bucketName        bucketName
     * @param retentionMode     {@link RetentionMode}
     * @param retentionDuration {@link RetentionDuration}
     */
    public void setObjectLockConfiguration(String bucketName, RetentionMode retentionMode, RetentionDuration retentionDuration) {
        setObjectLockConfiguration(bucketName, new ObjectLockConfiguration(retentionMode, retentionDuration));
    }

    /**
     * 设置对象锁定
     *
     * @param bucketName              bucketName
     * @param objectLockConfiguration {@link ObjectLockConfiguration}
     */
    public void setObjectLockConfiguration(String bucketName, ObjectLockConfiguration objectLockConfiguration) {
        setObjectLockConfiguration(SetObjectLockConfigurationArgs.builder().bucket(bucketName).config(objectLockConfiguration).build());
    }

    /**
     * 设置对象锁定
     *
     * @param bucketName        bucketName
     * @param region            region
     * @param retentionMode     {@link RetentionMode}
     * @param retentionDuration {@link RetentionDuration}
     */
    public void setObjectLockConfiguration(String bucketName, String region, RetentionMode retentionMode, RetentionDuration retentionDuration) {
        setObjectLockConfiguration(bucketName, region, new ObjectLockConfiguration(retentionMode, retentionDuration));
    }

    /**
     * 设置对象锁定
     *
     * @param bucketName              bucketName
     * @param region                  region
     * @param objectLockConfiguration {@link ObjectLockConfiguration}
     */
    public void setObjectLockConfiguration(String bucketName, String region, ObjectLockConfiguration objectLockConfiguration) {
        setObjectLockConfiguration(SetObjectLockConfigurationArgs.builder().bucket(bucketName).region(region).config(objectLockConfiguration).build());
    }

    /**
     * 设置对象锁定
     *
     * @param setObjectLockConfigurationArgs {@link SetObjectLockConfigurationArgs}
     */
    public void setObjectLockConfiguration(SetObjectLockConfigurationArgs setObjectLockConfigurationArgs) {
        String function = "setObjectLockConfiguration";
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.setObjectLockConfiguration(setObjectLockConfigurationArgs);
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new OssErrorResponseException("Minio response error.");
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new OssInsufficientDataException("Minio insufficient data error.");
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", function, e);
            throw new OssInternalException("Minio internal error.");
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new OssInvalidKeyException("Minio key invalid.");
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new OssInvalidResponseException("Minio response invalid.");
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            throw new OssIOException("Minio io error.");
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssNoSuchAlgorithmException("Minio no such algorithm.");
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio catch ServerException in [{}].", function, e);
            throw new OssServerException("Minio server error.");
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException in [{}].", function, e);
            throw new OssXmlParserException("Minio xml parser error.");
        } finally {
            close(minioClient);
        }
    }

    /**
     * 获取对象锁定配置
     *
     * @param bucketName bucketName
     * @return {@link ObjectLockConfiguration}
     */
    public ObjectLockConfiguration getObjectLockConfiguration(String bucketName) {
        return getObjectLockConfiguration(GetObjectLockConfigurationArgs.builder().bucket(bucketName).build());
    }

    /**
     * 获取对象锁定配置
     *
     * @param bucketName bucketName
     * @param region     region
     * @return {@link ObjectLockConfiguration}
     */
    public ObjectLockConfiguration getObjectLockConfiguration(String bucketName, String region) {
        return getObjectLockConfiguration(GetObjectLockConfigurationArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 获取对象锁定配置
     *
     * @param getObjectLockConfigurationArgs {@link GetObjectLockConfigurationArgs}
     * @return {@link ObjectLockConfiguration}
     */
    public ObjectLockConfiguration getObjectLockConfiguration(GetObjectLockConfigurationArgs getObjectLockConfigurationArgs) {
        String function = "getObjectLockConfiguration";
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.getObjectLockConfiguration(getObjectLockConfigurationArgs);
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new OssErrorResponseException("Minio response error.");
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new OssInsufficientDataException("Minio insufficient data error.");
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", function, e);
            throw new OssInternalException("Minio internal error.");
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new OssInvalidKeyException("Minio key invalid.");
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new OssInvalidResponseException("Minio response invalid.");
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            throw new OssIOException("Minio io error.");
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssNoSuchAlgorithmException("Minio no such algorithm.");
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio catch ServerException in [{}].", function, e);
            throw new OssServerException("Minio server error.");
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException in createBucket.", e);
            throw new OssXmlParserException("Minio xml parser error.");
        } finally {
            close(minioClient);
        }
    }

    /**
     * 删除对象锁定
     *
     * @param bucketName bucketName
     */
    public void deleteObjectLockConfiguration(String bucketName) {
        deleteObjectLockConfiguration(DeleteObjectLockConfigurationArgs.builder().bucket(bucketName).build());
    }

    /**
     * 删除对象锁定
     *
     * @param bucketName bucketName
     * @param region     region
     */
    public void deleteObjectLockConfiguration(String bucketName, String region) {
        deleteObjectLockConfiguration(DeleteObjectLockConfigurationArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 删除对象锁定
     *
     * @param deleteObjectLockConfigurationArgs {@link DeleteObjectLockConfigurationArgs}
     */
    public void deleteObjectLockConfiguration(DeleteObjectLockConfigurationArgs deleteObjectLockConfigurationArgs) {
        String function = "deleteObjectLockConfiguration";
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.deleteObjectLockConfiguration(deleteObjectLockConfigurationArgs);
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new OssErrorResponseException("Minio response error.");
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new OssInsufficientDataException("Minio insufficient data error.");
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", function, e);
            throw new OssInternalException("Minio internal error.");
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new OssInvalidKeyException("Minio key invalid.");
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new OssInvalidResponseException("Minio response invalid.");
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            throw new OssIOException("Minio io error.");
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssNoSuchAlgorithmException("Minio no such algorithm.");
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio catch ServerException in [{}].", function, e);
            throw new OssServerException("Minio server error.");
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException in [{}].", function, e);
            throw new OssXmlParserException("Minio xml parser error.");
        } finally {
            close(minioClient);
        }
    }
}
