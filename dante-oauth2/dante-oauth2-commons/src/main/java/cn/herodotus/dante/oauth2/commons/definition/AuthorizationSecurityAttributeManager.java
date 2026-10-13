/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 是 Dante Cloud 系统核心组件库，采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dante.oauth2.commons.definition;

import cn.herodotus.dante.messaging.domain.AttributeDistributor;
import cn.herodotus.dante.oauth2.commons.domain.HerodotusRequest;
import cn.herodotus.dante.oauth2.commons.domain.HerodotusSecurityAttribute;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: OAuth2 资源服务器安全属性管理器 </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/10/13 15:41
 */
public interface AuthorizationSecurityAttributeManager {

    void postLocalResourceMatcherProcess();

    /**
     * 处理分发的 SecurityAttribute，将其转换、解析为表达式权限，并存入本地缓存，用于权限校验
     * <p>
     * 处理过程中，会根据规则对权限类型分组，然后进行去重的操作。
     */
    void postAttributeDistributorProcess(AttributeDistributor dispatcher);

    /**
     * 从 compatible 缓存中获取全部不需要路径匹配的（包含*号的url）请求权限映射Map
     *
     * @return 如果缓存中存在，则返回请求权限映射Map集合，如果不存在则返回一个空的{@link LinkedHashMap}
     */
    Map<HerodotusRequest, List<HerodotusSecurityAttribute>> getRestCompatible();

    /**
     * 根据请求的 url method 和 version 获取权限对象
     *
     * @param url    请求 URL
     * @param method 请求 method
     * @return 与请求url 和 method 匹配的权限数据，或者是空集合
     */
    List<HerodotusSecurityAttribute> findRestAttribute(String url, String method, String version);

    List<HerodotusSecurityAttribute> findMcpAttribute(String name, String feature);
}
