package com.infrastructure.entity.system;

import com.infrastructure.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Set;

/**
 * 系统用户实体
 *
 * @author tyq
 * @date 2016/1/14
 */
public class SysUser extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = 336619963954639078L;
    private String prefix;//前缀
    private String login; // 登录名
    private String password; // 密码
    private String username; // 用户名
    private String sex;//性别 male:男 female:女
    private Integer age;//年龄
    private String birthday;//生日
    private String phone; // 手机
    private String email; // 邮箱
    private Boolean available = Boolean.TRUE; // 是否可用
    private String creataDate;//创建时间
    private String updateDate;//修改时间

    private String deptId;//部门ID
    private String deptName;//部门名称

    private String parentId;//上级ID
    private String parentRoleId;//上级角色ID
    private String parentName;//上级名称

    private String companyId;//所属公司ID
    private String companyName;//所属公司名称
    private String companyNumber;//所属公司名称
    private Company company;//所属公司

    private String roleId;//角色ID
    private Set<Role> roleSet;//角色
    private String roleName;//角色名称

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUsername() {
        return username;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getCreataDate() {
        return creataDate;
    }

    public void setCreataDate(String creataDate) {
        this.creataDate = creataDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getParentRoleId() {
        return parentRoleId;
    }

    public void setParentRoleId(String parentRoleId) {
        this.parentRoleId = parentRoleId;
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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
