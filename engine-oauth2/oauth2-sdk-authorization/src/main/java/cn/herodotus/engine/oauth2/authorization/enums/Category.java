/*
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君) (herodotus@aliyun.com & www.herodotus.cn)
 *
 * Dante Engine licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.gnu.org/licenses/lgpl.html>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.herodotus.engine.oauth2.authorization.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>Description: URL 路径类别 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/3/9 19:06
 */
public enum Category {

    /**
     * 含有通配符，含有 "*" 或 "?"
     */
    WILDCARD,
    /**
     * 含有占位符，含有 "{" 和 " } "
     */
    PLACEHOLDER,
    /**
     * 不含有任何特殊字符的完整路径
     */
    FULL_PATH;

    public static Category getCategory(String url) {

        if (StringUtils.containsAny(url, new String[]{"*", "?"})) {
            return Category.WILDCARD;
        }

        if (StringUtils.contains(url, "{")) {
            return Category.PLACEHOLDER;
        }

        return Category.FULL_PATH;
    }
}
