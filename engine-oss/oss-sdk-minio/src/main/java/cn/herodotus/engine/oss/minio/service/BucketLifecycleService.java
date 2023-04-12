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
import io.minio.DeleteBucketLifecycleArgs;
import io.minio.GetBucketLifecycleArgs;
import io.minio.MinioClient;
import io.minio.SetBucketLifecycleArgs;
import io.minio.errors.*;
import io.minio.messages.LifecycleConfiguration;
import io.minio.messages.LifecycleRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * <p>Description: Bucket 生命周期配置服务 </p>
 * <p>
 * 生命周期管理可适用于以下典型场景：
 * · 周期性上传的日志文件，可能只需要保留一个星期或一个月。到期后要删除它们。
 * · 某些文档在一段时间内经常访问，但是超过一定时间后便可能不再访问了。这些文档需要在一定时间后转化为低频访问存储，归档存储或者删除
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 15:39
 */
@Service
public class BucketLifecycleService extends BaseMinioService {

    private static final Logger log = LoggerFactory.getLogger(BucketLifecycleService.class);

    /**
     * 设置 Bucket 生命周期配置
     *
     * @param bucketName     bucketName
     * @param lifecycleRules {@link LifecycleRule}
     */
    public void setBucketLifecycle(String bucketName, List<LifecycleRule> lifecycleRules) {
        setBucketLifecycle(bucketName, new LifecycleConfiguration(lifecycleRules));
    }

    /**
     * 置 Bucket 生命周期配置
     *
     * @param bucketName     bucketName
     * @param region         region
     * @param lifecycleRules {@link LifecycleRule}
     */
    public void setBucketLifecycle(String bucketName, String region, List<LifecycleRule> lifecycleRules) {
        setBucketLifecycle(bucketName, region, new LifecycleConfiguration(lifecycleRules));
    }

    /**
     * 设置 Bucket 生命周期
     *
     * @param bucketName             bucketName
     * @param lifecycleConfiguration {@link LifecycleConfiguration}
     */
    public void setBucketLifecycle(String bucketName, LifecycleConfiguration lifecycleConfiguration) {
        setBucketLifecycle(SetBucketLifecycleArgs.builder().bucket(bucketName).config(lifecycleConfiguration).build());
    }

    /**
     * 设置 Bucket 生命周期
     *
     * @param bucketName             bucketName
     * @param region                 region
     * @param lifecycleConfiguration @link LifecycleConfiguration}
     */
    public void setBucketLifecycle(String bucketName, String region, LifecycleConfiguration lifecycleConfiguration) {
        setBucketLifecycle(SetBucketLifecycleArgs.builder().bucket(bucketName).region(region).config(lifecycleConfiguration).build());
    }

    /**
     * 设置 Bucket 生命周期
     *
     * @param setBucketLifecycleArgs {@link SetBucketLifecycleArgs}
     */
    public void setBucketLifecycle(SetBucketLifecycleArgs setBucketLifecycleArgs) {
        String function = "setBucketLifecycle";
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.setBucketLifecycle(setBucketLifecycleArgs);
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
            log.error("[Herodotus] |- Minio catch XmlParserException in in [{}].", function, e);
            throw new OssXmlParserException("Minio xml parser error.");
        } finally {
            close(minioClient);
        }
    }

    /**
     * 获取 Bucket 生命周期配置
     *
     * @param bucketName bucketName
     * @return {@link LifecycleConfiguration}
     */
    public LifecycleConfiguration getBucketLifecycle(String bucketName) {
        return getBucketLifecycle(GetBucketLifecycleArgs.builder().bucket(bucketName).build());
    }

    /**
     * 获取 Bucket 生命周期配置
     *
     * @param bucketName bucketName
     * @param region     region
     * @return {@link LifecycleConfiguration}
     */
    public LifecycleConfiguration getBucketLifecycle(String bucketName, String region) {
        return getBucketLifecycle(GetBucketLifecycleArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 获取 Bucket 生命周期配置
     *
     * @param getBucketLifecycleArgs {@link GetBucketLifecycleArgs}
     */
    public LifecycleConfiguration getBucketLifecycle(GetBucketLifecycleArgs getBucketLifecycleArgs) {
        String function = "getBucketLifecycle";
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.getBucketLifecycle(getBucketLifecycleArgs);
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
     * 删除 Bucket 生命周期配置
     *
     * @param bucketName bucketName
     */
    public void deleteBucketLifecycle(String bucketName) {
        deleteBucketLifecycle(DeleteBucketLifecycleArgs.builder().bucket(bucketName).build());
    }

    /**
     * 删除 Bucket 生命周期配置
     *
     * @param bucketName bucketName
     * @param region     region
     */
    public void deleteBucketLifecycle(String bucketName, String region) {
        deleteBucketLifecycle(DeleteBucketLifecycleArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 删除 Bucket 生命周期配置
     *
     * @param deleteBucketLifecycleArgs {@link DeleteBucketLifecycleArgs}
     */
    public void deleteBucketLifecycle(DeleteBucketLifecycleArgs deleteBucketLifecycleArgs) {
        String function = "deleteBucketLifecycle";
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.deleteBucketLifecycle(deleteBucketLifecycleArgs);
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
}
