/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.engine.supplier.upms.rest.dto;

import cn.herodotus.engine.assistant.core.definition.domain.AbstractDto;
import cn.herodotus.engine.supplier.upms.logic.entity.hr.SysDepartment;
import cn.herodotus.engine.supplier.upms.logic.entity.hr.SysEmployee;
import cn.herodotus.engine.supplier.upms.logic.entity.hr.SysOwnership;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Description: 设置人员归属参数BO对象 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/9/24 17:31
 */
@Schema(description = "增加人员归属参数BO对象")
public class AllocatableDeploy extends AbstractDto {

    @NotNull(message = "单位ID不能为空")
    @Schema(description = "单位ID")
    private String organizationId;

    @NotNull(message = "部门ID不能为空")
    @Schema(description = "部门ID")
    private String departmentId;

    @Schema(description = "配置的人员列表")
    private List<SysEmployee> employees;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public List<SysEmployee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<SysEmployee> employees) {
        this.employees = employees;
    }

    public List<SysEmployee> getAllocatable() {
        if (CollectionUtils.isNotEmpty(this.employees)) {
            return employees.stream().peek(employee -> {
                SysDepartment sysDepartment = new SysDepartment();
                sysDepartment.setDepartmentId(this.departmentId);
                Set<SysDepartment> sysDepartments = employee.getDepartments();
                if (CollectionUtils.isEmpty(sysDepartments)) {
                    sysDepartments = new HashSet<>();
                }
                sysDepartments.add(sysDepartment);
                employee.setDepartments(sysDepartments);
            }).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    public List<SysOwnership> getOwnerships() {
        if (CollectionUtils.isNotEmpty(this.employees) ) {
            return this.employees.stream().map(employee -> {
                SysOwnership sysOwnership = new SysOwnership();
                sysOwnership.setEmployeeId(employee.getEmployeeId());
                sysOwnership.setDepartmentId(this.departmentId);
                sysOwnership.setOrganizationId(this.organizationId);
                return sysOwnership;
            }).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("organizationId", organizationId)
                .add("departmentId", departmentId)
                .toString();
    }
}
