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

package cn.herodotus.engine.oss.minio.processor;

import cn.herodotus.engine.assistant.core.definition.constants.DefaultConstants;
import cn.herodotus.engine.assistant.core.definition.constants.SymbolConstants;
import cn.herodotus.engine.oss.minio.properties.MinioProperties;
import cn.herodotus.engine.oss.minio.service.PresignedService;
import cn.herodotus.engine.rest.core.properties.EndpointProperties;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <p>Description: Minio 代理地址转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/3 15:19
 */
@Component
public class MinioDelegateUrlConverter {

    private static final Logger log = LoggerFactory.getLogger(MinioDelegateUrlConverter.class);

    private final MinioProperties minioProperties;
    private final EndpointProperties endpointProperties;

    public MinioDelegateUrlConverter(MinioProperties minioProperties, EndpointProperties endpointProperties) {
        this.minioProperties = minioProperties;
        this.endpointProperties = endpointProperties;
    }

    public String toServiceUrl(String presignedObjectUrl) {
        String endpoint = endpointProperties.getOssServiceUri() + DefaultConstants.PRESIGNED_OBJECT_URL_DELEGATE;
        return StringUtils.replace(presignedObjectUrl, minioProperties.getEndpoint(), endpoint);
    }

    public String toPresignedObjectUrl(HttpServletRequest request) {
        String queryString = request.getQueryString();
        return minioProperties.getEndpoint()
                + request.getRequestURI().replace(DefaultConstants.PRESIGNED_OBJECT_URL_DELEGATE, SymbolConstants.BLANK)
                + (queryString != null ? SymbolConstants.QUESTION + queryString : SymbolConstants.BLANK);
    }
}
