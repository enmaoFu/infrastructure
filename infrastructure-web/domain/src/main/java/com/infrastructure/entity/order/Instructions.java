package com.infrastructure.entity.order;

import com.infrastructure.common.entity.BaseEntity;
import com.infrastructure.entity.system.SysFile;

import java.io.Serializable;
import java.util.Date;

public class Instructions extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = -4843899260203632316L;

    private String id;

    private String content;

    private Date createtime;

    private String createuser;

    private String companyid;

    private String state;

    private String usId;

    private String usName;

    private String userName;

    private String coName;

    private String deptId;

    private String sendName;//发出者

    private Date feedCreateTime;//转发时间

    private String feedName;//转发对象

    private String feedId;//转发对象编号

    private String objUserId;

    private String objId;

    private String attachment;//附件ID

    private String originalFileName;//附件名

    private SysFile sysFile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime == null ? null : createtime;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid == null ? null : companyid.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getUsId() {
        return usId;
    }

    public void setUsId(String usId) {
        this.usId = usId;
    }

    public String getUsName() {
        return usName;
    }

    public void setUsName(String usName) {
        this.usName = usName;
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

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public Date getFeedCreateTime() {
        return feedCreateTime;
    }

    public void setFeedCreateTime(Date feedCreateTime) {
        this.feedCreateTime = feedCreateTime;
    }

    public String getFeedName() {
        return feedName;
    }

    public void setFeedName(String feedName) {
        this.feedName = feedName;
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getObjUserId() {
        return objUserId;
    }

    public void setObjUserId(String objUserId) {
        this.objUserId = objUserId;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public SysFile getSysFile() {
        return sysFile;
    }

    public void setSysFile(SysFile sysFile) {
        this.sysFile = sysFile;
    }
}