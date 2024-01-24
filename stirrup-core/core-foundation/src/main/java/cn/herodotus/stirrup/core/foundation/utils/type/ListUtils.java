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

package cn.herodotus.stirrup.core.foundation.utils.type;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * <p>Description: List 常用工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/3/28 23:15
 */
public class ListUtils {

    /**
     * 将两个已排序的集合a和b合并到一个单独的已排序列表中，以便保留元素的自然顺序。
     *
     * @param appendResources  自定义配置
     * @param defaultResources 默认配置
     * @return 合并后的List
     */
    public static List<String> merge(List<String> appendResources, List<String> defaultResources) {
        if (CollectionUtils.isEmpty(appendResources)) {
            return defaultResources;
        } else {
            return CollectionUtils.collate(defaultResources, appendResources);
        }
    }

    /**
     * 将 List 转换为 String[]
     *
     * @param resources List
     * @return String[]
     */
    public static String[] toStringArray(List<String> resources) {
        if (CollectionUtils.isNotEmpty(resources)) {
            String[] result = new String[resources.size()];
            return resources.toArray(result);
        } else {
            return new String[]{};
        }
    }
}
