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

package cn.herodotus.engine.supplier.upms.rest.controller.hr;

import cn.herodotus.engine.data.core.service.WriteableService;
import cn.herodotus.engine.rest.core.controller.BaseWriteableRestController;
import cn.herodotus.engine.supplier.upms.logic.entity.hr.SysOwnership;
import cn.herodotus.engine.supplier.upms.logic.service.hr.SysOwnershipService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: 人事归属Controller </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/15 16:36
 */
@RestController
@RequestMapping("/hr/ownership")
@Tag(name = "人事归属管理接口")
public class SysOwnershipController extends BaseWriteableRestController<SysOwnership, String> {

    private final SysOwnershipService sysOwnershipService;

    public SysOwnershipController(SysOwnershipService sysOwnershipService) {
        this.sysOwnershipService = sysOwnershipService;
    }

    @Override
    public WriteableService<SysOwnership, String> getWriteableService() {
        return this.sysOwnershipService;
    }
}
