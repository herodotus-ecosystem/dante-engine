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

import cn.herodotus.engine.captcha.core.definition.domain.Metadata;
import cn.herodotus.engine.captcha.core.dto.Captcha;
import cn.herodotus.engine.captcha.core.dto.Verification;

/**
 * <p>Description: 基础绘制器定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/21 15:36
 */
public interface Renderer {

    /**
     * 验证码绘制
     *
     * @return 绘制的验证码和校验信息 {@link Metadata}
     */
    Metadata draw();

    /**
     * 创建验证码
     *
     * @param key 验证码标识，用于标记在缓存中的存储
     * @return 验证码数据 {@link Captcha}
     */
    Captcha getCapcha(String key);

    /**
     * 验证码校验
     *
     * @param verification 前端传入的验证值
     * @return true 验证成功，返回错误信息
     */
    boolean verify(Verification verification);

    /**
     * 获取验证码类别
     *
     * @return 验证码类别
     */
    String getCategory();
}
