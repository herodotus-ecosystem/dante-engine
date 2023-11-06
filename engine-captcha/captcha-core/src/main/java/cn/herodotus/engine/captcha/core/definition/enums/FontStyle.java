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

package cn.herodotus.engine.captcha.core.definition.enums;

import java.awt.*;

/**
 * <p>Description: 字体风格 </p>
 * <p>
 * 定义此类的目的：
 * 1. 设置字体风格和字体大小，最初都是使用int类型参数，很容混淆出错，增加个枚举类型以示区别
 * 2. 枚举类型让配置参数配置更便捷。
 *
 * @author : gengwei.zheng
 * @date : 2021/12/23 10:33
 */
public enum FontStyle {

    /**
     * 字体风格
     */
    PLAIN(Font.PLAIN),
    BOLD(Font.BOLD),
    ITALIC(Font.ITALIC);

    private final int mapping;

    FontStyle(int mapping) {
        this.mapping = mapping;
    }

    public int getMapping() {
        return mapping;
    }
}
