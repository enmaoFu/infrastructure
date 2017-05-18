package com.infrastructure.entity.culture;

import com.infrastructure.common.entity.BaseEntity;
import com.infrastructure.entity.system.SysUser;

import java.io.Serializable;
import java.util.List;

/**
 * User: 谭永强
 * Date: 2016/2/15
 * Time: 16:10
 */
public class Group extends BaseEntity<String> implements Serializable {
    private static final long serialVersionUID = 1199193199236201778L;

    private String name;//群组名称
    private String createDate;//创建时间
    private String createUser;//创建人ID
    private String userName;//创建人名称
    private String companyId;//所属公司
    private String companyName;//公司名称
    private String remark;//备注

    private List<SysUser> userList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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

    public List<SysUser> getUserList() {
        return userList;
    }

    public void setUserList(List<SysUser> userList) {
        this.userList = userList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
