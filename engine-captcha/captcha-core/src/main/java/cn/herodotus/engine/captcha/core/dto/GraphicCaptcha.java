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
