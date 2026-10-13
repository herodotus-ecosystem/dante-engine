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

package cn.herodotus.dante.oauth2.authorization.autoconfigure.listener;

import cn.herodotus.dante.core.jackson.JacksonUtils;
import cn.herodotus.dante.messaging.domain.AttributeDistributor;
import cn.herodotus.dante.oauth2.authorization.autoconfigure.bus.RemoteAttributeDistributionEvent;
import cn.herodotus.dante.oauth2.commons.definition.AuthorizationSecurityAttributeManager;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.context.ApplicationListener;

import java.util.Optional;

/**
 * <p>Description: Security Metadata 数据同步监听 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/6 12:23
 */
public class RemoteAttributeDistributionListener implements ApplicationListener<RemoteAttributeDistributionEvent> {

    private static final Logger log = LoggerFactory.getLogger(RemoteAttributeDistributionListener.class);

    private final AuthorizationSecurityAttributeManager authorizationSecurityAttributeManager;
    private final ServiceMatcher serviceMatcher;

    public RemoteAttributeDistributionListener(AuthorizationSecurityAttributeManager authorizationSecurityAttributeManager, ServiceMatcher serviceMatcher) {
        this.authorizationSecurityAttributeManager = authorizationSecurityAttributeManager;
        this.serviceMatcher = serviceMatcher;
    }

    @Override
    public void onApplicationEvent(RemoteAttributeDistributionEvent event) {

        if (!serviceMatcher.isFromSelf(event)) {
            log.info("[Herodotus] |- Attribute distribution REMOTE listener, response service [{}] event!", event.getOriginService());

            String data = event.getData();

            log.debug("[Herodotus] |- Got attribute data from service [{}], current [{}] start to process security attributes.", event.getOriginService(), event.getDestinationService());

            Optional.ofNullable(data)
                    .flatMap(value -> Optional.ofNullable(JacksonUtils.toObject(value, AttributeDistributor.class)))
                    .filter(dispatcher -> CollectionUtils.isNotEmpty(dispatcher.getAttributes()))
                    .ifPresent(authorizationSecurityAttributeManager::postAttributeDistributorProcess);
        }
    }
}
