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

package cn.herodotus.engine.captcha.graphic.renderer;

import cn.herodotus.engine.captcha.core.definition.enums.CaptchaCategory;
import cn.herodotus.engine.captcha.graphic.definition.AbstractGifGraphicRenderer;
import org.springframework.stereotype.Component;

/**
 * <p>Description: Gif 类型验证码绘制器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/20 22:54
 */
@Component
public class SpecGifCaptchaRenderer extends AbstractGifGraphicRenderer {

    @Override
    public String getCategory() {
        return CaptchaCategory.SPEC_GIF.getConstant();
    }

    @Override
    protected String[] getDrawCharacters() {
        return this.getCharCharacters();
    }
}
