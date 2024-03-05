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

package cn.herodotus.engine.assistant.ip2region.searcher;

import cn.herodotus.engine.assistant.core.constants.SymbolConstants;
import cn.herodotus.engine.assistant.core.utils.ResourceUtils;
import cn.herodotus.engine.assistant.ip2region.definition.Ip2RegionSearcher;
import cn.herodotus.engine.assistant.ip2region.domain.IpLocation;
import cn.herodotus.engine.assistant.ip2region.exception.SearchIpLocationException;
import cn.herodotus.engine.assistant.ip2region.utils.IpLocationUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * <p>Description: 默认的Ip2Region查询实现 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/10/23 15:28
 */
public class DefaultIp2RegionSearcher implements Ip2RegionSearcher, InitializingBean, DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(DefaultIp2RegionSearcher.class);

    private IpV4Searcher ipV4Searcher;
    private IpV6Searcher ipV6Searcher;
    private final String ipV4Resource;
    private final String ipV6Resource;

    public DefaultIp2RegionSearcher(String ipV4Resource, String ipV6Resource) {
        this.ipV4Resource = ipV4Resource;
        this.ipV6Resource = ipV6Resource;
    }

    private byte[] toBytes(String location) throws IllegalAccessException {
        Resource resource = ResourceUtils.getResource(location);
        if (ObjectUtils.isNotEmpty(resource)) {
            log.debug("[Herodotus] |- Load ip region database [{}]", resource.getFilename());
            return ResourceUtils.toBytes(resource);
        } else {
            log.error("[Herodotus] |- Cannot read ip region database in resources folder!");
            throw new IllegalAccessException("Cannot read ip region database in resources folder");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] ipV4Database = toBytes(this.ipV4Resource);
        if (ObjectUtils.isNotEmpty(ipV4Database)) {
            this.ipV4Searcher = IpV4Searcher.newWithBuffer(ipV4Database);
        }

        byte[] ipV6Database = toBytes(this.ipV6Resource);
        if (ObjectUtils.isNotEmpty(ipV6Database)) {
            this.ipV6Searcher = new IpV6Searcher(ipV6Database);
        }
    }

    @Override
    public IpLocation memorySearch(long ip) {
        try {
            return IpLocationUtils.toIpV4Location(ipV4Searcher.search(ip));
        } catch (IOException e) {
            log.error("[Herodotus] |- Search ip v4 location catch io exception!", e);
            throw new SearchIpLocationException(e);
        }
    }

    @Override
    public IpLocation memorySearch(String ip) {
        // 1. ipv4
        String[] ipV4Part = IpLocationUtils.getIpV4Part(ip);
        if (ipV4Part.length == 4) {
            return memorySearch(IpV4Searcher.getIpAdder(ipV4Part));
        }
        // 2. 非 ipv6
        if (!ip.contains(SymbolConstants.COLON)) {
            throw new IllegalArgumentException("invalid ip location [" + ip + "]");
        }
        // 3. ipv6
        return ipV6Searcher.query(ip);
    }

    @Override
    public void destroy() throws Exception {
        if (ObjectUtils.isNotEmpty(ipV4Searcher)) {
            this.ipV4Searcher.close();
        }
    }
}
