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

package cn.herodotus.dante.spring.initializer;

import cn.herodotus.dante.core.constant.SymbolConstants;
import cn.herodotus.dante.core.domain.Scan;
import cn.herodotus.dante.spring.context.PropertyResolver;
import cn.herodotus.dante.spring.event.DestinationStrategyEventManager;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Description: 应用启动扫描器抽象定义 </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/10/10 12:26
 */
public abstract class AbstractApplicationReadyScanner<T> implements ApplicationReadyProcessor {

    private static final Logger log = LoggerFactory.getLogger(AbstractApplicationReadyScanner.class);

    private final Scan scan;
    private final DestinationStrategyEventManager<T> strategyEventManager;
    private String serviceId;

    public AbstractApplicationReadyScanner(Scan scan, DestinationStrategyEventManager<T> strategyEventManager) {
        this.scan = scan;
        this.strategyEventManager = strategyEventManager;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();

        log.debug("[Herodotus] |- Application is READY, start to scan attributes!");

        this.serviceId = PropertyResolver.getApplicationName(applicationContext);

        onApplicationEvent(applicationContext);
    }

    protected abstract void onApplicationEvent(ApplicationContext applicationContext);

    /**
     * 判断数据是否为空
     *
     * @param data 传递的数据
     * @return {@code true} 数据不为空，{@code false} 数据为空。
     */
    protected abstract boolean isNotEmpty(T data);


    protected String getServiceId() {
        return this.serviceId;
    }

    protected DestinationStrategyEventManager<T> getStrategyEventManager() {
        return strategyEventManager;
    }

    /**
     * 判断当前类的 GroupId 是否为非法的 GroupId，即：当前类的前缀，在允许的 GroupId列表中
     *
     * @param className 当前扫描的类名
     * @return {@code true} 如果当前类类 GroupId 未在 GroupId 列表中 ， {@code false} 如果当前类 GroupId 在 GroupId 列表中
     */
    protected boolean isIllegalGroupId(String className) {
        List<String> groupIds = scan.getGroupIds();

        if (StringUtils.isEmpty(className) || CollectionUtils.isEmpty(groupIds)) {
            return true;
        }

        List<String> result = groupIds.stream()
                .map(item -> item + SymbolConstants.PERIOD)
                .filter(groupId -> Strings.CS.startsWith(className, groupId))
                .collect(Collectors.toList());

        // 如果 result size 为 0，则表示未与 groupId 列表中任意信息匹配，则表示为非法 groupId
        return CollectionUtils.sizeIsEmpty(result);
    }

    /**
     * 扫描完成，发送 Event 传递数据
     *
     * @param data 扫描到的属性值
     * @param size 扫描到的数据数量
     */
    protected void complete(T data, int size) {

        if (isNotEmpty(data)) {
            log.debug("[Herodotus] |- Scanner found [{}] resources in service [{}], go to next stage!", serviceId, size);
            strategyEventManager.postProcess(data);
        } else {
            log.debug("[Herodotus] |- Scanner can not find any resources in service [{}]!", serviceId);
        }

        log.info("[Herodotus] |- Scanning for Service: [{}] FINISHED!", serviceId);
    }
}
