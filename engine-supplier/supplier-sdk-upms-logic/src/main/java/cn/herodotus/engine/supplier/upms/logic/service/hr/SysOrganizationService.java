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

package cn.herodotus.engine.supplier.upms.logic.service.hr;

import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.engine.data.core.service.BaseService;
import cn.herodotus.engine.supplier.upms.logic.entity.hr.SysDepartment;
import cn.herodotus.engine.supplier.upms.logic.entity.hr.SysOrganization;
import cn.herodotus.engine.supplier.upms.logic.enums.OrganizationCategory;
import cn.herodotus.engine.supplier.upms.logic.repository.hr.SysOrganizationRepository;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: 单位管理服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/20 11:39
 */
@Service
public class SysOrganizationService extends BaseService<SysOrganization, String> {

    private final SysOrganizationRepository sysOrganizationRepository;
    private final SysOwnershipService sysOwnershipService;
    private final SysDepartmentService sysDepartmentService;

    public SysOrganizationService(SysOrganizationRepository sysOrganizationRepository, SysOwnershipService sysOwnershipService, SysDepartmentService sysDepartmentService) {
        this.sysOrganizationRepository = sysOrganizationRepository;
        this.sysOwnershipService = sysOwnershipService;
        this.sysDepartmentService = sysDepartmentService;
    }

    @Override
    public BaseRepository<SysOrganization, String> getRepository() {
        return sysOrganizationRepository;
    }

    public List<SysOrganization> findAll(OrganizationCategory organizationCategory) {
        if (ObjectUtils.isNotEmpty(organizationCategory)) {
            return sysOrganizationRepository.findByCategory(organizationCategory);
        } else {
            return sysOrganizationRepository.findAll();
        }
    }

    public Page<SysOrganization> findByCondition(int pageNumber, int pageSize, OrganizationCategory organizationCategory) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Specification<SysOrganization> specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (ObjectUtils.isNotEmpty(organizationCategory)) {
                predicates.add(criteriaBuilder.equal(root.get("category"), organizationCategory));
            }

            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
            return criteriaQuery.getRestriction();
        };

        return this.findByPage(specification, pageable);
    }

    /**
     * Transactional 注解 可以作用于接口、接口方法、类以及类方法上。当作用于类上时，该类的所有 public 方法将都具有该类型的事务属性，同时，我们也可以在方法级别使用该标注来覆盖类级别的定义。
     * <p>
     * 虽然@Transactional 注解可以作用于接口、接口方法、类以及类方法上，但是 Spring 建议不要在接口或者接口方法上使用该注解，因为这只有在使用基于接口的代理时它才会生效。另外， @Transactional注解应该只被应用到 public 方法上，这是由Spring AOP的本质决定的。如果你在 protected、private 或者默认可见性的方法上使用 @Transactional 注解，这将被忽略，也不会抛出任何异常。
     * <p>
     * 默认情况下，只有来自外部的方法调用才会被AOP代理捕获，也就是，类内部方法调用本类内部的其他方法并不会引起事务行为，即使被调用方法使用@Transactional注解进行修饰。
     * <p>
     * 作者：tuacy
     * 链接：<a href="https://www.jianshu.com/p/befc2d73e487">...</a>
     *
     * @param organizationId 单位ID
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String organizationId) {
        sysOwnershipService.deleteByOrganizationId(organizationId);
        super.deleteById(organizationId);
    }

    /**
     * 检测某个组织机构是否被其它数据关联。
     *
     * @param organizationId 单位ID
     * @return true 被其它数据使用，false 没有被使用。
     */
    public boolean isInUse(String organizationId) {
        List<SysDepartment> sysDepartments = sysDepartmentService.findAll(organizationId);
        return CollectionUtils.isNotEmpty(sysDepartments);
    }
}
