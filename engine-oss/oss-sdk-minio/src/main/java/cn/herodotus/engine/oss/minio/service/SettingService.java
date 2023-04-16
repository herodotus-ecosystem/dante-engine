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
import io.minio.DisableObjectLegalHoldArgs;
import io.minio.EnableObjectLegalHoldArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>Description: 设置相关操作 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/4/16 16:10
 */
@Service
public class SettingService extends BaseMinioService {

    private static final Logger log = LoggerFactory.getLogger(SettingService.class);
    public SettingService(MinioClientObjectPool minioClientObjectPool) {
        super(minioClientObjectPool);
    }

    /**
     * Disables accelerate endpoint for Amazon S3 endpoint.
     */
    public void disableAccelerateEndpoint() {
        MinioClient minioClient = getMinioClient();
        minioClient.disableAccelerateEndpoint();
    }

    /**
     * Enables accelerate endpoint for Amazon S3 endpoint.
     */
    public void enableAccelerateEndpoint() {
        MinioClient minioClient = getMinioClient();
        minioClient.enableAccelerateEndpoint();
    }

    /**
     * Disables dual-stack endpoint for Amazon S3 endpoint.
     */
    public void disableDualStackEndpoint() {
        MinioClient minioClient = getMinioClient();
        minioClient.disableDualStackEndpoint();
    }

    /**
     * Enables dual-stack endpoint for Amazon S3 endpoint.
     */
    public void enableDualStackEndpoint() {
        MinioClient minioClient = getMinioClient();
        minioClient.enableDualStackEndpoint();
    }

    /**
     * Disables virtual-style endpoint
     */
    public void disableVirtualStyleEndpoint() {
        MinioClient minioClient = getMinioClient();
        minioClient.disableVirtualStyleEndpoint();
    }

    /**
     * Enables virtual-style endpoint.
     */
    public void enableVirtualStyleEndpoint() {
        MinioClient minioClient = getMinioClient();
        minioClient.enableVirtualStyleEndpoint();
    }

    /**
     * Sets HTTP connect, write and read timeouts. A value of 0 means no timeout, otherwise values
     * must be between 1 and Integer.MAX_VALUE when converted to milliseconds.
     *
     * <pre>Example:{@code
     * minioClient.setTimeout(TimeUnit.SECONDS.toMillis(10), TimeUnit.SECONDS.toMillis(10),
     *     TimeUnit.SECONDS.toMillis(30));
     * }</pre>
     *
     * @param connectTimeout HTTP connect timeout in milliseconds.
     * @param writeTimeout HTTP write timeout in milliseconds.
     * @param readTimeout HTTP read timeout in milliseconds.
     */
    public void setTimeout(long connectTimeout, long writeTimeout, long readTimeout) {
        MinioClient minioClient = getMinioClient();
        minioClient.setTimeout(connectTimeout, writeTimeout, readTimeout);
    }

    /**
     * Sets application's name/version to user agent. For more information about user agent refer <a
     * href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html">#rfc2616</a>.
     *
     * @param name Your application name.
     * @param version Your application version.
     */
    public void setAppInfo(String name, String version) {
        MinioClient minioClient = getMinioClient();
        minioClient.setAppInfo(name, version);
    }

    /**
     * 启用对对象的合法保留
     *
     * @param bucketName bucketName
     * @param objectName objectName
     */
    public void enableObjectLegalHold(String bucketName, String objectName) {
        enableObjectLegalHold(EnableObjectLegalHoldArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 启用对对象的合法保留
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param versionId  versionId
     */
    public void enableObjectLegalHold(String bucketName, String objectName, String versionId) {
        enableObjectLegalHold(EnableObjectLegalHoldArgs.builder().bucket(bucketName).object(objectName).versionId(versionId).build());
    }

    /**
     * 启用对对象的合法保留
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param region     region
     * @param versionId  versionId
     */
    public void enableObjectLegalHold(String bucketName, String objectName, String region, String versionId) {
        enableObjectLegalHold(EnableObjectLegalHoldArgs.builder().bucket(bucketName).object(objectName).region(region).versionId(versionId).build());
    }

    /**
     * 启用对对象的合法保留
     *
     * @param enableObjectLegalHoldArgs {@link EnableObjectLegalHoldArgs}
     */
    public void enableObjectLegalHold(EnableObjectLegalHoldArgs enableObjectLegalHoldArgs) {
        String function = "enableObjectLegalHold";
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.enableObjectLegalHold(enableObjectLegalHoldArgs);
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
     * 禁用对对象的合法保留。
     *
     * @param bucketName bucketName
     * @param objectName objectName
     */
    public void disableObjectLegalHold(String bucketName, String objectName) {
        disableObjectLegalHold(DisableObjectLegalHoldArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 禁用对对象的合法保留。
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param versionId  versionId
     */
    public void disableObjectLegalHold(String bucketName, String objectName, String versionId) {
        disableObjectLegalHold(DisableObjectLegalHoldArgs.builder().bucket(bucketName).object(objectName).versionId(versionId).build());
    }

    /**
     * 禁用对对象的合法保留。
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param region     region
     * @param versionId  versionId
     */
    public void disableObjectLegalHold(String bucketName, String objectName, String region, String versionId) {
        disableObjectLegalHold(DisableObjectLegalHoldArgs.builder().bucket(bucketName).object(objectName).region(region).versionId(versionId).build());
    }

    /**
     * 禁用对对象的合法保留。
     *
     * @param disableObjectLegalHoldArgs {@link DisableObjectLegalHoldArgs}
     */
    public void disableObjectLegalHold(DisableObjectLegalHoldArgs disableObjectLegalHoldArgs) {
        String function = "disableObjectLegalHold";
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.disableObjectLegalHold(disableObjectLegalHoldArgs);
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
