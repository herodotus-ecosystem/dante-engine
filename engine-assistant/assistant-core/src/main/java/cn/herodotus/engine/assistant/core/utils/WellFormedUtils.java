/*
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君) (herodotus@aliyun.com & www.herodotus.cn)
 *
 * Dante Engine licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.gnu.org/licenses/lgpl-3.0.html>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.herodotus.engine.assistant.core.utils;

import cn.herodotus.engine.assistant.core.enums.Protocol;
import cn.herodotus.engine.assistant.core.exception.properties.PropertyValueIsNotSetException;
import cn.herodotus.engine.assistant.definition.constants.DefaultConstants;
import cn.herodotus.engine.assistant.definition.constants.SymbolConstants;
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

    public static final ParameterizedTypeReference<Map<String, Object>> MAP_TYPE_REFERENCE = new ParameterizedTypeReference<>() {
    };
    private static final Logger log = LoggerFactory.getLogger(WellFormedUtils.class);

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

    public static String serviceUri(String serviceUri, String serviceName, String gatewayServiceUri, String abbreviation) {
        if (StringUtils.isNotBlank(serviceUri)) {
            return serviceUri;
        } else {
            if (StringUtils.isBlank(serviceName)) {
                log.error("[Herodotus] |- Property [{} Service Name] is not set or property format is incorrect!", abbreviation);
                throw new PropertyValueIsNotSetException();
            } else {
                if (StringUtils.isBlank(gatewayServiceUri)) {
                    log.error("[Herodotus] |- Property [gateway-service-uri] is not set or property format is incorrect!");
                    throw new PropertyValueIsNotSetException();
                } else {
                    return WellFormedUtils.url(gatewayServiceUri) + serviceName;
                }
            }
        }
    }

    public static String sasUri(String uri, String endpoint, String issuerUri) {
        if (StringUtils.isNotBlank(uri)) {
            return uri;
        } else {
            if (StringUtils.isBlank(issuerUri)) {
                log.error("[Herodotus] |- Property [issuer-uri] is not set or property format is incorrect!");
                throw new PropertyValueIsNotSetException();
            } else {
                return issuerUri + endpoint;
            }
        }
    }
}
