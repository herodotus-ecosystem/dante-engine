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

package cn.herodotus.engine.message.core.definition;

import cn.herodotus.engine.assistant.core.context.ServiceContextHolder;
import cn.herodotus.engine.assistant.core.json.jackson2.utils.Jackson2Utils;
import org.apache.commons.lang3.StringUtils;

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
     * 创建本地事件
     *
     * @param data 事件携带数据
     */
    void postLocalProcess(T data);

    /**
     * 创建远程事件
     *
     * @param data               事件携带数据。JSON 格式的数据。
     * @param originService      发送远程事件原始服务
     * @param destinationService 接收远程事件目的地
     */
    void postRemoteProcess(String data, String originService, String destinationService);

    /**
     * 是否是本地处理事件。
     *
     * @param destinationService 接收远程事件目的地
     * @return false 远程事件，local 本地事件
     */
    default boolean isLocal(String destinationService) {
        return !ServiceContextHolder.getInstance().isDistributedArchitecture() || StringUtils.equals(ServiceContextHolder.getInstance().getApplicationName(), destinationService);
    }

    /**
     * 发送事件
     *
     * @param data               事件携带数据
     * @param destinationService 接收远程事件目的地
     */
    default void postProcess(String destinationService, T data) {
        postProcess(ServiceContextHolder.getInstance().getOriginService(), destinationService, data);
    }

    /**
     * 发送事件
     *
     * @param data               事件携带数据
     * @param originService      发送远程事件原始服务
     * @param destinationService 接收远程事件目的地
     */
    default void postProcess(String originService, String destinationService, T data) {
        if (isLocal(destinationService)) {
            postLocalProcess(data);
        } else {
            postRemoteProcess(Jackson2Utils.toJson(data), originService, destinationService);
        }
    }
}
