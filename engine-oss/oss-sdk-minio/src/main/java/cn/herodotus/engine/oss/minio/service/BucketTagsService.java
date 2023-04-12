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
import io.minio.DeleteBucketTagsArgs;
import io.minio.GetBucketTagsArgs;
import io.minio.MinioClient;
import io.minio.SetBucketTagsArgs;
import io.minio.errors.*;
import io.minio.messages.Tags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * <p>Description: Bucket 标签服务 </p>
 * 当为桶添加标签时，该桶上所有请求产生的计费话单里都会带上这些标签，从而可以针对话单报表做分类筛选，进行更详细的成本分析。例如：某个应用程序在运行过程会往桶里上传数据，我们可以用应用名称作为标签，设置到被使用的桶上。在分析话单时，就可以通过应用名称的标签来分析此应用的成本
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 15:58
 */
@Service
public class BucketTagsService extends BaseMinioService {

    private static final Logger log = LoggerFactory.getLogger(BucketPolicyService.class);

    /**
     * 设置 Bucket 标签
     *
     * @param bucketName bucketName
     * @param tags       {@link Tags}
     */
    public void setBucketTags(String bucketName, Tags tags) {
        setBucketTags(SetBucketTagsArgs.builder().bucket(bucketName).tags(tags).build());
    }

    /**
     * 设置 Bucket 标签
     *
     * @param bucketName bucketName
     * @param region     region
     * @param tags       tags
     */
    public void setBucketTags(String bucketName, String region, Tags tags) {
        setBucketTags(SetBucketTagsArgs.builder().bucket(bucketName).region(region).tags(tags).build());
    }

    /**
     * 设置 Bucket 标签
     *
     * @param bucketName bucketName
     * @param tags       {@link Map}
     */
    public void setBucketTags(String bucketName, Map<String, String> tags) {
        setBucketTags(SetBucketTagsArgs.builder().bucket(bucketName).tags(tags).build());
    }

    /**
     * 设置 Bucket 标签
     *
     * @param bucketName bucketName
     * @param region     region
     * @param tags       {@link Map}
     */
    public void setBucketTags(String bucketName, String region, Map<String, String> tags) {
        setBucketTags(SetBucketTagsArgs.builder().bucket(bucketName).region(region).tags(tags).build());
    }

    /**
     * 设置 Bucket 标签
     *
     * @param setBucketTagsArgs {@link SetBucketTagsArgs}
     */
    public void setBucketTags(SetBucketTagsArgs setBucketTagsArgs) {
        String function = "setBucketTags";
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.setBucketTags(setBucketTagsArgs);
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
     * 获取 Bucket 标签配置
     *
     * @param bucketName bucketName
     * @return {@link Tags}
     */
    public Tags getBucketTags(String bucketName) {
        return getBucketTags(GetBucketTagsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 获取 Bucket 标签配置
     *
     * @param bucketName bucketName
     * @param region     region
     * @return {@link Tags}
     */
    public Tags getBucketTags(String bucketName, String region) {
        return getBucketTags(GetBucketTagsArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 获取 Bucket 标签配置
     *
     * @param getBucketTagsArgs {@link GetBucketTagsArgs}
     */
    public Tags getBucketTags(GetBucketTagsArgs getBucketTagsArgs) {
        String function = "getBucketTags";
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.getBucketTags(getBucketTagsArgs);
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
     * 删除 Bucket 标签
     *
     * @param bucketName bucketName
     */
    public void deleteBucketTags(String bucketName) {
        deleteBucketTags(DeleteBucketTagsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 删除 Bucket 标签
     *
     * @param bucketName bucketName
     * @param region     region
     */
    public void deleteBucketTags(String bucketName, String region) {
        deleteBucketTags(DeleteBucketTagsArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 删除 Bucket 标签
     *
     * @param deleteBucketTagsArgs {@link DeleteBucketTagsArgs}
     */
    public void deleteBucketTags(DeleteBucketTagsArgs deleteBucketTagsArgs) {
        String function = "deleteBucketTags";
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.deleteBucketTags(deleteBucketTagsArgs);
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
