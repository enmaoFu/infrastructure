package com.infrastructure.entity.order;


import com.infrastructure.common.entity.BaseEntity;

import java.io.Serializable;

public class InstReceiveObj extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = -4843899260203632316L;

    private String id;

    private String instid;

    private String userid;

    private String parentid;

    private String forward;

    private String userName;//接收人

    private String instName;//发出指令人

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getInstid() {
        return instid;
    }

    public void setInstid(String instid) {
        this.instid = instid == null ? null : instid.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid == null ? null : parentid.trim();
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward == null ? null : forward.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }
}