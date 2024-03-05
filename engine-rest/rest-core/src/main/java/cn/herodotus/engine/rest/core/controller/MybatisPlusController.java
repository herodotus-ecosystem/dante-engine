/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.rest.core.controller;

import cn.herodotus.engine.assistant.core.definition.domain.Entity;
import cn.herodotus.engine.assistant.core.domain.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: Mybatis Plus 基础 Controller </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/1/21 16:17
 */
public interface MybatisPlusController extends Controller {

    default <E extends Entity> Result<Map<String, Object>> result(IPage<E> pages) {
        if (ObjectUtils.isNotEmpty(pages)) {
            if (CollectionUtils.isNotEmpty(pages.getRecords())) {
                return Result.success("查询数据成功！", getPageInfoMap(pages));
            } else {
                return Result.empty("未查询到数据！");
            }
        } else {
            return Result.failure("查询数据失败！");
        }
    }

    default <E extends Entity> Map<String, Object> getPageInfoMap(IPage<E> page) {
        Map<String, Object> result = new HashMap<>(8);
        result.put("content", page.getRecords());
        result.put("totalPages", (int) page.getPages());
        result.put("totalElements", (int) page.getTotal());
        return result;
    }
}
