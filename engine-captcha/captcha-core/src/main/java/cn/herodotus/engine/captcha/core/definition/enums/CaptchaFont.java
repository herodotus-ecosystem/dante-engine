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

/**
 * <p>Description: 字体资源 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/21 16:00
 */
public enum CaptchaFont {
    /**
     * 内置字体类型
     */
    ACTION("Action.ttf"),
    BEATAE("Beatae.ttf"),
    EPILOG("Epilog.ttf"),
    FRESNEL("Fresnel.ttf"),
    HEADACHE("Headache.ttf"),
    LEXOGRAPHER("Lexographer.ttf"),
    PREFIX("Prefix"),
    PROG_BOT("ProgBot"),
    ROBOT_TEACHER("RobotTeacher.ttf"),
    SCANDAL("Scandal.ttf");

    private final String fontName;

    CaptchaFont(String fontName) {
        this.fontName = fontName;
    }

    public String getFontName() {
        return fontName;
    }
}
