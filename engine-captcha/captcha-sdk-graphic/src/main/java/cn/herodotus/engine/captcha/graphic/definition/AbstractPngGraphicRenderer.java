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

package cn.herodotus.engine.captcha.graphic.definition;

import cn.herodotus.engine.assistant.definition.constants.SymbolConstants;
import cn.herodotus.engine.captcha.core.definition.domain.Metadata;
import org.apache.commons.lang3.StringUtils;

import java.awt.image.BufferedImage;

/**
 * <p>Description: Png 类型图形验证码绘制器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/21 23:17
 */
public abstract class AbstractPngGraphicRenderer extends AbstractBaseGraphicRenderer {

    @Override
    public Metadata draw() {
        String[] drawCharacters = this.getDrawCharacters();

        BufferedImage bufferedImage = createPngBufferedImage(drawCharacters);

        String characters = StringUtils.join(drawCharacters, SymbolConstants.BLANK);

        Metadata metadata = new Metadata();
        metadata.setGraphicImageBase64(toBase64(bufferedImage));
        metadata.setCharacters(characters);

        return metadata;
    }
}
