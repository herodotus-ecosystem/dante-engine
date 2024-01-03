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

package cn.herodotus.engine.assistant.definition.domain;

import cn.herodotus.engine.assistant.definition.constants.ErrorCodes;
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
