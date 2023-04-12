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
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.DeleteError;
import io.minio.messages.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: Minio 对象操作服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 20:06
 */
@Service
public class ObjectService extends BaseMinioService {

    private static final Logger log = LoggerFactory.getLogger(ObjectService.class);

    /**
     * 上传文件
     * <p>
     * · 添加的Object大小不能超过5 GB。
     * · 默认情况下，如果已存在同名Object且对该Object有访问权限，则新添加的Object将覆盖原有的Object，并返回200 OK。
     * · OSS没有文件夹的概念，所有资源都是以文件来存储，但您可以通过创建一个以正斜线（/）结尾，大小为0的Object来创建模拟文件夹。
     *
     * @param putObjectArgs {@link PutObjectArgs}
     * @return {@link ObjectWriteResponse}
     */
    public ObjectWriteResponse putObject(PutObjectArgs putObjectArgs) {
        String function = "putObject";
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.putObject(putObjectArgs);
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
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @return {@link GetObjectResponse}
     */
    public GetObjectResponse getObject(String bucketName, String objectName) {
        return getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param versionId  versionId
     * @return {@link GetObjectResponse}
     */
    public GetObjectResponse getObject(String bucketName, String objectName, String versionId) {
        return getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).versionId(versionId).build());
    }

    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param region     region
     * @param versionId  versionId
     * @return {@link GetObjectResponse}
     */
    public GetObjectResponse getObject(String bucketName, String objectName, String region, String versionId) {
        return getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).region(region).versionId(versionId).build());
    }

    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     * <p>
     * 获取对象的数据。InputStream使用后返回必须关闭以释放网络资源。
     *
     * @param getObjectArgs {@link GetObjectArgs}
     * @return {@link GetObjectResponse}
     */
    public GetObjectResponse getObject(GetObjectArgs getObjectArgs) {
        String function = "getObject";
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.getObject(getObjectArgs);
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
     * 将文件中的内容作为存储桶中的对象上传
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param filename   filename
     */
    public ObjectWriteResponse uploadObject(String bucketName, String objectName, String filename) {
        try {
            return uploadObject(UploadObjectArgs.builder().bucket(bucketName).object(objectName).filename(filename).build());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [uploadObject].", e);
            throw new OssIOException("Minio uploadObject io error.");
        }
    }

    /**
     * 将文件中的内容作为存储桶中的对象上传
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param region     region
     * @param filename   filename
     */
    public ObjectWriteResponse uploadObject(String bucketName, String objectName, String region, String filename) {
        try {
            return uploadObject(UploadObjectArgs.builder().bucket(bucketName).object(objectName).region(region).filename(filename).build());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [uploadObject].", e);
            throw new OssIOException("Minio uploadObject io error.");
        }
    }

    /**
     * 将文件中的内容作为存储桶中的对象上传
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param filename   filename
     * @param partSize   partSize
     */
    public ObjectWriteResponse uploadObject(String bucketName, String objectName, String filename, long partSize) {
        try {
            return uploadObject(UploadObjectArgs.builder().bucket(bucketName).object(objectName).filename(filename, partSize).build());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [uploadObject].", e);
            throw new OssIOException("Minio uploadObject io error.");
        }
    }

    /**
     * 将文件中的内容作为存储桶中的对象上传
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param region     region
     * @param filename   filename
     * @param partSize   partSize
     */
    public ObjectWriteResponse uploadObject(String bucketName, String objectName, String region, String filename, long partSize) {
        try {
            return uploadObject(UploadObjectArgs.builder().bucket(bucketName).object(objectName).region(region).filename(filename, partSize).build());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [uploadObject].", e);
            throw new OssIOException("Minio uploadObject io error.");
        }
    }

    /**
     * 将文件中的内容作为存储桶中的对象上传
     *
     * @param uploadObjectArgs {@link UploadObjectArgs}
     * @return {@link ObjectWriteResponse}
     */
    public ObjectWriteResponse uploadObject(UploadObjectArgs uploadObjectArgs) {
        String function = "uploadObject";
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.uploadObject(uploadObjectArgs);
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
     * 将对象的数据下载到文件。
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param filename   filename
     */
    public void downloadObject(String bucketName, String objectName, String filename) {
        downloadObject(DownloadObjectArgs.builder().bucket(bucketName).object(objectName).filename(filename).build());
    }

    /**
     * 将对象的数据下载到文件。
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param filename   filename
     * @param overwrite  overwrite
     */
    public void downloadObject(String bucketName, String objectName, String filename, Boolean overwrite) {
        downloadObject(DownloadObjectArgs.builder().bucket(bucketName).object(objectName).filename(filename).overwrite(overwrite).build());
    }

    /**
     * 将对象的数据下载到文件。
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param versionId  versionId
     * @param filename   filename
     * @param overwrite  overwrite
     */
    public void downloadObject(String bucketName, String objectName, String versionId, String filename, Boolean overwrite) {
        downloadObject(DownloadObjectArgs.builder().bucket(bucketName).object(objectName).versionId(versionId).filename(filename).overwrite(overwrite).build());
    }

    /**
     * 将对象的数据下载到文件。
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param region     region
     * @param versionId  versionId
     * @param filename   filename
     * @param overwrite  overwrite
     */
    public void downloadObject(String bucketName, String objectName, String region, String versionId, String filename, Boolean overwrite) {
        downloadObject(DownloadObjectArgs.builder().bucket(bucketName).object(objectName).region(region).versionId(versionId).filename(filename).overwrite(overwrite).build());
    }

    /**
     * 将对象的数据下载到文件。
     *
     * @param downloadObjectArgs {@link DownloadObjectArgs}
     */
    public void downloadObject(DownloadObjectArgs downloadObjectArgs) {
        String function = "downloadObject";
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.downloadObject(downloadObjectArgs);
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
     * 通过服务器端从另一个对象复制数据来创建一个对象
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param copySource {@link CopySource}
     */
    public void copyObject(String bucketName, String objectName, CopySource copySource) {
        copyObject(CopyObjectArgs.builder().bucket(bucketName).object(objectName).source(copySource).build());
    }

    /**
     * 通过服务器端从另一个对象复制数据来创建一个对象
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param region     region
     * @param copySource {@link CopySource}
     */
    public void copyObject(String bucketName, String objectName, String region, CopySource copySource) {
        copyObject(CopyObjectArgs.builder().bucket(bucketName).object(objectName).region(region).source(copySource).build());
    }

    /**
     * 通过服务器端从另一个对象复制数据来创建一个对象
     *
     * @param copyObjectArgs {@link CopyObjectArgs}
     * @return {@link ObjectWriteResponse}
     */
    public ObjectWriteResponse copyObject(CopyObjectArgs copyObjectArgs) {
        String function = "copyObject";
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.copyObject(copyObjectArgs);
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
     * 移除一个对象
     *
     * @param bucketName bucketName
     * @param objectName objectName
     */
    public void removeObject(String bucketName, String objectName) {
        removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 移除一个对象
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param versionId  versionId
     */
    public void removeObject(String bucketName, String objectName, String versionId) {
        removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).versionId(versionId).build());
    }

    /**
     * 移除一个对象
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param region     region
     * @param versionId  versionId
     */
    public void removeObject(String bucketName, String objectName, String region, String versionId) {
        removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).region(region).versionId(versionId).build());
    }

    /**
     * 移除一个对象
     *
     * @param removeObjectArgs {@link RemoveObjectArgs}
     */
    public void removeObject(RemoveObjectArgs removeObjectArgs) {
        String function = "removeObject";
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.removeObject(removeObjectArgs);
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
     * 懒惰地删除多个对象。它需要迭代返回的 Iterable 以执行删除
     *
     * @param bucketName bucketName
     * @param region     region
     * @return Iterable<Result < DeleteError>>
     */
    public Iterable<Result<DeleteError>> removeObjects(String bucketName, String region) {
        return removeObjects(RemoveObjectsArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 懒惰地删除多个对象。它需要迭代返回的 Iterable 以执行删除
     *
     * @param removeObjectsArgs {@link RemoveObjectsArgs}
     * @return Iterable<Result < DeleteError>>
     */
    public Iterable<Result<DeleteError>> removeObjects(RemoveObjectsArgs removeObjectsArgs) {
        MinioClient minioClient = getMinioClient();
        return minioClient.removeObjects(removeObjectsArgs);
    }

    /**
     * listObjects列出桶的对象信息
     *
     * @param bucketName bucketName
     * @return Iterable<Result < Item>>
     */
    public Iterable<Result<Item>> listObjects(String bucketName) {
        return listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 递归listObjects列出桶的对象信息
     *
     * @param bucketName bucketName
     * @param recursive  是否递归
     * @return Iterable<Result < Item>>
     */
    public Iterable<Result<Item>> listObjects(String bucketName, Boolean recursive) {
        return listObjects(ListObjectsArgs.builder().bucket(bucketName).recursive(recursive).build());
    }

    /**
     * listObjects列出桶的对象信息
     *
     * @param listObjectsArgs {@link ListObjectsArgs}
     * @return Iterable<Result < Item>>
     */
    public Iterable<Result<Item>> listObjects(ListObjectsArgs listObjectsArgs) {
        MinioClient minioClient = getMinioClient();
        return minioClient.listObjects(listObjectsArgs);
    }

    /**
     * 通过使用服务器端副本组合来自不同源对象的数据来创建对象，比如可以将文件分片上传，然后将他们合并为一个文件
     *
     * @param bucketName     bucketName
     * @param objectName     objectName
     * @param composeSources {@link ComposeSource}
     * @return {@link ObjectWriteResponse}
     */
    public ObjectWriteResponse composeObject(String bucketName, String objectName, List<ComposeSource> composeSources) {
        return composeObject(ComposeObjectArgs.builder().bucket(bucketName).object(objectName).sources(composeSources).build());
    }

    /**
     * 通过使用服务器端副本组合来自不同源对象的数据来创建对象，比如可以将文件分片上传，然后将他们合并为一个文件
     *
     * @param bucketName     bucketName
     * @param objectName     objectName
     * @param region         region
     * @param composeSources {@link ComposeSource}
     * @return {@link ObjectWriteResponse}
     */
    public ObjectWriteResponse composeObject(String bucketName, String objectName, String region, List<ComposeSource> composeSources) {
        return composeObject(ComposeObjectArgs.builder().bucket(bucketName).object(objectName).region(region).sources(composeSources).build());
    }

    /**
     * 通过使用服务器端副本组合来自不同源对象的数据来创建对象，比如可以将文件分片上传，然后将他们合并为一个文件
     *
     * @param composeObjectArgs {@link ComposeObjectArgs}
     * @return {@link ObjectWriteResponse}
     */
    public ObjectWriteResponse composeObject(ComposeObjectArgs composeObjectArgs) {
        String function = "composeObject";
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.composeObject(composeObjectArgs);
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
     * 取对象的对象信息和元数据
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @return {@link StatObjectResponse}
     */
    public StatObjectResponse statObject(String bucketName, String objectName) {
        return statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 取对象的对象信息和元数据
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param region     region
     * @return {@link StatObjectResponse}
     */
    public StatObjectResponse statObject(String bucketName, String objectName, String region) {
        return statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).region(region).build());
    }

    /**
     * 获取对象的对象信息和元数据
     *
     * @param statObjectArgs {@link StatObjectArgs}
     * @return {@link StatObjectResponse}
     */
    public StatObjectResponse statObject(StatObjectArgs statObjectArgs) {
        String function = "statObject";
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.statObject(statObjectArgs);
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
     * 获取一个指定了 HTTP 方法、到期时间和自定义请求参数的对象URL地址，也就是返回带签名的URL，这个地址可以提供给没有登录的第三方共享访问或者上传对象。
     *
     * @param getPresignedObjectUrlArgs {@link GetPresignedObjectUrlArgs}
     * @return url string
     */
    public String getPresignedObjectUrl(GetPresignedObjectUrlArgs getPresignedObjectUrlArgs) {
        String function = "getPresignedObjectUrl";
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs);
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
     * 通过 SQL 表达式选择对象的内容
     *
     * @param selectObjectContentArgs {@link SelectObjectContentArgs}
     * @return {@link SelectResponseStream}
     */
    public SelectResponseStream selectObjectContent(SelectObjectContentArgs selectObjectContentArgs) {
        String function = "selectObjectContent";
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.selectObjectContent(selectObjectContentArgs);
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
     * 使用此方法，获取对象的上传策略（包含签名、文件信息、路径等），然后使用这些信息采用POST 方法的表单数据上传数据。也就是可以生成一个临时上传的信息对象，第三方可以使用这些信息，就可以上传文件。
     * <p>
     * 一般可用于，前端请求一个上传策略，后端返回给前端，前端使用Post请求+访问策略去上传文件，这可以用于JS+SDK的混合方式集成
     *
     * @param postPolicy {@link PostPolicy}
     * @return {@link  Map}
     */
    public Map<String, String> getPresignedPostFormData(PostPolicy postPolicy) {
        String function = "getPresignedPostFormData";
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.getPresignedPostFormData(postPolicy);
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
