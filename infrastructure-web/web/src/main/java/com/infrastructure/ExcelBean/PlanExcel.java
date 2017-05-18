package com.infrastructure.ExcelBean;

import com.infrastructure.common.ExcelAnnotation;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Filename PlanExcel.java
 * @Description
 * @Version 1.0
 * @Author suyl
 * @History <li>Author: suyl</li>
 * <li>Date: 2016-5-11</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@Component
public class PlanExcel {

    @ExcelAnnotation(name = "所属部门", isMergedCell = true, id = 0, width = 18, rank = 1)
    private String deptName;

    @ExcelAnnotation(name = "工作内容", id = 1,mergedCell = true, width = 22, rank = 1)
    private List<String> detailPlanContent;

    @ExcelAnnotation(name = "完成标准", id = 2, width = 18, rank = 1)
    private List<String> detailPlanStandard;

    @ExcelAnnotation(name = "配合部门", id = 3, width = 16, rank = 1)
    private List<String> planCoordination;

    @ExcelAnnotation(name = "重点工作", id = 4, width = 8, rank = 1)
    private List<String> keyWork;

    @ExcelAnnotation(name = "完成时间", id = 5, width = 16, rank = 1)
    private List<String> planCompletionTime;

    @ExcelAnnotation(name = "完成状态", id = 6, width = 16, rank = 1)
    private List<String> completionStatus;

    @ExcelAnnotation(name = "完成情况", id = 7, width = 16, rank = 1)
    private List<String> detailCompletionStatus;

    @ExcelAnnotation(name = "实际完成时间", id = 8, width = 16, rank = 1)
    private List<String> actualCompletionTime;

    @ExcelAnnotation(name = "执行人", id = 9,isMergedCell = true,  width = 16, rank = 1)
    private String userName;

    @ExcelAnnotation(name = "创建时间", id = 10, width = 16, rank = 1)
    private List<String> createTime;

    public PlanExcel() {
    }

    public PlanExcel(String deptName, List<String> detailPlanContent, List<String> detailPlanStandard, List<String> planCoordination, List<String> keyWork, List<String> planCompletionTime, List<String> completionStatus, List<String> detailCompletionStatus, List<String> actualCompletionTime, String userName, List<String> createTime) {
        this.deptName = deptName;
        this.detailPlanContent = detailPlanContent;
        this.detailPlanStandard = detailPlanStandard;
        this.planCoordination = planCoordination;
        this.keyWork = keyWork;
        this.planCompletionTime = planCompletionTime;
        this.completionStatus = completionStatus;
        this.detailCompletionStatus = detailCompletionStatus;
        this.actualCompletionTime = actualCompletionTime;
        this.userName = userName;
        this.createTime = createTime;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public List<String> getDetailPlanContent() {
        return detailPlanContent;
    }

    public void setDetailPlanContent(List<String> detailPlanContent) {
        this.detailPlanContent = detailPlanContent;
    }

    public List<String> getDetailPlanStandard() {
        return detailPlanStandard;
    }

    public void setDetailPlanStandard(List<String> detailPlanStandard) {
        this.detailPlanStandard = detailPlanStandard;
    }

    public List<String> getPlanCoordination() {
        return planCoordination;
    }

    public void setPlanCoordination(List<String> planCoordination) {
        this.planCoordination = planCoordination;
    }

    public List<String> getKeyWork() {
        return keyWork;
    }

    public void setKeyWork(List<String> keyWork) {
        this.keyWork = keyWork;
    }

    public List<String> getPlanCompletionTime() {
        return planCompletionTime;
    }

    public void setPlanCompletionTime(List<String> planCompletionTime) {
        this.planCompletionTime = planCompletionTime;
    }

    public List<String> getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(List<String> completionStatus) {
        this.completionStatus = completionStatus;
    }

    public List<String> getDetailCompletionStatus() {
        return detailCompletionStatus;
    }

    public void setDetailCompletionStatus(List<String> detailCompletionStatus) {
        this.detailCompletionStatus = detailCompletionStatus;
    }

    public List<String> getActualCompletionTime() {
        return actualCompletionTime;
    }

    public void setActualCompletionTime(List<String> actualCompletionTime) {
        this.actualCompletionTime = actualCompletionTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getCreateTime() {
        return createTime;
    }

    public void setCreateTime(List<String> createTime) {
        this.createTime = createTime;
    }
}
