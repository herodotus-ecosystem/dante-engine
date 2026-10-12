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

package cn.herodotus.dante.messaging.processor;

import cn.herodotus.dante.messaging.domain.AttributeCollector;
import cn.herodotus.dante.messaging.domain.AttributeDistributor;

import java.util.function.BiConsumer;

/**
 * <p>Description: 属性传出处理器 </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/10/12 22:45
 */
public interface AttributeTransferProcessor {

    /**
     * 将服务发送的 {@link AttributeCollector} 信息，聚合存储至 SysInterface。然后将 SysInterface 表中存在但 SysAttribute 表中不存在的数据同步至 SysAttribute ，保证两侧数据一致
     *
     * @param collector    权限元数据收集器 {@link AttributeCollector}
     * @param publishEvent 数据发送操作
     */
    void storeAndMergeAttributeCollector(AttributeCollector collector, BiConsumer<String, AttributeDistributor> publishEvent);

    /**
     * SysAttribute 数据条目发生变化后触发数据更新
     *
     * @param attribute    变更后数据。JSON 字符串较少代码耦合
     * @param publishEvent 数据发送操作
     */
    void distributeChangedSecurityAttribute(String attribute, BiConsumer<String, AttributeDistributor> publishEvent);
}
