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

package cn.herodotus.engine.supplier.upms.logic.repository.hr;

import cn.herodotus.stirrup.data.kernel.repository.BaseRepository;
import cn.herodotus.engine.supplier.upms.logic.entity.hr.SysOrganization;
import cn.herodotus.engine.supplier.upms.logic.enums.OrganizationCategory;

import java.util.List;

/**
 * <p>Description: 单位管理Repository </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/20 11:37
 */
public interface SysOrganizationRepository extends BaseRepository<SysOrganization, String> {

    /**
     * 根据组织分类查询组织
     *
     * @param category 组织分类 {@link OrganizationCategory}
     * @return 组织列表
     */
    List<SysOrganization> findByCategory(OrganizationCategory category);

}
