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

package cn.herodotus.engine.supplier.upms.logic.service.security;

import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.engine.data.core.service.BaseService;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysAttribute;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysPermission;
import cn.herodotus.engine.supplier.upms.logic.repository.security.SysAttributeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>Description: SysAttributeService </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/4 6:48
 */
@Service
public class SysAttributeService extends BaseService<SysAttribute, String> {

    private static final Logger log = LoggerFactory.getLogger(SysAttributeService.class);

    private final SysAttributeRepository sysAttributeRepository;

    @Autowired
    public SysAttributeService(SysAttributeRepository sysAttributeRepository) {
        this.sysAttributeRepository = sysAttributeRepository;
    }

    @Override
    public BaseRepository<SysAttribute, String> getRepository() {
        return this.sysAttributeRepository;
    }

    public SysAttribute assign(String attributeId, String[] permissionIds) {

        Set<SysPermission> sysPermissions = new HashSet<>();
        for (String permissionId : permissionIds) {
            SysPermission sysPermission = new SysPermission();
            sysPermission.setPermissionId(permissionId);
            sysPermissions.add(sysPermission);
        }

        SysAttribute sysAttribute = findById(attributeId);
        sysAttribute.setPermissions(sysPermissions);

        log.debug("[Herodotus] |- SysAttribute Service assign.");
        return saveOrUpdate(sysAttribute);
    }

    public List<SysAttribute> batchSaveOrUpdate(List<SysAttribute> domains) {
        List<SysAttribute> sysAttributes = sysAttributeRepository.saveAllAndFlush(domains);
        log.debug("[Herodotus] |- SysAttribute Service batchSaveOrUpdate.");
        return sysAttributes;
    }

    public List<SysAttribute> findByAttributeIdIn(List<String> ids) {
        log.debug("[Herodotus] |- SysAttribute Service findByAttributeIdIn.");
        return sysAttributeRepository.findByAttributeIdIn(ids);
    }
}
