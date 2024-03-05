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

package cn.herodotus.engine.assistant.core.domain;

import cn.herodotus.engine.assistant.core.constants.ErrorCodes;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>Description: 错误代码映射器 </p>
 * <p>
 * 采用双重检查方式的单例模式。
 * 1. 保证线程安全
 * 2. 方便在 Exception 中直接调用。
 *
 * @author : gengwei.zheng
 * @date : 2023/9/25 9:11
 */
public class ErrorCodeMapper {

    private static volatile ErrorCodeMapper instance;

    private final Map<Feedback, Integer> dictionary;

    private ErrorCodeMapper() {
        dictionary = new LinkedHashMap<>() {{
            put(ErrorCodes.OK, ErrorCodes.OK.getSequence());
            put(ErrorCodes.NO_CONTENT, ErrorCodes.NO_CONTENT.getSequence());
        }};
    }

    public static ErrorCodeMapper getInstance() {
        if (ObjectUtils.isEmpty(instance)) {
            synchronized (ErrorCodeMapper.class) {
                if (ObjectUtils.isEmpty(instance)) {
                    instance = new ErrorCodeMapper();
                }
            }
        }
        return instance;
    }

    private Integer getErrorCode(Feedback feedback) {
        return dictionary.get(feedback);
    }

    public void append(Map<Feedback, Integer> indexes) {
        if (MapUtils.isNotEmpty(indexes)) {
            dictionary.putAll(indexes);
        }
    }

    public static Integer get(Feedback feedback) {
        return getInstance().getErrorCode(feedback);
    }
}
