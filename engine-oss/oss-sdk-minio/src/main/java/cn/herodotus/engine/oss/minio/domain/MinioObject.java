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

package cn.herodotus.engine.oss.minio.domain;

import com.google.common.base.MoreObjects;
import io.minio.StatObjectResponse;

import java.util.Date;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/11/8 15:58
 */
public class MinioObject {

    private String bucketName;
    private String name;
    private Date lastModified;
    private long length;
    private String etag;
    private String contentType;

    public MinioObject(String bucketName, String name, Date lastModified, long length, String etag, String contentType) {
        this.bucketName = bucketName;
        this.name = name;
        this.lastModified = lastModified;
        this.length = length;
        this.etag = etag;
        this.contentType = contentType;
    }

    public MinioObject(StatObjectResponse os) {
        this.bucketName = os.bucket();
        this.name = os.object();
        this.lastModified = Date.from(os.lastModified().toInstant());
        this.length = os.size();
        this.etag = os.etag();
        this.contentType = os.contentType();
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("bucketName", bucketName)
                .add("name", name)
                .add("lastModified", lastModified)
                .add("length", length)
                .add("etag", etag)
                .add("contentType", contentType)
                .toString();
    }
}
