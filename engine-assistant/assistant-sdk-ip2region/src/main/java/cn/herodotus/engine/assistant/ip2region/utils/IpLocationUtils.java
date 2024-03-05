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

package cn.herodotus.engine.assistant.ip2region.utils;

import cn.herodotus.engine.assistant.core.constants.SymbolConstants;
import cn.herodotus.engine.assistant.ip2region.domain.IpLocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.Arrays;

/**
 * <p>Description: Ip 信息解析工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/10/23 12:02
 */
public class IpLocationUtils {

    private static String check(String element) {
        if (StringUtils.isBlank(element) || StringUtils.equals(element, SymbolConstants.ZERO)) {
            return null;
        } else {
            return element;
        }
    }

    /**
     * 区域信息转成 Ip 位置信息实体
     *
     * @param region    区域
     * @param separator 分隔符
     * @param cover     补足位数
     * @param isp       运营商
     * @return Ip 信息详情 {@link IpLocation}
     */
    private static IpLocation toIpLocation(String region, String separator, int cover, String isp) {
        Assert.notNull(region, "The region is required.");

        String[] informations = StringUtils.split(region, separator);
        if (informations.length < cover) {
            informations = Arrays.copyOf(informations, cover);
        }

        IpLocation location = new IpLocation();
        location.setCountry(check(informations[0]));
        location.setProvince(check(informations[1]));
        location.setCity(check(informations[2]));
        location.setRegion(check(informations[3]));
        location.setIsp(check(isp));
        return location;
    }

    /**
     * 将 DataBlock 数据转换成 IpInformation 实体
     *
     * @param region 区域数据
     * @return Ip 信息详情 {@link IpLocation}
     */
    public static IpLocation toIpV4Location(String region) {
        return toIpLocation(region, SymbolConstants.PIPE, 5, null);
    }

    /**
     * 将 ipv6 地区转换成 IpInformation 实体
     *
     * @param region 区域数据
     * @param isp    运营商
     * @return Ip 信息详情 {@link IpLocation}
     */
    public static IpLocation toIpV6Location(String region, String isp) {
        return toIpLocation(region, "t", 4, isp);
    }

    /**
     * 将 ipv6 地区转换成 IpInformation 实体
     *
     * @param record 区域数据
     * @return Ip 信息详情 {@link IpLocation}
     */
    public static IpLocation toIpV6Location(String[] record) {
        return toIpV6Location(record[0], record[1]);
    }

    public static String[] getIpV4Part(String ip) {
        return StringUtils.split(ip, SymbolConstants.PERIOD);
    }
}
