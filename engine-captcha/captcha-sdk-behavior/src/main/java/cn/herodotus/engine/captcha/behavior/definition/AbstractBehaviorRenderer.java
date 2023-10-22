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

package cn.herodotus.engine.captcha.behavior.definition;

import cn.herodotus.engine.captcha.core.definition.AbstractRenderer;
import com.alicp.jetcache.anno.CacheType;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * <p>Description: 验证码通用基础类 </p>
 *
 * @param <K> 验证码缓存对应Key值的类型。
 * @param <V> 验证码缓存存储数据的值的类型
 * @author : gengwei.zheng
 * @date : 2021/12/11 13:53
 */
public abstract class AbstractBehaviorRenderer<K, V> extends AbstractRenderer<K, V> {

    public AbstractBehaviorRenderer(String cacheName) {
        super(cacheName);
    }

    public AbstractBehaviorRenderer(String cacheName, CacheType cacheType) {
        super(cacheName, cacheType);
    }

    public AbstractBehaviorRenderer(String cacheName, CacheType cacheType, Duration expire) {
        super(cacheName, cacheType, expire);
    }

    protected int getEnOrZhLength(String s) {
        int enCount = 0;
        int zhCount = 0;
        for (int i = 0; i < s.length(); i++) {
            int length = String.valueOf(s.charAt(i)).getBytes(StandardCharsets.UTF_8).length;
            if (length > 1) {
                zhCount++;
            } else {
                enCount++;
            }
        }
        int zhOffset = getHalfWatermarkFontSize() * zhCount + 5;
        int enOffset = enCount * 8;
        return zhOffset + enOffset;
    }

    private int getWatermarkFontSize() {
        return getCaptchaProperties().getWatermark().getFontSize();
    }

    private int getHalfWatermarkFontSize() {
        return getWatermarkFontSize() / 2;
    }

    protected void addWatermark(Graphics graphics, int width, int height) {
        int fontSize = getHalfWatermarkFontSize();
        Font watermakFont = this.getResourceProvider().getWaterMarkFont(fontSize);
        graphics.setFont(watermakFont);
        graphics.setColor(Color.white);
        String content = this.getCaptchaProperties().getWatermark().getContent();
        graphics.drawString(content, width - getEnOrZhLength(content), height - getHalfWatermarkFontSize() + 7);
    }

    protected boolean isUnderOffset(int actualValue, int standardValue, int threshold) {
        return actualValue < standardValue - threshold;
    }

    protected boolean isOverOffset(int actualValue, int standardValue, int threshold) {
        return actualValue > standardValue + threshold;
    }

    protected boolean isDeflected(int actualValue, int standardValue, int threshold) {
        return isUnderOffset(actualValue, standardValue, threshold) || isOverOffset(actualValue, standardValue, threshold);
    }
}
