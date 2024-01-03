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

package cn.herodotus.engine.captcha.behavior.renderer;

import cn.herodotus.engine.assistant.definition.constants.SymbolConstants;
import cn.herodotus.engine.captcha.core.definition.domain.Coordinate;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.util.RandomUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>Description: 文字点选信息混淆器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/17 12:19
 */
public class WordClickObfuscator {
    /**
     * 文字点选验证码文字坐标信息列表
     */
    private final List<Coordinate> coordinates;
    /**
     * 文字点选验证码校验文字
     */
    private final List<String> words;

    private String wordString;

    public WordClickObfuscator(List<String> originalWords, List<Coordinate> originalCoordinates) {
        this.coordinates = new ArrayList<>();
        this.words = new ArrayList<>();
        this.execute(originalWords, originalCoordinates);
    }

    private void execute(List<String> originalWords, List<Coordinate> originalCoordinates) {

        int[] indexes = RandomUtil.randomInts(originalWords.size());

        Arrays.stream(indexes).forEach(value -> {
            this.words.add(this.words.size(), originalWords.get(value));
            this.coordinates.add(this.coordinates.size(), originalCoordinates.get(value));
        });

        this.wordString = StringUtils.join(getWords(), SymbolConstants.COMMA);
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public List<String> getWords() {
        return words;
    }

    public String getWordString() {
        return this.wordString;
    }
}
