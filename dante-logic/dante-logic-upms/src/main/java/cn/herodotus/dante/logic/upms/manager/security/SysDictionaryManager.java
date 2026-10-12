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

package cn.herodotus.dante.logic.upms.manager.security;

import cn.herodotus.dante.core.domain.Dictionary;
import cn.herodotus.dante.logic.upms.converter.SysEnumToSysDictionaryConverter;
import cn.herodotus.dante.logic.upms.entity.security.SysDictionary;
import cn.herodotus.dante.logic.upms.entity.security.SysEnum;
import cn.herodotus.dante.logic.upms.service.security.SysDictionaryService;
import cn.herodotus.dante.logic.upms.service.security.SysEnumService;
import cn.herodotus.dante.messaging.processor.EnumDictionaryTransferProcessor;
import cn.herodotus.dante.spring.founction.ListConverter;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Description: {@link SysDictionary} Manager </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/10/12 22:52
 */
public class SysDictionaryManager implements EnumDictionaryTransferProcessor {

    private static final Logger log = LoggerFactory.getLogger(SysDictionaryManager.class);

    private final SysEnumService sysEnumService;
    private final SysDictionaryService sysDictionaryService;
    private final ListConverter<SysEnum, SysDictionary> toDictionaries;

    public SysDictionaryManager(SysEnumService sysEnumService, SysDictionaryService sysDictionaryService) {
        this.sysEnumService = sysEnumService;
        this.sysDictionaryService = sysDictionaryService;
        this.toDictionaries = new SysEnumToSysDictionaryConverter();
    }

    @Override
    public void storeAndMergeDictionaries(List<Dictionary> dictionaries) {
        // 将各个服务发送回来的 enums 存储到 SysEnum 中
        List<SysEnum> storedEnums = sysEnumService.storeDictionaries(dictionaries);

        if (CollectionUtils.isNotEmpty(storedEnums)) {
            log.debug("[Herodotus] |- [E4] Dictionary store success, start to merge system dictionary!");

            // 查询将新增的 SysEnum，将其转存到 SysDictionary 中
            List<SysEnum> sysEnums = sysEnumService.findAllocatable();
            if (CollectionUtils.isNotEmpty(sysEnums)) {
                List<SysDictionary> elements = toDictionaries.convert(sysEnums);
                List<SysDictionary> result = sysDictionaryService.saveAll(elements);
                if (CollectionUtils.isNotEmpty(result)) {
                    log.debug("[Herodotus] |- [E5] Merge enum dictionary SUCCESS and FINISHED!");
                } else {
                    log.error("[Herodotus] |- [E5] Merge enum dictionary failed!, Please Check!");
                }
            } else {
                log.debug("[Herodotus] |- No enum dictionary requires merge, SKIP!");
            }
        }
    }
}
