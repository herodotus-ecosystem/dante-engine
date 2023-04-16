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
import io.minio.DeleteObjectTagsArgs;
import io.minio.GetObjectTagsArgs;
import io.minio.MinioClient;
import io.minio.SetObjectTagsArgs;
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
 * <p>Description: Minio 对象标签服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 21:10
 */
@Service
public class ObjectTagsService extends BaseMinioService {

    private static final Logger log = LoggerFactory.getLogger(ObjectTagsService.class);

    public ObjectTagsService(MinioClientObjectPool minioClientObjectPool) {
        super(minioClientObjectPool);
    }

    /**
     * 为对象设置标签
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param tags       {@link Map}
     */
    public void setObjectTags(String bucketName, String objectName, Map<String, String> tags) {
        setObjectTags(SetObjectTagsArgs.builder().bucket(bucketName).object(objectName).tags(tags).build());
    }

    /**
     * 为对象设置标签
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param versionId  versionId
     * @param tags       {@link Map}
     */
    public void setObjectTags(String bucketName, String objectName, String versionId, Map<String, String> tags) {
        setObjectTags(SetObjectTagsArgs.builder().bucket(bucketName).object(objectName).versionId(versionId).tags(tags).build());
    }

    /**
     * 为对象设置标签
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param region     region
     * @param versionId  versionId
     * @param tags       {@link Map}
     */
    public void setObjectTags(String bucketName, String objectName, String region, String versionId, Map<String, String> tags) {
        setObjectTags(SetObjectTagsArgs.builder().bucket(bucketName).object(objectName).region(region).versionId(versionId).tags(tags).build());
    }

    /**
     * 为对象设置标签
     *
     * @param setObjectTagsArgs {@link SetObjectTagsArgs}
     */
    public void setObjectTags(SetObjectTagsArgs setObjectTagsArgs) {
        String function = "setObjectTags";
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.setObjectTags(setObjectTagsArgs);
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
     * 获取对象的标签
     *
     * @param bucketName bucketName
     * @param objectName objectName
     */
    public Tags getObjectTags(String bucketName, String objectName) {
        return getObjectTags(GetObjectTagsArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 获取对象的标签
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param versionId  versionId
     */
    public Tags getObjectTags(String bucketName, String objectName, String versionId) {
        return getObjectTags(GetObjectTagsArgs.builder().bucket(bucketName).object(objectName).versionId(versionId).build());
    }

    /**
     * 获取对象的标签
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param region     region
     * @param versionId  versionId
     */
    public Tags getObjectTags(String bucketName, String objectName, String region, String versionId) {
        return getObjectTags(GetObjectTagsArgs.builder().bucket(bucketName).object(objectName).region(region).versionId(versionId).build());
    }

    /**
     * 获取对象的标签。
     *
     * @param getObjectTagsArgs {@link GetObjectTagsArgs}
     */
    public Tags getObjectTags(GetObjectTagsArgs getObjectTagsArgs) {
        String function = "getObjectTags";
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.getObjectTags(getObjectTagsArgs);
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
     * 删除对象设置标签
     *
     * @param bucketName bucketName
     * @param objectName objectName
     */
    public void deleteObjectTags(String bucketName, String objectName) {
        deleteObjectTags(DeleteObjectTagsArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 删除对象设置标签
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param versionId  versionId
     */
    public void deleteObjectTags(String bucketName, String objectName, String versionId) {
        deleteObjectTags(DeleteObjectTagsArgs.builder().bucket(bucketName).object(objectName).versionId(versionId).build());
    }

    /**
     * 删除对象设置标签
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param region     region
     * @param versionId  versionId
     */
    public void deleteObjectTags(String bucketName, String objectName, String region, String versionId) {
        deleteObjectTags(DeleteObjectTagsArgs.builder().bucket(bucketName).object(objectName).region(region).versionId(versionId).build());
    }

    /**
     * 删除对象标签
     *
     * @param deleteObjectTagsArgs {@link DeleteObjectTagsArgs}
     */
    public void deleteObjectTags(DeleteObjectTagsArgs deleteObjectTagsArgs) {
        String function = "deleteObjectTags";
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.deleteObjectTags(deleteObjectTagsArgs);
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
