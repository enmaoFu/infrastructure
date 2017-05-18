package com.infrastructure.entity.order;


import com.infrastructure.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class InstFeedback extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = -4843899260203632316L;

    private String id;

    private String instid;

    private String fcontent;

    private Date createtime;

    private String userid;

    private String userName;//创建人

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

    public String getFcontent() {
        return fcontent;
    }

    public void setFcontent(String fcontent) {
        this.fcontent = fcontent == null ? null : fcontent.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime == null ? null : createtime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}