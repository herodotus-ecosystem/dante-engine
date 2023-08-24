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
import cn.herodotus.engine.message.core.domain.RequestMapping;
import cn.herodotus.engine.supplier.upms.logic.converter.RequestMappingToSysInterfaceConverter;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysAttribute;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysInterface;
import cn.herodotus.engine.supplier.upms.logic.repository.security.SysInterfaceRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Description: 系统应用程序接口 Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/3/7 11:11
 */
@Service
public class SysInterfaceService extends BaseService<SysInterface, String> {

    private final SysInterfaceRepository sysInterfaceRepository;
    private final Converter<RequestMapping, SysInterface> toSysInterface;

    public SysInterfaceService(SysInterfaceRepository sysInterfaceRepository) {
        this.sysInterfaceRepository = sysInterfaceRepository;
        this.toSysInterface = new RequestMappingToSysInterfaceConverter();
    }

    @Override
    public BaseRepository<SysInterface, String> getRepository() {
        return sysInterfaceRepository;
    }

    /**
     * 查找SysSecurityAttribute中不存在的SysAuthority
     *
     * @return SysAuthority列表
     */
    public List<SysInterface> findAllocatable() {

        // exist sql 结构示例： SELECT * FROM article WHERE EXISTS (SELECT * FROM user WHERE article.uid = user.uid)
        Specification<SysInterface> specification = (root, criteriaQuery, criteriaBuilder) -> {

            // 构造Not Exist子查询
            Subquery<SysAttribute> subQuery = criteriaQuery.subquery(SysAttribute.class);
            Root<SysAttribute> subRoot = subQuery.from(SysAttribute.class);

            // 构造Not Exist 子查询的where条件
            Predicate subPredicate = criteriaBuilder.equal(subRoot.get("attributeId"), root.get("interfaceId"));
            subQuery.where(subPredicate);

            // 构造完整的子查询语句
            //这句话不加会报错，因为他不知道你子查询要查出什么字段。就是上面示例中的子查询中的“select *”的作用
            subQuery.select(subRoot.get("attributeId"));

            // 构造完整SQL
            // 正确的结构参考：SELECT * FROM sys_authority sa WHERE NOT EXISTS ( SELECT * FROM sys_metadata sm WHERE sm.metadata_id = sa.authority_id )
            criteriaQuery.where(criteriaBuilder.not(criteriaBuilder.exists(subQuery)));
            return criteriaQuery.getRestriction();
        };

        return this.findAll(specification);
    }

    public List<SysInterface> storeRequestMappings(Collection<RequestMapping> requestMappings) {
        List<SysInterface> sysAuthorities = toSysInterfaces(requestMappings);
        return saveAllAndFlush(sysAuthorities);
    }

    private List<SysInterface> toSysInterfaces(Collection<RequestMapping> requestMappings) {
        if (CollectionUtils.isNotEmpty(requestMappings)) {
            return requestMappings.stream().map(toSysInterface::convert).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
