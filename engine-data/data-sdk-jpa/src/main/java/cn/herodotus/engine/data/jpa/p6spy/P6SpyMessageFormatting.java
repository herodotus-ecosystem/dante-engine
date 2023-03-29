/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.data.jpa.p6spy;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Description: P6Spy自定义格式化</p>
 *
 * @author gengwei.zheng
 * @date 2019/1/20
 */
public class P6SpyMessageFormatting implements MessageFormattingStrategy {

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

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
