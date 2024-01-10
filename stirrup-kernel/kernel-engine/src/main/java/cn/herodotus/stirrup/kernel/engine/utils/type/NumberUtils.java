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

/**
 * <p>Description: 数字类型工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/11/1 21:26
 */
public class NumberUtils {

    /**
     * long 转 int
     *
     * @param value long 型数值
     * @return int 型数值
     */
    public static int longToInt(long value) {
        return Long.valueOf(value).intValue();
    }
}
