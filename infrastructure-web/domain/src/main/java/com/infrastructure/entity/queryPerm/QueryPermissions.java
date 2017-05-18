package com.infrastructure.entity.queryPerm;


import com.infrastructure.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询权限实体
 * Created by suyl on 2016/2/25.
 */
public class QueryPermissions extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = -4843899260203632316L;

    /**
     * 查询权限名称
     */
    private String permName;

    /**
     * 查询权限标识
     */
    private String permIdentification;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 部门
     */
    private String deptName;

    /**
     * 名称
     */
    private String userName;

    /**
     * 企业名称
     */
    private String coName;

    /**
     * 开始创建时间
     */
    private String stertsignDate;

    /**
     * 结束创建时间
     */
    private String endsignDate;

    /**
     * perm1
     */
    private String perm1;

    /**
     * perm2
     */
    private String perm2;

    /**
     * perm2
     */
    private String perm3;


    public QueryPermissions() {
        super();
    }

    public QueryPermissions(String permName, String permIdentification, String remark, Date createTime, String deptName, String userName, String coName, String stertsignDate, String endsignDate, String perm1, String perm2, String perm3) {
        this.permName = permName;
        this.permIdentification = permIdentification;
        this.remark = remark;
        this.createTime = createTime;
        this.deptName = deptName;
        this.userName = userName;
        this.coName = coName;
        this.stertsignDate = stertsignDate;
        this.endsignDate = endsignDate;
        this.perm1 = perm1;
        this.perm2 = perm2;
        this.perm3 = perm3;
    }

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName;
    }

    public String getPermIdentification() {
        return permIdentification;
    }

    public void setPermIdentification(String permIdentification) {
        this.permIdentification = permIdentification;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCoName() {
        return coName;
    }

    public void setCoName(String coName) {
        this.coName = coName;
    }

    public String getStertsignDate() {
        return stertsignDate;
    }

    public void setStertsignDate(String stertsignDate) {
        this.stertsignDate = stertsignDate;
    }

    public String getEndsignDate() {
        return endsignDate;
    }

    public void setEndsignDate(String endsignDate) {
        this.endsignDate = endsignDate;
    }

    public String getPerm1() {
        return perm1;
    }

    public void setPerm1(String perm1) {
        this.perm1 = perm1;
    }

    public String getPerm2() {
        return perm2;
    }

    public void setPerm2(String perm2) {
        this.perm2 = perm2;
    }

    public String getPerm3() {
        return perm3;
    }

    public void setPerm3(String perm3) {
        this.perm3 = perm3;
    }

    @Override
    public String toString() {
        return "QueryPermissions{" +
                "permName='" + permName + '\'' +
                ", permIdentification='" + permIdentification + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", deptName='" + deptName + '\'' +
                ", userName='" + userName + '\'' +
                ", coName='" + coName + '\'' +
                ", stertsignDate='" + stertsignDate + '\'' +
                ", endsignDate='" + endsignDate + '\'' +
                ", perm1='" + perm1 + '\'' +
                ", perm2='" + perm2 + '\'' +
                ", perm3='" + perm3 + '\'' +
                '}';
    }
}
