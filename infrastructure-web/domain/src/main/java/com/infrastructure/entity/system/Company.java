package com.infrastructure.entity.system;


import com.infrastructure.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 公司实体
 *
 * @author tyq
 * @date 2016/1/14
 */
public class Company extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = -5081937236877736474L;
    private String name; // 公司名称
    private String abbreviation; // 简称
    private String address; // 地址
    private String parent; // 直接上级公司
    private String parents; // 所有上级公司
    private String motto; // 经营范围
    private String master; // 法定代表
    private String createDate; // 创建时间

    private List<Role> roleList;//公司角色

    private List<SysUser> userList;//公司用户

    private List<Dept> deptList;//公司

    public List<Dept> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<Dept> deptList) {
        this.deptList = deptList;
    }

    public List<SysUser> getUserList() {
        return userList;
    }

    public void setUserList(List<SysUser> userList) {
        this.userList = userList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getParents() {
        return parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}
