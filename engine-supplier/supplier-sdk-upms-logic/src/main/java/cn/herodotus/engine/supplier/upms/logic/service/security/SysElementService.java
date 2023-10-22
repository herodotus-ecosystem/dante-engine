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

package cn.herodotus.engine.supplier.upms.logic.service.security;

import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.engine.data.core.service.BaseService;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysElement;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysRole;
import cn.herodotus.engine.supplier.upms.logic.repository.security.SysElementRepository;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>Description: SysMenuService </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/14 15:55
 */
@Service
public class SysElementService extends BaseService<SysElement, String> {

    private final SysElementRepository sysElementRepository;

    public SysElementService(SysElementRepository sysElementRepository) {
        this.sysElementRepository = sysElementRepository;
    }

    @Override
    public BaseRepository<SysElement, String> getRepository() {
        return sysElementRepository;
    }

    public Page<SysElement> findByCondition(int pageNumber, int pageSize, String path, String title) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Specification<SysElement> specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (ObjectUtils.isNotEmpty(path)) {
                predicates.add(criteriaBuilder.like(root.get("path"), like(path)));
            }

            if (ObjectUtils.isNotEmpty(title)) {
                predicates.add(criteriaBuilder.like(root.get("title"), like(title)));
            }

            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
            return criteriaQuery.getRestriction();
        };

        return this.findByPage(specification, pageable);
    }

    public SysElement assign(String elementId, String[] roles) {

        Set<SysRole> sysRoles = new HashSet<>();
        for (String role : roles) {
            SysRole sysRole = new SysRole();
            sysRole.setRoleId(role);
            sysRoles.add(sysRole);
        }

        SysElement sysElement = findById(elementId);
        sysElement.setRoles(sysRoles);

        return saveAndFlush(sysElement);
    }
}
