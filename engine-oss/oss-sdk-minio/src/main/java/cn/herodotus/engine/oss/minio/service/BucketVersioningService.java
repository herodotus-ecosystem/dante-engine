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
import cn.herodotus.engine.oss.minio.definition.service.BaseMinioService;
import io.minio.GetBucketVersioningArgs;
import io.minio.MinioClient;
import io.minio.SetBucketVersioningArgs;
import io.minio.errors.*;
import io.minio.messages.VersioningConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>Description: Bucket 版本控制 </p>
 * <p>
 * 若开启了多版本控制，上传对象时，OBS自动为每个对象创建唯一的版本号。上传同名的对象将以不同的版本号同时保存在OBS中。
 * <p>
 * 若未开启多版本控制，向同一个文件夹中上传同名的对象时，新上传的对象将覆盖原有的对象。
 * <p>
 * 某些功能（例如版本控制、对象锁定和存储桶复制）需要使用擦除编码分布式部署 MinIO。开启了版本控制后，允许在同一密钥下保留同一对象的多个版本。
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 16:01
 */
@Service
public class BucketVersioningService extends BaseMinioService {

    private static final Logger log = LoggerFactory.getLogger(BucketVersioningService.class);

    /**
     * 开启 Bucket 版本控制
     *
     * @param bucketName bucketName
     */
    public void enabledBucketVersioning(String bucketName) {
        setBucketVersioning(bucketName, VersioningConfiguration.Status.ENABLED);
    }

    /**
     * 开启 Bucket 版本控制
     *
     * @param bucketName bucketName
     * @param region     region
     */
    public void enabledBucketVersioning(String bucketName, String region) {
        setBucketVersioning(bucketName, region, VersioningConfiguration.Status.ENABLED);
    }

    /**
     * 暂停 Bucket 版本控制
     *
     * @param bucketName bucketName
     */
    public void suspendedBucketVersioning(String bucketName) {
        setBucketVersioning(bucketName, VersioningConfiguration.Status.SUSPENDED);
    }

    /**
     * 暂停 Bucket 版本控制
     *
     * @param bucketName bucketName
     * @param region     region
     */
    public void suspendedBucketVersioning(String bucketName, String region) {
        setBucketVersioning(bucketName, region, VersioningConfiguration.Status.SUSPENDED);
    }

    /**
     * 关闭 Bucket 版本控制
     *
     * @param bucketName bucketName
     */
    public void offBucketVersioning(String bucketName) {
        setBucketVersioning(bucketName, VersioningConfiguration.Status.OFF);
    }

    /**
     * 关闭 Bucket 版本控制
     *
     * @param bucketName bucketName
     * @param region     region
     */
    public void offBucketVersioning(String bucketName, String region) {
        setBucketVersioning(bucketName, region, VersioningConfiguration.Status.OFF);
    }


    /**
     * 设置 Bucket 版本控制
     *
     * @param bucketName bucketName
     * @param status     {@link  VersioningConfiguration.Status}
     */
    public void setBucketVersioning(String bucketName, VersioningConfiguration.Status status) {
        setBucketVersioning(bucketName, status, null);
    }

    /**
     * 设置 Bucket 版本控制
     *
     * @param bucketName bucketName
     * @param status     {@link  VersioningConfiguration.Status}
     * @param mfaDelete  mfaDelete
     */
    public void setBucketVersioning(String bucketName, VersioningConfiguration.Status status, Boolean mfaDelete) {
        setBucketVersioning(bucketName, new VersioningConfiguration(status, mfaDelete));
    }

    /**
     * 设置 Bucket 版本控制
     *
     * @param bucketName              bucketName
     * @param versioningConfiguration {@link VersioningConfiguration}
     */
    public void setBucketVersioning(String bucketName, VersioningConfiguration versioningConfiguration) {
        setBucketVersioning(SetBucketVersioningArgs.builder().bucket(bucketName).config(versioningConfiguration).build());
    }

    /**
     * 设置 Bucket 版本控制
     *
     * @param bucketName bucketName
     * @param region     region
     * @param status     {@link  VersioningConfiguration.Status}
     */
    public void setBucketVersioning(String bucketName, String region, VersioningConfiguration.Status status) {
        setBucketVersioning(bucketName, region, status, null);
    }

    /**
     * 设置 Bucket 版本控制
     *
     * @param bucketName bucketName
     * @param region     region
     * @param status     {@link  VersioningConfiguration.Status}
     * @param mfaDelete  mfaDelete
     */
    public void setBucketVersioning(String bucketName, String region, VersioningConfiguration.Status status, Boolean mfaDelete) {
        setBucketVersioning(bucketName, region, new VersioningConfiguration(status, mfaDelete));
    }

    /**
     * 设置 Bucket 版本控制
     *
     * @param bucketName              bucketName
     * @param region                  region
     * @param versioningConfiguration {@link VersioningConfiguration}
     */
    public void setBucketVersioning(String bucketName, String region, VersioningConfiguration versioningConfiguration) {
        setBucketVersioning(SetBucketVersioningArgs.builder().bucket(bucketName).region(region).config(versioningConfiguration).build());
    }

    /**
     * 设置 Bucket 版本控制
     *
     * @param setBucketVersioningArgs {@link SetBucketVersioningArgs}
     */
    public void setBucketVersioning(SetBucketVersioningArgs setBucketVersioningArgs) {
        String function = "setBucketVersioning";
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.setBucketVersioning(setBucketVersioningArgs);
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
     * 获取 Bucket 版本配置
     *
     * @param bucketName bucketName
     * @return {@link VersioningConfiguration}
     */
    public VersioningConfiguration getBucketVersioning(String bucketName) {
        return getBucketVersioning(GetBucketVersioningArgs.builder().bucket(bucketName).build());
    }

    /**
     * 获取 Bucket 版本配置
     *
     * @param bucketName bucketName
     * @param region     region
     * @return {@link VersioningConfiguration}
     */
    public VersioningConfiguration getBucketVersioning(String bucketName, String region) {
        return getBucketVersioning(GetBucketVersioningArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 获取 Bucket 版本配置
     *
     * @param getBucketVersioningArgs {@link GetBucketVersioningArgs}
     * @return {@link VersioningConfiguration}
     */
    public VersioningConfiguration getBucketVersioning(GetBucketVersioningArgs getBucketVersioningArgs) {
        String function = "getBucketVersioning";
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.getBucketVersioning(getBucketVersioningArgs);
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
            log.error("[Herodotus] |- Minio catch XmlParserException [{}].", function, e);
            throw new OssXmlParserException("Minio xml parser error.");
        } finally {
            close(minioClient);
        }
    }
}
