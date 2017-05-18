package com.infrastructure.entity.main;

import com.infrastructure.common.entity.BaseEntity;
import com.infrastructure.entity.system.SysFile;

import java.io.Serializable;

/**
 * 通知公告/会议通知
 * Created by 谭永强 on 2016/5/11.
 */
public class AnnOrMeet extends BaseEntity<String> implements Serializable{

    private String title;//标题
    private String content;//内容
    private String releaseTime;//发布时间
    private String companyId;//所属公司
    private String userId;//用户ID

    private String parentId;//最上级公司ID
    private String msId;//会议附表ID
    private String flg;//会议查阅状态 y/N 已查阅/未查阅
    private String aType;//类型 1通知公告2会议管理
    private String isView;//是否已查看
    private String abbreviation;//公司简称
    private SysFile sysFile;

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

    public String getaType() {
        return aType;
    }

    public void setaType(String aType) {
        this.aType = aType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMsId() {
        return msId;
    }

    public void setMsId(String msId) {
        this.msId = msId;
    }

    public String getFlg() {
        return flg;
    }

    public void setFlg(String flg) {
        this.flg = flg;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
