package com.infrastructure.entity.daily;

import com.infrastructure.common.entity.BaseEntity;
import com.infrastructure.entity.system.SysFile;

import java.io.Serializable;

/**
 * 制度
 * Created by 谭永强 on 2016/4/25.
 */
public class Regime extends BaseEntity<String> implements Serializable{

    private static final long serialVersionUID = -3166584415855051835L;
    private String content;//内容
    private String attachment;//附件
    private String model;//所属模块
    private String companyId;//所属公司ID
    private String createTime;//创建时间
    private String createUser;//创建人ID

    private SysFile sysFile;
    private String modelName;//模块名称
    private String beginTime;
    private String endTime;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public SysFile getSysFile() {
        return sysFile;
    }

    public void setSysFile(SysFile sysFile) {
        this.sysFile = sysFile;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
