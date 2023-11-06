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

package cn.herodotus.engine.captcha.core.dto;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * <p>Description: 图形验证码 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/21 22:19
 */
public class GraphicCaptcha extends Captcha {

    /**
     * 图形验证码成的图。
     */
    private String graphicImageBase64;

    public GraphicCaptcha() {
    }

    public String getGraphicImageBase64() {
        return graphicImageBase64;
    }

    public void setGraphicImageBase64(String graphicImageBase64) {
        this.graphicImageBase64 = graphicImageBase64;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GraphicCaptcha that = (GraphicCaptcha) o;
        return Objects.equal(graphicImageBase64, that.graphicImageBase64);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(graphicImageBase64);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("graphicImageBase64", graphicImageBase64)
                .toString();
    }
}
