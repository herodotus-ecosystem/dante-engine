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

package cn.herodotus.engine.oss.minio.core;


import cn.herodotus.engine.oss.core.exception.OssClientPoolErrorException;
import cn.herodotus.engine.oss.minio.properties.MinioProperties;
import io.minio.MinioClient;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Description: Minio 客户端连接池 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/11/8 10:54
 */
public class MinioClientObjectPool {

    private static final Logger log = LoggerFactory.getLogger(MinioClientObjectPool.class);

    private final GenericObjectPool<MinioClient> minioClientPool;

    public MinioClientObjectPool(MinioProperties minioProperties) {

        MinioClientPooledObjectFactory factory = new MinioClientPooledObjectFactory(minioProperties);

        GenericObjectPoolConfig<MinioClient> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(minioProperties.getPool().getMaxTotal());
        config.setMaxIdle(minioProperties.getPool().getMaxIdle());
        config.setMinIdle(minioProperties.getPool().getMinIdle());
        config.setMaxWait(minioProperties.getPool().getMaxWait());
        config.setMinEvictableIdleTime(minioProperties.getPool().getMinEvictableIdleTime());
        config.setSoftMinEvictableIdleTime(minioProperties.getPool().getSoftMinEvictableIdleTime());
        config.setLifo(minioProperties.getPool().getLifo());
        config.setBlockWhenExhausted(minioProperties.getPool().getBlockWhenExhausted());
        minioClientPool = new GenericObjectPool<>(factory, config);
    }

    public MinioClient getMinioClient() throws OssClientPoolErrorException {
        try {
            MinioClient minioClient = minioClientPool.borrowObject();
            log.debug("[Herodotus] |- Fetch minio client from object pool.");
            return minioClient;
        } catch (Exception e) {
            log.error("[Herodotus] |- Can not fetch minio client from pool.");
            throw new OssClientPoolErrorException("Can not fetch minio client from pool.");
        }
    }

    public void close(MinioClient minioClient) {
        if (ObjectUtils.isNotEmpty(minioClient)) {
            minioClientPool.returnObject(minioClient);
        }
    }
}
