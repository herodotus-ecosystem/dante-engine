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

package cn.herodotus.engine.message.core.logic.strategy;

import cn.herodotus.engine.assistant.core.context.ServiceContextHolder;
import cn.herodotus.engine.message.core.definition.strategy.ApplicationStrategyEventManager;
import cn.herodotus.engine.message.core.logic.domain.RequestMapping;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: RequestMapping 扫描管理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/16 18:42
 */
public interface RequestMappingScanEventManager extends ApplicationStrategyEventManager<List<RequestMapping>> {

    /**
     * 获取是否执行扫描的标记注解。
     *
     * @return 标记注解
     */
    Class<? extends Annotation> getScanAnnotationClass();

    /**
     * 执行本地数据存储
     *
     * @param requestMappings 扫描到的RequestMapping
     */
    void postLocalStorage(List<RequestMapping> requestMappings);

    /**
     * 发布远程事件，传送RequestMapping
     *
     * @param requestMappings 扫描到的RequestMapping
     */
    @Override
    default void postProcess(List<RequestMapping> requestMappings) {
        postLocalStorage(requestMappings);
        ApplicationStrategyEventManager.super.postProcess(requestMappings);
    }

    /**
     * 是否满足执行扫描的条件。
     * 根据扫描标记注解 {@link #getScanAnnotationClass()} 以及 是否是分布式架构 决定是否执行接口的扫描。
     * <p>
     * 分布式架构根据注解判断是否扫描，单体架构直接扫描即可无须判断
     *
     * @return true 执行， false 不执行
     */
    default boolean isPerformScan() {
        if (ServiceContextHolder.getInstance().isDistributedArchitecture()) {
            if (ObjectUtils.isEmpty(getScanAnnotationClass())) {
                return false;
            }

            Map<String, Object> content = ServiceContextHolder.getInstance().getApplicationContext().getBeansWithAnnotation(getScanAnnotationClass());
            return !MapUtils.isEmpty(content);
        }

        return true;
    }
}
