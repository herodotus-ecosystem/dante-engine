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

package cn.herodotus.engine.supplier.upms.logic.service.assistant;

import cn.herodotus.stirrup.data.kernel.enums.DataItemStatus;
import cn.herodotus.engine.supplier.upms.logic.enums.Gender;
import cn.herodotus.engine.supplier.upms.logic.enums.Identity;
import cn.herodotus.engine.supplier.upms.logic.enums.OrganizationCategory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: Upms 常量 服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/25 15:37
 */
@Service
public class ConstantsService {

    private static final List<Map<String, Object>> STATUS_ENUM = DataItemStatus.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> GENDER_ENUM = Gender.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> IDENTITY_ENUM = Identity.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> ORGANIZATION_CATEGORY_ENUM = OrganizationCategory.getPreprocessedJsonStructure();

    public Map<String, Object> getAllEnums() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("status", STATUS_ENUM);
        map.put("gender", GENDER_ENUM);
        map.put("identity", IDENTITY_ENUM);
        map.put("organizationCategory", ORGANIZATION_CATEGORY_ENUM);
        return map;
    }
}
