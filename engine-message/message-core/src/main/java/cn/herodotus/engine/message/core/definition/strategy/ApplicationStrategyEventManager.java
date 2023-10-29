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

package cn.herodotus.engine.message.core.definition.strategy;

import cn.herodotus.engine.message.core.definition.strategy.StrategyEventManager;

/**
 * <p>Description: 应用策略事件 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/3/29 7:26
 */
public interface ApplicationStrategyEventManager<T> extends StrategyEventManager<T> {

    /**
     * 目的服务名称
     *
     * @return 服务名称
     */
    String getDestinationServiceName();

    /**
     * 发送事件
     *
     * @param data 事件携带数据
     */
    default void postProcess(T data) {
        postProcess(getDestinationServiceName(), data);
    }
}
