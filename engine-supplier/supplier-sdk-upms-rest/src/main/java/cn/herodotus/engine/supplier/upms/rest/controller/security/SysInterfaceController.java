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

package cn.herodotus.engine.supplier.upms.rest.controller.security;

import cn.herodotus.engine.data.core.service.WriteableService;
import cn.herodotus.engine.rest.core.controller.BaseWriteableRestController;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysInterface;
import cn.herodotus.engine.supplier.upms.logic.service.security.SysInterfaceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: 系统应用程序接口 Controller </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/3/9 5:05
 */
@RestController
@RequestMapping("/security/interface")
@Tags({
        @Tag(name = "用户安全管理接口"),
        @Tag(name = "系统接口管理接口")
})
public class SysInterfaceController extends BaseWriteableRestController<SysInterface, String> {

    private final SysInterfaceService sysInterfaceService;

    public SysInterfaceController(SysInterfaceService sysInterfaceService) {
        this.sysInterfaceService = sysInterfaceService;
    }

    @Override
    public WriteableService<SysInterface, String> getWriteableService() {
        return sysInterfaceService;
    }
}
