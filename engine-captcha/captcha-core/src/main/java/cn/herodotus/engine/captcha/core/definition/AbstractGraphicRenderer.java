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

package cn.herodotus.engine.captcha.core.definition;

import cn.herodotus.engine.captcha.core.constants.CaptchaConstants;
import cn.herodotus.engine.captcha.core.definition.domain.Metadata;
import cn.herodotus.engine.captcha.core.dto.Captcha;
import cn.herodotus.engine.captcha.core.dto.GraphicCaptcha;
import cn.herodotus.engine.captcha.core.dto.Verification;
import cn.herodotus.engine.captcha.core.exception.CaptchaHasExpiredException;
import cn.herodotus.engine.captcha.core.exception.CaptchaIsEmptyException;
import cn.herodotus.engine.captcha.core.exception.CaptchaMismatchException;
import cn.herodotus.engine.captcha.core.exception.CaptchaParameterIllegalException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.data.id.IdUtil;

import java.awt.*;

/**
 * <p>Description: 抽象的图形验证码 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/20 20:42
 */
public abstract class AbstractGraphicRenderer extends AbstractRenderer<String, String> {

    private GraphicCaptcha graphicCaptcha;

    public AbstractGraphicRenderer() {
        super(CaptchaConstants.CACHE_NAME_CAPTCHA_GRAPHIC);
    }

    protected Font getFont() {
        return this.getResourceProvider().getGraphicFont();
    }

    protected int getWidth() {
        return this.getCaptchaProperties().getGraphics().getWidth();
    }

    protected int getHeight() {
        return this.getCaptchaProperties().getGraphics().getHeight();
    }

    protected int getLength() {
        return this.getCaptchaProperties().getGraphics().getLength();
    }

    @Override
    public Captcha getCapcha(String key) {
        String identity = key;
        if (StringUtils.isBlank(identity)) {
            identity = IdUtil.fastUUID();
        }

        this.create(identity);
        return getGraphicCaptcha();
    }

    @Override
    public boolean verify(Verification verification) {

        if (ObjectUtils.isEmpty(verification) || StringUtils.isEmpty(verification.getIdentity())) {
            throw new CaptchaParameterIllegalException("Parameter value is illegal");
        }

        if (StringUtils.isEmpty(verification.getCharacters())) {
            throw new CaptchaIsEmptyException("Captcha is empty");
        }

        String store = this.get(verification.getIdentity());
        if (StringUtils.isEmpty(store)) {
            throw new CaptchaHasExpiredException("Stamp is invalid!");
        }

        this.delete(verification.getIdentity());

        String real = verification.getCharacters();

        if (!StringUtils.equalsIgnoreCase(store, real)) {
            throw new CaptchaMismatchException();
        }

        return true;
    }

    private GraphicCaptcha getGraphicCaptcha() {
        return graphicCaptcha;
    }

    protected void setGraphicCaptcha(GraphicCaptcha graphicCaptcha) {
        this.graphicCaptcha = graphicCaptcha;
    }

    @Override
    public String nextStamp(String key) {
        Metadata metadata = draw();

        GraphicCaptcha graphicCaptcha = new GraphicCaptcha();
        graphicCaptcha.setIdentity(key);
        graphicCaptcha.setGraphicImageBase64(metadata.getGraphicImageBase64());
        graphicCaptcha.setCategory(getCategory());
        this.setGraphicCaptcha(graphicCaptcha);

        return metadata.getCharacters();
    }
}