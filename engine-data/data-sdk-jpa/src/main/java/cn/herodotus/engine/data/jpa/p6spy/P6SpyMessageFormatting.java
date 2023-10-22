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

package cn.herodotus.engine.data.jpa.p6spy;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.date.DatePattern;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Description: P6Spy自定义格式化</p>
 *
 * @author gengwei.zheng
 * @date 2019/1/20
 */
public class P6SpyMessageFormatting implements MessageFormattingStrategy {

    private final SimpleDateFormat format = new SimpleDateFormat(DatePattern.NORM_DATETIME_MS_PATTERN);

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {

        /**
         *
         * StringBuilder 是为了避免字符串拼接过程中产生很多不必要的字符串对象。
         * 经过编译器优化，多个字符串相‘+’，优化后，与StringBuilder等价
         *
         * 关注idea的“'StringBuilder builder' can be replaced with 'String'”提示
         */
        String builder = this.format.format(new Date()) + " | took " +
                elapsed +
                "ms | " +
                category +
                " | connection " +
                connectionId +
                " | url " +
                url +
                "\n------------------------| " +
                sql +
                ";";
        return StringUtils.isNotEmpty(sql.trim()) ? String.valueOf(builder) : "";
    }
}
