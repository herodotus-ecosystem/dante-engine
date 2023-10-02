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

package cn.herodotus.engine.assistant.core.utils.type;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * <p>Description: 特殊日期处理 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/2 22:54
 */
public class DateTimeUtils {

    private static final String DEFAULT_DATA_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_TIME_ZONE_NAME = "Asia/Shanghai";

    public static String zonedDateTimeToString(ZonedDateTime zonedDateTime) {
        return zonedDateTimeToString(zonedDateTime, DEFAULT_DATA_TIME_FORMAT);
    }

    public static String zonedDateTimeToString(ZonedDateTime zonedDateTime, String format) {
        if (ObjectUtils.isNotEmpty(zonedDateTime) && StringUtils.isNotBlank(format)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format).withZone(ZoneId.of(DEFAULT_TIME_ZONE_NAME));
            return zonedDateTime.format(formatter);
        }
        return null;
    }

    public static ZonedDateTime stringToZonedDateTime(String dateString) {
        return stringToZonedDateTime(dateString, DEFAULT_DATA_TIME_FORMAT);
    }

    public static ZonedDateTime stringToZonedDateTime(String dateString, String format) {
        if (StringUtils.isNotBlank(dateString) && StringUtils.isNotBlank(format)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format).withZone(ZoneId.of(DEFAULT_TIME_ZONE_NAME));
            return ZonedDateTime.parse(dateString, formatter);
        }
        return null;
    }

    /**
     * ZonedDateTime 转换成 Date
     *
     * @param zonedDateTime {@link ZonedDateTime}
     * @return 如果 ZonedDateTime 有值则返回对应的 Date，如果为空则返回 当前日期
     */
    public static Date zonedDateTimeToDate(ZonedDateTime zonedDateTime) {
        if (ObjectUtils.isNotEmpty(zonedDateTime)) {
            return Date.from(zonedDateTime.toInstant());
        }
        return new Date();
    }

    /**
     * Date 转换成  ZonedDateTime
     *
     * @param date {@link Date}
     * @return 如果 Date 有值则返回对应的 ZonedDateTime，如果为空则返回 当前日期
     */
    public static ZonedDateTime dateToZonedDateTime(Date date) {
        if (ObjectUtils.isNotEmpty(date)) {
            return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        }
        return ZonedDateTime.now();
    }
}
