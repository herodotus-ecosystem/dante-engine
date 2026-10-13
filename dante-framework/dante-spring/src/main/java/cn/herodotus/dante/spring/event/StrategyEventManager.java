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

package cn.herodotus.dante.spring.event;

import cn.herodotus.dante.core.jackson.JacksonUtils;
import cn.herodotus.dante.spring.context.ServiceContextHolder;
import org.apache.commons.lang3.Strings;
import org.springframework.context.ApplicationEvent;

/**
 * <p>Description: 策略 Event 定义 </p>
 * <p>
 * 为了同时支持 分布式模式 和 单体式模式。所以很多事件均需要同时支持本地发送和远程发送两种模式。
 * 抽象本定义用于统一处理两种模式的事件支持。
 *
 * @author : gengwei.zheng
 * @date : 2022/2/5 15:32
 */
public interface StrategyEventManager<T> {

    /**
     * 本地处理逻辑。通常用于发送本地 Event，可以直接调用本地处理逻辑。
     *
     * @param data 数据
     */
    void postLocalProcess(T data);

    /**
     * 远程处理逻辑。通常发送基于 Spring Cloud Bus 的 Remote Event。
     *
     * @param data               事件携带数据。JSON 格式的数据。
     * @param originService      发送远程事件原始服务
     * @param destinationService 接收远程事件目的地服务
     */
    void postRemoteProcess(String data, String originService, String destinationService);

    /**
     * 判断是否为是本地处理还是远程处理。
     * <p>
     * 如果是单体架构：那么只会有本地处理逻辑。
     * 如果是微服务架构：那么会判断 destinationService 是否与服务自身名称相同，如果相同则认为是服务自身，服务自身就采用本地处理逻辑。
     *
     * @param destinationService 接收远程事件目的地服务
     * @return false 远程处理，true 本地处理。
     */
    default boolean isLocal(String destinationService) {
        return !ServiceContextHolder.isDistributedArchitecture() || Strings.CS.equals(ServiceContextHolder.getApplicationName(), destinationService);
    }

    /**
     * 发送事件处理逻辑。如果判断为本地，则调用本地处理逻辑；如果判断为远程，则调用远程处理逻辑。
     *
     * @param destinationService 接收远程事件目的地服务
     * @param data               传输数据
     */
    default void postProcess(String destinationService, T data) {
        if (isLocal(destinationService)) {
            postLocalProcess(data);
        } else {
            postRemoteProcess(JacksonUtils.toJson(data), ServiceContextHolder.getOriginService(), destinationService);
        }
    }

    /**
     * 发送 Spring Event 操作。支持本地 Event 和 远程 Event。
     * <p>
     * 注意：远程 Remote Event，需要被 Spring Cloud Bus 注解 @RemoteApplicationEventScan 扫描到
     *
     * @param event 自定义 Spring Event
     */
    default void publishEvent(ApplicationEvent event) {
        ServiceContextHolder.publishEvent(event);
    }
}
