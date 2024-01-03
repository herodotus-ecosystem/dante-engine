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

package cn.herodotus.engine.assistant.ip2region.searcher;

import cn.herodotus.engine.assistant.definition.constants.SymbolConstants;
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
