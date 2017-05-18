package com.infrastructure.entity.culture;


import com.infrastructure.common.entity.BaseEntity;

import java.io.Serializable;

/**
 * 群组-成员
 * User: 谭永强
 * Date: 2016/2/15
 * Time: 17:21
 */
public class GroupsUser extends BaseEntity<String> implements Serializable{

    private static final long serialVersionUID = 6904828671434232028L;

    private String groupId;//群组ID
    private String userId;//用户ID
    private String isDelete = "N";//删除状态 Y/N

    private String userName;//用户姓名

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }
}
