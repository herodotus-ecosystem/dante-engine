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

package cn.herodotus.engine.captcha.behavior.dto;

import cn.herodotus.engine.captcha.core.dto.Captcha;
import com.google.common.base.MoreObjects;

/**
 * <p>Description: 文字点选验证码返回前台信息 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/14 11:35
 */
public class WordClickCaptcha extends Captcha {

    /**
     * 文字点选验证码生成的带文字背景图。
     */
    private String wordClickImageBase64;

    /**
     * 文字点选验证码文字
     */
    private String words;

    /**
     * 需要点击的文字数量
     */
    private Integer wordsCount;

    public String getWordClickImageBase64() {
        return wordClickImageBase64;
    }

    public void setWordClickImageBase64(String wordClickImageBase64) {
        this.wordClickImageBase64 = wordClickImageBase64;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public Integer getWordsCount() {
        return wordsCount;
    }

    public void setWordsCount(Integer wordsCount) {
        this.wordsCount = wordsCount;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("wordClickImageBase64", wordClickImageBase64)
                .add("words", words)
                .add("wordsCount", wordsCount)
                .toString();
    }
}
