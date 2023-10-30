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

package cn.herodotus.engine.supplier.upms.rest.configuration;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * <p>Description: UpmsRest配置类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/1/5 11:58
 */
@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = {
        "cn.herodotus.engine.supplier.upms.rest.controller.hr",
        "cn.herodotus.engine.supplier.upms.rest.controller.security",
        "cn.herodotus.engine.supplier.upms.rest.controller.assistant",
        "cn.herodotus.engine.supplier.upms.rest.controller.social",
})
public class SupplierUpmsRestConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SupplierUpmsRestConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Supplier Upms Rest] Auto Configure.");
    }


}
