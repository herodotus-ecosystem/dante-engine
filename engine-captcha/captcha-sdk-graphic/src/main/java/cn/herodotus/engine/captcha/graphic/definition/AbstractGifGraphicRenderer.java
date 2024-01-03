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
import com.madgag.gif.fmsware.AnimatedGifEncoder;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.codec.binary.Base64;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.stream.IntStream;

/**
 * <p>Description: Gif 类型图形验证码绘制器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/21 23:25
 */
public abstract class AbstractGifGraphicRenderer extends AbstractBaseGraphicRenderer {

    @Override
    protected String getBase64ImagePrefix() {
        return BASE64_GIF_IMAGE_PREFIX;
    }

    @Override
    public Metadata draw() {

        String[] drawCharacters = this.getDrawCharacters();

        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        // gif编码类
        AnimatedGifEncoder gifEncoder = new AnimatedGifEncoder();
        // 生成字符
        gifEncoder.start(out);
        // 设置量化器取样间隔
        gifEncoder.setQuality(180);
        // 帧延迟 (默认100)
        int delay = 100;
        //设置帧延迟
        gifEncoder.setDelay(delay);
        //帧循环次数
        gifEncoder.setRepeat(0);

        IntStream.range(0, drawCharacters.length).forEach(i -> {
            BufferedImage frame = createGifBufferedImage(drawCharacters, i);
            gifEncoder.addFrame(frame);
            frame.flush();
        });

        gifEncoder.finish();

        String characters = StringUtils.join(drawCharacters, SymbolConstants.BLANK);

        Metadata metadata = new Metadata();
        metadata.setGraphicImageBase64(getBase64ImagePrefix() + Base64.encode(out.toByteArray()));
        metadata.setCharacters(characters);
        return metadata;
    }
}
