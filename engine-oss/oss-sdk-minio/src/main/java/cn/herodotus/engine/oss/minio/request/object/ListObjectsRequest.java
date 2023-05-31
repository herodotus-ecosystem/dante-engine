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

package cn.herodotus.engine.oss.minio.request.object;

import cn.herodotus.engine.oss.minio.definition.request.BucketRequest;
import io.minio.ListObjectsArgs;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.apache.commons.lang3.BooleanUtils;

/**
 * <p>Description: 对象列表请求参数 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/4/16 21:51
 */
public class ListObjectsRequest extends BucketRequest<ListObjectsArgs.Builder, ListObjectsArgs> {

    /**
     * 分隔符
     */
    private String delimiter = "";
    /**
     * 使用 UrlEncoding
     */
    private Boolean useUrlEncodingType = true;
    /**
     * 'marker' for ListObjectsV1 and 'startAfter' for ListObjectsV2.
     */
    private String keyMarker;
    private String startAfter;
    @Min(value = 1, message = "maxKeys 值不能小于 1")
    @Max(value = 1000, message = "maxKeys 值不能大于 1000")
    private Integer maxKeys = 1000;
    private String prefix;

    /**
     * only for ListObjectsV2.
     */
    private String continuationToken;
    /**
     * only for ListObjectsV2.
     */
    private Boolean fetchOwner;
    /**
     * only for GetObjectVersions.
     */
    private String versionIdMarker;
    /**
     * MinIO extension applicable to ListObjectsV2.
     */
    private Boolean includeUserMetadata;
    /**
     * 是否递归
     */
    private Boolean recursive = false;
    private Boolean useApiVersion1;
    private Boolean includeVersions = false;

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public Boolean getUseUrlEncodingType() {
        return useUrlEncodingType;
    }

    public void setUseUrlEncodingType(Boolean useUrlEncodingType) {
        this.useUrlEncodingType = useUrlEncodingType;
    }

    public String getKeyMarker() {
        return keyMarker;
    }

    public void setKeyMarker(String keyMarker) {
        this.keyMarker = keyMarker;
    }

    public String getStartAfter() {
        return startAfter;
    }

    public void setStartAfter(String startAfter) {
        this.startAfter = startAfter;
    }

    public Integer getMaxKeys() {
        return maxKeys;
    }

    public void setMaxKeys(Integer maxKeys) {
        this.maxKeys = maxKeys;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getContinuationToken() {
        return continuationToken;
    }

    public void setContinuationToken(String continuationToken) {
        this.continuationToken = continuationToken;
    }

    public Boolean getFetchOwner() {
        return fetchOwner;
    }

    public void setFetchOwner(Boolean fetchOwner) {
        this.fetchOwner = fetchOwner;
    }

    public String getVersionIdMarker() {
        return versionIdMarker;
    }

    public void setVersionIdMarker(String versionIdMarker) {
        this.versionIdMarker = versionIdMarker;
    }

    public Boolean getIncludeUserMetadata() {
        return includeUserMetadata;
    }

    public void setIncludeUserMetadata(Boolean includeUserMetadata) {
        this.includeUserMetadata = includeUserMetadata;
    }

    public Boolean getRecursive() {
        return recursive;
    }

    public void setRecursive(Boolean recursive) {
        this.recursive = recursive;
    }

    public Boolean getUseApiVersion1() {
        return useApiVersion1;
    }

    public void setUseApiVersion1(Boolean useApiVersion1) {
        this.useApiVersion1 = useApiVersion1;
    }

    public Boolean getIncludeVersions() {
        return includeVersions;
    }

    public void setIncludeVersions(Boolean includeVersions) {
        this.includeVersions = includeVersions;
    }

    @Override
    public void prepare(ListObjectsArgs.Builder builder) {

        builder.delimiter(getDelimiter());
        builder.useUrlEncodingType(getUseUrlEncodingType());
        builder.maxKeys(getMaxKeys());
        builder.prefix(getPrefix());
        builder.recursive(getRecursive());

        if (BooleanUtils.isTrue(getUseApiVersion1())) {
            builder.keyMarker(getKeyMarker());
            builder.includeVersions(getIncludeVersions());
            builder.continuationToken(null);
            builder.fetchOwner(false);
            builder.includeUserMetadata(false);
        } else {
            builder.startAfter(getStartAfter());
            builder.continuationToken(getContinuationToken());
            builder.fetchOwner(getFetchOwner());
            builder.includeUserMetadata(getIncludeUserMetadata());
            builder.keyMarker(null);
            builder.includeVersions(false);
        }

        builder.versionIdMarker(getVersionIdMarker());

        super.prepare(builder);
    }

    @Override
    public ListObjectsArgs.Builder getBuilder() {
        return ListObjectsArgs.builder();
    }
}
