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

package cn.herodotus.stirrup.oauth2.authorization.autoconfigure.metadata;

import cn.herodotus.engine.oauth2.authorization.processor.SecurityMetadataSourceAnalyzer;
import cn.herodotus.engine.oauth2.core.definition.domain.SecurityAttribute;
import cn.herodotus.stirrup.core.foundation.json.jackson2.utils.Jackson2Utils;
import cn.herodotus.stirrup.oauth2.authorization.autoconfigure.bus.RemoteSecurityMetadataSyncEvent;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.context.ApplicationListener;

import java.util.List;

/**
 * <p>Description: Security Metadata 数据同步监听 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/6 12:23
 */
public class RemoteSecurityMetadataSyncListener implements ApplicationListener<RemoteSecurityMetadataSyncEvent> {

    private static final Logger log = LoggerFactory.getLogger(RemoteSecurityMetadataSyncListener.class);

    private final SecurityMetadataSourceAnalyzer securityMetadataSourceAnalyzer;
    private final ServiceMatcher serviceMatcher;

    public RemoteSecurityMetadataSyncListener(SecurityMetadataSourceAnalyzer securityMetadataSourceAnalyzer, ServiceMatcher serviceMatcher) {
        this.securityMetadataSourceAnalyzer = securityMetadataSourceAnalyzer;
        this.serviceMatcher = serviceMatcher;
    }

    @Override
    public void onApplicationEvent(RemoteSecurityMetadataSyncEvent event) {

        if (!serviceMatcher.isFromSelf(event)) {
            log.info("[Herodotus] |- Remote security metadata sync listener, response event!");

            String data = event.getData();
            if (StringUtils.isNotBlank(data)) {
                List<SecurityAttribute> securityMetadata = Jackson2Utils.toList(data, SecurityAttribute.class);

                if (CollectionUtils.isNotEmpty(securityMetadata)) {
                    log.debug("[Herodotus] |- Got security attributes from service [{}], current [{}] start to process security attributes.", event.getOriginService(), event.getDestinationService());
                    securityMetadataSourceAnalyzer.processSecurityAttribute(securityMetadata);
                }
            }
        }
    }
}
