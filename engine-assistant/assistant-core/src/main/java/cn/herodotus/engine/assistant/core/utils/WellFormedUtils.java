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

package cn.herodotus.engine.assistant.core.utils;

import cn.herodotus.engine.assistant.core.definition.constants.DefaultConstants;
import cn.herodotus.engine.assistant.core.definition.constants.SymbolConstants;
import cn.herodotus.engine.assistant.core.enums.Protocol;
import cn.herodotus.engine.assistant.core.exception.properties.PropertyValueIsNotSetException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * <p>Description: 符合语法规则的工具类 </p>
 * <p>
 * 主要包含增强代码健壮性和目前将无法形成工具箱的、不方便归类的工具方法，统一放在该类中。待某一类方法比较多了之后，再行拆分。
 *
 * @author : gengwei.zheng
 * @date : 2023/5/23 16:55
 */
public class WellFormedUtils {

    private static final Logger log = LoggerFactory.getLogger(WellFormedUtils.class);

    public static final ParameterizedTypeReference<Map<String, Object>> MAP_TYPE_REFERENCE = new ParameterizedTypeReference<>() {
    };

    /**
     * 符合语法规则的 URL
     * <p>
     * 检测地址相关字符串是否以"/"结尾，如果没有就帮助增加一个 ""/""
     *
     * @param url http 请求地址字符串
     * @return 结构合理的请求地址字符串
     */
    public static String url(String url) {
        if (StringUtils.endsWith(url, SymbolConstants.FORWARD_SLASH)) {
            return url;
        } else {
            return url + SymbolConstants.FORWARD_SLASH;
        }
    }

    /**
     * 符合语法规则的 ParentId
     * <p>
     * 树形结构 ParentId 健壮性校验方法。
     *
     * @param parentId 父节点ID
     * @return 格式友好的 parentId
     */
    public static String parentId(String parentId) {
        if (StringUtils.isBlank(parentId)) {
            return DefaultConstants.TREE_ROOT_ID;
        } else {
            return parentId;
        }
    }

    /**
     * 将IP地址加端口号，转换为http地址。
     *
     * @param address             ip地址加端口号，格式：ip:port
     * @param protocol            http协议类型 {@link Protocol}
     * @param endWithForwardSlash 是否在结尾添加“/”
     * @return http格式地址
     */
    public static String addressToUri(String address, Protocol protocol, boolean endWithForwardSlash) {
        StringBuilder stringBuilder = new StringBuilder();

        if (!StringUtils.startsWith(address, protocol.getFormat())) {
            stringBuilder.append(protocol.getFormat());
        }

        if (endWithForwardSlash) {
            stringBuilder.append(url(address));
        } else {
            stringBuilder.append(address);
        }

        return stringBuilder.toString();
    }

    /**
     * 将IP地址加端口号，转换为http地址。
     *
     * @param address             ip地址加端口号，格式：ip:port
     * @param endWithForwardSlash 是否在结尾添加“/”
     * @return http格式地址
     */
    public static String addressToUri(String address, boolean endWithForwardSlash) {
        return addressToUri(address, Protocol.HTTP, endWithForwardSlash);
    }

    /**
     * 将IP地址加端口号，转换为http地址。
     *
     * @param address ip地址加端口号，格式：ip:port
     * @return http格式地址
     */
    public static String addressToUri(String address) {
        return addressToUri(address, false);
    }

    /**
     * 获取运行主机ip地址
     *
     * @return ip地址，或者null
     */
    public static String getHostAddress() {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
            return address.getHostAddress();
        } catch (UnknownHostException e) {
            log.error("[Herodotus] |- Get host address error: {}", e.getLocalizedMessage());
            return null;
        }
    }

    public static String serviceUri(String gatewayServiceUri, String serviceUri, String serviceName, String abbreviation) {
        if (StringUtils.isNotBlank(serviceUri)) {
            return serviceUri;
        } else {
            if (StringUtils.isBlank(serviceName)) {
                log.error("[Herodotus] |- Property [{} Service Name] is not set or property format is incorrect!", abbreviation);
                throw new PropertyValueIsNotSetException();
            } else {
                return WellFormedUtils.url(gatewayServiceUri) + serviceName;
            }
        }
    }

}
