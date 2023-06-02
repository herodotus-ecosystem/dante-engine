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

package cn.herodotus.engine.oss.minio.converter;

import cn.herodotus.engine.assistant.core.utils.DateTimeUtils;
import cn.herodotus.engine.oss.core.exception.*;
import cn.herodotus.engine.oss.minio.entity.ItemEntity;
import cn.herodotus.engine.oss.minio.entity.OwnerEntity;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Item;
import io.minio.messages.Owner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>Description: Result<Item> 转 ItemEntity 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/30 21:33
 */
public class ItemToEntityConverter implements Converter<Result<Item>, ItemEntity> {

    private static final Logger log = LoggerFactory.getLogger(ItemToEntityConverter.class);
    private final Converter<Owner, OwnerEntity> toEntity;

    public ItemToEntityConverter() {
        this.toEntity = new OwnerToEntityConverter();
    }

    @Override
    public ItemEntity convert(Result<Item> result) {

        String function = "convert";

        try {
            Item item = result.get();
            ItemEntity entity = new ItemEntity();
            entity.setEtag(item.etag());
            entity.setObjectName(item.objectName());
            entity.setLastModified(DateTimeUtils.zonedDateTimeToString(item.lastModified()));
            entity.setOwner(toEntity.convert(item.owner()));
            entity.setSize(item.size());
            entity.setStorageClass(item.storageClass());
            entity.setLatest(item.isLatest());
            entity.setUserMetadata(item.userMetadata());
            entity.setDir(item.isDir());
            return entity;
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
        }
    }
}
