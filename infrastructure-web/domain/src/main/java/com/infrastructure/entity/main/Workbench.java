package com.infrastructure.entity.main;


import com.infrastructure.common.entity.BaseEntity;

import java.io.Serializable;

/**
 * 工作台
 * Created by 谭永强 on 2016/5/19.
 */
public class Workbench extends BaseEntity<String> implements Serializable{

    private String content;//内容
    private String mark;//标识符
    private String userId;//用户ID
    private String planTime;//计划时间
    private String companyId;//公司ID
    private String apId;//审批ID
    private String aocId;
    private String state;//状态
    private String fileId;//附件ID
    private String fileName;//附件名称

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlanTime() {
        return planTime;
    }

    public void setPlanTime(String planTime) {
        this.planTime = planTime;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getApId() {
        return apId;
    }

    public void setApId(String apId) {
        this.apId = apId;
    }

    public String getAocId() {
        return aocId;
    }

    public void setAocId(String aocId) {
        this.aocId = aocId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
