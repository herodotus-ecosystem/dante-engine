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

package cn.herodotus.engine.captcha.core.definition;

import cn.herodotus.engine.captcha.core.constants.CaptchaConstants;
import cn.herodotus.engine.captcha.core.definition.domain.Metadata;
import cn.herodotus.engine.captcha.core.dto.Captcha;
import cn.herodotus.engine.captcha.core.dto.GraphicCaptcha;
import cn.herodotus.engine.captcha.core.dto.Verification;
import cn.herodotus.engine.captcha.core.exception.*;
import cn.herodotus.engine.captcha.core.exception.CaptchaHasExpiredException;
import cn.herodotus.engine.captcha.core.exception.CaptchaIsEmptyException;
import cn.hutool.core.util.IdUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

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