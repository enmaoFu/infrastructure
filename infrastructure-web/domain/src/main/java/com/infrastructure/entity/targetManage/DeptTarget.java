package com.infrastructure.entity.targetManage;


import com.infrastructure.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 部门目标
 * Created by suyl on 2016/4/1.
 */
public class DeptTarget extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = -4843899260203632316L;

    /**
     * 公司目标编号
     */
    private String companyTargetId;
    /**
     * 部门目标标题
     */
    private String deptTargetTitle;
    /**
     * 部门目标年份
     */
    private String deptTargetYear;
    /**
     * 部门目标时间段(slot_year:年,slot_half_year:半年)
     */
    private String deptTargetTimeSlot;
    /**
     * 部门目标类型(type_economic_goals:经济目标,type_management_objectives:管理目标)
     */
    private String deptTargetType;
    /**
     * 企业编号
     */
    private String companyId;
    /**
     * 所属部门
     */
    private String deptId;
    /**
     * 创建人
     */
    private String userId;
    /**
     * 部门目标内容
     */
    private String deptTargetContent;
    /**
     * 金额
     */
    private String deptTargetAmount;
    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 名称
     */
    private String userName;

    /**
     * 企业名称
     */
    private String coName;
    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 开始创建时间
     */
    private String stertsignDate;

    /**
     * 结束创建时间
     */
    private String endsignDate;

    /**
     * deptTarget1
     */
    private String deptTarget1;

    /**
     * deptTarget2
     */
    private String deptTarget2;

    /**
     * deptTarget3
     */
    private String deptTarget3;

    /**
     * deptTarget4
     */
    private String deptTarget4;

    public DeptTarget() {
        super();
    }

    public DeptTarget(String companyTargetId, String deptTargetTitle, String deptTargetYear, String deptTargetTimeSlot, String deptTargetType, String companyId, String deptId, String userId, String deptTargetContent, String deptTargetAmount, String remark, Date createTime, String userName, String coName, String deptName, String stertsignDate, String endsignDate, String deptTarget1, String deptTarget2, String deptTarget3, String deptTarget4) {
        this.companyTargetId = companyTargetId;
        this.deptTargetTitle = deptTargetTitle;
        this.deptTargetYear = deptTargetYear;
        this.deptTargetTimeSlot = deptTargetTimeSlot;
        this.deptTargetType = deptTargetType;
        this.companyId = companyId;
        this.deptId = deptId;
        this.userId = userId;
        this.deptTargetContent = deptTargetContent;
        this.deptTargetAmount = deptTargetAmount;
        this.remark = remark;
        this.createTime = createTime;
        this.userName = userName;
        this.coName = coName;
        this.deptName = deptName;
        this.stertsignDate = stertsignDate;
        this.endsignDate = endsignDate;
        this.deptTarget1 = deptTarget1;
        this.deptTarget2 = deptTarget2;
        this.deptTarget3 = deptTarget3;
        this.deptTarget4 = deptTarget4;
    }

    public String getCompanyTargetId() {
        return companyTargetId;
    }

    public void setCompanyTargetId(String companyTargetId) {
        this.companyTargetId = companyTargetId;
    }

    public String getDeptTargetTitle() {
        return deptTargetTitle;
    }

    public void setDeptTargetTitle(String deptTargetTitle) {
        this.deptTargetTitle = deptTargetTitle;
    }

    public String getDeptTargetYear() {
        return deptTargetYear;
    }

    public void setDeptTargetYear(String deptTargetYear) {
        this.deptTargetYear = deptTargetYear;
    }

    public String getDeptTargetTimeSlot() {
        return deptTargetTimeSlot;
    }

    public void setDeptTargetTimeSlot(String deptTargetTimeSlot) {
        this.deptTargetTimeSlot = deptTargetTimeSlot;
    }

    public String getDeptTargetType() {
        return deptTargetType;
    }

    public void setDeptTargetType(String deptTargetType) {
        this.deptTargetType = deptTargetType;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeptTargetContent() {
        return deptTargetContent;
    }

    public void setDeptTargetContent(String deptTargetContent) {
        this.deptTargetContent = deptTargetContent;
    }

    public String getDeptTargetAmount() {
        return deptTargetAmount;
    }

    public void setDeptTargetAmount(String deptTargetAmount) {
        this.deptTargetAmount = deptTargetAmount;
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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public String getDeptTarget1() {
        return deptTarget1;
    }

    public void setDeptTarget1(String deptTarget1) {
        this.deptTarget1 = deptTarget1;
    }

    public String getDeptTarget2() {
        return deptTarget2;
    }

    public void setDeptTarget2(String deptTarget2) {
        this.deptTarget2 = deptTarget2;
    }

    public String getDeptTarget3() {
        return deptTarget3;
    }

    public void setDeptTarget3(String deptTarget3) {
        this.deptTarget3 = deptTarget3;
    }

    public String getDeptTarget4() {
        return deptTarget4;
    }

    public void setDeptTarget4(String deptTarget4) {
        this.deptTarget4 = deptTarget4;
    }

    @Override
    public String toString() {
        return "DeptTarget{" +
                "companyTargetId='" + companyTargetId + '\'' +
                ", deptTargetTitle='" + deptTargetTitle + '\'' +
                ", deptTargetYear='" + deptTargetYear + '\'' +
                ", deptTargetTimeSlot='" + deptTargetTimeSlot + '\'' +
                ", deptTargetType='" + deptTargetType + '\'' +
                ", companyId='" + companyId + '\'' +
                ", deptId='" + deptId + '\'' +
                ", userId='" + userId + '\'' +
                ", deptTargetContent='" + deptTargetContent + '\'' +
                ", deptTargetAmount='" + deptTargetAmount + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", userName='" + userName + '\'' +
                ", coName='" + coName + '\'' +
                ", deptName='" + deptName + '\'' +
                ", stertsignDate='" + stertsignDate + '\'' +
                ", endsignDate='" + endsignDate + '\'' +
                ", deptTarget1='" + deptTarget1 + '\'' +
                ", deptTarget2='" + deptTarget2 + '\'' +
                ", deptTarget3='" + deptTarget3 + '\'' +
                ", deptTarget4='" + deptTarget4 + '\'' +
                '}';
    }
}
