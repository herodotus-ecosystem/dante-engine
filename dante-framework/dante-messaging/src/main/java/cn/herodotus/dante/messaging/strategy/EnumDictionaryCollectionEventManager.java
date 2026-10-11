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

package cn.herodotus.dante.messaging.strategy;

import cn.herodotus.dante.core.domain.Dictionary;
import cn.herodotus.dante.spring.event.ApplicationStrategyEventManager;

import java.util.List;

/**
 * <p>Description: 枚举数据字典收集事件管理器 </p>
 * <p>
 * 微服务架构下：服务启动时，会扫描服务中枚举字典信息，通过该事件管理器发送远程事件将扫描结果发送至 UPMS 服务中进行聚合存储。UPMS 服务本身也需要扫描枚举字典，但不需要发送远程事件，使用本地事件即可
 * 单体架构下：仅需要发送本地事件即可。
 * <p>
 * 该事件管理器会自动判断在具体场景下，使用远程事件还是本地事件
 *
 * @author : gengwei.zheng
 * @date : 2024/8/23 17:02
 */
public interface EnumDictionaryCollectionEventManager extends ApplicationStrategyEventManager<List<Dictionary>> {
}
