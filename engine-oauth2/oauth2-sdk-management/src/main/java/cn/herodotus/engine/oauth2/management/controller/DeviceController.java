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

package cn.herodotus.engine.oauth2.management.controller;

import cn.herodotus.engine.assistant.definition.constants.DefaultConstants;
import cn.herodotus.engine.assistant.definition.constants.SymbolConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>Description: 设备激活 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/3/24 17:09
 */
@Controller
public class DeviceController {

    @GetMapping(DefaultConstants.DEVICE_ACTIVATION_URI)
    public String activate(@RequestParam(value = OAuth2ParameterNames.USER_CODE, required = false) String userCode) {
        if (StringUtils.isNotBlank(userCode)) {
            return "redirect:" + DefaultConstants.DEVICE_VERIFICATION_ENDPOINT + SymbolConstants.QUESTION + OAuth2ParameterNames.USER_CODE + SymbolConstants.EQUAL + userCode;
        }
        return "activation";
    }

    @GetMapping(value = DefaultConstants.DEVICE_VERIFICATION_SUCCESS_URI)
    public String activated() {
        return "activation-allowed";
    }
}
