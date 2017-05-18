package com.infrastructure.entity.main;


import com.infrastructure.common.entity.BaseEntity;

import java.io.Serializable;

/**
 * Created by 谭永强 on 2016/4/27.
 */
public class EventDay extends BaseEntity<String> implements Serializable{
    private static final long serialVersionUID = 628449725731344163L;

    private String title;//事件
    private String day;//事件日期
    private String dtime;//时间
    private String userId;//创建人
    private String createTime;//创建时间

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDtime() {
        return dtime;
    }

    public void setDtime(String dtime) {
        this.dtime = dtime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
