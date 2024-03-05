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

package cn.herodotus.engine.supplier.upms.logic.service.hr;

import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.engine.data.core.service.BaseService;
import cn.herodotus.engine.supplier.upms.logic.entity.hr.SysDepartment;
import cn.herodotus.engine.supplier.upms.logic.repository.hr.SysDepartmentRepository;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: 部门管理服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/20 11:50
 */
@Service
public class SysDepartmentService extends BaseService<SysDepartment, String> {

    private final SysDepartmentRepository sysDepartmentRepository;
    private final SysOwnershipService sysOwnershipService;

    public SysDepartmentService(SysDepartmentRepository sysDepartmentRepository, SysOwnershipService sysOwnershipService) {
        this.sysDepartmentRepository = sysDepartmentRepository;
        this.sysOwnershipService = sysOwnershipService;
    }

    @Override
    public BaseRepository<SysDepartment, String> getRepository() {
        return sysDepartmentRepository;
    }

    public Page<SysDepartment> findByCondition(int pageNumber, int pageSize, String organizationId) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Specification<SysDepartment> specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (ObjectUtils.isNotEmpty(organizationId)) {
                predicates.add(criteriaBuilder.equal(root.get("organizationId"), organizationId));
            }

            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
            return criteriaQuery.getRestriction();
        };

        return this.findByPage(specification, pageable);
    }

    public List<SysDepartment> findAll(String organizationId) {
        if (ObjectUtils.isNotEmpty(organizationId)) {
            return sysDepartmentRepository.findByOrganizationId(organizationId);
        } else {
            return sysDepartmentRepository.findAll();
        }
    }

    @Override
    public void deleteById(String departmentId) {
        sysOwnershipService.deleteByDepartmentId(departmentId);
        super.deleteById(departmentId);
    }
}
