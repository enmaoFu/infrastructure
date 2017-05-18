package com.infrastructure.entity.culture;

import com.infrastructure.common.entity.BaseEntity;

import java.io.Serializable;

/**
 * 群组验证消息
 * Created by tyq on 16/3/12.
 */
public class GroupsVerification extends BaseEntity<String> implements Serializable{

    private static final long serialVersionUID = -1810227005814183311L;

    private String groupId;//群组ID
    private String applyUser;//申请人ID
    private String information;//验证消息
    private String createTime;//创建时间
    private String isPass = "P";//是否通过Y/N/P 通过/拒绝/待处理

    private String userName;//申请人名称
    private String groupName;//群租名称

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsPass() {
        return isPass;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
