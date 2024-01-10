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

package cn.herodotus.stirrup.kernel.engine.utils.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>Description: 数组工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/3 23:12
 */
public class ArrayUtils {

    /**
     * 将字符串数组转换成字符串List
     *
     * @param array 字符串数组
     * @return 字符串List
     */
    public static List<String> toStringList(String[] array) {
        if (org.apache.commons.lang3.ArrayUtils.isNotEmpty(array)) {
            List<String> list = new ArrayList<>(array.length);
            Collections.addAll(list, array);
            return list;
        } else {
            return new ArrayList<>();
        }
    }
}
