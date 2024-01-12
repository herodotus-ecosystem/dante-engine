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

package cn.herodotus.engine.captcha.core.provider;

import cn.herodotus.stirrup.kernel.engine.utils.ResourceUtils;
import cn.herodotus.engine.captcha.core.definition.enums.CaptchaResource;
import cn.herodotus.engine.captcha.core.definition.enums.FontStyle;
import cn.herodotus.engine.captcha.core.properties.CaptchaProperties;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.data.id.IdUtil;
import org.dromara.hutool.core.io.IORuntimeException;
import org.dromara.hutool.core.io.file.FileUtil;
import org.dromara.hutool.extra.management.ManagementUtil;
import org.dromara.hutool.swing.img.FontUtil;
import org.dromara.hutool.swing.img.ImgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Description: 验证码静态资源加载器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/22 18:52
 */
@Component
public class ResourceProvider implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(ResourceProvider.class);

    private static final String FONT_RESOURCE = "classpath*:/fonts/*.ttf";
    private static final String FONT_FOLDER = "/usr/share/fonts/herodotus/";

    private final Map<String, String[]> imageIndexes = new ConcurrentHashMap<>();
    private final Map<String, String> jigsawOriginalImages = new ConcurrentHashMap<>();
    private final Map<String, String> jigsawTemplateImages = new ConcurrentHashMap<>();
    private final Map<String, String> wordClickImages = new ConcurrentHashMap<>();
    private final CaptchaProperties captchaProperties;
    private Map<String, Font> fonts = new ConcurrentHashMap<>();

    public ResourceProvider(CaptchaProperties captchaProperties) {
        this.captchaProperties = captchaProperties;
    }

    private static Map<String, String> getImages(String location) {
        if (ResourceUtils.isClasspathAllUrl(location)) {
            try {
                Resource[] resources = ResourceUtils.getResources(location);
                Map<String, String> images = new ConcurrentHashMap<>();
                if (ArrayUtils.isNotEmpty(resources)) {
                    Arrays.stream(resources).forEach(resource -> {
                        String data = ResourceUtils.toBase64(resource);
                        if (StringUtils.isNotBlank(data)) {
                            images.put(IdUtil.fastSimpleUUID(), data);
                        }
                    });
                }
                return images;
            } catch (IOException e) {
                log.error("[Herodotus] |- Analysis the  location [{}] catch io error!", location, e);
            }
        }

        return new ConcurrentHashMap<>(8);
    }

    private static Font getFont(Resource resource) {

        try {
            return FontUtil.createFont(resource.getInputStream());
        } catch (IORuntimeException e) {
            // 虽然 java.awt.Font 抛出的是 IOException, 因为使用 Hutool FontUtil 将错误又包装了一次。所以出错时必须要拦截 IORuntimeException，否则会导致错误不被拦截直接抛出，应用启动失败。
            log.warn("[Herodotus] |- Can not read font in the resources folder, maybe in docker.");
            // TODO: 2022-10-21 尝试在 docker alpine 下解决字体问题的多种方式之一。目前改用 debian，下面代码已经不再需要。暂留，确保确实没有问题后再做处理
            Font fontInfileSystem = getFontUnderDocker(resource.getFilename());
            if (ObjectUtils.isNotEmpty(fontInfileSystem)) {
                return fontInfileSystem;
            }
        } catch (IOException e) {
            log.error("[Herodotus] |- Resource object in resources folder catch io error!", e);
        }

        return null;
    }

    private static Font getFontUnderDocker(String filename) {
        if (ManagementUtil.getOsInfo().isLinux()) {
            String path = FONT_FOLDER + filename;

            File file = new File(path);
            if (ObjectUtils.isNotEmpty(file) && FileUtil.exists(file)) {
                System.out.println(file.getAbsolutePath());
                try {
                    Font font = FontUtil.createFont(file);
                    log.debug("[Herodotus] |- Read font [{}] under the DOCKER.", font.getFontName());
                    return font;
                } catch (IORuntimeException e) {
                    log.error("[Herodotus] |- Read font under the DOCKER catch error.");
                } catch (NullPointerException e) {
                    log.error("[Herodotus] |- Read font under the DOCKER catch null error.");
                }
            }
        }
        return null;
    }

    private static Map<String, Font> getFonts(String location) {

        if (ResourceUtils.isClasspathAllUrl(location)) {
            try {
                Resource[] resources = ResourceUtils.getResources(location);
                Map<String, Font> fonts = new ConcurrentHashMap<>();
                if (ArrayUtils.isNotEmpty(resources)) {
                    Arrays.stream(resources).forEach(resource -> {
                        Font font = getFont(resource);
                        if (ObjectUtils.isNotEmpty(font)) {
                            fonts.put(resource.getFilename(), font);
                        }
                    });
                }
                return fonts;
            } catch (IOException e) {
                log.error("[Herodotus] |- Analysis the  location [{}] catch io error!", location, e);
            }
        }

        return new ConcurrentHashMap<>(8);
    }

    public CaptchaProperties getCaptchaProperties() {
        return captchaProperties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        String systemName = ManagementUtil.getOsInfo().getName();
        log.debug("[Herodotus] |- Before captcha resource loading, check system. Current system is [{}]", systemName);

        log.debug("[Herodotus] |- Captcha resource loading is BEGIN！");

        loadImages(jigsawOriginalImages, getCaptchaProperties().getJigsaw().getOriginalResource(), CaptchaResource.JIGSAW_ORIGINAL);

        loadImages(jigsawTemplateImages, getCaptchaProperties().getJigsaw().getTemplateResource(), CaptchaResource.JIGSAW_TEMPLATE);

        loadImages(wordClickImages, getCaptchaProperties().getWordClick().getImageResource(), CaptchaResource.WORD_CLICK);

        loadFonts();

        log.debug("[Herodotus] |- Jigsaw captcha resource loading is END！");
    }

    private void loadImages(Map<String, String> container, String location, CaptchaResource captchaResource) {
        Map<String, String> resource = getImages(location);

        if (MapUtils.isNotEmpty(resource)) {
            container.putAll(resource);
            log.debug("[Herodotus] |- {} load complete, total number is [{}]", captchaResource.getContent(), resource.size());
            imageIndexes.put(captchaResource.name(), resource.keySet().toArray(new String[0]));
        }
    }

    private void loadFonts() {
        if (MapUtils.isEmpty(fonts)) {
            this.fonts = getFonts(FONT_RESOURCE);
            log.debug("[Herodotus] |- Font load complete, total number is [{}]", fonts.size());
        }
    }

    private Font getDefaultFont(String fontName, int fontSize, FontStyle fontStyle) {
        if (StringUtils.isNotBlank(fontName)) {
            return new Font(fontName, fontStyle.getMapping(), fontSize);
        } else {
            return new Font("WenQuanYi Zen Hei", fontStyle.getMapping(), fontSize);
        }
    }

    public Font getFont(String fontName, int fontSize, FontStyle fontStyle) {
        if (MapUtils.isEmpty(fonts) || ObjectUtils.isEmpty(fonts.get(fontName))) {
            return getDefaultFont(fontName, fontSize, fontStyle);
        } else {
            return fonts.get(fontName).deriveFont(fontStyle.getMapping(), Integer.valueOf(fontSize).floatValue());
        }
    }

    public Font getFont(String fontName) {
        return getFont(fontName, 32, FontStyle.BOLD);
    }

    public Font getGraphicFont() {
        String fontName = getCaptchaProperties().getGraphics().getFont().getFontName();
        return this.getFont(fontName);
    }

    public Font getWaterMarkFont(int fontSize) {
        String fontName = getCaptchaProperties().getWatermark().getFontName();
        FontStyle fontStyle = getCaptchaProperties().getWatermark().getFontStyle();
        return getFont(fontName, fontSize, fontStyle);
    }

    public Font getChineseFont() {
        return getFont("WenQuanYi Zen Hei", 25, FontStyle.PLAIN);
    }

    private String getRandomBase64Image(Map<String, String> container, CaptchaResource captchaResource) {
        String[] data = this.imageIndexes.get(captchaResource.name());
        if (ArrayUtils.isNotEmpty(data)) {
            int randomInt = RandomProvider.randomInt(0, data.length);
            return container.get(data[randomInt]);
        }
        return null;
    }

    protected BufferedImage getRandomImage(Map<String, String> container, CaptchaResource captchaResource) {
        String data = getRandomBase64Image(container, captchaResource);
        if (StringUtils.isNotBlank(data)) {
            return ImgUtil.toImage(data);
        }

        return null;
    }

    public String getRandomBase64OriginalImage() {
        return getRandomBase64Image(jigsawOriginalImages, CaptchaResource.JIGSAW_ORIGINAL);
    }

    public String getRandomBase64TemplateImage() {
        return getRandomBase64Image(jigsawTemplateImages, CaptchaResource.JIGSAW_TEMPLATE);
    }

    public BufferedImage getRandomOriginalImage() {
        return getRandomImage(jigsawOriginalImages, CaptchaResource.JIGSAW_ORIGINAL);
    }

    public BufferedImage getRandomTemplateImage() {
        return getRandomImage(jigsawOriginalImages, CaptchaResource.JIGSAW_ORIGINAL);
    }

    public BufferedImage getRandomWordClickImage() {
        return getRandomImage(wordClickImages, CaptchaResource.WORD_CLICK);
    }
}
