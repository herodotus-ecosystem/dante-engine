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

package cn.herodotus.engine.supplier.upms.logic.converter;

import cn.herodotus.engine.assistant.core.component.router.BaseMeta;
import cn.herodotus.engine.assistant.core.component.router.ChildMeta;
import cn.herodotus.engine.assistant.core.component.router.ParentMeta;
import cn.herodotus.engine.assistant.core.component.router.RootMeta;
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
