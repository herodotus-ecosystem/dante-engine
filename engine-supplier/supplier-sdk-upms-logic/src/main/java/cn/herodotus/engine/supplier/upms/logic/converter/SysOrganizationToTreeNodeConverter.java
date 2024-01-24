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

package cn.herodotus.engine.supplier.upms.logic.converter;

import cn.herodotus.stirrup.core.foundation.utils.WellFormedUtils;
import cn.herodotus.engine.supplier.upms.logic.entity.hr.SysOrganization;
import org.dromara.hutool.core.tree.TreeNode;
import org.springframework.core.convert.converter.Converter;


/**
 * <p>Description: SysOrganization 转 TreeNode 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/30 10:37
 */
public class SysOrganizationToTreeNodeConverter implements Converter<SysOrganization, TreeNode<String>> {
    @Override
    public TreeNode<String> convert(SysOrganization sysOrganization) {
        TreeNode<String> treeNode = new TreeNode<>();
        treeNode.setId(sysOrganization.getOrganizationId());
        treeNode.setName(sysOrganization.getOrganizationName());
        treeNode.setParentId(WellFormedUtils.parentId(sysOrganization.getParentId()));
        return treeNode;
    }
}
