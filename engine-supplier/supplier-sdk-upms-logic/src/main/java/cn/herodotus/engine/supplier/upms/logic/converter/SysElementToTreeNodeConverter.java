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

import cn.herodotus.engine.assistant.definition.domain.view.vue.BaseMeta;
import cn.herodotus.engine.assistant.definition.domain.view.vue.ChildMeta;
import cn.herodotus.engine.assistant.definition.domain.view.vue.ParentMeta;
import cn.herodotus.engine.assistant.definition.domain.view.vue.RootMeta;
import cn.herodotus.engine.assistant.core.utils.WellFormedUtils;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysElement;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysRole;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.tree.TreeNode;
import org.springframework.core.convert.converter.Converter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Description: SysElement 转 TreeNode 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/23 16:47
 */
public class SysElementToTreeNodeConverter implements Converter<SysElement, TreeNode<String>> {
    @Override
    public TreeNode<String> convert(SysElement sysMenu) {
        TreeNode<String> treeNode = new TreeNode<>();
        treeNode.setId(sysMenu.getElementId());
        treeNode.setName(sysMenu.getPath());
        treeNode.setWeight(sysMenu.getRanking());
        treeNode.setParentId(WellFormedUtils.parentId(sysMenu.getParentId()));
        treeNode.setExtra(getExtra(sysMenu));
        return treeNode;
    }

    private Map<String, Object> getExtra(SysElement sysMenu) {
        Map<String, Object> extra = new HashMap<>();

        if (StringUtils.isBlank(sysMenu.getParentId())) {
            RootMeta meta = new RootMeta();
            meta.setSort(sysMenu.getRanking());
            setBaseMeta(sysMenu, meta);
            extra.put("meta", meta);
            extra.put("redirect", sysMenu.getRedirect());
        } else {
            if (BooleanUtils.isTrue(sysMenu.getHaveChild())) {
                ParentMeta meta = new ParentMeta();
                meta.setHideAllChild(sysMenu.getHideAllChild());
                setBaseMeta(sysMenu, meta);
                extra.put("meta", meta);
                extra.put("componentName", sysMenu.getName());
            } else {
                ChildMeta meta = new ChildMeta();
                meta.setDetailContent(sysMenu.getDetailContent());
                setBaseMeta(sysMenu, meta);
                extra.put("meta", meta);
                extra.put("componentName", sysMenu.getName());
            }
        }
        extra.put("componentPath", sysMenu.getComponent());

        Set<SysRole> sysRoles = sysMenu.getRoles();
        if (CollectionUtils.isNotEmpty(sysRoles)) {
            List<String> roles = sysRoles.stream().map(SysRole::getRoleCode).collect(Collectors.toList());
            extra.put("roles", roles);
        } else {
            extra.put("roles", new ArrayList<>());
        }

        return extra;
    }

    private void setBaseMeta(SysElement sysMenu, BaseMeta meta) {
        meta.setIcon(sysMenu.getIcon());
        meta.setTitle(sysMenu.getTitle());
        meta.setIgnoreAuth(sysMenu.getIgnoreAuth());
        meta.setNotKeepAlive(sysMenu.getNotKeepAlive());
    }
}
