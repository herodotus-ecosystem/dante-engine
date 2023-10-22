/*
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君) (herodotus@aliyun.com & www.herodotus.cn)
 *
 * Dante Engine licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.gnu.org/licenses/lgpl.html>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.herodotus.engine.supplier.upms.logic.configuration;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * <p>Description: UPMS SDK 模块配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/16 19:00
 */
@Configuration(proxyBeanMethods = false)
@EntityScan(basePackages = {
        "cn.herodotus.engine.supplier.upms.logic.entity.security",
        "cn.herodotus.engine.supplier.upms.logic.entity.hr",
})
@EnableJpaRepositories(basePackages = {
        "cn.herodotus.engine.supplier.upms.logic.repository.security",
        "cn.herodotus.engine.supplier.upms.logic.repository.hr",
})
@ComponentScan(basePackages = {
        "cn.herodotus.engine.supplier.upms.logic.service.security",
        "cn.herodotus.engine.supplier.upms.logic.service.hr",
        "cn.herodotus.engine.supplier.upms.logic.service.assistant",
})
public class SupplierUpmsLogicConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SupplierUpmsLogicConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Supplier Upms Logic] Auto Configure.");
    }
}
