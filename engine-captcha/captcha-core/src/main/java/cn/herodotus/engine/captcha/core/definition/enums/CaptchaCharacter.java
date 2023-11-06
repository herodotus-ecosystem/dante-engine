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

import cn.herodotus.engine.captcha.core.provider.RandomProvider;

/**
 * <p>Description: 验证码字符类型 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/21 16:26
 */
public enum CaptchaCharacter {

    /**
     * 验证码字母显示类别
     */
    NUM_AND_CHAR(RandomProvider.NUM_MIN_INDEX, RandomProvider.CHAR_MAX_INDEX, "数字和字母混合"),
    ONLY_NUM(RandomProvider.NUM_MIN_INDEX, RandomProvider.NUM_MAX_INDEX, "纯数字"),
    ONLY_CHAR(RandomProvider.CHAR_MIN_INDEX, RandomProvider.CHAR_MAX_INDEX, "纯字母"),
    ONLY_UPPER_CHAR(RandomProvider.UPPER_MIN_INDEX, RandomProvider.UPPER_MAX_INDEX, "纯大写字母"),
    ONLY_LOWER_CHAR(RandomProvider.LOWER_MIN_INDEX, RandomProvider.LOWER_MAX_INDEX, "纯小写字母"),
    NUM_AND_UPPER_CHAR(RandomProvider.NUM_MIN_INDEX, RandomProvider.UPPER_MAX_INDEX, "数字和大写字母");

    /**
     * 字符枚举值开始位置
     */
    private final int start;
    /**
     * 字符枚举值结束位置
     */
    private final int end;
    /**
     * 类型说明
     */
    private final String description;

    CaptchaCharacter(int start, int end, String description) {
        this.start = start;
        this.end = end;
        this.description = description;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getDescription() {
        return description;
    }
}
