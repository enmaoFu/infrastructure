package com.infrastructure.entity.culture;


import com.infrastructure.common.entity.BaseEntity;

import java.io.Serializable;

/**
 * User: 谭永强
 * Date: 2016/2/19
 * Time: 12:26
 */
public class GroupsUserMessages extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = -5088594294606014094L;

    private String group_user_id;//群组员工ID
    private String content;//内容
    private String sendDate;//发送时间
    private String userName;//用户名称

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGroup_user_id() {
        return group_user_id;
    }

    public void setGroup_user_id(String group_user_id) {
        this.group_user_id = group_user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }
}
