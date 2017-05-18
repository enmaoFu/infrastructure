package com.infrastructure.entity.system;


import com.infrastructure.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Set;

/**
 * User: 谭永强
 * Date: 2016/1/28
 * Time: 16:02
 */
public class Dept extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = 1918113694366679434L;

    private String name;//部门名称
    private String code;//部门编号
    private String parentId;//上级部门
    private String parentIds;//所有上级部门
    private String companyId;//所属工作
    private String remark;//备注

    private String companyName;//企业名称

    private Set<SysUser> userSet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Set<SysUser> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<SysUser> userSet) {
        this.userSet = userSet;
    }
}
