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

package cn.herodotus.dante.oauth2.authorization.autoconfigure.strategy;

import cn.herodotus.dante.messaging.domain.AttributeCollector;
import cn.herodotus.dante.messaging.event.AttributeCollectionEvent;
import cn.herodotus.dante.messaging.strategy.AttributeCollectionEventManager;
import cn.herodotus.dante.oauth2.authorization.autoconfigure.bus.RemoteAttributeCollectionEvent;
import cn.herodotus.dante.oauth2.commons.definition.ResourceServiceSecurityAttributeManager;
import cn.herodotus.dante.spring.context.ServiceContextHolder;

import java.lang.annotation.Annotation;

/**
 * <p>Description: 默认 RequestMapping 收集事件管理器</p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/17 0:08
 */
public class DefaultAttributeCollectionEventManager implements AttributeCollectionEventManager {

    private final ResourceServiceSecurityAttributeManager resourceServiceSecurityAttributeManager;
    private final Class<? extends Annotation> scanAnnotationClass;

    public DefaultAttributeCollectionEventManager(ResourceServiceSecurityAttributeManager resourceServiceSecurityAttributeManager, Class<? extends Annotation> scanAnnotationClass) {
        this.resourceServiceSecurityAttributeManager = resourceServiceSecurityAttributeManager;
        this.scanAnnotationClass = scanAnnotationClass;
    }

    @Override
    public Class<? extends Annotation> getScanAnnotationClass() {
        return scanAnnotationClass;
    }

    @Override
    public String getDestinationServiceName() {
        return ServiceContextHolder.getUpmsServiceName();
    }

    @Override
    public void postLocalStorage(AttributeCollector collector) {
        resourceServiceSecurityAttributeManager.postLocalResourceMatcherProcess();
    }

    @Override
    public void postLocalProcess(AttributeCollector data) {
        publishEvent(new AttributeCollectionEvent(data));
    }

    @Override
    public void postRemoteProcess(String data, String originService, String destinationService) {
        publishEvent(new RemoteAttributeCollectionEvent(data, originService, destinationService));
    }
}
