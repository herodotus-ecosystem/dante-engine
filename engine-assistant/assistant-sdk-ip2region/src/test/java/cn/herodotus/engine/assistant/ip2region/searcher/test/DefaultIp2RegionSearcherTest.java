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

package cn.herodotus.engine.assistant.ip2region.searcher.test;

import cn.herodotus.engine.assistant.ip2region.configuration.Ip2RegionConfiguration;
import cn.herodotus.engine.assistant.ip2region.definition.Ip2RegionSearcher;
import cn.herodotus.engine.assistant.ip2region.domain.IpLocation;
import cn.herodotus.engine.assistant.ip2region.properties.Ip2RegionProperties;
import cn.herodotus.engine.assistant.ip2region.searcher.DefaultIp2RegionSearcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * <p>Description: Ip2Region 测试 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/10/24 14:08
 */
class DefaultIp2RegionSearcherTest {

    private Ip2RegionSearcher searcher;

    @BeforeEach
    public void setup() throws Exception {
        Ip2RegionConfiguration configuration = new Ip2RegionConfiguration();
        searcher = configuration.defaultIp2RegionSearcher(new Ip2RegionProperties());
        ((DefaultIp2RegionSearcher) searcher).afterPropertiesSet();
    }

    @Test
    void testIpV4Region() throws Exception {
        Assertions.assertEquals(searcher.memorySearch("220.248.12.158").getLocation(), "中国上海市上海");
        Assertions.assertEquals(searcher.memorySearch("222.240.36.135").getLocation(), "中国长沙市湖南省");
        Assertions.assertEquals(searcher.memorySearch("172.30.13.97").getLocation(), "内网IP");
        Assertions.assertEquals(searcher.memorySearch("223.26.64.0").getLocation(), "中国台北台湾省");
        Assertions.assertEquals(searcher.memorySearch("223.26.128.0").getLocation(), "韩国首尔");
        Assertions.assertEquals(searcher.memorySearch("223.26.67.0").getLocation(), "中国台湾省");
        Assertions.assertEquals(searcher.memorySearch("223.29.220.0").getLocation(), "印度北方邦");
        Assertions.assertEquals(searcher.memorySearch("82.120.124.0").getLocation(), "法国Loire");
    }

    @Test
    void testIpV6Region() throws Exception {
        Assertions.assertNotNull(searcher.memorySearch("::ffff:1111:2222"));
        Assertions.assertNotNull(searcher.memorySearch("2001:db8::ffff:1111:2222"));
        Assertions.assertNotNull(searcher.memorySearch("::1"));
        Assertions.assertNotNull(searcher.memorySearch("2406:840:20::1"));
        Assertions.assertNotNull(searcher.memorySearch("2c0f:feb0:a::"));
        Assertions.assertNotNull(searcher.memorySearch("240e:109:8047::"));
        Assertions.assertNotNull(searcher.memorySearch("1111:1111:1111::1111"));
    }

    @Test
    void test2() {
        IpLocation location = searcher.memorySearch("127.0.0.1");
        Assertions.assertNotNull(location);
    }
}
