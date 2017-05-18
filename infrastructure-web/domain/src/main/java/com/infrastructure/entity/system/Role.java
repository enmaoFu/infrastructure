package com.infrastructure.entity.system;


import com.infrastructure.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Set;

/**
 * 角色实体
 *
 * @author tyq
 * @date 2016/1/14
 */
public class Role extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = -452757325093957956L;

    private String name; // 名称
    private String description; // 描述 系统管理员
    private String remark; // 备注信息
    private String companyId; //所属企业
    private String deptId;//所属部门
    private String parentId;//上级岗位
    private String isManager="N";//是否是部门经理
    private Integer sorted;//排序标识
    private String isSys = "N";//是否是管理员 Y/N

    private String companyName; //企业名称
    private String deptName;//部门名称

    private Set<Resource> resources; // 拥有的资源权限

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Set<Resource> getResources() {
        return resources;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getIsManager() {
        return isManager;
    }

    public void setIsManager(String isManager) {
        this.isManager = isManager;
    }

    public Integer getSorted() {
        return sorted;
    }

    public void setSorted(Integer sorted) {
        this.sorted = sorted;
    }

    public String getIsSys() {
        return isSys;
    }

    public void setIsSys(String isSys) {
        this.isSys = isSys;
    }
}
