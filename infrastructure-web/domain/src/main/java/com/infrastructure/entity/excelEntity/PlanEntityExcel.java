package com.infrastructure.entity.excelEntity;


import java.io.Serializable;

/**
 * @Filename PlanEntityExcel.java
 * @Description
 * @Version 1.0
 * @Author suyl
 * @History <li>Author: suyl</li>
 * <li>Date: 2016-5-11</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class PlanEntityExcel implements Serializable {

    private static final long serialVersionUID =  -8219646580401396962L;

    /**
     * 工作内容
     */
    private String detailPlanContent;

    /**
     * 完成标准
     */
    private String detailPlanStandard;

    /**
     * 配合部门
     */
    private String planCoordination;

    /**
     * 重点工作
     */
    private String keyWork;

    /**
     * 完成时间
     */
    private String planCompletionTime;

    /**
     * 完成状态
     */
    private String completionStatus;

    /**
     * 完成情况
     */
    private String detailCompletionStatus;

    /**
     * 实际完成时间
     */
    private String actualCompletionTime;

    /**
     * 创建人
     */
    private String userName;

    /**
     * 创建时间
     */
    private String createTime;

    public String getDetailPlanContent() {
        return detailPlanContent;
    }

    public void setDetailPlanContent(String detailPlanContent) {
        this.detailPlanContent = detailPlanContent;
    }

    public String getDetailPlanStandard() {
        return detailPlanStandard;
    }

    public void setDetailPlanStandard(String detailPlanStandard) {
        this.detailPlanStandard = detailPlanStandard;
    }

    public String getPlanCoordination() {
        return planCoordination;
    }

    public void setPlanCoordination(String planCoordination) {
        this.planCoordination = planCoordination;
    }

    public String getKeyWork() {
        return keyWork;
    }

    public void setKeyWork(String keyWork) {
        this.keyWork = keyWork;
    }

    public String getPlanCompletionTime() {
        return planCompletionTime;
    }

    public void setPlanCompletionTime(String planCompletionTime) {
        this.planCompletionTime = planCompletionTime;
    }

    public String getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(String completionStatus) {
        this.completionStatus = completionStatus;
    }

    public String getDetailCompletionStatus() {
        return detailCompletionStatus;
    }

    public void setDetailCompletionStatus(String detailCompletionStatus) {
        this.detailCompletionStatus = detailCompletionStatus;
    }

    public String getActualCompletionTime() {
        return actualCompletionTime;
    }

    public void setActualCompletionTime(String actualCompletionTime) {
        this.actualCompletionTime = actualCompletionTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
