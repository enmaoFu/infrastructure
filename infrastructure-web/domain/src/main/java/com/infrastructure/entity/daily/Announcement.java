package com.infrastructure.entity.daily;

import com.infrastructure.common.entity.BaseEntity;
import com.infrastructure.entity.system.SysFile;

import java.io.Serializable;

/**
 * Created by 谭永强 on 2016/4/26.
 */
public class Announcement extends BaseEntity<String> implements Serializable{

    private static final long serialVersionUID = 5976877820069382584L;

    private String title;//标题
    private String content;//内容
    private String attachment;//附件
    private String arange;//发布范围
    private String releaseTime;//发布时间
    private String companyId;//所属公司
    private String createTime;//创建时间
    private String userId;//当前用户ID

    private String modelName;//模块名称
    private String isView;//是否已查看
    private String abbreviation;//公司简称
    private SysFile sysFile;
    private String model;

    public Announcement() {
    }

    public Announcement(String title, String content, String attachment, String arange, String releaseTime, String companyId, String createTime, String userId, String isView, String abbreviation, SysFile sysFile, String model) {
        this.title = title;
        this.content = content;
        this.attachment = attachment;
        this.arange = arange;
        this.releaseTime = releaseTime;
        this.companyId = companyId;
        this.createTime = createTime;
        this.userId = userId;
        this.isView = isView;
        this.abbreviation = abbreviation;
        this.sysFile = sysFile;
        this.model = model;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getArange() {
        return arange;
    }

    public void setArange(String arange) {
        this.arange = arange;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsView() {
        return isView;
    }

    public void setIsView(String isView) {
        this.isView = isView;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public SysFile getSysFile() {
        return sysFile;
    }

    public void setSysFile(SysFile sysFile) {
        this.sysFile = sysFile;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", attachment='" + attachment + '\'' +
                ", arange='" + arange + '\'' +
                ", releaseTime='" + releaseTime + '\'' +
                ", companyId='" + companyId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", userId='" + userId + '\'' +
                ", isView='" + isView + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", sysFile=" + sysFile +
                ", model='" + model + '\'' +
                '}';
    }
}
