package com.infrastructure.entity.targetManage;


import com.infrastructure.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 公司目标
 * Created by suyl on 2016/4/1.
 */
public class CompanyTarget extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = -4843899260203632316L;
    /**
     * 标题
     */
    private String targetTitle;
    /**
     * 年份
     */
    private String targetYear;
    /**
     * 目标时间段(slot_year:年,slot_half_year:半年)
     */
    private String targetTimeSlot;
    /**
     * 目标类型(type_economic_goals:经济目标,type_management_objectives:管理目标)
     */
    private String targetType;
    /**
     * 企业编号
     */
    private String companyId;
    /**
     * 创建人
     */
    private String userId;
    /**
     * 目标内容
     */
    private String targetContent;
    /**
     * 金额
     */
    private String targetAmount;
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
     * 开始创建时间
     */
    private String stertsignDate;

    /**
     * 结束创建时间
     */
    private String endsignDate;

    /**
     * coTarget1
     */
    private String coTarget1;

    /**
     * coTarget2
     */
    private String coTarget2;

    /**
     * coTarget3
     */
    private String coTarget3;

    /**
     * coTarget4
     */
    private String coTarget4;

    public CompanyTarget() {
        super();
    }

    public CompanyTarget(String targetTitle, String targetYear, String targetTimeSlot, String targetType, String companyId, String userId, String targetContent, String targetAmount, String remark, Date createTime, String userName, String coName, String stertsignDate, String endsignDate, String coTarget1, String coTarget2, String coTarget3, String coTarget4) {
        this.targetTitle = targetTitle;
        this.targetYear = targetYear;
        this.targetTimeSlot = targetTimeSlot;
        this.targetType = targetType;
        this.companyId = companyId;
        this.userId = userId;
        this.targetContent = targetContent;
        this.targetAmount = targetAmount;
        this.remark = remark;
        this.createTime = createTime;
        this.userName = userName;
        this.coName = coName;
        this.stertsignDate = stertsignDate;
        this.endsignDate = endsignDate;
        this.coTarget1 = coTarget1;
        this.coTarget2 = coTarget2;
        this.coTarget3 = coTarget3;
        this.coTarget4 = coTarget4;
    }

    public String getTargetTitle() {
        return targetTitle;
    }

    public void setTargetTitle(String targetTitle) {
        this.targetTitle = targetTitle;
    }

    public String getTargetYear() {
        return targetYear;
    }

    public void setTargetYear(String targetYear) {
        this.targetYear = targetYear;
    }

    public String getTargetTimeSlot() {
        return targetTimeSlot;
    }

    public void setTargetTimeSlot(String targetTimeSlot) {
        this.targetTimeSlot = targetTimeSlot;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTargetContent() {
        return targetContent;
    }

    public void setTargetContent(String targetContent) {
        this.targetContent = targetContent;
    }

    public String getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(String targetAmount) {
        this.targetAmount = targetAmount;
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

    public String getCoTarget1() {
        return coTarget1;
    }

    public void setCoTarget1(String coTarget1) {
        this.coTarget1 = coTarget1;
    }

    public String getCoTarget2() {
        return coTarget2;
    }

    public void setCoTarget2(String coTarget2) {
        this.coTarget2 = coTarget2;
    }

    public String getCoTarget3() {
        return coTarget3;
    }

    public void setCoTarget3(String coTarget3) {
        this.coTarget3 = coTarget3;
    }

    public String getCoTarget4() {
        return coTarget4;
    }

    public void setCoTarget4(String coTarget4) {
        this.coTarget4 = coTarget4;
    }

    @Override
    public String toString() {
        return "CompanyTarget{" +
                "targetTitle='" + targetTitle + '\'' +
                ", targetYear='" + targetYear + '\'' +
                ", targetTimeSlot='" + targetTimeSlot + '\'' +
                ", targetType='" + targetType + '\'' +
                ", companyId='" + companyId + '\'' +
                ", userId='" + userId + '\'' +
                ", targetContent='" + targetContent + '\'' +
                ", targetAmount='" + targetAmount + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", userName='" + userName + '\'' +
                ", coName='" + coName + '\'' +
                ", stertsignDate='" + stertsignDate + '\'' +
                ", endsignDate='" + endsignDate + '\'' +
                ", coTarget1='" + coTarget1 + '\'' +
                ", coTarget2='" + coTarget2 + '\'' +
                ", coTarget3='" + coTarget3 + '\'' +
                ", coTarget4='" + coTarget4 + '\'' +
                '}';
    }
}
